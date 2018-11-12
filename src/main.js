import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

// import '@/plugins/directive'
import '@/plugins/filters'

import '@/plugins/toast'

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

// use
Vue.use(mavonEditor)

router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  // console.log(to.path);
  let title = to.name;
  if (to.path != "/") {
    title += " - Bucai' Blog"
  }
  if (to.name) {
    document.title = title
  }
  next()
})

Vue.config.productionTip = false
import "@/assets/global.css";
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
