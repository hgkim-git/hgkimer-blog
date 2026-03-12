// 전체 선택 체크박스
const selectAllCheckbox = document.getElementById('selectAll');
const rowCheckboxes = document.querySelectorAll('.row-checkbox');
const selectedCount = document.getElementById('selectedCount');
const bulkDeleteBtn = document.getElementById('bulkDeleteBtn');

selectAllCheckbox.addEventListener('change', () => {
  rowCheckboxes.forEach(checkbox => {
    checkbox.checked = selectAllCheckbox.checked;
  });
  updateSelectedCount();
});

rowCheckboxes.forEach(checkbox => {
  checkbox.addEventListener('change', () => {
    updateSelectAllState();
    updateSelectedCount();
  });
});

function updateSelectAllState() {
  const checkedCount = Array.from(rowCheckboxes).filter(
      cb => cb.checked).length;
  selectAllCheckbox.checked = checkedCount === rowCheckboxes.length;
  selectAllCheckbox.indeterminate = checkedCount > 0 && checkedCount
      < rowCheckboxes.length;
}

function updateSelectedCount() {
  const checkedCount = Array.from(rowCheckboxes).filter(
      cb => cb.checked).length;

  if (checkedCount > 0) {
    selectedCount.textContent = `${checkedCount}개 선택됨`;
    bulkDeleteBtn.style.display = 'block';
  } else {
    selectedCount.textContent = '';
    bulkDeleteBtn.style.display = 'none';
  }
}

// 일괄 삭제
bulkDeleteBtn.addEventListener('click', () => {
  const selectedIds = Array.from(rowCheckboxes)
  .filter(cb => cb.checked)
  .map(cb => cb.dataset.id);

  if (confirm(`선택한 ${selectedIds.length}개의 게시글을 삭제하시겠습니까?`)) {
    console.log('삭제할 게시글 ID:', selectedIds);
    alert('게시글이 삭제되었습니다.');
  }
});

// 개별 삭제
function deletePost(id) {
  if (confirm('이 게시글을 삭제하시겠습니까?')) {
    console.log('삭제할 게시글 ID:', id);
    alert('게시글이 삭제되었습니다.');
  }
}

// 수정
function editPost(id) {
  console.log('수정할 게시글 ID:', id);
  window.location.href = 'admin-post-editor.html';
}

// 필터 초기화
const resetBtn = document.getElementById('resetBtn');
const searchInput = document.getElementById('searchInput');
const categoryFilter = document.getElementById('categoryFilter');
const statusFilter = document.getElementById('statusFilter');

resetBtn.addEventListener('click', () => {
  searchInput.value = '';
  categoryFilter.value = '';
  statusFilter.value = '';
  console.log('필터 초기화');
});

// Window에 함수 노출
window.deletePost = deletePost;
window.editPost = editPost;