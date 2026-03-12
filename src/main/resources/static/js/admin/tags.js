// Slug 자동 생성
const newTagName = document.getElementById('newTagName');
const newTagSlug = document.getElementById('newTagSlug');

newTagName.addEventListener('input', (e) => {
  if (!newTagSlug.value) {
    newTagSlug.value = generateSlug(e.target.value);
  }
});

newTagSlug.addEventListener('input', (e) => {
  e.target.value = e.target.value
  .toLowerCase()
  .replace(/[^a-z0-9-]/g, '')
  .replace(/--+/g, '-')
  .replace(/^-|-$/g, '');
});

// Slug 생성 함수
function generateSlug(text) {
  return text
  .toLowerCase()
  .trim()
  .replace(/[^a-z0-9\s-]/g, '')
  .replace(/\s+/g, '-')
  .replace(/-+/g, '-')
  .replace(/^-|-$/g, '')
  .substring(0, 250);
}

// 태그 추가
document.getElementById('addTagForm').addEventListener('submit', (e) => {
  e.preventDefault();
  const name = newTagName.value.trim();
  const slug = newTagSlug.value.trim();

  if (name && slug) {
    console.log('태그 추가:', {name, slug});
    alert(`태그 "${name}" (${slug})가 추가되었습니다.`);
    newTagName.value = '';
    newTagSlug.value = '';
  }
});

// 태그 수정
function editTag(id) {
  const item = document.querySelector(`.item[data-id="${id}"]`);
  const nameEl = item.querySelector('.item-name');
  const metaEl = item.querySelector('.item-meta');
  const actionsEl = item.querySelector('.item-actions');

  const currentName = nameEl.textContent;
  const currentSlug = metaEl.textContent.match(/Slug: ([^\s]+)/)[1];

  item.classList.add('editing');

  const editForm = document.createElement('div');
  editForm.className = 'edit-form';
  editForm.innerHTML = `
                <input 
                    type="text" 
                    class="edit-input" 
                    id="editName${id}"
                    value="${currentName}"
                >
                <input 
                    type="text" 
                    class="edit-input" 
                    id="editSlug${id}"
                    value="${currentSlug}"
                    pattern="[a-z0-9-]+"
                >
                <button class="btn btn-success" onclick="saveTag(${id})">저장</button>
                <button class="btn btn-cancel" onclick="cancelEdit(${id})">취소</button>
            `;

  item.querySelector('.item-info').style.display = 'none';
  actionsEl.style.display = 'none';
  item.insertBefore(editForm, actionsEl);

  // Slug 입력 검증
  document.getElementById(`editSlug${id}`).addEventListener('input', (e) => {
    e.target.value = e.target.value
    .toLowerCase()
    .replace(/[^a-z0-9-]/g, '')
    .replace(/--+/g, '-');
  });
}

// 저장
function saveTag(id) {
  const name = document.getElementById(`editName${id}`).value.trim();
  const slug = document.getElementById(`editSlug${id}`).value.trim();

  if (!name || !slug) {
    alert('이름과 Slug를 모두 입력해주세요.');
    return;
  }

  if (!/^[a-z0-9-]+$/.test(slug)) {
    alert('Slug는 영문 소문자, 숫자, 하이픈만 사용할 수 있습니다.');
    return;
  }

  console.log('태그 수정:', {id, name, slug});
  alert(`태그가 "${name}" (${slug})로 수정되었습니다.`);
  location.reload();
}

// 취소
function cancelEdit(id) {
  location.reload();
}

// 삭제
function deleteTag(id) {
  if (confirm('이 태그를 삭제하시겠습니까?\n태그에 연결된 게시글에서 태그가 제거됩니다.')) {
    console.log('태그 삭제:', id);
    alert('태그가 삭제되었습니다.');
  }
}

// Window에 함수 노출
window.editTag = editTag;
window.saveTag = saveTag;
window.cancelEdit = cancelEdit;
window.deleteTag = deleteTag;