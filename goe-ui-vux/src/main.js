// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import FastClick from 'fastclick'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import VueResource from 'vue-resource'
import App from './App'
import router from './router/index'
import { sync } from 'vuex-router-sync'
import GoeConfig from '../config/goe'
// import { DatetimePlugin, BusPlugin, DevicePlugin, ToastPlugin, AlertPlugin, ConfirmPlugin, LoadingPlugin, WechatPlugin } from 'vux'
import { ToastPlugin, LoadingPlugin, ConfirmPlugin } from 'vux'
// import API from './js/api'

Vue.use(VueRouter)
Vue.use(VueResource)
Vue.use(Vuex)
// Vue.use(API)

Vue.http.options.emulateJSON = true
Vue.http.options.timeout = 5000

Vue.http.interceptors.push((request, next) => {
  console.log('进入拦截器拦截方法')
  Vue.$vux.loading.hide()
  Vue.$vux.loading.show({
    text: '加载中',
    delay: 500
  })
  console.log(request)
  var timeout
  if (request._timeout) {
    timeout = setTimeout(() => {
      console.log('进入拦截器超时方法')
      Vue.$vux.toast.show({
        type: 'cancel',
        text: '请求超时'
      })
      Vue.$vux.loading.hide()
      if (request.onTimeout) request.onTimeout(request)
      request.abort()
    }, request._timeout)
  }
  next((response) => {
    Vue.$vux.loading.hide()
    clearTimeout(timeout)
    if (GoeConfig.useAuth) {
      console.log('进入拦截器响应方法,输出获取的相应数据,读取cookie和header')
      console.log('获取登陆状态:' + response.headers.get('loginstatus'))
      if (!(response.headers.get('loginStatus') === 'true')) {
        window.localStorage.clear()
        if (request.url === 'http://mobile.mythvip.top/api/user/login') {
          console.log('登陆页面,不进行刷新')
        } else {
          window.location.href = 'http://mobile.mythvip.top/login'
        }
      } else {
        console.log('已登录状态')
        Vue.$vux.loading.hide()
        console.log(response.body)
      }
    } else {
      console.log(response.body)
    }
  })
})

// plugins
// Vue.use(DevicePlugin)
Vue.use(ToastPlugin)
// Vue.use(AlertPlugin)
Vue.use(ConfirmPlugin)
Vue.use(LoadingPlugin)
// Vue.use(WechatPlugin)
// Vue.use(BusPlugin)
// Vue.use(DatetimePlugin)

FastClick.attach(document.body)

let store = new Vuex.Store({
  modules: {}
})

Vue.use(store)
store.registerModule('vux', {
  state: {
    isLoading: false
  },
  mutations: {
    updateLoadingStatus (state, payload) {
      state.isLoading = payload.isLoading
    }
  },
  actions: {}
})

sync(store, router)

router.beforeEach(function (to, from, next) {
  // var arr, reg = new RegExp('(^| )' + 'autoLogin' + '=([^;]*)(;|$)')
  //
  // if (arr = document.cookie.match(reg))
  //   return unescape(arr[2])
  // else
  //   return null
  // if (to.name === 'index' && (from.name === 'getCashOrderView')) {
  //   console.log('从获取提现记录到首页')
  //   router.push({name: 'index', params: {view: 'wallet'}})
  // }
  if (to.name !== 'login') {
    if (window.localStorage.getItem('User') == null) {
      router.push({name: 'login'})
    }
    // if (to.name === 'index') {
    //   console.log('进入首页,来源网页:' + from.name)
    //   if (from.name === 'getCash') {
    //     console.log('返回钱包页面')
    //     router.push({name: 'index', params: {view: 'wallet'}})
    //   }
    // }
  }
  next()
})

router.afterEach(function (to) {
  // store.commit('updateLoadingStatus', {isLoading: false})
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  Vuex,
  render: h => h(App)
}).$mount('#app-box')
