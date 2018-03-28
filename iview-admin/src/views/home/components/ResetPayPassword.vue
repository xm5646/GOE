<template>
    <div @click="handleChange" v-if="true" class="full-screen-btn-con">
        <Tooltip content="重置交易密码" placement="bottom">
            <Icon type="refresh" :size="23"></Icon>
        </Tooltip>
        <Modal :width="500" v-model="showModal" @on-ok="submitReset" title="重置密码">
            <Form ref="formValidate2" :model="formValidate" :rules="ruleValidate" :label-width="100">
                <FormItem label="原交易密码" prop="oldPassword">
                    <Input type="password" v-model="formValidate.oldPassword" style="width: 200px" placeholder=""></Input>
                </FormItem>
                <FormItem label="新交易密码" prop="newPassword">
                    <Input type="password" v-model="formValidate.newPassword" style="width: 200px" placeholder=""></Input>
                </FormItem>
                <FormItem label="确认新交易密码" prop="confirmNewPassword">
                    <Input type="password" v-model="formValidate.confirmNewPassword" style="width: 200px" placeholder=""></Input>
                </FormItem>
                <FormItem>
                    <Button type="primary" @click="handleSubmit('formValidate')">重置密码</Button>
                    <Button type="ghost" @click="handleReset('formValidate')" style="margin-left: 8px">重填</Button>
                </FormItem>
            </Form>
        </Modal>
    </div>
</template>

<script>
    import Cookies from 'js-cookie';
    export default {
        name: 'fullScreen',
        data () {
            const validateMobile = (rule, value, callback) => {
                if (!Number.isInteger(+value)) {
                    callback(new Error('请输入数字值!'));
                } else {
                    callback();
                }
            }
            return {
                showModal: false,
                formValidate: {
                    oldPassword: '',
                    newPassword: '',
                    confirmNewPassword: ''
                },
                ruleValidate: {
                    oldPassword: [
                        {required: true, message: '原密码不能为空', trigger: 'blur'},
                        {len: 6, message: '原密码长度不合法,请输入6位数字', trigger: 'blur'}
                        {validator: validateMobile, trigger: 'blur'}
                    ],
                    newPassword: [
                        {required: true, message: '新密码不能为空', trigger: 'blur'},
                        {len: 6, message: '原密码长度不合法,请输入6位数字', trigger: 'blur'}
                        {validator: validateMobile, trigger: 'blur'}
                    ],
                    confirmNewPassword: [
                        {required: true, message: '确认新密码不能为空', trigger: 'blur'},
                        {len: 6, message: '原密码长度不合法,请输入6位数字', trigger: 'blur'}
                        {validator: validateMobile, trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            handleChange () {
                this.showModal = true;
            },
            submitReset () {
                console.log('确定更新');
            },
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.doPost({
                            url: this.APIServer + '/user/updatePaymentPassword',
                            params: {
                                account: Cookies.get('user'),
                                paymentPassword: this.form.payPassword,
                                consumeCoin: rechargeNumber
                            }
                        }).then(result => {
                            if (result.success) {
                                this.$Message.success(result.message);
                                this.userInfo.consumeCoin = result.data;
                            } else {
                                this.$Message.error(result.message);
                            }
                        });
                    } else {
                        this.$Message.error('请完整填写信息!');
                    }
                });
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            }
        }
    };
</script>
