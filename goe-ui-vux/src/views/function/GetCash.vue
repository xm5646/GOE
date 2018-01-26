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
        <selector ref="defaultValueRef" title="选择银行卡" :options="cardsInfo" v-model="selectCard"></selector>
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
  </div>
</template>

<script>
  import { XHeader, XInput, Group, XButton, Card, FormPreview, Selector, Cell } from 'vux'
  import Message from '../../components/msg'

  export default {
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Card,
      FormPreview,
      Message,
      Selector,
      Cell
    },
    data () {
      return {
        bonusCoin: 2000,
        selectCard: '',
        getNumber: '',
        tel: '',
        cards: [{
          id: '2',
          ownerName: '李晓明',
          bankName: '招商银行',
          cardNumber: '**** **** **** 7228'
        }, {
          id: '4',
          ownerName: '李晓明',
          bankName: '招商银行',
          cardNumber: '**** **** **** 7228'
        }, {
          id: '8',
          ownerName: '李晓明',
          bankName: '招商银行',
          cardNumber: '**** **** **** 7228'
        }],
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
          onButtonClick: (name) => {
            this.getCashSubmit()
          }
        }]
      }
    },
    methods: {
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
