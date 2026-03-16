import {goTo} from "/js/utils/nav.js";

const searchInput = document.getElementById('searchInput');
searchInput.addEventListener('keyup', (e) => {
  if (e.key === 'Enter') {
    e.preventDefault();
    e.stopPropagation();
    search();
  }
});
// 검색 조건 유지
(() => {
  const url = new URL(window.location.href);
  if (url.searchParams.has('keyword')) {
    searchInput.value = url.searchParams.get('keyword');
  }
})();

function search() {
  const options = {
    cache: false,
    clearParams: true,
  };
  const params = {};
  const keyword = searchInput.value.trim();
  if (keyword) {
    params.keyword = keyword;
  }
  Object.assign(options, {params});
  goTo(options);
}