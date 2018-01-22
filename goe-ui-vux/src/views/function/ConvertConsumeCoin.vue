<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '奖金转报单币'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>{{bonusCoin}}</span>
          <br/>
          奖金
        </div>
        <div class="vux-1px-r">
          ￥<span>{{consumeCoin}}</span>
          <br/>
          报单币
        </div>
      </div>
    </card>
    <x-input title="￥" type="number" placeholder="请输入转换金额" v-model="convertNumber" :min="1" :max="12" ></x-input>
    <div v-if="isInput">
      <form-preview header-label="转换金额" :header-value="showConvertNumber" :body-items="list" :footer-buttons="buttons2" name="demo"></form-preview>
    </div>
  </div>
</template>
<script>
  import {XHeader, XInput, Group, XButton, Card, FormPreview} from 'vux'

  export default {
    mounted: function () {
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Card,
      FormPreview
    },
    data () {
      return {
        bonusCoin: 20000,
        consumeCoin: 1000,
        convertNumber: '',
        showConvertNumber: '',
        list: [{
          label: '转换后奖金余额',
          value: '2000'
        }, {
          label: '转换后报单币余额',
          value: '3000'
        }],
        buttons2: [{
          style: 'primary',
          text: '转换',
          onButtonClick: (name) => {
            alert(`clicking ${name}`)
          }
        }]
      }
    },
    methods: {
    },
    computed: {
      isInput: function () {
        return (Number(this.convertNumber) > 0 && this.convertNumber <= this.bonusCoin)
      }
    },
    watch: {
      convertNumber (newValue, oldValue) {
        this.showConvertNumber = '￥' + newValue
        this.list[0].value = '￥' + (this.bonusCoin - newValue)
        this.list[1].value = '￥' + (Number(this.consumeCoin) + Number(newValue))
      }
    }
  }
</script>

<style lang="less" scoped>
  @import '../../../node_modules/vux/src/styles/1px.less';

  .card-demo-flex {
    display: flex;
  }

  .card-demo-content01 {
    padding: 10px 0;
  }

  .card-padding {
    padding: 15px;
  }

  .card-demo-flex > div {
    flex: 1;
    text-align: center;
    font-size: 12px;
  }

  .card-demo-flex span {
    color: #f74c31;
  }
</style>
