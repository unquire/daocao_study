import request from '@/utils/request.js'
// 登录接口

export function searchMenuAll(){
    return request({
        url:'sys/menu/list',
        method:"get"
    })
}

// 保存菜单
export function saveMenu(data){
    return request({
        url:'sys/menu',
        method:"post",
        data:data
    })
}

// 回显菜单
export function searchMenuById(id){
    return request({
        url:`sys/menu/${id}`,
        method:"get",
    })
}
// 修改
export function updateMenu(data){
    return request({
        url:'sys/menu',
        method:"put",
        data:data
    })
}
// 删除菜单
export function deleteMenu(ids){
    return request({
        url:'sys/menu',
        method:"delete",
        data:ids
    })

}
