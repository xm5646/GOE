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
                console.log('logout');
            }
        }
    };
</script>
