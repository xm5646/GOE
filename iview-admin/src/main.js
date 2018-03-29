import Vue from 'vue';
import iView from 'iview';
import {router} from './router/index';
import {appRouter} from './router/router';
import VueResource from 'vue-resource';
import store from './store';
import App from './app.vue';
import Cookies from 'js-cookie';
import '@/locale';
import 'iview/dist/styles/iview.css';
import VueI18n from 'vue-i18n';
import util from './libs/util';
import API from './libs/ajax';

Vue.use(VueI18n);
Vue.use(iView);
Vue.use(VueResource);
Vue.use(API);
var useAuth = true;
Vue.http.options.emulateJSON = true
Vue.http.options.timeout = 5000
Vue.prototype.APIServer = 'http://www.mythvip.top/api'
Vue.http.interceptors.push((request, next) => {
    // console.log('进入拦截器拦截方法')
    // Vue.$vux.loading.hide()
    // Vue.$vux.loading.show({
    //     text: '加载中',
    //     delay: 500
    // })
    console.log(request)
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
        clearTimeout(timeout1)
        if (useAuth) {
            console.log('进入拦截器响应方法,输出获取的相应数据,读取cookie和header')
            console.log('获取登陆状态:' + response.headers.get('loginstatus'));
            if (!(response.headers.get('loginStatus') === 'true')) {
                window.localStorage.clear();
                var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
                if (keys) {
                    for (var i = keys.length; i--;) {
                        document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString();
                    }
                }
                // Vue.$router.push({name: 'login'});
                window.location.href = 'http://www.mythvip.top';
            } else {
                console.log('已登录状态')
                console.log(response.body);
            }
        } else {
            console.log(response.body);
        }
    });
})

router.beforeEach(function (to, from, next) {
    if (to.name !== 'login') {
        if (Cookies.get('autoLogin') === null) {
            router.push({name: 'login'});
        }
    }
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
    mounted() {
        this.currentPageName = this.$route.name;
        // 显示打开的页面的列表
        this.$store.commit('setOpenedList');
        this.$store.commit('initCachepage');
        // 权限菜单过滤相关
        this.$store.commit('updateMenulist');
        // iview-admin检查更新
        util.checkUpdate(this);
    },
    created() {
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
