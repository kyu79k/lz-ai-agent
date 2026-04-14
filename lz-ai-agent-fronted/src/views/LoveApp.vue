<template>
  <div class="chat-container">
    <div class="chat-header">
      <div class="header-left">
        <button class="back-btn" @click="goBack">
          &larr;
        </button>
        <div class="app-icon love-icon"></div>
        <h2>AI 恋爱智能体</h2>
      </div>
      <div class="chat-id">聊天室 ID: {{ chatId }}</div>
    </div>
    <div class="chat-messages" ref="messagesContainer">
      <div v-for="(message, index) in messages" :key="index" :class="['message', message.isUser ? 'user-message' : 'ai-message']">
        <div v-if="!message.isUser" class="avatar ai-avatar">
          <div class="avatar-icon love-avatar"></div>
        </div>
        <div class="message-content">{{ message.content }}</div>
        <div v-if="message.isUser" class="avatar user-avatar">
          <div class="avatar-icon user-avatar-icon"></div>
        </div>
      </div>
    </div>
    <div class="chat-input">
      <input
        v-model="inputMessage"
        type="text"
        placeholder="输入消息..."
        @keyup.enter="sendMessage"
        class="input"
      />
      <button @click="sendMessage" :disabled="isLoading" class="btn btn-primary">
        {{ isLoading ? '发送中...' : '发送' }}
      </button>
    </div>
    <div class="footer">
      <p>AI 智能体</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { doChatWithLoveAppSse } from '../api'

const router = useRouter()

function goBack() {
  router.push('/')
}

const messages = ref([])
const inputMessage = ref('')
const chatId = ref('')
const isLoading = ref(false)
const messagesContainer = ref(null)

function generateChatId() {
  return 'chat_' + Math.random().toString(36).substr(2, 9)
}

function sendMessage() {
  if (!inputMessage.value.trim() || isLoading.value) return

  const userMessage = inputMessage.value
  messages.value.push({ content: userMessage, isUser: true })
  inputMessage.value = ''
  scrollToBottom()

  isLoading.value = true
  const eventSource = doChatWithLoveAppSse(userMessage, chatId.value)

  let aiResponse = ''
  const aiMessageIndex = messages.value.length
  messages.value.push({ content: '', isUser: false })

  eventSource.onmessage = (event) => {
    aiResponse += event.data
    messages.value[aiMessageIndex].content = aiResponse
    scrollToBottom()
  }

  eventSource.onerror = () => {
    eventSource.close()
    isLoading.value = false
  }

  eventSource.onclose = () => {
    isLoading.value = false
  }
}

function scrollToBottom() {
  setTimeout(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  }, 100)
}

onMounted(() => {
  chatId.value = generateChatId()
  messages.value.push({ content: '你好！我是 AI 恋爱智能体，有什么可以帮助你的吗？', isUser: false })
  scrollToBottom()
})

watch(messages, () => {
  scrollToBottom()
}, { deep: true })
</script>

<style scoped>
.chat-container {
  max-width: 1000px;
  margin: 0 auto;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f5f5f5;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
}

.chat-header {
  padding: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 15px;
}

.back-btn {
  background: rgba(255, 255, 255, 0.2);
  border: none;
  color: white;
  font-size: 1.5rem;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.back-btn:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: scale(1.1);
}

.app-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 0 15px rgba(255, 255, 255, 0.3);
}

.love-icon {
  background: linear-gradient(45deg, #ff6b6b, #ee5a6f);
}

.chat-header h2 {
  margin: 0;
  font-size: 1.5rem;
  font-weight: 700;
}

.chat-id {
  font-size: 14px;
  opacity: 0.8;
  background: rgba(255, 255, 255, 0.2);
  padding: 5px 10px;
  border-radius: 15px;
}

.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background: #f9f9f9;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.message {
  max-width: 70%;
  display: flex;
  align-items: flex-start;
  gap: 10px;
}

.ai-message {
  align-self: flex-start;
  flex-direction: row;
}

.user-message {
  align-self: flex-end;
  flex-direction: row-reverse;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  flex-shrink: 0;
  margin-top: 5px;
}

.avatar-icon {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  color: white;
}

.love-avatar {
  background: linear-gradient(45deg, #ff6b6b, #ee5a6f);
  box-shadow: 0 0 10px rgba(255, 107, 107, 0.5);
}

.user-avatar-icon {
  background: linear-gradient(45deg, #667eea, #764ba2);
  box-shadow: 0 0 10px rgba(102, 126, 234, 0.5);
}

.message-content {
  padding: 12px 18px;
  border-radius: 18px;
  line-height: 1.5;
  word-wrap: break-word;
  text-align: left;
  max-width: calc(100% - 50px);
}

.ai-message .message-content {
  background: white;
  border-bottom-left-radius: 4px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
  color: #333;
}

.user-message .message-content {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-bottom-right-radius: 4px;
  box-shadow: 0 2px 5px rgba(102, 126, 234, 0.2);
}

.chat-input {
  padding: 20px;
  background: white;
  border-top: 1px solid #e6e6e6;
  display: flex;
  gap: 10px;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
}

.chat-input .input {
  flex: 1;
  padding: 12px 15px;
  border: 1px solid #ddd;
  border-radius: 25px;
  font-size: 16px;
  transition: border-color 0.3s ease;
}

.chat-input .input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.chat-input .btn {
  padding: 0 25px;
  font-size: 16px;
  font-weight: 500;
}

.footer {
  background: #333;
  color: white;
  padding: 15px;
  text-align: center;
  font-size: 14px;
  border-top: 1px solid #444;
}

.footer a {
  color: #667eea;
  text-decoration: none;
  transition: color 0.3s ease;
}

.footer a:hover {
  color: #764ba2;
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .chat-container {
    max-width: 100%;
    height: 100vh;
  }
  
  .chat-header {
    padding: 15px;
  }
  
  .chat-header h2 {
    font-size: 1.2rem;
  }
  
  .app-icon {
    width: 40px;
    height: 40px;
  }
  
  .chat-messages {
    padding: 15px;
  }
  
  .message {
    max-width: 80%;
  }
  
  .message-content {
    padding: 10px 15px;
    font-size: 14px;
  }
  
  .chat-input {
    padding: 15px;
  }
  
  .chat-input .input {
    padding: 10px 12px;
    font-size: 14px;
  }
  
  .chat-input .btn {
    padding: 0 20px;
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .chat-header {
    padding: 12px;
  }
  
  .header-left {
    gap: 10px;
  }
  
  .chat-header h2 {
    font-size: 1.1rem;
  }
  
  .chat-id {
    font-size: 12px;
    padding: 3px 8px;
  }
  
  .chat-messages {
    padding: 10px;
  }
  
  .message {
    max-width: 85%;
  }
  
  .avatar {
    width: 35px;
    height: 35px;
  }
  
  .message-content {
    padding: 8px 12px;
    font-size: 13px;
  }
  
  .chat-input {
    padding: 10px;
  }
  
  .chat-input .input {
    padding: 8px 10px;
    font-size: 13px;
  }
  
  .chat-input .btn {
    padding: 0 15px;
    font-size: 13px;
  }
}
</style>
