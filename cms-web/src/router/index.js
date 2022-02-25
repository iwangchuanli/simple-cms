import Vue from 'vue'
import VueRouter from 'vue-router'
import index from '@/views/index.vue'
import channel from '@/views/channel.vue'
import detail from "@/views/detail";
import about from "@/views/about";

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
const routes = [
    {
        path: '/',
        name: 'index',
        component: index,
        meta: {
            index: 1,      // 同样的标识
            keepAlive: true,
            content: '首页',
        }
    },
    {
        path: '/channel/:id',
        name: 'channel',
        component: channel,
        meta: {
            index: 2,      // 同样的标识
            keepAlive: true,
            content: '分类'
        }
    } ,{
        path: '/detail/:id',
        name: 'detail',
        component: detail,
        meta: {
            index: 3,      // 同样的标识
            keepAlive: true,
            content: '详情'
        }
    },{
        path: '/about',
        name: 'about',
        component: about,
        meta: {
            index: 4,      // 同样的标识
            keepAlive: true,
            content: '关于我们'
        }
    }
]

const router = new VueRouter({
    mode:'history',
    routes
})

// router.beforeEach((to, from, next) => {
//     /* 路由发生变化修改页面meta */
//     if (to.meta.content) {
//
//     }
// })

export default router
