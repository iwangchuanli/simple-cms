import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        siteId: 4,
        cateList: [],   // 分类
        activeId: -1,   // 当前激活分类
        activeIndex: -1,   // 当前激活分类下级分类
        siteInfo: {},   // 站点信息
        activeArticleId: -1,
        meta: null
    },
    getters: {
        siteId: state => state.siteId,
        cateList: state => state.cateList,
        activeId: state => state.activeId,
        activeIndex: state => state.activeIndex,
        siteInfo: state => state.siteInfo,
        activeArticleId: state => state.activeArticleId,
        meta: state => state.meta,
    },
    mutations: {
        SET_CETELIST(state, payload) {
            state.cateList = payload
        },
        SET_SITEINFO(state, payload) {
            state.siteInfo = payload
        },
        SET_ACTIVEID(state, payload) {
            state.activeId = payload.toString()
        },
        SET_ACTIVEIDDEX(state, payload) {
            state.activeIndex = payload
        },
        SET_ACTIVEARTIVLEID(state, payload) {
            state.activeArticleId =  payload
        },
        SET_META(state, payload) {
            state.meta = payload
        }
    },
    actions: {},
    modules: {}
})
