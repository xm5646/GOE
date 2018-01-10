import Vue from 'vue'
import Router from 'vue-router'
import FastClick from 'fastclick'
import Vum from 'vum'

// demos
import Index from './views/Index'
import Login from './views/Login'
import ResetPassword from './views/function/ResetPassword'

Vue.use(Router)
Vue.use(Vum)

// Vue.http.options.emulateJSON = true

let router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/resetpassword',
      name: 'resetpassword',
      component: ResetPassword
    }
  ]
})

new Vue({
  router
}).$mount('#app')

Vum.router(router)  // config router by vum

FastClick.attach(document.body)
