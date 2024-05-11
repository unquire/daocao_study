import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../Layout/index.vue'
// 创建路由
const constRoust = [
  // 重定向 
  {
    path:'',
    redirect:'/login'
   }, 
   {
      path: '/login',
      name: 'login',
      component: Login
    },
    // {
    //   path: '/',
    //   name: 'index',
    //   component: Layout,
    //   children:[{
    //     path:'/index',
    //     component:()=> import("@/views/index.vue")
    //   }]
    // }
  ]
const router = createRouter({
  history:createWebHistory(import.meta.env.BASE_URL),
  // 所有的路由规则
  routes:constRoust
})
// 导出router
export default router
