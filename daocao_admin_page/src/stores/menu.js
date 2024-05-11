import { searchSelfRouter } from '@/api/user/index.js';
import { defineStore } from 'pinia'

export const useMenuStore = defineStore('menu', {
    // 数据定义
    state: () => ({
        menuList:[],
        routerList:[], // 动态路由数据，也就是左侧菜单的路由信息
        tabList:[{title:'首页',path:'/index'}],// 存放tab列表的数据
        activeTab:'/index'
    }),
    // 获取数据
    getters: {
      Array: (state) => state.menuList,
      Array: (state) => state.routerList,
      Array: (state) => state.tabList,
      String:(state) => state.activeTab, 
    },
    // 操作数据
    actions: {
     setMenuList(data){
        console.log("setMenuList====>",data);
        this.menuList=data;
     },
    //  渲染动态路由数据结构，存储到pinia中，不需要每次都去渲染数据结构了
    // data就是查询出来的用户菜单
     setRouterList(data){
        data.forEach(item =>{
          // 定义数据结构
          let routerInfo = {
            name:item.menuName,
            path:item.path,
            meta:{title:item.menuName},
            // 设置组件
            component: () => import(/*@vite-ignore*/ `../views/${item.path}.vue`),                                 
          }
          // 将路由信息添加到数组中
          this.routerList.push(routerInfo)
          // 设置子菜单
          let childrenList = item.children;
          childrenList.forEach(children=>{
            let routerInfo={
              name: children.menuName,
              path: children.path,
              meta:{title:item.menuName},
              // 设置组件
              component:()=>import(/*@vite-ignore*/ `../views/${children.path}.vue`)
            }
            this.routerList.push(routerInfo)
          })
        })
        console.log("routerList=====>",this.routerList)
     }, 
    // 查询用户菜单
     generateRouter(){
      return new Promise((resolve,reject)=>{
        // 查询用户的菜单
        searchSelfRouter().then(res =>{
          if(res.data.code==200){
            this.setRouterList(res.data.data);
            resolve()
          }
          reject()
        })
      })
     },
     // 添加tab至tabList列表
     addTabList(data){
        this.tabList.push(data)
     },
     // 删除tabList列表中的tab
     removeTabList(name){
      // 直接就给删除了，true留下，false删除
        this.tabList = this.tabList.filter(item=>{
          if(item.path==name){
            return false;
          }
          return true;
        })
     },
    //  选中activeTab
    setActive(name){
      this.activeTab = name;
    }
    },
    // 使用持久化
    // persist:{
    //   enabled:true,
    //   storage:localStorage,
    //   key:'useMenu',
    //   path:['menuList','routerList']
    // }
  })
