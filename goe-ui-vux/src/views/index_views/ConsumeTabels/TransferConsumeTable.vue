<template>
  <div class="divFont">
    <x-table :cell-bordered="false" :content-bordered="true" style="background-color:#fff;">
      <thead>
      <tr style="background-color: darkgray">
        <th>转账金额(报单币)</th>
        <th>转账日期</th>
        <th>收款用户编号</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="row in rows">
        <td>{{row.consumeNumber}}</td>
        <td>{{row.consumeTime}}</td>
        <td>{{row.description}}</td>
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
    props: ['list'],
    data () {
      return {
        rows: [],
        totalPageNum: 1
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
      getPage (pageNum) {
        console.log(pageNum)
        const url = GoeConfig.apiServer + '/consumerRecord/findByAccountAndConsumeType?account=' + JSON.parse(window.localStorage.getItem('User')).account + '&consumeTypeCode=3' + '&pageNum=' + (pageNum - 1)
        this.$http.get(url,
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            console.log(response.body)
            if (response.body.totalElements > 0) {
              this.totalPageNum = response.body.totalPages
              console.log(response.body.content)
              const GetNumber = response.body.content.length
              this.rows.splice(0, this.rows.length)
              for (var i = 0; i < GetNumber; i++) {
                const payDate = response.body.content[i].consumeTime
                const showDate = new Date(parseInt(payDate)).toLocaleDateString()
                response.body.content[i].consumeTime = this.formatDate(showDate)
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
<style scoped>
  .divFont {
    font-size: small;
  }
</style>

