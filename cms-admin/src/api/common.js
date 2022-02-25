import request from '@/utils/request'

export function getVerify(data) {   // 获取验证码
    return request({
        url: '/common/code/verify',
        method: 'get',
        data
    })
}

export function getUploadUrl() {
    return request.defaults.baseURL + '/common/ali/oss/upload'
}



