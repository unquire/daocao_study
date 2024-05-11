
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
// 引入路由规则
import router from './router/index.js'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// 导入路由守卫
import  './router/RouterGuider.js'
// 引入pinia持久化

import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
// 注册全局组件
import SvgIcon from '@/components/SvgIcon/index.vue';
import 'virtual:svg-icons-register';

const app = createApp(App)


for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
// 使用持久化
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
// 自注册全局组件 
app.component('svg-icon',SvgIcon);

app.use(createPinia())
// app.use(pinia)
app.use(router)
app.mount('#app')
