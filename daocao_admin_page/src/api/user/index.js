import request from '@/utils/request.js'
// 登录接口

export function searchSelfRouter(){
    return request({
        url:'sys/menu/self',
        method:"get"
    })
}

export function searchUserInfo(){
    return request({
        url:'sys/user/info',
        method:"get"
    })
}