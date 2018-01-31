<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '推荐新用户'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>{{bonusCoin}}</span>
          <br/>
          奖金
        </div>
        <div class="vux-1px-r">
          ￥<span>{{consumeCoin}}</span>
          <br/>
          报单币
        </div>
      </div>
    </card>
    <div v-if="isEnableAdd">
        <group title="新用户信息">
          <x-input title="推荐人" :min="5" :max="20" readonly v-model="recommendAccount">
          </x-input>
          <x-input title="上级员工"  :min="5" :max="20" readonly v-model="parentAccount">
          </x-input>
          <x-input title="放置部门"   readonly v-model="place">
          </x-input>
          <x-input title="用户名" name="username" placeholder="请输入新用户编号" :min="5" :max="20" v-model="newAccount">
            <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-name.png"
                 width="24" height="24">
          </x-input>
          <x-input title="密码" type="password" placeholder="请输入临时密码" v-model="tempPassword" :min="6" :max="12">
            <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png"
                 width="24" height="24">
          </x-input>
          <br>
          <x-button type="primary" action-type="submit" @click.native="addUser">创建</x-button>
        </group>
    </div>
    <div v-else>
      <message title="无法新增用户" description="请确认报单币是否充足"></message>
    </div>
  </div>
</template>
<script>
  import {XHeader, XInput, Group, XButton, Card, FormPreview} from 'vux'
  import GoeConfig from '../../../config/goe'
  import Message from '../../components/msg'
  export default {
    mounted: function () {
      const userObj = JSON.parse(window.localStorage.getItem('User'))
      this.bonusCoin = userObj.bonusCoin
      this.consumeCoin = userObj.consumeCoin
      const recommendInfo = this.$route.params
      if (recommendInfo.recommendAccount == null) {
        this.$router.push({name: 'index'})
      } else {
        this.recommendAccount = recommendInfo.recommendAccount
        this.parentAccount = recommendInfo.parentAccount
        this.place = recommendInfo.place
      }
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Card,
      FormPreview,
      Message
    },
    data () {
      return {
        bonusCoin: 20000,
        consumeCoin: 1000,
        recommendAccount: '',
        parentAccount: '',
        place: '',
        newAccount: '',
        tempPassword: ''
      }
    },
    methods: {
      addUser () {
        if (this.newAccount === '' || this.tempPassword === '') {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '输入信息不完整'
          })
        } else if (this.newAccount.length < 5 || this.tempPassword.length < 6) {
          this.$vux.toast.show({
            type: 'text',
            width: '20em',
            text: '用户编号最少5位字符,临时密码最低6位字符'
          })
        } else {
          this.postAddUser()
        }
        console.log('add user' + this.place + this.parentAccount + this.recommendAccount)
      },
      postAddUser () {
        const url = GoeConfig.apiServer + '/user/save'
        this.$http.post(url,
          {
            account: this.newAccount,
            password: this.tempPassword,
            parentAccount: this.parentAccount,
            position: this.place,
            recommendAccount: this.recommendAccount
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            console.log(response.body)
            if (response.body.success) {
              this.$vux.toast.show({
                text: '创建成功'
              })
//              this.getUserInfo()
              window.history.go(-1)
//              this.$router.push({name: 'index', params: {view: 'performance', parentAccount: this.parentAccount}})
            } else {
              console.log(response.body.message)
              this.$vux.toast.show({
                type: 'cancel',
                text: (response.body.message || '系统异常')
              })
            }
          }, responseErr => {
            console.log(responseErr)
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
          })
      },
      getUserInfo () {
        const url = GoeConfig.apiServer + '/user/findByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.success) {
              const result = response.body.data
              window.localStorage.setItem('User', JSON.stringify(result))
            } else {
              this.$vux.toast.show({
                type: 'cancel',
                text: (response.body.message || '系统异常')
              })
            }
          }, responseErr => {
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
          })
      }
    },
    computed: {
      isEnableAdd: function () {
        return this.consumeCoin >= GoeConfig.goe.price
      }
    }
  }
</script>

<style lang="less" scoped>
  @import '../../../node_modules/vux/src/styles/1px.less';

  .card-demo-flex {
    display: flex;
  }

  .card-demo-content01 {
    padding: 10px 0;
  }

  .card-padding {
    padding: 15px;
  }

  .card-demo-flex > div {
    flex: 1;
    text-align: center;
    font-size: 12px;
  }

  .card-demo-flex span {
    color: #f74c31;
  }
</style>
