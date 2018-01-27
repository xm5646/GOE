// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import FastClick from 'fastclick'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import App from './App'
import router from './router/index'
import { sync } from 'vuex-router-sync'
import { DatetimePlugin, BusPlugin, DevicePlugin, ToastPlugin, AlertPlugin, ConfirmPlugin, LoadingPlugin, WechatPlugin, AjaxPlugin } from 'vux'
import API from './js/api'

Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(API)

// plugins
Vue.use(DevicePlugin)
Vue.use(ToastPlugin)
Vue.use(AlertPlugin)
Vue.use(ConfirmPlugin)
Vue.use(LoadingPlugin)
Vue.use(WechatPlugin)
Vue.use(AjaxPlugin)
Vue.use(BusPlugin)
Vue.use(DatetimePlugin)

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
  store.commit('updateLoadingStatus', {isLoading: true})
  // if (to.name !== 'login') {
  //   if (window.localStorage.getItem('User') == null) {
  //     router.push({name: 'login'})
  //   }
  // }
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
