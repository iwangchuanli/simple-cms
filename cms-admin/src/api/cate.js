import request from '@/utils/request'

export function saveOrUpdate(data) {
    return request({url: '/cate/saveOrUpdate', method: 'post', data})
}
export function tree(data) {
    return request({url: '/cate/common/tree', method: 'post', data})
}
export function del(data) {
    return request({url: '/cate/delete', method: 'post', data})
}
