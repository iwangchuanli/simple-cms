import request from '@/utils/request'

export function update(data) {   // 更新
    return request({
        url: '/article/saveOrUpdate',
        method: 'post',
        data
    })
}

export function del(data) {   // 删除
    return request({
        url: '/article/delete',
        method: 'post',
        data
    })
}

export function page(data) {   // 分页
    return request({
        url: '/article/common/page',
        method: 'post',
        data
    })
}

export function item(data) {   // 查询
    return request({
        url: '/article/common/item',
        method: 'post',
        data
    })
}

