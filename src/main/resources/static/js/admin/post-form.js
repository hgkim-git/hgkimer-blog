import {slugify} from "/js/utils/slug.js";
import {api} from "/js/utils/api.js";

function initEasyMDE() {
  const options = {
    element: document.getElementById('markdownEditor'),
    autoDownloadFontAwesome: false,
    spellChecker: false,
    indentWithTabs: true,
    placeholder: '마크다운으로 내용을 작성하세요...',
    status: false,
    toolbar: [
      'bold', 'italic', 'heading', '|',
      'quote', 'unordered-list', 'ordered-list', '|',
      'link', 'image', 'table', '|',
      'preview', 'side-by-side', 'fullscreen', '|',
      'guide'
    ]
  }
  return new EasyMDE(options);
}

const easyMDE = initEasyMDE();
easyMDE.codemirror.on('change', () => {
  updateStats();
});

let originalTags = [];
try {
  originalTags = await api.get('/api/tags');
} catch (e) {
  console.error(e.message);
  originalTags = [];
}

const tags = [];

// 이벤트 바인딩
document.getElementById('postSlug').addEventListener('input', countSlug);
document.getElementById('postSlug').addEventListener('compositionend',
    countSlug);
document.getElementById('postSummary').addEventListener('input', countSummary);
document.getElementById('postTitle').addEventListener('blur', createAutoSlug);
document.getElementById('tagInput').addEventListener('keydown', onTagInput);
document.getElementById('cancelBtn').addEventListener('click', onCancel);
document.getElementById('postForm').addEventListener('submit', (e) => {
  e.preventDefault();
  savePost();
});
document.getElementById('tagsContainer').addEventListener('click', (e) => {
  const removeBtn = e.target.closest('.tag-remove');
  if (removeBtn) {
    removeTag(parseInt(removeBtn.parentElement.dataset.index));
  }
});

// Slug 글자 수 카운터
function countSlug(e) {
  if (e.isComposing) {
    return;
  }
  const slugCount = document.getElementById('slugCount');
  e.target.value = e.target.value
  .toLowerCase()
  .replace(/[^a-z0-9-]/g, '')
  .replace(/--+/g, '-'); // 연속된 하이픈 제거
  slugCount.textContent = e.target.value.length;
  markExceeded(e.target.value, 250, slugCount);
}

// Summary 글자 수 카운터
function countSummary(e) {
  const summaryCount = document.getElementById('summaryCount');
  summaryCount.textContent = e.target.value.length;
  markExceeded(e.target.value, 500, summaryCount);
}

// 제목에서 Slug 자동 생성 (Slug가 비어있을 때만)
function createAutoSlug(e) {
  const title = e.target.value;
  const slugInput = document.getElementById('postSlug');
  const slugCount = document.getElementById('slugCount');
  if (!slugInput.value && title) {
    const autoSlug = slugify(title, 'post');
    slugInput.value = autoSlug;
    slugCount.textContent = autoSlug.length;
  }
}

// 태그 입력 처리
function onTagInput(e) {
  if (e.key === 'Enter' || e.key === ',') {
    e.preventDefault();
    addTag();
  } else if (e.key === 'Backspace' && e.target.value === '' && tags.length
      > 0) {
    removeTag(tags.length - 1);
  }
}

// 취소 버튼
function onCancel() {
  if (confirm('작성 중인 내용이 저장되지 않을 수 있습니다. 정말 취소하시겠습니까?')) {
    window.location.href = '/admin/posts';
  }
}

// 태그 추가
function addTag() {
  const tagInput = document.getElementById('tagInput');
  const value = tagInput.value.trim().replace(/,/g, '');
  const slug = slugify(value, 'tag');
  if (value && slug && !tags.some(t => t.slug === slug)) {
    const tag = {name: value, slug: slug};
    const found = originalTags.find(t => t.slug === tag.slug);
    if (found) {
      tag.id = found.id;
      tag.name = found.name;
      tag.slug = found.slug;
    }
    tags.push(tag);
  }
  tagInput.value = '';
  renderTags();
}

// 태그 제거
function removeTag(index) {
  tags.splice(index, 1);
  renderTags();
}

// 태그 렌더링
function renderTags() {
  const tagInput = document.getElementById('tagInput');

  const tagsContainer = document.getElementById('tagsContainer');
  tagsContainer.replaceChildren(); // 비우기
  tags.forEach((tag, index) => {
    const span = document.createElement('span');
    span.classList.add('tag');
    span.setAttribute('data-id', tag.id ?? 'new');
    span.setAttribute('data-index', index);
    span.textContent = tag.name; // XSS 방지
    const remove = document.createElement('span')
    remove.classList.add('tag-remove');
    span.appendChild(remove);
    tagsContainer.appendChild(span);
  });
  tagsContainer.appendChild(tagInput);
  tagInput.focus();
}

// 통계 업데이트
function updateStats() {
  const content = easyMDE.value();
  document.getElementById('lineCount').textContent = `${content.split(
      '\n').length}줄`;
  document.getElementById('wordCount').textContent = `${content.length}자`;
}

// 게시글 저장
async function savePost() {
  const title = document.getElementById('postTitle').value;
  const slug = document.getElementById('postSlug').value;
  const summary = document.getElementById('postSummary').value;
  const categoryId = document.getElementById('postCategory').value || null;
  const content = easyMDE.value();
  const status = document.querySelector('input[name="status"]:checked').value;

  if (!title || !slug || !content) {
    alert('필수 항목을 모두 입력해주세요.');
    return;
  }
  if (!/^[a-z0-9-]+$/.test(slug)) {
    alert('Slug는 영문 소문자, 숫자, 하이픈(-)만 사용할 수 있습니다.');
    return;
  }
  if (slug.length > 250) {
    alert('Slug는 250자를 초과할 수 없습니다.');
    return;
  }
  if (summary.length > 500) {
    alert('요약은 500자를 초과할 수 없습니다.');
    return;
  }

  // 새 태그를 먼저 모두 생성한 뒤 tagIds 수집
  const tagIds = [];
  for (const tag of tags) {
    if (!tag.id) {
      try {
        const created = await api.post('/api/tags', {
          name: tag.name,
          slug: tag.slug,
        });
        tagIds.push(created.id);
      } catch (error) {
        alert(`태그 '${tag.name}' 생성에 실패했습니다.`);
        return;
      }
    } else {
      tagIds.push(tag.id);
    }
  }

  try {
    await api.post('/api/posts', {
      title,
      categoryId,
      slug,
      summary,
      tagIds,
      content,
      status,
      author: 'hgkimer@gmail.com'
    })
  } catch (error) {
    alert('게시글 저장에 실패했습니다.');
    return;
  }
  alert('게시글이 저장되었습니다.');
  window.location.href = '/admin/posts?_=' + new Date().getTime();
}

function markExceeded(str, limit, elem) {
  elem.style.color = str.length >= limit ? 'var(--color-danger)'
      : 'var(--text-secondary)';
}
