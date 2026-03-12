// 전체 선택
const selectAllCheckbox = document.getElementById('selectAll');
const rowCheckboxes = document.querySelectorAll('.row-checkbox');
const selectedCount = document.getElementById('selectedCount');
const bulkDeleteBtn = document.getElementById('bulkDeleteBtn');

selectAllCheckbox.addEventListener('change', () => {
  rowCheckboxes.forEach(cb => cb.checked = selectAllCheckbox.checked);
  updateSelectedCount();
});

rowCheckboxes.forEach(cb => {
  cb.addEventListener('change', updateSelectedCount);
});

function updateSelectedCount() {
  const count = Array.from(rowCheckboxes).filter(cb => cb.checked).length;
  if (count > 0) {
    selectedCount.textContent = `${count}개 선택됨`;
    bulkDeleteBtn.style.display = 'block';
  } else {
    selectedCount.textContent = '';
    bulkDeleteBtn.style.display = 'none';
  }
}

// 댓글 액션
function approveComment(id) {
  console.log('댓글 승인:', id);
  alert('댓글이 승인되었습니다.');
}

function rejectComment(id) {
  if (confirm('이 댓글을 거부하시겠습니까?')) {
    console.log('댓글 거부:', id);
    alert('댓글이 거부되었습니다.');
  }
}

function deleteComment(id) {
  if (confirm('이 댓글을 삭제하시겠습니까?')) {
    console.log('댓글 삭제:', id);
    alert('댓글이 삭제되었습니다.');
  }
}

// Window에 함수 노출
window.approveComment = approveComment;
window.rejectComment = rejectComment;
window.deleteComment = deleteComment;