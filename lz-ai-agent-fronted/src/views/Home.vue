<template>
  <div class="home-container">
    <div class="particles" ref="particlesContainer"></div>
    <div class="hero-section">
      <h1 class="neon-text">AI 智能应用中心</h1>
      <p class="subtitle">探索未来科技，体验智能对话</p>
    </div>
    <div class="app-list">
      <router-link to="/love-app" class="app-card">
        <div class="app-icon love-icon"></div>
        <h2 class="app-title">AI 恋爱智能体</h2>
        <p class="app-description">获取专业恋爱建议和聊天</p>
      </router-link>
      <router-link to="/manus" class="app-card">
        <div class="app-icon manus-icon"></div>
        <h2 class="app-title">AI 超级智能体</h2>
        <p class="app-description">获取智能回答和全方位帮助</p>
      </router-link>
    </div>
    <div class="footer">
      <p>AI 智能体</p>
    </div>
  </div>
</template>

<style scoped>
.home-container {
  min-height: 100vh;
  background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: gradientBG 15s ease infinite;
  padding: 40px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.home-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: radial-gradient(circle at 50% 50%, rgba(255, 255, 255, 0.1) 0%, transparent 50%);
  z-index: 0;
}

/* 粒子效果 */
.particles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
  overflow: hidden;
}

.particle {
  position: absolute;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 50%;
  animation: float 6s ease-in-out infinite;
}

@keyframes gradientBG {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

@keyframes float {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
    opacity: 0.7;
  }
  50% {
    transform: translateY(-20px) rotate(180deg);
    opacity: 0.3;
  }
}

.hero-section {
  text-align: center;
  margin-bottom: 60px;
  position: relative;
  z-index: 1;
}

.neon-text {
  font-size: 4rem;
  font-weight: 800;
  background: linear-gradient(45deg, #667eea, #764ba2, #f093fb, #f5576c);
  background-size: 400% 400%;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  animation: gradient 8s ease infinite;
  margin-bottom: 20px;
  text-shadow: 0 0 20px rgba(102, 126, 234, 0.5);
}

.subtitle {
  font-size: 1.5rem;
  color: #a8b2d1;
  margin-bottom: 40px;
}

.app-list {
  display: flex;
  gap: 40px;
  justify-content: center;
  flex-wrap: wrap;
  position: relative;
  z-index: 1;
  margin-bottom: 80px;
}

.app-card {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 40px 30px;
  border-radius: 20px;
  text-decoration: none;
  color: white;
  transition: all 0.3s ease;
  min-width: 300px;
  text-align: center;
  position: relative;
  overflow: hidden;
}

.app-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.1), transparent);
  transition: left 0.5s ease;
}

.app-card:hover::before {
  left: 100%;
}

.app-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 30px rgba(102, 126, 234, 0.3);
  border-color: rgba(102, 126, 234, 0.5);
}

.app-icon {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin: 0 auto 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
  position: relative;
  overflow: hidden;
}

.love-icon {
  background: linear-gradient(45deg, #ff6b6b, #ee5a6f);
  box-shadow: 0 0 20px rgba(255, 107, 107, 0.5);
}

.manus-icon {
  background: linear-gradient(45deg, #4ecdc4, #45b7d1);
  box-shadow: 0 0 20px rgba(78, 205, 196, 0.5);
}

.app-icon::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: radial-gradient(circle at 50% 50%, rgba(255, 255, 255, 0.3) 0%, transparent 70%);
}

.app-title {
  font-size: 1.8rem;
  margin-bottom: 15px;
  color: white;
  font-weight: 700;
}

.app-description {
  color: #a8b2d1;
  font-size: 1.1rem;
  line-height: 1.5;
}

.footer {
  position: relative;
  z-index: 1;
  text-align: center;
  color: #a8b2d1;
  padding: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  width: 100%;
  margin-top: auto;
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

@keyframes gradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .neon-text {
    font-size: 3rem;
  }
  
  .subtitle {
    font-size: 1.2rem;
  }
  
  .app-card {
    min-width: 250px;
    padding: 30px 20px;
  }
  
  .app-list {
    gap: 20px;
  }
}

@media (max-width: 480px) {
  .neon-text {
    font-size: 2.5rem;
  }
  
  .subtitle {
    font-size: 1rem;
  }
  
  .app-card {
    min-width: 200px;
    padding: 25px 15px;
  }
  
  .app-icon {
    width: 80px;
    height: 80px;
    font-size: 1.5rem;
  }
  
  .app-title {
    font-size: 1.5rem;
  }
  
  .app-description {
    font-size: 1rem;
  }
}
</style>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const particlesContainer = ref(null)
const particles = ref([])

function createParticle() {
  if (!particlesContainer.value) return

  const particle = document.createElement('div')
  particle.className = 'particle'
  
  // 随机大小
  const size = Math.random() * 10 + 5
  particle.style.width = `${size}px`
  particle.style.height = `${size}px`
  
  // 随机位置
  particle.style.left = `${Math.random() * 100}%`
  particle.style.top = `${Math.random() * 100}%`
  
  // 随机动画延迟
  particle.style.animationDelay = `${Math.random() * 6}s`
  
  // 随机透明度
  particle.style.opacity = Math.random() * 0.5 + 0.3
  
  particlesContainer.value.appendChild(particle)
  particles.value.push(particle)
}

function createParticles() {
  // 清除旧粒子
  particles.value.forEach(particle => {
    if (particle.parentNode) {
      particle.parentNode.removeChild(particle)
    }
  })
  particles.value = []
  
  // 创建新粒子
  for (let i = 0; i < 50; i++) {
    setTimeout(createParticle, i * 100)
  }
}

onMounted(() => {
  createParticles()
  // 每10秒重新生成粒子
  const interval = setInterval(createParticles, 10000)
  
  onUnmounted(() => {
    clearInterval(interval)
    // 清除粒子
    particles.value.forEach(particle => {
      if (particle.parentNode) {
        particle.parentNode.removeChild(particle)
      }
    })
  })
})
</script>
