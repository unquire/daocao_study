// 导入router/index.js
import router from './index.js'
// 导入stores/menu.js
import { useMenuStore } from '@/stores/menu.js'
// 设置路由白名单
const whiteRouter = ['/','/login','/error','/404']

import Layout from '@/Layout/index.vue'

// 设置全局的前置路由守卫

router.beforeEach((to,from,next)=>{
    console.log("from=======>",from);
    console.log("to=====>",to);
    // 使用pinia
    const menuStore = useMenuStore();
    // 判断 to是否处于白名单
    if(whiteRouter.indexOf(to.path)== -1){
        // 判断routerlist里面是否有动态路由的数据
        if(menuStore.routerList.length == 0){
            console.log("menuStore.routerList=====>没有数据");
            // 设置动态路由数据结构，并且添加到路由中
            menuStore.generateRouter().then(()=>{
                // 添加动态路由
                const routerList = menuStore.routerList;
                console.log("routerList===>",routerList);
                // 所有的页面都是加载到Layout/Main组件的RouterView中
                // 相当于所有的路由都是Layout的子路由
                router.addRoute({
                    component: Layout,
                    path:"/",
                    redirect:'index',
                    children:routerList
                })
                console.log("router=====>",router)
                // 跳转页面
                next({...to,replace:true})
            })
        }else{
            // 已经有动态路由了
            // 情况一：路由的路径是合法的，正常的
            if(to.matched.length!=0){
                console.log("menuStore.routerList=====> 路径合法",menuStore.routerList);
                next();
            }else {
                // 情况二：路由的页面并没有
                console.log("menuStore.routerList=====> 路径不合法",menuStore.routerList);
                next('/404');
            }
               
        }
    }else{
        // 直接放行
        next();
    }


})