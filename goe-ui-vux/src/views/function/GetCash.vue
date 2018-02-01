<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '奖金提现'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>{{bonusCoin}}</span>
          <br/>
          奖金
        </div>
      </div>
    </card>
    <div v-if="isHasCards">
      <group title="提现到">
        <selector ref="defaultValueRef" title="选择银行卡" :options="cardsInfo" v-model="selectCardId"></selector>
        <x-input title="手机号" type="number" is-type="china-mobile" placeholder="请输入手机号" v-model="tel" :min="11"
                 :max="11">
          <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-tel.png" width="24" height="24">
        </x-input>
        <x-input title="￥" type="number" placeholder="最小提现金额100元" v-model="getNumber" :min="1" :max="12"></x-input>
      </group>
      <div v-if="isInput">
        <form-preview header-label="" header-value="" :body-items="formView"
                      :footer-buttons="getCashButton"></form-preview>
      </div>
    </div>
    <div v-else>
      <group>
        <message title="未绑定银行卡" description="请先添加银行卡再进行提现"></message>
        <cell title='添加银行卡' is-link @click.native="addCard">
          <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/plus.png">
        </cell>
      </group>
    </div>
    <pay-password :showPayPasswordInput="showPayPasswordStatus" @paySubmitEvent="submitPay" @cancelPayEvent="cancelPay"></pay-password>
  </div>
</template>

<script>
  import { XHeader, XInput, Group, XButton, Card, FormPreview, Selector, Cell } from 'vux'
  import Message from '../../components/msg'
  import GoeConfig from '../../../config/goe'
  import PayPassword from '../../components/PayPassword.vue'

  export default {
    mounted: function () {
      this.bonusCoin = JSON.parse(window.localStorage.getItem('User')).bonusCoin
      this.getCardList()
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Card,
      FormPreview,
      Message,
      Selector,
      Cell,
      PayPassword
    },
    data () {
      return {
        bonusCoin: 0,
        selectCardId: '',
        getNumber: '',
        showConvertNumber: '',
        showPayPasswordStatus: false,
        tel: '',
        cards: [],
        formView: [{
          label: '提现金额',
          value: ''
        }, {
          label: '手续费',
          value: '￥10元'
        }, {
          label: '实际到账金额',
          value: ''
        }],
        getCashButton: [{
          style: 'primary',
          text: '提现',
          onButtonClick: () => {
            this.confirmGetCash()
          }
        }]
      }
    },
    methods: {
      submitPay (payPassword) {
        this.showPayPasswordStatus = false
        this.doGetCash(payPassword)
      },
      cancelPay () {
        console.log('close')
        this.showPayPasswordStatus = false
      },
      confirmGetCash () {
        if (this.selectCard === '' || this.tel === '') {
          this.$vux.toast.show({
            type: 'text',
            width: '17em',
            text: '请选择银行卡信息,并输入手机号码'
          })
        } else if (this.tel.length !== 11) {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '手机号码格式不正确'
          })
        } else {
          const _this = this
          this.$vux.confirm.show({
            // 组件除show外的属性
            title: '确定进行提现?',
            onCancel () {
              console.log(this) // 非当前 vm
              console.log(_this) // 当前 vm
            },
            onConfirm () {
              _this.showPayPasswordStatus = true
              console.log('进行交易密码认证')
            }
          })
        }
      },
      doGetCash (payPassword) {
        console.log('do get cash')
        const url = GoeConfig.apiServer + '/drawCashRecord/save'
        this.$http.post(url,
          {
            account: JSON.parse(window.localStorage.getItem('User')).account,
            drawNumber: this.getNumber,
            phone: this.tel,
            paymentPassword: payPassword,
            cardInfoId: this.selectCardId
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.success) {
              this.$vux.toast.show({
                text: '申请成功'
              })
              window.history.go(-1)
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
      },
      getCardList () {
        const url = GoeConfig.apiServer + '/cardInfo/findCardInfoByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.totalElements > 0) {
              const cardsInfo = response.body.content
              this.cards.splice(0, this.cards.length)
              for (let i = 0; i < cardsInfo.length; i++) {
                var card = {}
                card.id = cardsInfo[i].cardInfoId
                card.ownerName = cardsInfo[i].cardOwnerName
                card.bankName = cardsInfo[i].bankName
                card.cardNumber = cardsInfo[i].cardNumber
                this.cards[i] = card
              }
            } else {
              this.cards.splice(0, this.cards.length)
              console.log('no cards')
            }
          }, responseErr => {
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
          })
      },
      getCashSubmit () {
        console.log('get cash')
      },
      addCard () {
        this.$router.push({name: 'addBankCard'})
      }
    },
    computed: {
      isInput: function () {
        return this.getNumber >= 100 && this.getNumber < this.bonusCoin
      },
      isHasCards: function () {
        return (this.cards && this.cards.constructor === Array && this.cards.length !== 0)
      },
      cardsInfo: function () {
        if (this.cards && this.cards.constructor === Array && this.cards.length !== 0) {
          const infos = new Array([])
          const tmpcards = this.cards
          for (let i = 0; i < tmpcards.length; i++) {
            infos[i] = {
              key: tmpcards[i].id,
              value: tmpcards[i].ownerName + ' ' + tmpcards[i].bankName + ' ' + tmpcards[i].cardNumber.substr(tmpcards[i].cardNumber.length - 4, 4)
            }
          }
          return infos
        } else {
          return null
        }
      }
    },
    watch: {
//      selectCard (newValue, oldValue) {
//        console.log(newValue)
//      }
      getNumber (newValue) {
        this.formView[0].value = '￥' + newValue + '元'
        this.formView[2].value = '￥' + (newValue - 10) + '元'
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
