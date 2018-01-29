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
// import { DatetimePlugin, BusPlugin, DevicePlugin, ToastPlugin, AlertPlugin, ConfirmPlugin, LoadingPlugin, WechatPlugin } from 'vux'
import { ToastPlugin, LoadingPlugin } from 'vux'
// import API from './js/api'

Vue.use(VueRouter)
Vue.use(VueResource)
Vue.use(Vuex)
// Vue.use(API)

Vue.http.options.emulateJSON = true
Vue.http.options.timeout = 5000

Vue.http.interceptors.push((request, next) => {
  console.log('进入拦截方法')
  Vue.$vux.loading.show({
    text: '加载中',
    delay: 500
  })
  console.log('11111')
  console.log(request)
  var timeout
  if (request._timeout) {
    timeout = setTimeout(() => {
      console.log('进入超时方法')
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
    console.log('进入响应方法')
    Vue.$vux.loading.hide()
    console.log(response.body)
    clearTimeout(timeout)
  })
})

// plugins
// Vue.use(DevicePlugin)
Vue.use(ToastPlugin)
// Vue.use(AlertPlugin)
// Vue.use(ConfirmPlugin)
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
  actions: {
  }
})

sync(store, router)

router.beforeEach(function (to, from, next) {
  if (to.name !== 'login') {
    if (window.localStorage.getItem('User') == null) {
      router.push({name: 'login'})
    }
  }
  next()
})

router.afterEach(function (to) {
  store.commit('updateLoadingStatus', {isLoading: false})
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  Vuex,
  render: h => h(App)
}).$mount('#app-box')
