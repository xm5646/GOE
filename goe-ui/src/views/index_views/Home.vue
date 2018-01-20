<template>
  <div class="my-page">
    <page-header>
      <header-title style="background-color: orange" :back-link="true">会员管理系统</header-title>
    </page-header>
    <page-content>
      <header class='demos-header'>
        <list style="margin: 0px">
          <list-item>
            <div class="item-media"><img src="../../assets/images/person.png" width="80"></div>
            <div class="item-content">
              <div class="item-title-row">
                <div class="item-title">{{ LoginUser.account}}</div>
                <div class="item-after">{{ LoginUser.userStatus }}</div>
              </div>
              <div class="item-subtitle">
                {{ LoginUser.userLevel }}
              </div>
              <div class="item-text">
                考核状态: {{ LoginUser.assessStatus }}<br>
                距离下次重销日：  {{ LoginUser.assessDate}}

              </div>
            </div>
          </list-item>
        </list>
      </header>
      <!--业绩显示-->
      <list>
        <list-item>
          <div class="item-media"><img src="../../assets/images/performance.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">累计业绩</div>
            </div>
            <div class="item-subtitle">
              <span class="span-performance">A:{{ MyPerformance.departAcount }}</span>
              <span class="span-performance">B:{{ MyPerformance.departBcount }}</span>
              <span class="span-performance">C:{{ MyPerformance.departCcount }}</span>
            </div>
          </div>
        </list-item>
        <list-item>
          <div class="item-media"><img src="../../assets/images/performance.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">新增业绩</div>
            </div>
            <div class="item-subtitle">
              <span class="span-performance">A:{{ MyPerformance.addDepartAcount}}</span>
              <span class="span-performance">B:{{ MyPerformance.addDepartBcount}}</span>
              <span class="span-performance">C:{{ MyPerformance.addDepartCcount}}</span>
            </div>
          </div>
        </list-item>
      </list>


      <list>
        <list-item :link="true">
          <div class="item-media">
            <img src="../../assets/images/home/form.png" width="30">
          </div>
          <div class="item-content">
            <router-link :to="{ name: 'convertConsumeCoin'}">
              <div class="item-title-row">
                <div class="item-title">奖金转换报单币</div>
                <div class="item-after">
                  <div class="link-arrow icon icon-link"></div>
                </div>
              </div>
            </router-link>
          </div>
        </list-item>
        <list-item :link="true">
          <div class="item-media">
            <img src="../../assets/images/home/form.png" width="30">
          </div>
          <div class="item-content">
            <router-link :to="{ name: 'transferConsumeCoin' }">
              <div class="item-title-row">
                <div class="item-title">报单币转账</div>
                <div class="item-after">
                  <div class="link-arrow icon icon-link"></div>
                </div>
              </div>
            </router-link>
          </div>
        </list-item>
        <list-item :link="true">
          <div class="item-media">
            <img src="../../assets/images/home/form.png" width="30">
          </div>
          <div class="item-content">
            <router-link :to="{ name: 'reConsume' }">
              <div class="item-title-row">
                <div class="item-title">重复消费</div>
                <div class="item-after">
                  <div class="link-arrow icon icon-link"></div>
                </div>
              </div>
            </router-link>
          </div>
        </list-item>
        <list-item :link="true">
          <div class="item-media">
            <img src="../../assets/images/home/form.png" width="30">
          </div>
          <div class="item-content">
            <router-link :to="{ path: '/resetPassword' }">
              <div class="item-title-row">
                <div class="item-title">修改密码</div>
                <div class="item-after">
                  <div class="link-arrow icon icon-link"></div>
                </div>
              </div>
            </router-link>
          </div>
        </list-item>
        <list-item :link="true">
          <div class="item-media">
            <img src="../../assets/images/home/form.png" width="30">
          </div>
          <div class="item-content" >
            <router-link :to="{ name: 'login' }" @click.native="logout">
              <div class="item-title-row">
                <div class="item-title">退出登录</div>
                <div class="item-after">
                  <div class="link-arrow icon icon-link"></div>
                </div>
              </div>
            </router-link>
          </div>
        </list-item>
      </list>
    </page-content>
  </div>
</template>
<script>
  import Grid from '../../../node_modules/vum/src/components/grid'
  import { Header, HeaderLink, HeaderTitle } from '../../../node_modules/vum/src/components/header'
  import Content from '../../../node_modules/vum/src/components/content'
  import { List, ListItem } from '../../../node_modules/vum/src/components/list'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
      // 设置当前登陆用户
      this.LoginUser = JSON.parse(window.localStorage.getItem('User'))
      // 加载首页业绩信息
      this.getPerformance()
    },
    components: {
      'page-header': Header,
      HeaderLink,
      HeaderTitle,
      List,
      ListItem,
      'page-content': Content,
      Grid
    },
    data () {
      return {
        LoginUser: '',
        MyPerformance: ''
      }
    },
    methods: {
      linkTo (url) {
        this.$emit('linkTo', url)
      },
      logout () {
        window.localStorage.clear()
        this.$router.push({name: 'login'})
      },
      getPerformance () {
        const url = GoeConfig.apiServer + '/user/performance?account=' + this.LoginUser.account
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
//              this.errMsg = '请求超时超时'
//              this.password = ''
          }
        })
          .then(response => {
            this.MyPerformance = response.body.data
          }, responseErr => {
            console.log(responseErr.body)
          })
      },
      update () {
        console.log('home update')
        this.LoginUser = JSON.parse(window.localStorage.getItem('User'))
        this.getPerformance()
      }
    }
  }
</script>
<style>
  .span-performance {
    margin-right: 10%;
  }
</style>
