<template>
  <div class="divFont">
    <x-table :cell-bordered="false" :content-bordered="true" style="background-color:#fff;font-size: xx-small">
      <thead>
      <tr style="background-color: #d43e2e;color: white">
        <th>消费积分金额</th>
        <th>兑换日期</th>
        <th>描述</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="row in rows">
        <td>{{row.transferCoinNumber}}</td>
        <td>{{row.consumeTime}}</td>
        <td>积分兑换</td>
      </tr>
      </tbody>
    </x-table>
    <pager :value="1" title="" fillable :min="1" :max="totalPageNum" @on-change="change"></pager>
  </div>
</template>
<script>
  import { XHeader, Grid, Panel, Divider, XTable, Tab, TabItem } from 'vux'
  import pager from '../../../components/Pager'
  import XButton from '../../../../node_modules/vux/src/components/x-button/index.vue'
  import GridItem from '../../../../node_modules/vux/src/components/grid/grid-item.vue'
  import Group from '../../../../node_modules/vux/src/components/group/index.vue'
  import GoeConfig from '../../../../config/goe'

  export default {
    mounted: function () {
//      this.getPage(1)
    },
    props: ['list'],
    data () {
      return {
        currentViewTable: '',
        totalPageNum: 1,
        rows: []
      }
    },
    components: {
      Group,
      GridItem,
      XButton,
      XHeader,
      Grid,
      Panel,
      Divider,
      XTable,
      pager,
      Tab,
      TabItem
    },
    methods: {
      change (val) {
        if (val === '') {
        } else {
          console.log('change', val)
          this.getPage(val)
        }
      },
      update () {
        this.getPage(1)
      },
      getPage (pageNum) {
        console.log(pageNum)
        const url = GoeConfig.apiServer + '/consumerRecord/findByAccountAndConsumeType?account=' + JSON.parse(window.localStorage.getItem('User')).account + '&consumeTypeCode=5' + '&pageNum=' + (pageNum - 1)
        this.$http.get(url,
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            console.log(response.body)
            if (response.body.success) {
              this.rows = response.body.data.content
              this.totalPageNum = response.body.data.totalPages
            } else {
              this.$vux.toast.show({
                type: 'cancel',
                text: response.body.message
              })
            }
          }, responseErr => {
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
          })
      },
      getDateStr (timestamp) {
        var date = new Date(timestamp)
        var year = date.getFullYear()
        var month = date.getMonth()
        var day = date.getDate()
        var showMonth = ''
        var showDay = ''
        if (Number(month) < 9) {
          showMonth = '0' + Number(month + 1)
        } else {
          showMonth = Number(month + 1)
        }
        if (Number(day) < 10) {
          showDay = '0' + day
        } else {
          showDay = day
        }
        return year + '-' + showMonth + '-' + showDay
      }
    }
  }
</script>
<style scoped>
  .divFont {
    font-size: small;
  }
</style>
