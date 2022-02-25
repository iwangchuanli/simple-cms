import request from '@/utils/request'

export function item(data) {
    return request({url: '/site/common/item', method: 'post', data})
}
export function saveOrUpdate(data) {
    return request({url: '/site/saveOrUpdate', method: 'post', data})
}
export function page(data) {
    return request({url: '/site/page', method: 'post', data})
}
export function updateRecommend(data) {
    return request({url: '/site/updateRecommend', method: 'post', data})
}
export function updateBanner(data) {
    return request({url: '/site/updateBanner', method: 'post', data})
}
export function updateDetail(data) {
    return request({url: '/site/updateDetail', method: 'post', data})
}

export function adminIndex(data) {
    return request({url: '/index/admin/index', method: 'post', data})
}
