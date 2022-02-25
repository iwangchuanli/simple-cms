import request from '@/api/request'

export function siteItem(data) {
    return request({url: '/site/common/item', method: 'post', data})
}

export function cateTree(data) {    // 文章分类树形
    return request({url: '/cate/common/tree', method: 'post', data})
}

export function updateRecommend(data) {
    return request({url: '/site/updateRecommend', method: 'post', data})
}

export function updateBanner(data) {
    return request({url: '/site/updateBanner', method: 'post', data})
}

export function articlePage(data) {   // 文章分页
    return request({url: '/article/common/page', method: 'post', data})
}

export function articleItem(data) {   // 文章单条
    return request({url: '/article/common/item', method: 'post', data})
}

export function indexData(data) {   // 主页
    return request({url: '/index/common/index', method: 'post', data})
}


export function cateArticle(data) {   // 一级分类下的文章
    return request({url: '/article/common/cateArticle', method: 'post', data})
}

