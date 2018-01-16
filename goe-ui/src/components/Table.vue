<template>
  <div class="page">
    <page-content>
      <table class="imagetable">
        <tr>
          <th>总收入</th><th>奖金</th><th>产品积分</th><th>综合费</th><th>领取时间</th>
        </tr>
        <tr v-for="(row, index) in rows">
          <td> {{ row.totalCoin }}</td>
          <td> {{ row.bonusCoin }}</td>
          <td> {{ row.productCoin }}</td>
          <td> {{ row.managementCoin }}</td>
          <td> {{ row.payTime }}</td>
        </tr>
        <tr >
          <td style="border: none">

          </td>
          <td style="border: none">

          </td >
          <td style="border: none">
            当前第 {{ pageInfo.pageNum }}页
          </td>
          <td style="border: none">
            共 {{ pageInfo.totalPageNum }}页
          </td>
          <td style="border: none">

          </td>
        </tr>
      </table>

      <div class="content-block">
        <button-group :bordered="true" size="small">
          <m-button :disabled="isFirstPage" @click.native="changePage('firstPage')">首页</m-button>
          <m-button :disabled="isFirstPage" @click.native="changePage('prePage')">上一页</m-button>
          <m-button :disabled="isEndPage" @click.native="changePage('nextPage')">下一页</m-button>
          <p><m-button  :disabled="isEndPage" @click.native="changePage('lastPage')">末页</m-button></p>
        </button-group>
      </div>
    </page-content>
  </div>

</template>

<script>
  import Grid from '../../node_modules/vum/src/components/grid'
  import { Button, ButtonGroup } from '../../node_modules/vum/src/components/buttons'
  import Content from '../../node_modules/vum/src/components/content'
  export default {
    props: ['rows', 'pageInfo'],
    components: {
      'm-button': Button,
      ButtonGroup,
      Grid,
      'page-content': Content
    },
    data () {
      return {
      }
    },
    methods: {
      changePage (data) {
        if (data === 'firstPage' && !this.isFirstPage) {
          this.$emit('changePageEvent', 1)
        } else if (data === 'lastPage' && !this.isEndPage) {
          this.$emit('changePageEvent', this.pageInfo.totalPageNum)
        } else if (data === 'prePage' && !this.isFirstPage) {
          this.$emit('changePageEvent', this.pageInfo.pageNum - 1)
        } else if (data === 'nextPage' && !this.isEndPage) {
          this.$emit('changePageEvent', this.pageInfo.pageNum + 1)
        }
      }
    },
    computed: {
      isEndPage: function () {
        return this.pageInfo.pageNum === this.pageInfo.totalPageNum
      },
      isFirstPage: function () {
        return this.pageInfo.pageNum === 1
      }
    }
  }
</script>

<style>
  table.imagetable {
    font-family: verdana,arial,sans-serif;
    font-size:11px;
    color:#333333;
    border-width: 1px;
    border-color: #999999;
    border-collapse: collapse;
    width: 100%;
  }
  table.imagetable th {
    border-width: 1px;
    padding: 8px;
    border-style: solid;
    border-color: #999999;
    background-color: aliceblue;
  }
  table.imagetable td {
    border-width: 1px;
    padding: 8px;
    border-style: solid;
    border-color: #999999;
  }
</style>
