<template>
  <div >
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="添加银行卡">
      <x-input title="持卡人姓名" placeholder="" is-type="china-name" v-model="ownerName">
      </x-input>
      <selector v-model="bankName" placeholder="请选择银行"  :options="bankList" @on-change="changeBank"></selector>
      <x-input title="卡号" type="number"   v-model="cardNumber" :min="16" :max="19">
      </x-input>
      <x-input title="手机号" type="number" is-type="china-mobile" placeholder="请输入手机号" v-model="tel" :min="11"
               :max="11">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-tel.png" width="24" height="24">
      </x-input>
    </group>
    <br>
    <x-button @click.native="addCard">添加</x-button>
  </div>
</template>
<script>
  import { XHeader, XInput, Group, XButton, Selector } from 'vux'
  import GoeConfig from '../../../config/goe'
  export default {
    mounted: function () {
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Selector
    },
    data () {
      return {
        msg: 'Login',
        cardNumber: '',
        tel: '',
        ownerName: '',
        bankName: '',
        bankList: ['农业银行', '建设银行', '工商银行', '中国银行', '中国邮政储蓄银行']
      }
    },
    methods: {
      addCard () {
        if (this.bankName === '' || this.cardNumber === '' || this.ownerName === '' || this.tel === '') {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '请完整输入银行卡相关信息'
          })
        } else if (this.cardNumber.length < 16) {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '银行卡号码格式不正确'
          })
        } else if (this.tel.length !== 11) {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '手机号码格式不正确'
          })
        } else if (this.ownerName.length < 2) {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '持卡人姓名格式不正确'
          })
        } else {
//          this.$vux.toast.show({
//            type: 'text',
//            width: '15em',
//            text: '银行卡相关功能暂时不可用'
//          })
          this.doAddCard()
        }
      },
      changeBank (bank) {
        this.bankName = bank
      },
      doAddCard () {
        console.log('add card')
        const url = GoeConfig.apiServer + '/cardInfo/save'
        this.$http.post(url,
          {
            account: JSON.parse(window.localStorage.getItem('User')).account,
            cardNo: this.cardNumber,
            bankName: this.bankName,
            cardOwnerName: this.ownerName,
            phone: this.tel
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.success) {
              this.$vux.toast.show({
                text: '添加成功'
              })
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
    }
  }
</script>

<style lang="less">
  #img-div {
    text-align: center
  }
</style>
