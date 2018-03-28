<template>
    <div @click="handleChange" v-if="true" class="full-screen-btn-con">
        <Tooltip content="重置密码" placement="bottom">
            <Icon type="refresh" :size="23"></Icon>
        </Tooltip>
        <Modal :width="500" v-model="showModal" @on-ok="submitReset" title="重置密码">
            <Tabs value="name1">
                <TabPane label="更改登陆密码" name="name1">
                    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100">
                        <FormItem label="原密码" prop="oldPassword">
                            <Input type="password" v-model="formValidate.oldPassword" style="width: 200px" placeholder=""></Input>
                        </FormItem>
                        <FormItem label="新密码" prop="newPassword">
                            <Input type="password" v-model="formValidate.newPassword" style="width: 200px" placeholder=""></Input>
                        </FormItem>
                        <FormItem label="确认新密码" prop="confirmNewPassword">
                            <Input type="password" v-model="formValidate.confirmNewPassword" style="width: 200px" placeholder=""></Input>
                        </FormItem>
                        <FormItem>
                            <Button type="primary" @click="handleSubmit('formValidate')">重置密码</Button>
                            <Button type="ghost" @click="handleReset('formValidate')" style="margin-left: 8px">重填</Button>
                        </FormItem>
                    </Form>
                </TabPane>
                <TabPane label="更改交易密码" name="name2">
                    <Form ref="formValidate2" :model="formValidate2" :rules="ruleValidate2" :label-width="120">
                        <FormItem label="原交易密码" prop="oldPayPassword">
                            <Input type="password" v-model="formValidate2.oldPayPassword" style="width: 200px" placeholder=""></Input>
                        </FormItem>
                        <FormItem label="新交易密码" prop="newPayPassword">
                            <Input type="password" v-model="formValidate2.newPayPassword" style="width: 200px" placeholder=""></Input>
                        </FormItem>
                        <FormItem label="确认新交易密码" prop="confirmNewPayPassword">
                            <Input type="password" v-model="formValidate2.confirmNewPayPassword" style="width: 200px" placeholder=""></Input>
                        </FormItem>
                        <FormItem>
                            <Button type="primary" @click="handleSubmit2('formValidate2')">重置密码</Button>
                            <Button type="ghost" @click="handleReset2('formValidate2')" style="margin-left: 8px">重填</Button>
                        </FormItem>
                    </Form>
                </TabPane>
            </Tabs>

        </Modal>
    </div>
</template>

<script>
    export default {
        name: 'fullScreen',
        data () {
            const validateMobile = (rule, value, callback) => {
                if (!Number.isInteger(+value)) {
                    callback(new Error('请输入6位纯数字值'));
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
                formValidate2: {
                    oldPayPassword: '',
                    newPayPassword: '',
                    confirmNewPayPassword: ''
                },
                ruleValidate: {
                    oldPassword: [
                        {required: true, message: '原密码不能为空', trigger: 'blur'},
                        {min: 6, max: 20, message: '原密码长度不合法,请输入6-20位字符', trigger: 'blur'},
                    ],
                    newPassword: [
                        {required: true, message: '新密码不能为空', trigger: 'blur'},
                        {min: 6, max: 20, message: '原密码长度不合法,请输入6-20位字符', trigger: 'blur'},
                    ],
                    confirmNewPassword: [
                        {required: true, message: '确认新密码不能为空', trigger: 'blur'},
                        {min: 6, max: 20, message: '原密码长度不合法,请输入6-20位字符', trigger: 'blur'},
                    ]
                },
                ruleValidate2: {
                    oldPayPassword: [
                        {required: true, message: '原交易密码不能为空', trigger: 'blur'},
                        {len: 6, message: '原交易密码长度不合法,请输入6位数字', trigger: 'blur'},
                        {validator: validateMobile, trigger: 'blur'}
                    ],
                    newPayPassword: [
                        {required: true, message: '新交易密码不能为空', trigger: 'blur'},
                        {len: 6, message: '原交易密码长度不合法,请输入6位数字', trigger: 'blur'},
                        {validator: validateMobile, trigger: 'blur'}
                    ],
                    confirmNewPayPassword: [
                        {required: true, message: '确认新交易密码不能为空', trigger: 'blur'},
                        {len: 6, message: '原交易密码长度不合法,请输入6位数字', trigger: 'blur'},
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
                        if (this.formValidate.newPassword !== this.formValidate.confirmNewPassword) {
                            this.$Message.error('两次输入的新密码不一致!');
                        } else {
                            this.doPost({
                                url: this.APIServer + '/goeIndexUserManagement/updatePasswordOfAdmin',
                                params: {
                                    oldLoginPassword: this.formValidate.oldPassword,
                                    newLoginPassword: this.formValidate.newPassword
                                }
                            }).then(result => {
                                if (result.success) {
                                    this.handleReset('formValidate');
                                    this.showModal = false;
                                    this.$Message.success(result.message);
                                } else {
                                    this.$Message.error(result.message);
                                }
                            });
                        }
                    } else {
                        this.$Message.error('请完整填写信息!');
                    }
                });
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            },
            handleSubmit2 (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        if (this.formValidate2.newPayPassword !== this.formValidate2.confirmNewPayPassword) {
                            this.$Message.error('两次输入的新交易密码不一致!');
                        } else {
                            this.doPost({
                                url: this.APIServer + '/goeIndexUserManagement/updatePasswordOfAdmin',
                                params: {
                                    oldPaymentPassword: this.formValidate2.oldPayPassword,
                                    newPaymentPassword: this.formValidate2.newPayPassword
                                }
                            }).then(result => {
                                if (result.success) {
                                    this.handleReset('formValidate');
                                    this.showModal = false;
                                    this.$Message.success(result.message);
                                } else {
                                    this.$Message.error(result.message);
                                }
                            });
                        }
                    } else {
                        this.$Message.error('请完整填写信息!');
                    }
                });
            },
            handleReset2 (name) {
                this.$refs[name].resetFields();
            }
        }
    };
</script>
