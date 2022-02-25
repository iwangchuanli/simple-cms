import request from '@/utils/request'

export function update(data) {   // 更新
    return request({
        url: '/cate/update',
        method: 'post',
        data
    })
}

export function add(data) {   // 添加
    return request({
        url: '/cate/add',
        method: 'post',
        data
    })
}

export function del(data) {   // 删除
    return request({
        url: '/cate/delete',
        method: 'post',
        data
    })
}

export function tree(data) {   // 分页
    return request({
        url: '/cate/common/tree',
        method: 'post',
        data
    })
}
