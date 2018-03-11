<template>
    <div>
                <Form :model="userInfo" :label-width="80">
                    <FormItem label="用户编号">
                        <!--<Input style="width: 200px" disabled v-model="userInfo.user_account" placeholder="Enter something..."></Input>-->
                        <span>{{userInfo.account}}</span>
                    </FormItem>
                    <FormItem label="姓名">
                        <Input style="width: 200px" v-model="userInfo.nickName" placeholder=""/>
                    </FormItem>
                    <FormItem label="手机号码">
                        <Input style="width: 200px" v-model="userInfo.userPhone" placeholder=""/>
                    </FormItem>
                    <FormItem label="激活状态">
                        <i-switch v-model="userInfo.userStatus === '已激活'? true: false" size="large">
                            <span slot="open">激活</span>
                            <span slot="close">冻结</span>
                        </i-switch>
                    </FormItem>
                    <FormItem label="考核状态">
                        <i-switch v-model="userInfo.assessStatus === '已通过考核'? true: false" size="large">
                            <span slot="open">通过</span>
                            <span slot="close">未完</span>
                        </i-switch>
                    </FormItem>
                </Form>
                <Form :model="userInfo" :label-width="80" inline>
                    <FormItem label="登陆密码" style="margin-right: -60px;">
                        <Input style="width: 200px" v-model="password1" placeholder="重置登陆密码6-20位字符" />
                    </FormItem>
                    <FormItem>
                        <Button type="primary" @click="resetPassword">重置</Button>
                    </FormItem>
                </Form>
                <Form :model="userInfo" :label-width="80" inline>
                    <FormItem label="交易密码" style="margin-right: -60px;">
                        <Input style="width: 200px" v-model="payPassword" placeholder="重置6位数字交易密码"></Input>
                    </FormItem>
                    <FormItem>
                        <Button type="primary" @click="resetPayPassword">重置</Button>
                    </FormItem>
                </Form>
    </div>
</template>

<script>
export default {
    name: 'page',
    props: {
        userInfo: Object
    },
    data () {
        return {
            nickName: '',
            password1: '',
            payPassword: '',
            oriUser: null
        };
    },
    methods: {
        resetPassword () {
            if (this.password.length >= 6 && this.password.length <= 20) {
                this.$Modal.confirm({
                    title: '提示',
                    content: '确定要修改用户 ' + this.userInfo.account + ' 的登录密码?',
                    onOk: this.doUpdatePassword
                });
            } else {
                this.$Modal.warning({
                    content: '输入密码格式不正确!'
                });
            }
        },
        resetPayPassword () {
            if (this.payPassword.length === 6) {
                this.$Modal.confirm({
                    title: '提示',
                    content: '确定要修改用户 ' + this.userInfo.account + ' 的交易密码?',
                    onOk: this.doUpdatePayPassword
                });
            } else {
                this.$Modal.warning({
                    content: '输入交易密码格式不正确!'
                });
            }
        },
        doUpdatePassword () {
            console.log(this.password1);
        },
        doUpdatePayPassword () {
            console.log(this.payPassword);
        }
    }
};
</script>