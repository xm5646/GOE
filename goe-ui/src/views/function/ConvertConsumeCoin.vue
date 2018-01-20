<template>
  <div class="page">
    <simple-header style="background-color: orange" title="会员管理系统" :back-link="true"></simple-header>
    <page-content>
      <div class='content-padded'>
        <h1 class="demos-title">转换保单币</h1>
        <list>
          <list-item>
            <div class="item-media"><img src="../../assets/images/rmb.png" width="44"></div>
            <div class="item-content">
              <div class="item-title-row">
                <div class="item-title">奖金余额</div>
              </div>
              <div class="item-subtitle">
                <span class="span-performance">{{ currentUser.bonusCoin }}元</span>
              </div>
            </div>
          </list-item>
          <list-item>
            <div class="item-media"><img src="../../assets/images/rmb.png" width="44"></div>
            <div class="item-content">
              <div class="item-title-row">
                <div class="item-title">报单币余额</div>
              </div>
              <div class="item-subtitle">
                <span class="span-performance">{{ currentUser.consumeCoin.toFixed(2) }}元</span>
              </div>
            </div>
          </list-item>
        </list>
        <form-list>
          <form-item>
            <div class="row">
              <div class="col-60"><span>转换奖金金额￥：</span></div>
              <div class="col-40" ><input type="number" v-model="convertNumber"></div>
            </div>
          </form-item>
          <form-item>
            <div class="row">
              <div class="col-50"><span>剩余奖金金额：{{ finalBonusCoin.toFixed(2) }}</span></div>
              <div class="col-50" ><span>报单币总金额：{{ finalConsumeCoin.toFixed(2) }}</span></div>
            </div>
          </form-item>

        </form-list>
        <m-button type="warning" @click.native="ConvertConsumeCoin" :disabled="convertBtnEnable">转换</m-button>
        <toast text="完成!" ref="t1"></toast>
        <toast text="失败!" type="error" ref="t2"></toast>
      </div>
    </page-content>
  </div>
</template>

<script>
  import { Button, ButtonGroup } from '../../../node_modules/vum/src/components/buttons'
  import { SimpleHeader } from '../../../node_modules/vum/src/components/header'
  import Content from '../../../node_modules/vum/src/components/content'
  import { Form, FormItem } from '../../../node_modules/vum/src/components/form'
  import Toast from '../../../node_modules/vum/src/components/toast'
  import Column from '../../../node_modules/vum/src/components/column'
  import { List, ListItem } from '../../../node_modules/vum/src/components/list'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
      this.currentUser = JSON.parse(window.localStorage.getItem('User'))
    },
    components: {
      SimpleHeader,
      'page-content': Content,
      ButtonGroup,
      FormItem,
      Toast,
      Column,
      List,
      ListItem,
      'form-list': Form,
      'm-button': Button
    },
    data () {
      return {
        convertNumber: '',
        currentUser: {
          bonusCoin: 0,
          consumeCoin: 0
        }
      }
    },
    methods: {
      ConvertConsumeCoin () {
        if (!this.convertBtnEnable) {
          this.isErr = false
          const url = GoeConfig.apiServer + '/consumerRecord/save'
          this.$http.post(url,
            {
              sendUserAccount: this.currentUser.account,
              receiveUserAccount: this.currentUser.account,
              consumeTypeCode: 2,
              consumeNumber: this.convertNumber,
              description: '用户:' + this.currentUser.account + '使用奖金转换报单币'
            },
            {
              _timeout: 3000,
              onTimeout: (request) => {
                this.isErr = true
                this.errMsg = '请求超时'
                this.password = ''
                this.SecondPassword = ''
              }
            })
            .then(response => {
              if (response.body.success) {
                this.convertNumber = ''
                this.isErr = false
                this.updateUser()
                this.$refs.t1.open()
              } else {
                this.isErr = true
                this.errMsg = (response.body.message || '未知错误')
                this.password = ''
                this.SecondPassword = ''
              }
            }, responseErr => {
              this.isErr = true
              this.errMsg = '未知错误'
              this.password = ''
              this.SecondPassword = ''
            })
        } else {
        }
      },
      updateUser () {
        const url = GoeConfig.apiServer + '/user/findByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
        this.$http.get(url,
          {
            _timeout: 3000,
            onTimeout: (request) => {
              this.errMsg = '请求超时'
            }
          })
          .then(response => {
            if (response.body.success) {
              this.currentUser = response.body.data
              window.localStorage.setItem('User', JSON.stringify(response.body.data))
            } else {
              this.errMsg = response.body.message
            }
          }, responseErr => {
            this.errMsg = '未知错误'
          })
      }
    },
    computed: {
      convertBtnEnable: function () {
        return (this.convertNumber <= 0 || this.convertNumber > this.currentUser.bonusCoin)
      },
      finalConsumeCoin: function () {
        return Number(this.currentUser.consumeCoin) + Number(this.convertNumber)
      },
      finalBonusCoin: function () {
        return Number(this.currentUser.bonusCoin) + Number(this.convertNumber)
      }
    }
  }
</script>

<style lang="less" scoped>
  p {
    text-align: center;
  }
  .row{
    margin-bottom: 2%;
  }
</style>
