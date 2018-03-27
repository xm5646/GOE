<template>
    <div @click="handleChange" v-if="true" class="full-screen-btn-con">
        <Tooltip content="退出登录" placement="bottom">
            <Icon type="log-out" :size="23"></Icon>
        </Tooltip>
    </div>
</template>

<script>
    export default {
        name: 'fullScreen',
        methods: {
            handleChange () {
                this.clearCookie();
                this.$router.push({
                    name: 'login'
                });
            },
            clearCookie () {
                var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
                if (keys) {
                    for (var i = keys.length; i--;)
                        document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()
                }
            }
        }
    };
</script>
