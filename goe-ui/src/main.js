import Vue from 'vue'
import Router from 'vue-router'
import VueResource from 'vue-resource'
import FastClick from 'fastclick'
import Vum from 'vum'
// my components
import Index from './views/Index'
import Login from './views/Login'
import ResetPassword from './views/function/ResetPassword'
import ReConsume from './views/function/ReConsume'
import ConvertConsume from './views/function/ConvertConsumeCoin'

Vue.use(Router)
Vue.use(Vum)
Vue.use(VueResource)
Vue.http.options.emulateJSON = true
Vue.http.options.timeout = 3000

Vue.http.interceptors.push((request, next) => {
  var timeout
  if (request._timeout) {
    timeout = setTimeout(() => {
      if (request.onTimeout) request.onTimeout(request)
      request.abort()
    }, request._timeout)
  }
  next((response) => {
    clearTimeout(timeout)
  })
})

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
      path: '/resetPassword',
      name: 'resetPassword',
      component: ResetPassword
    },
    {
      path: '/reConsume',
      name: 'reConsume',
      component: ReConsume
    },
    {
      path: '/convertConsumeCoin',
      name: 'convertConsumeCoin',
      component: ConvertConsume
    }
  ]
})

new Vue({
  router
}).$mount('#app')

Vum.router(router)  // config router by vum

FastClick.attach(document.body)
