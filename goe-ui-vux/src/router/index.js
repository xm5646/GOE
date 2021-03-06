import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login'
import Index from '../views/Index'
import InitPassword from '../views/function/InitPassword'
import ConvertConsumeCoin from '../views/function/ConvertConsumeCoin'
import TransferConsumeCoin from '../views/function/TransferConsumeCoin'
import ReConsume from '../views/function/ReConsume'
import ResetPassword from '../views/function/ResetPassword'
import ResetPayPassword from '../views/function/ResetPayPassword'
import ProductCoinExchange from '../views/function/ProductCoinExchange'
import BankCardManagement from '../views/function/BankCardManagement'
import AddBankCard from '../views/function/AddBankCard'
import AddressManagement from '../views/function/AddressManagement'
import AddAddress from '../views/function/AddAddress'
import EditAddress from '../views/function/EditAddress'
import GetCash from '../views/function/GetCash'
import AddUser from '../views/function/AddUser'
import GetCashOrderView from '../views/function/GetCashOrderView'
import OrderView from '../views/function/OrderView'

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
    },
    {
      path: '/resetPassword',
      name: 'resetPassword',
      component: ResetPassword
    },
    {
      path: '/resetPayPassword',
      name: 'resetPayPassword',
      component: ResetPayPassword
    },
    {
      path: '/productCoinExchange',
      name: 'productCoinExchange',
      component: ProductCoinExchange
    },
    {
      path: '/bankCardManagement',
      name: 'bankCardManagement',
      component: BankCardManagement
    },
    {
      path: '/addBankCard',
      name: 'addBankCard',
      component: AddBankCard
    },
    {
      path: '/addressManagement',
      name: 'addressManagement',
      component: AddressManagement
    },
    {
      path: '/addAddress',
      name: 'addAddress',
      component: AddAddress
    },
    {
      path: '/editAddress',
      name: 'editAddress',
      component: EditAddress,
      props: {
        backView: 'wallet'
      }
    },
    {
      path: '/getCash',
      name: 'getCash',
      component: GetCash,
      props: {
        backView: 'wallet'
      }
    },
    {
      path: '/addUser',
      name: 'addUser',
      component: AddUser
    },
    {
      path: '/getCashOrderView',
      name: 'getCashOrderView',
      component: GetCashOrderView
    },
    {
      path: '/getCashOrderView',
      name: 'getCashOrderView',
      component: GetCashOrderView
    },
    {
      path: '/orderView',
      name: 'orderView',
      component: OrderView
    }
  ]
})
