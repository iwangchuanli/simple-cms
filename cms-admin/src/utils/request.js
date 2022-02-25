import axios from 'axios'
import {MessageBox, Message} from 'element-ui'
import store from '@/store'

const baseUrl = "baseurl"

const service = axios.create({
  // baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  baseURL: baseUrl, // url = base url + request url
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
    config => {

        if (store.getters.token) {
            config.headers['cmsToken'] = store.getters.token
        }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

// response interceptor
service.interceptors.response.use(
    response => {
        const res = response.data
        // if the custom code is not 20000, it is judged as an error.
        if (res.code !== 0) {
            if (res.msg){
                Message({
                    message: res.msg || 'Error',
                    type: 'error',
                    duration: 5 * 1000
                })
            }
            return Promise.reject(new Error(res.msg || 'Error'))
        } else {
            return res.data
        }
    },
    error => {
        console.log('err' + error) // for debug
        Message({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service
