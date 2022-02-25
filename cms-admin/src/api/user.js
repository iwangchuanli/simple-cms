import request from '@/utils/request'

export function login(data) {   // 用户登陆
    return request({
        url: '/user/auth/login',
        method: 'post',
        data
    })
}

export function page(data) {   // 分页
    return request({
        url: '/user/info/page',
        method: 'post',
        data
    })
}

export function update(data) {   // 修改信息
    return request({
        url: '/user/info/update',
        method: 'post',
        data
    })
}

export function updateRole(data) {   // 修改信息
    return request({
        url: '/user/info/role',
        method: 'post',
        data
    })
}


export function item(data) {   // 修改信息
    return request({
        url: '/user/info/item',
        method: 'post',
        data
    })
}

export function modifyPwd(data) {   // 修改密码
    return request({
        url: '/user/modifyPwd',
        method: 'post',
        data
    })
}
