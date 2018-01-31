<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="银行卡信息管理">
      <cell title='添加银行卡' is-link @click.native="addCard">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/plus.png">
      </cell>
    </group>
    <performance-view
      :list="cards"
      :type="type">
    </performance-view>


  </div>
</template>
<script>
  import { XHeader, Group, Panel, Divider, Card, Cell } from 'vux'
  import PerformanceView from '../../components/ViewPerformance.vue'
  import GoeConfig from '../../../config/goe'
  export default {
    mounted: function () {
    },
    components: {
      XHeader,
      Group,
      Panel,
      Divider,
      Card,
      Cell,
      PerformanceView
    },
    data () {
      return {
        type: '2',
        cards2: [{
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
        cards: []
      }
    },
    methods: {
      addCard () {
        this.$router.push({name: 'addBankCard'})
      },
      getCardsByAccount (account) {
        const url = GoeConfig.apiServer + '/user/findByAccount?account=' + this.user.account
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.success) {
              const result = response.body.data
              console.log('getuserinfo' + result)
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
    computed: {},
    watch: {}
  }
</script>

<style lang="less" scoped>
</style>
