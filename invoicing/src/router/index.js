import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

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
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '进销存管理系统', icon: 'dashboard' }
    }]
  },

  {
    path: '/user',
    component: Layout,
    children: [
      {
        path: 'list',
        name: '用户管理',
        component: () => import('@/views/user/list'),
        meta: { title: '用户管理', icon: 'user' }
      }
    ]
  },

  {
    path: '/good',
    component: Layout,
    children: [
      {
        path: 'good',
        name: '商品管理',
        component: () => import('@/views/good/list'),
        meta: { title: '商品管理', icon: 'example' }
      }
    ]
  },

  {
    path: '/stock',
    component: Layout,
    children: [
      {
        path: 'stock',
        name: '库存管理',
        component: () => import('@/views/stock/list'),
        meta: { title: '库存管理', icon: 'link' }
      }
    ]
  },

  {
    path: '/import',
    component: Layout,
    children: [
      {
        path: 'import',
        name: '进货管理',
        component: () => import('@/views/import/list'),
        meta: { title: '进货管理', icon: 'nested' }
      }
    ]
  },

  {
    path: '/export',
    component: Layout,
    children: [
      {
        path: 'export',
        name: '出货管理',
        component: () => import('@/views/export/list'),
        meta: { title: '出货管理', icon: 'tree' }
      }
    ]
  },

  {
    path: '/unit',
    component: Layout,
    children: [
      {
        path: 'unit',
        name: '单位管理',
        component: () => import('@/views/unit/list'),
        meta: { title: '单位管理', icon: 'table' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
