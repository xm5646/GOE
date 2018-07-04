<template>
  <div>
    <p class="dialog-title">通知 <span v-if="totalPage > 1">({{pageNo}}/{{totalPage}})</span></p>
    <div style="height:250px;padding:15px 0;overflow:scroll;-webkit-overflow-scrolling:touch;background-color: whitesmoke">
      <h4>{{notice.title}}</h4>
      <P style="font-size: smaller; color: gray;">发布时间:{{notice.createTime}}</P>
      <p style="font-size: small; text-align: left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        {{notice.content}}
        </p>
    </div>
    <div style="margin-bottom: 10px;margin-top: 5px">
      <span v-if="totalPage > 1">
        <x-button mini @click.native="previous" :disabled="isFirst">上一条</x-button>&nbsp;
      </span>
      <x-button mini @click.native="closePanel">知道了</x-button>&nbsp;
      <span v-if="totalPage > 1">
        <x-button mini @click.native="next" :disabled="isLast">下一条</x-button>
      </span>
    </div>
  </div>
</template>
<script>
  import { TransferDom, Popup, Group, XInput, XButton } from 'vux'
  export default {
    beforeMount: function () {
      this.totalPage = this.noticesList.length
      this.notice = this.noticesList[0]
      this.notice.createTime = this.getDateStr(this.notice.createTime)
      if (this.totalPage > 1) {
        this.isLast = false
      } else {
        this.isLast = true
      }
    },
    directives: {
      TransferDom
    },
    props: ['noticesList'],
    data () {
      return {
        password: '',
        isFirst: true,
        isLast: false,
        pageNo: 1,
        totalPage: 3,
        notice: null
      }
    },
    watch: {
      pageNo: function (val) {
        this.notice = this.noticesList[val - 1]
        this.notice.createTime = this.getDateStr(this.notice.createTime)
        if (val < this.totalPage) {
          this.isLast = false
        } else {
          this.isLast = true
        }
        if (val > 1) {
          this.isFirst = false
        } else {
          this.isFirst = true
        }
      }
    },
    methods: {
      closePanel () {
        this.$emit('closePanelEvent')
      },
      previous () {
        if (this.pageNo > 1) {
          this.pageNo = this.pageNo - 1
        }
      },
      next () {
        if (this.pageNo < this.totalPage) {
          this.pageNo = this.pageNo + 1
        }
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
    },
    components: {
      Popup,
      Group,
      XInput,
      XButton
    }
  }
</script>
<style scoped>

</style>
