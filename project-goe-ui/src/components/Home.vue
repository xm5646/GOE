<template>
    <div>
        张三 【组长】  已激活 <br>
        业绩：<br>
        累计业绩：  A:12  B:10  C:0 <br>
        新增业绩：  A:2   B:2  C:0  <br>
        <button>我的业绩</button><br>
      <button>奖金转换</button><br>
      <button>报单币转账</button><br>
      <button>工资记录</button><br>
      <button>消费记录</button><br>

    </div>
</template>
<script>
export default {
  data () {
    return {
      title: '组件标题',
      firstName: '',
      lastName: ''
    }
  }
}
</script>
<style scoped>
    /**使用scss编写样式，既可提高开发效率，也方便维护
*  scoped省略后，该样式片段会应用到页面全局
*  支持import语法引入css文件
*/
</style>
