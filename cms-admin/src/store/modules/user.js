import { login, logout, getInfo } from '@/api/user'
import { resetRouter } from '@/router'

const state = {
    token: "",
    user: {}
}

const mutations = {
    SET_TOKEN: (state, token) => {
        state.token = token
    },
    SET_USERINFO: (state, userInfo) => {
        state.userInfo = userInfo
    }
}

const actions = {}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}

