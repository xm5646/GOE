<template>
    <div>
        <Form ref="rechargeForm"  :rules="rules" :model="form" :label-width="80">
            <FormItem label="用户编号">
                <!--<Input style="width: 200px" disabled v-model="userInfo.user_account" placeholder="Enter something..."></Input>-->
                <span>{{userInfo.user_account}}</span>
            </FormItem>
            <FormItem label="用户昵称">
                <!--<Input style="width: 200px" disabled v-model="userInfo.user_account" placeholder="Enter something..."></Input>-->
                <span>{{userInfo.user_nickName}}</span>
            </FormItem>
            <FormItem label="充值类型">
                <RadioGroup v-model="coinType" type="button" ref="coinType1">
                    <Radio label="consumeCoin">报单币</Radio>
                    <Radio label="bonusCoin">奖金</Radio>
                    <Radio label="productCoin">产品积分</Radio>
                </RadioGroup>
            </FormItem>
            <FormItem prop="rechargeNumber" label="金额">
                <Input style="width: 200px" v-model="form.rechargeNumber" placeholder="请输入充值金额"></Input>
                <!--<span>{{userInfo.user_account}}</span>-->
            </FormItem>
            <FormItem label="补发/扣除">
                <RadioGroup v-model="rechargeType" type="button">
                    <Radio label="plus">加</Radio>
                    <Radio label="subtract">减</Radio>
                </RadioGroup>
            </FormItem>
            <FormItem prop="payPassword" label="交易密码">
                <Input style="width: 200px" type="password" v-model="form.payPassword" placeholder="请输入交易密码"></Input>
            </FormItem>
            <FormItem label="">
                <Button type="primary" @click="rechargeConfirm">充值</Button>
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
        data() {
            return {
                form: {
                    rechargeNumber: '',
                    payPassword: ''
                },
                nickName: '',
                coinType: 'consumeCoin',
                rechargeType: 'plus',
                rules: {
                    rechargeNumber: [
                        { required: true, message: '请输入充值金额!', trigger: 'blur' },
                        {type: 'number', message: '充值金额必须为数字', trigger: 'blur'}
                    ],
                    payPassword: [
                        { required: true, message: '请输入交易密码!', trigger: 'blur' },
                        {len: 6, message: '交易密码为6位数字!', trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            rechargeConfirm: function () {
                this.$refs.rechargeForm.validate(valid => {
                    if (valid) {
                        this.$Modal.confirm({
                            title: '确定充值?',
                            content: '为用户' + this.userInfo.user_account + '充值' + this.coinTypeCN + ' ' + this.form.rechargeNumber + ' ?',
                            onOk: function () {
                                console.log('recharge success');
                            }
                        });
                    }
                });
            }
        },
        computed: {
            coinTypeCN: function () {
                if (this.coinType === 'consumeCoin') {
                    return '报单币';
                } else if (this.coinType === 'bonusCoin') {
                    return '奖金';
                } else if (this.coinType === 'productCoin') {
                    return '产品积分';
                }
            }
        }
    };
</script>