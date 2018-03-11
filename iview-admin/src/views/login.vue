<style lang="less">
    @import './login.less';
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
        <div class="login-con">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    欢迎登录
                </p>
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="userName">
                            <Input v-model="form.userName" placeholder="请输入用户名">
                                <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="password">
                            <Input type="password" v-model="form.password" placeholder="请输入密码">
                                <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem>
                            <Button @click="handleSubmit" type="primary" long>登录</Button>
                        </FormItem>
                    </Form>
                    <p class="login-tip">输入任意用户名和密码即可</p>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
import Cookies from 'js-cookie';
export default {
    data () {
        return {
            form: {
                userName: '',
                password: ''
            },
            rules: {
                userName: [
                    { required: true, message: '账号不能为空', trigger: 'blur' },
                    {min: 6, max: 20, message: '用户编号长度为6-20位字符', trigger: 'blur'}
                ],
                password: [
                    { required: true, message: '密码不能为空', trigger: 'blur' },
                    {min: 6, max: 20, message: '密码长度为6-20位字符', trigger: 'blur'}
                ]
            }
        };
    },
    methods: {
        handleSubmit () {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    console.log('do login');
                    var requestOption = {
                        url: this.APIServer + '/user/login',
                        params: {
                            account: this.form.userName,
                            password: this.form.password
                        }
                    }
                    this.doPost(requestOption).then(result => {
                        if (result.success) {
                            if (result.data.userType === '公司') {
                                Cookies.set('user', this.form.userName);
                                Cookies.set('password', this.form.password);
                                this.$store.commit('setAvator', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3448484253,3685836170&fm=27&gp=0.jpg');
                                this.$router.push({
                                    name: 'home_index'
                                });
                                this.$Message.success('登陆成功!');
                            } else {
                                this.form.password = '';
                                this.$Message.error('您没有权限登陆管理后台!');
                            }
                        } else {
                            this.form.password = '';
                            this.$Message.error(result.message);
                        }
                    });

                }
            });
        }
    }
};
</script>

<style>

</style>
