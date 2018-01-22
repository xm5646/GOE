import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login'
import Index from '../views/Index'
import InitPassword from '../views/function/InitPassword'
import ConvertConsumeCoin from '../views/function/ConvertConsumeCoin'
import TransferConsumeCoin from '../views/function/TransferConsumeCoin'
import ReConsume from '../views/function/ReConsume'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/',
      name: 'index',
      component: Index
    },
    {
      path: '/initPassword',
      name: 'initPassword',
      component: InitPassword
    },
    {
      path: '/convertConsumeCoin',
      name: 'convertConsumeCoin',
      component: ConvertConsumeCoin
    },
    {
      path: '/transferConsumeCoin',
      name: 'transferConsumeCoin',
      component: TransferConsumeCoin
    },
    {
      path: '/reConsume',
      name: 'reConsume',
      component: ReConsume
    }
  ]
})
