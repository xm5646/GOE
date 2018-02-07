<template>
  <div class="page">
    <x-header :left-options="{showBack: false}">会员管理系统</x-header>
    <group>
      <div>
        <x-table :cell-bordered="false" :content-bordered="true" style="background-color:#fff; font-size: small">
          <thead>
          <tr style="background-color: #d43e2e; color: white">
            <th>发放总额</th>
            <th>奖金</th>
            <th>产品积分</th>
            <th>综合费</th>
            <th>发放日期</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="row in rows">
            <td>{{row.totalMoney.toFixed(0)}}</td>
            <td>{{row.bonusNumber.toFixed(0)}}</td>
            <td>{{row.productCoinNumber.toFixed(0)}}</td>
            <td>{{row.manageCost.toFixed(0)}}</td>
            <td v-html="row.showPayTime"></td>
          </tr>
          </tbody>
        </x-table>
      </div>
      <pager :value="page" title="" fillable :min="1" :max="totalPageNum" @on-change="change"></pager>
    </group>
  </div>
</template>

<script>
  import { XHeader, Grid, Panel, Divider, XTable } from 'vux'
  import pager from '../../components/Pager'
  import XButton from '../../../node_modules/vux/src/components/x-button/index.vue'
  import GridItem from '../../../node_modules/vux/src/components/grid/grid-item.vue'
  import Group from '../../../node_modules/vux/src/components/group/index.vue'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
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
      pager
    },
    data () {
      return {
        page: 1,
        totalPageNum: 1,
        rows: []
      }
    },
    methods: {
      change (val) {
        if (val === '') {
//          this.getPage(1)
        } else if (val > this.totalPageNum) {
          this.$vux.toast.show({
            type: 'text',
            text: '输入的页面不存在'
          })
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
        const url = GoeConfig.apiServer + '/bonus/findBonusPageByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account + '&pageNum=' + (pageNum - 1)
        this.$http.get(url,
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.totalElements > 0) {
              this.totalPageNum = response.body.totalPages
              console.log(response.body.content)
              const GetNumber = response.body.content.length
              this.rows.splice(0, this.rows.length)
              for (var i = 0; i < GetNumber; i++) {
                response.body.content[i].showPayTime = this.getDateStr(response.body.content[i].payTime)
                this.rows[i] = response.body.content[i]
              }
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
