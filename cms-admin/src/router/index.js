import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import fa from "element-ui/src/locale/lang/fa";

export const constantRoutes = [
    {
        path: '/login',
        component: () => import('@/views/login/index'),
        hidden: true
    },
    {
        path: '/404',
        component: () => import('@/views/404'),
        hidden: true
    },
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [
            {
                path: '/dashboard',
                name: 'dashboard',
                component: () => import('@/views/dashboard/index'),
                meta: { title: '首页', icon: 'dashboard' }
            }
        ]
    },
    {
        path: '/user',
        component: Layout,
        children: [{
            path: '/user',
            name: '用户管理',
            component: () => import('@/views/user/index'),
            meta: { title: '用户管理', icon: 'menu' }
        }]
    },
    {
        path: '/site',
        component: Layout,
        children: [{
            path: '/site',
            name: '站点设置',
            component: () => import('@/views/site/index'),
            meta: { title: '站点设置', icon: 'menu' }
        }]
    },
    {
        path: '/category',
        component: Layout,
        children: [{
            path: '/category',
            name: '分类管理',
            component: () => import('@/views/category/manage'),
            meta: { title: '分类管理', icon: 'dashboard' }
        }]
    },
    {
        path: '/article',
        component: Layout,
        children: [{
            path: '/article',
            name: '文章管理',
            component: () => import('@/views/article/index'),
            meta: { title: '文章管理', icon: 'dashboard' }
        }]
    },
    // 404 page must be placed at the end !!!
    { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher // reset router
}

export default router
