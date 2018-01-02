<template>
    <div>
      {{ msg }} <br>
      用户名：<input type="text"><br>
      密码： <input type="password"><br>
      <button type="button" >登录</button>
    </div>
</template>
<script>
  export default {
    name: 'Login',
    data () {
      return {
        msg: 'please login'
      }
    }
  }
</script>
<style  scoped>

</style>
