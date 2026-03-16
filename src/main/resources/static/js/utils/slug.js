const defaultMaxLength = 50;
const maxLength = {
  tag: 50,
  category: 100,
  post: 250,
};

export function slugify(text, type) {
  return text.toString()
  .toLowerCase()
  .replace(/\s+/g, '-')
  .replace(/[^\w\-]+/g, '')
  .replace(/--+/g, '-')
  .replace(/^-+/, '')
  .replace(/-+$/, '')
  .substring(0, maxLength[type] || defaultMaxLength);
}

export function normalizeSlugInput(input, type) {
  return input.toString()
  .toLowerCase()
  .replace(/[^a-z0-9-]/g, '')
  .replace(/--+/g, '-')
  .substring(0, maxLength[type] || defaultMaxLength);
}