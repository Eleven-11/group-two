import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */
import Layout from '../views/layout/Layout'

const _import = require('./_import_' + process.env.NODE_ENV)
Vue.use(Router)
export const constantRouterMap = [
  {path: '/login', component: _import('login/index'), hidden: true},
  {path: '/userfans', component: _import('comUser/userfans'), hidden: true},
  {path: '/404', component: _import('404'), hidden: true},
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{
      path: 'dashboard', component: _import('dashboard/index')
    }]
  }
]
export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/system',
    component: Layout,
    redirect: '/system/article',
    name: '功能模块',
    meta: {title: '功能模块', icon: 'tree'},
    children: [
      {
        path: 'article',
        name: '文章',
        component: _import('article/article'),
        meta: {title: '文章', icon: 'example'},
        menu: 'article'
      },
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/',
    name: '',
    meta: {title: '用户权限', icon: 'table'},
    children: [
      {
        path: '',
        name: '用户列表',
        component: _import('user/user'),
        meta: {title: '用户列表', icon: 'user'},
        menu: 'user'
      },
      {
        path: 'role',
        name: '权限管理',
        component: _import('user/role'),
        meta: {title: '权限管理', icon: 'password'},
        menu: 'role'
      },
    ]
  },
  {
    path: '/comUser',
    component: Layout,
    redirect: '/comUser/',
    name: '',
    meta: {title: '普通用户', icon: 'table'},
    children: [
      {
        path: 'comUser',
        name: '普通用户列表',
        component: _import('comUser/comUser'),
        meta: {title: '普通用户列表', icon: 'user'},
        menu: 'comuser'
      },
      {
        path: 'fans',
        name: '用户关注列表',
        component: _import('comUser/fans'),
        meta: {title: '用户关注列表', icon: 'user'},
        menu: 'comuserfans'
      },
      {
        path: 'myPost',
        name: '用户帖子列表',
        component: _import('comUser/myPost'),
        meta: {title: '用户帖子列表', icon: 'user'},
        menu: 'comuserpost'
      },
      {
        path: 'like',
        name: '用户点赞列表',
        component: _import('comUser/like'),
        meta: {title: '用户点赞列表', icon: 'user'},
        menu: 'comuserlike'
      },
      {
        path: 'comment',
        name: '用户帖子评论列表',
        component: _import('comUser/comment'),
        meta: {title: '用户帖子评论列表', icon: 'user'},
        menu: 'comusercomment'
      },
      {
        path: 'browse',
        name: '用户浏览记录列表',
        component: _import('comUser/browse'),
        meta: {title: '用户浏览记录列表', icon: 'user'},
        menu: 'comuserbrowse'
      },
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]
