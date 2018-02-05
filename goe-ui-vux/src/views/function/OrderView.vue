<template>
  <div class="page">
    <x-header :left-options="{showBack: true}">会员管理系统</x-header>
    <group>
      <div>
        <x-table :cell-bordered="false" :content-bordered="true" style="background-color:#fff; font-size: small">
          <thead>
          <tr style="background-color: #d43e2e;color: white">
            <th>订单类型</th>
            <th>订单总额</th>
            <th>购买数量</th>
            <th>发货状态</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="row in rows">
            <td>{{row.orderType}}</td>
            <td>{{row.totalPrice}}</td>
            <td>{{row.productCount}}</td>
            <td>{{row.delivery? '已发货': '未发货'}}</td>
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
      change (val) {
        if (val === '') {
        } else {
          console.log('change', val)
          this.getPage(val)
        }
      },
      getPage (pageNum) {
        console.log(pageNum)
        const url = GoeConfig.apiServer + '/order/findOrderInfoByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account + '&pageNum=' + (pageNum - 1)
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
      formatDate (date) {
        const dates = date.split('/')
        if (dates.length === 3) {
          if (dates[1].length === 1) {
            dates[1] = '0' + dates[1]
          }
          if (dates[2].length === 1) {
            dates[2] = '0' + dates[2]
          }
          date = dates.join('-')
          return date
        } else {
          return null
        }
      }
    }
  }
</script>
