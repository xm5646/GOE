<template>
  <div v-transfer-dom>
    <popup v-model="showPayPasswordInput" height="270px" is-transparent>
      <div style="width: 95%;background-color:#fff;height:250px;margin:0 auto;border-radius:5px;padding-top:10px;">
        <group title="安全验证">
          <x-input title="密码" type="password" placeholder="请输入六位数字交易密码" v-model="password" :min="6" :max="6"  @on-enter="login">
            <img slot="label" style="padding-right:10px;display:block;" src="../assets/images/form/i-form-password.png" width="24" height="24">
          </x-input>
        </group>
        <div style="padding:20px 15px;">
          <x-button type="primary" @click.native="confirmPay">确定</x-button>
          <x-button @click.native="cancelPay">取消</x-button>
        </div>
      </div>
    </popup>
  </div>
</template>
<script>
  import { TransferDom, Popup, Group, XInput, XButton } from 'vux'
  export default {
    directives: {
      TransferDom
    },
    props: ['showPayPasswordInput'],
    data () {
      return {
        password: ''
      }
    },
    methods: {
      confirmPay () {
        if (this.password.length === 6) {
          this.$emit('paySubmitEvent', this.password)
        } else {
          console.log('is not allowed')
        }
      },
      cancelPay () {
        this.$emit('cancelPayEvent')
      }
    },
    components: {
      Popup,
      Group,
      XInput,
      XButton
    }
  }
</script>
<style scoped>

</style>
