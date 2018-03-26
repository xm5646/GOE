import Vue from 'vue';
import iView from 'iview';
import {router} from './router/index';
import {appRouter} from './router/router';
import VueResource from 'vue-resource';
import store from './store';
import App from './app.vue';
import '@/locale';
import 'iview/dist/styles/iview.css';
import VueI18n from 'vue-i18n';
import util from './libs/util';
import API from './libs/ajax';

Vue.use(VueI18n);
Vue.use(iView);
Vue.use(VueResource);
Vue.use(API);
var useAuth = false;
Vue.http.options.emulateJSON = true
Vue.http.options.timeout = 5000
Vue.prototype.APIServer = 'http://localhost:8088'
Vue.http.interceptors.push((request, next) => {
    // console.log('进入拦截器拦截方法')
    // Vue.$vux.loading.hide()
    // Vue.$vux.loading.show({
    //     text: '加载中',
    //     delay: 500
    // })
    console.log(request)
    if (request.url === '"http://localhost:8088/excel/uploadExcel"') {
        request.headers.set('Content-Type', 'multipart/form-data');
    }
    var timeout1;
    // 這裡改用 _timeout
    if (request.timeout) {
        timeout1 = setTimeout(() => {
            next(request.respondWith(request.body, {
                status: 408,
                statusText: '请求超时'
            }));
        }, 5000);
    }
    next((response) => {
        // Vue.$vux.loading.hide()
        clearTimeout(timeout1)
        if (useAuth) {
            console.log('进入拦截器响应方法,输出获取的相应数据,读取cookie和header')
            console.log('获取登陆状态:' + response.headers.get('loginstatus'))
            if (!(response.headers.get('loginstatus') === 'true')) {
                // Vue.$router.push({name: 'login'})
                window.localStorage.clear()
                window.location.href = 'http://xm.xmkkk023.cn/login'
                response.abort();
            } else {
                console.log('已登录状态')
                Vue.$vux.loading.hide()
                console.log(response.body);
            }
        } else {
            console.log(response.body);
        }
    });
})

router.beforeEach(function (to, from, next) {
    console.log('from :' + from.name + ',to: ' + to.name)
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
    // if (to.name !== 'login') {
    //     if (window.localStorage.getItem('User') == null) {
    //         router.push({name: 'login'})
    //     }
    //     // if (to.name === 'index') {
    //     //   console.log('进入首页,来源网页:' + from.name)
    //     //   if (from.name === 'getCash') {
    //     //     console.log('返回钱包页面')
    //     //     router.push({name: 'index', params: {view: 'wallet'}})
    //     //   }
    //     // }
    // }
    next();
})

new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => h(App),
    data: {
        currentPageName: ''
    },
    mounted () {
        this.currentPageName = this.$route.name;
        // 显示打开的页面的列表
        this.$store.commit('setOpenedList');
        this.$store.commit('initCachepage');
        // 权限菜单过滤相关
        this.$store.commit('updateMenulist');
        // iview-admin检查更新
        util.checkUpdate(this);
    },
    created () {
        let tagsList = [];
        appRouter.map((item) => {
            if (item.children.length <= 1) {
                tagsList.push(item.children[0]);
            } else {
                tagsList.push(...item.children);
            }
        });
        this.$store.commit('setTagsList', tagsList);
    }
});
