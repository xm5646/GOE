<template>
    <div>
        <Form ref="rechargeForm" :rules="rules" :model="form" :label-width="80">
            <FormItem label="用户编号">
                <!--<Input style="width: 200px" disabled v-model="userInfo.user_account" placeholder="Enter something..."></Input>-->
                <span>{{userInfo.account}}</span>
            </FormItem>
            <FormItem label="用户昵称">
                <!--<Input style="width: 200px" disabled v-model="userInfo.user_account" placeholder="Enter something..."></Input>-->
                <span>{{userInfo.nickName}}</span>
            </FormItem>
            <FormItem label="充值类型">
                <RadioGroup v-model="coinType" type="button" ref="coinType1">
                    <Radio label="consumeCoin">报单币</Radio>
                    <Radio label="bonusCoin">奖金</Radio>
                    <Radio label="productCoin">产品积分</Radio>
                </RadioGroup>
            </FormItem>
            <FormItem prop="rechargeNumber" label="金额">
                <Input style="width: 200px" v-model="form.rechargeNumber" type="text" placeholder="请输入充值金额"></Input>
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
            const validateMobile = (rule, value, callback) => {

                if (!Number.isInteger(+value)) {
                    callback(new Error('请输入数字值!'));
                } else {
                    callback();
                }
            }
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
                        {required: true, message: '请输入充值金额!', trigger: 'blur'},
                        {validator: validateMobile, trigger: 'blur'}
                    ],
                    payPassword: [
                        {required: true, message: '请输入交易密码!', trigger: 'blur'},
                        {len: 6, message: '交易密码为6位数字!', trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            rechargeConfirm: function () {
                let that = this;
                this.$refs.rechargeForm.validate(valid => {
                    if (valid) {
                        this.$Modal.confirm({
                            title: '确定充值?',
                            content: '为用户' + this.userInfo.account + '充值' + this.coinTypeCN + ' ' + this.form.rechargeNumber + ' ?',
                            onOk: function () {
                                if (that.coinType === 'consumeCoin') {
                                    that.rechargeConsumeCoin();
                                } else {
                                    that.rechargeBonusProductCoin();
                                }
                            }
                        });
                    }
                });
            },
            rechargeConsumeCoin() {
                let rechargeNumber = (this.rechargeType === 'plus' ? Number(this.form.rechargeNumber) : Number(0 - this.form.rechargeNumber))
                this.doPost({
                    url: this.APIServer + '/goeIndexCharge/chargeReconsumeCoin',
                    params: {
                        account: this.userInfo.account,
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
            },
            rechargeBonusProductCoin () {
                let rechargeNumber = (this.rechargeType === 'plus' ? Number(this.form.rechargeNumber) : Number(0 - this.form.rechargeNumber))
                this.doPost({
                    url: this.APIServer + '/goeIndexCharge/chargeBonusAndProductCoin',
                    params: {
                        account: this.userInfo.account,
                        paymentPassword: this.form.payPassword,
                        bonus: this.coinType === 'bonusCoin' ? rechargeNumber : 0,
                        productCoin: this.coinType === 'productCoin' ? rechargeNumber : 0
                    }
                }).then(result => {
                    if (result.success) {
                        this.$Message.success(result.message);
                        this.userInfo.productCoin = result.data[1];
                        this.userInfo.bonusCoin = result.data[0];
                    } else {
                        this.$Message.error(result.message);
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