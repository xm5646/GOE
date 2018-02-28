<template>
    <div @click="handleChange" v-if="true" class="full-screen-btn-con">
        <Tooltip content="重置密码" placement="bottom">
            <Icon type="refresh" :size="23"></Icon>
        </Tooltip>
        <Modal :width="500" v-model="showModal" @on-ok="submitReset" title="重置密码">
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
        </Modal>
    </div>
</template>

<script>
    export default {
        name: 'fullScreen',
        data () {
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
                        {min: 5, max: 20, message: '原密码长度不合法,请输入5-20位字符', trigger: 'blur'}
                    ],
                    newPassword: [
                        {required: true, message: '新密码不能为空', trigger: 'blur'},
                        {min: 5, max: 20, message: '原密码长度不合法,请输入5-20位字符', trigger: 'blur'}
                    ],
                    confirmNewPassword: [
                        {required: true, message: '确认新密码不能为空', trigger: 'blur'},
                        {min: 5, max: 20, message: '原密码长度不合法,请输入5-20位字符', trigger: 'blur'}
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
                        this.$Message.success('成功!');
                        this.handleReset('formValidate');
                        this.showModal = false;
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
