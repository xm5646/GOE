<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '报单币转账'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>{{consumeCoin}}</span>
          <br/>
          报单币
        </div>
      </div>
    </card>
    <divider title="收款用户查询">收款用户查询</divider>
    <group title="" class="weui-cells_form">
      <x-input class="weui-vcode" placeholder="请输入收款用的编号" v-model="receiveAccount">
        <x-button slot="right" type="primary" mini @click.native="findUser">查找</x-button>
      </x-input>
    </group>
    <div v-if="isFinded">
      <panel :list="list" :type="type" ></panel>
      <x-input title="￥" type="number" placeholder="请输入转帐金额" v-model="transferNumber" :min="1" :max="12" ></x-input>
    </div>
    <div v-if="isNotFinded">
      <message title="未找到用户" description="请确认要查找的用户编号是否正确"></message>
    </div>
    <div v-if="isInput">
      <form-preview header-label="转账金额" :header-value="showConvertNumber" :body-items="list2" :footer-buttons="buttons2" name="demo"></form-preview>
    </div>
    <pay-password :showPayPasswordInput="showPayPasswordStatus" @paySubmitEvent="submitPay" @cancelPayEvent="cancelPay"></pay-password>
  </div>
</template>

<script>
  import { XHeader, XInput, Group, XButton, Card, FormPreview, Search, Divider, Panel, Msg } from 'vux'
  import Message from '../../components/msg'
  import PayPassword from '../../components/PayPassword'
  import GoeConfig from '../../../config/goe'
  export default {
    mounted: function () {
      const userObj = JSON.parse(window.localStorage.getItem('User'))
      this.user = userObj
      this.consumeCoin = userObj.consumeCoin
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Card,
      FormPreview,
      Search,
      Divider,
      Panel,
      Msg,
      Message,
      PayPassword
    },
    data () {
      return {
        user: '',
        consumeCoin: 2000,
        isFinded: false,
        isNotFinded: false,
        receiveAccount: '',
        transferNumber: '',
        payPassword: '',
        showPayPasswordStatus: false,
        type: '1',
        list: [{
          src: require('../../assets/images/person.png'),
          title: '张三 [组长]',
          desc: 'A:20  B:20:  C:0'
        }],
        list2: [{
          label: '收款用户',
          value: 'admin'
        }],
        buttons2: [{
          style: 'primary',
          text: '转账',
          onButtonClick: (name) => {
            this.showPayPasswordStatus = true
          }
        }]
      }
    },
    methods: {
      findUser () {
        if (this.receiveAccount.length > 0) {
          this.getPerformance()
        }
      },
      submitPay (payPassword) {
        this.showPayPasswordStatus = false
        this.payPassword = payPassword
        this.doTransfer()
      },
      cancelPay () {
        console.log('close')
        this.showPayPasswordStatus = false
      },
      getPerformance () {
        const url = GoeConfig.apiServer + '/user/performance?account=' + this.receiveAccount
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
            alert('请求超时')
          }
        })
          .then(response => {
            if (response.body.success) {
              const info = response.body.data
              this.isFinded = true
              this.isNotFinded = false
              this.list[0].title = this.receiveAccount + ' [' + info.userLevel + ']'
              this.list[0].desc = 'A:' + info.performance.departAcount + '  B:' + info.performance.departBcount + ':  C:' + info.performance.departCcount
              this.list2[0].value = this.receiveAccount
              console.log(this.performance)
            } else {
              if (response.body.message === '当前用户不存在!') {
                this.isFinded = false
                this.isNotFinded = true
              } else {
                alert(response.body.message)
              }
            }
          }, responseErr => {
            alert('未知错误')
          })
      },
      doTransfer () {
        const url = GoeConfig.apiServer + '/consumerRecord/save'
        this.$http.post(url,
          {
            sendUserAccount: this.user.account,
            receiveUserAccount: this.receiveAccount,
            consumeTypeCode: 3,
            consumeNumber: this.transferNumber,
            paymentPassword: this.payPassword,
            description: '用户:' + this.user.account + '转帐报单币' + this.transferNumber + '元到用户' + this.receiveAccount
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
              alert('请求超时')
            }
          })
          .then(response => {
            if (response.body.success) {
              this.updateUser()
              this.showPayPasswordStatus = false
              alert('转换成功')
            } else {
              alert(response.body.message || '未知错误')
            }
          }, responseErr => {
            alert('未知错误')
          })
      },
      updateUser () {
        const url = GoeConfig.apiServer + '/user/findByAccount?account=' + this.user.account
        this.$http.get(url,
          {
            _timeout: 3000,
            onTimeout: (request) => {
              alert('请求超时')
            }
          })
          .then(response => {
            if (response.body.success) {
              this.consumeCoin = response.body.data.consumeCoin
              this.isFinded = false
              this.isNotFinded = false
              window.localStorage.setItem('User', JSON.stringify(response.body.data))
            } else {
              this.errMsg = response.body.message
            }
          }, responseErr => {
            console.log(responseErr)
            this.errMsg = '未知错误'
          })
      }
    },
    computed: {
      isInput: function () {
        return (Number(this.transferNumber) > 0 && this.transferNumber <= this.consumeCoin)
      }
    },
    watch: {
      transferNumber (newValue, oldValue) {
        this.showConvertNumber = '￥' + newValue
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
