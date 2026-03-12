export const api = {
  get,
  post,
}

async function get(url) {
  const options = {
    method: 'GET',
    cache: 'no-cache',
  }
  const response = await fetch(url, options);
  if (!response.ok) {
    const error = await response.json();
    alert(`[${response.status}] ${error.message}`);
    throw new Error(error.message);
  }
  return await response.json();
}

async function post(url, data) {
  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  }
  const response = await fetch(url, options);
  if (!response.ok) {
    const error = await response.json();
    alert(`[${response.status}] ${error.message}`);
    throw new Error(error.message);
  }
  return await response.json();
}
