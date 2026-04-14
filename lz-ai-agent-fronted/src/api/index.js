const API_BASE_URL = 'http://localhost:8123/api'

export function doChatWithLoveAppSse(message, chatId) {
  return new EventSource(`${API_BASE_URL}/ai/love_app/chat/sse?message=${encodeURIComponent(message)}&chatId=${chatId}`)
}

export function doChatWithManus(message, chatId) {
  return new EventSource(`${API_BASE_URL}/ai/manus/chat?message=${encodeURIComponent(message)}&chatId=${chatId}`)
}
