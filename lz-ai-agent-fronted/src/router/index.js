import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/love-app',
    name: 'LoveApp',
    component: () => import('../views/LoveApp.vue')
  },
  {
    path: '/manus',
    name: 'Manus',
    component: () => import('../views/Manus.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
