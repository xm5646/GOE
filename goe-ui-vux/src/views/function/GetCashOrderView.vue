<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" @on-click-back='goWallet'>会员管理系统</x-header>
    <group>
      <div>
        <x-table :cell-bordered="false" :content-bordered="true" style="background-color:#fff; font-size: small">
          <thead>
          <tr style="background-color: #d43e2e;color: white">
            <th>提现金额</th>
            <th>到账金额</th>
            <th>申请时间</th>
            <th>打款状态</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="row in rows">
            <td>{{row.drawnumber.toFixed(0)}}</td>
            <td>{{row.finalNumber.toFixed(0)}}</td>
            <td>{{row.showPayTime}}</td>
            <td>{{row.drawStatus}}</td>
          </tr>
          </tbody>
        </x-table>
      </div>
      <pager :value="1" title="" fillable :min="1" :max="totalPageNum" @on-change="change"></pager>
    </group>
  </div>
</template>

<script>
  import {XHeader, Grid, Panel, Divider, XTable} from 'vux'
  import pager from '../../components/Pager'
  import XButton from '../../../node_modules/vux/src/components/x-button/index.vue'
  import GridItem from '../../../node_modules/vux/src/components/grid/grid-item.vue'
  import Group from '../../../node_modules/vux/src/components/group/index.vue'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
//      this.getPage(1)
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
      goWallet () {
        this.$router.push({name: 'index', params: {view: 'wallet'}})
      },
      change (val) {
        if (val === '') {
        } else {
          console.log('change', val)
          this.getPage(val)
        }
      },
      getPage (pageNum) {
        console.log(pageNum)
        const url = GoeConfig.apiServer + '/drawCashRecord/findDrawCashRecordByUserId?account=' + JSON.parse(window.localStorage.getItem('User')).account + '&pageNum=' + (pageNum - 1)
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
                response.body.content[i].showPayTime = this.getDateStr(response.body.content[i].drawCommitTime)
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
