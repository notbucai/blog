import Vue from 'vue'
import Router from 'vue-router'
import Index from './views/Index.vue'
import Archives from './views/Archives.vue'
import Blog from './views/Blog.vue'
import Tags from './views/Tags.vue'
import Tag from './views/Tag.vue'
import Links from './views/Links.vue'

// Blog
Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  },
  routes: [
    {
      path: '/',
      name: 'Bucai\'s blog',
      component: Index
    },
    {
      path: "/archives",
      name: "归档",
      component: Archives
    },
    {
      path: "/blog/:id",
      name: "博客",
      component: Blog
    },
    {
      path: "/tags",
      name: "标签",
      component: Tags
    },
    {
      path: "/tag/:id",
      name: " - 标签",
      component: Tag
    },
    {
      path: "/links",
      name: "Dalao 们",
      component: Links
    },
    {
      path: '/bucai_admin',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('./views/Admin.vue'),
      
      children: [
        { path: '',name:"后台管理", component: ()=>import('./views/admin/Index.vue') },
      ]
    }
  ]
})
