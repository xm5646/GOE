<template>
  <div class="page">
    <simple-header title="会员管理系统" ></simple-header>
    <page-content>
        <v-table :rows="rows" :pageInfo="pageInfo" @changePageEvent="changePage">

        </v-table>
    </page-content>
  </div>
</template>

<script>
  import { SimpleHeader } from '../../../node_modules/vum/src/components/header'
  import Content from '../../../node_modules/vum/src/components/content'
  import Scroll from '../../../node_modules/vum/src/components/scroll'
  import VTable from '../../components/Table'

  export default {
    components: {
      SimpleHeader,
      'page-content': Content,
      Scroll,
      VTable
    },
    data () {
      return {
        time: new Date(),
        rows: [
          {
            totalCoin: 1000,
            bonusCoin: 111,
            productCoin: 111,
            managementCoin: 11,
            payTime: '2018-1-14'
          },
          {
            totalCoin: 1000,
            bonusCoin: 111,
            productCoin: 111,
            managementCoin: 11,
            payTime: '2018-1-14'
          }
        ],
        pageInfo: {
          totalPageNum: 4,
          pageNum: 1
        }
      }
    },
    methods: {
      onRefresh (done) {
        let self = this
        setTimeout(function () {
          self.time = new Date()
          done()  // call done
        }, 2000)
      },
      onInfinite (done) {
        console.log('infinite')
        setTimeout(function () {
          var f = document.createDocumentFragment()
          for (let i = 0; i < 10; i++) {
            let p = document.createElement('p')
            p.textContent = 'Write some HTML, grab some JSON, create a Vue instance, that\'s it.'
            f.appendChild(p)
          }
          document.getElementById('list').appendChild(f)
          done()  // call done()
        }, 2000)
      },
      changePage (data) {
        this.pageInfo.pageNum = data
        console.log('跳转到' + data + '页')
      }
    }
  }
</script>
