<template>
  <div class="page">
    <x-header :left-options="{showBack: true, preventGoBack: true}" @on-click-back="backToWallet">会员管理系统</x-header>
    <group title="银行卡信息管理">
      <cell title='添加银行卡' is-link @click.native="addCard">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/plus.png">
      </cell>
    </group>
    <performance-view
      @delCardEvent="confirmDelete"
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
      this.getCardsByAccount()
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
      backToWallet () {
        this.$router.push({name: 'index', params: {view: 'wallet'}})
      },
      addCard () {
        this.$router.push({name: 'addBankCard', params: {cardNumber: this.cards.length}})
      },
      getCardsByAccount () {
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
                card.cardNumber = '**** **** **** ' + cardsInfo[i].cardNumber.substr(cardsInfo[i].cardNumber.length - 4, 4)
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
      confirmDelete (id) {
        const _this = this
        this.$vux.confirm.show({
          // 组件除show外的属性
          title: '确定要删除吗?',
          onCancel () {
            console.log(this) // 非当前 vm
            console.log(_this) // 当前 vm
          },
          onConfirm () {
            this.doDeleteCard(id)
          }
        })
      },
      doDeleteCard (id) {
        console.log('delete card id: ' + id)
        const url = GoeConfig.apiServer + '/cardInfo/delete'
        this.$http.post(url,
          {
            account: JSON.parse(window.localStorage.getItem('User')).account,
            cardInfoId: id
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.message === '银行卡信息删除成功!') {
              this.getCardsByAccount()
              this.$vux.toast.show({
                text: '删除成功'
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
    },
    computed: {},
    watch: {}
  }
</script>

<style lang="less" scoped>
</style>
