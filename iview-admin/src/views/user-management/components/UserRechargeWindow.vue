<template>
    <div>
                <Form :model="userInfo" :label-width="80">
                    <FormItem label="用户编号">
                        <!--<Input style="width: 200px" disabled v-model="userInfo.user_account" placeholder="Enter something..."></Input>-->
                        <span>{{userInfo.user_account}}</span>
                    </FormItem>
                    <FormItem label="充值类型">
                        <RadioGroup v-model="coinType" type="button" ref="coinType1">
                            <Radio label="consumeCoin">报单币</Radio>
                            <Radio label="bonusCoin">奖金</Radio>
                            <Radio label="productCoin">产品积分</Radio>
                        </RadioGroup>
                    </FormItem>
                    <FormItem label="金额">
                        <Input style="width: 200px"  v-model="rechargeNumber" placeholder="请输入充值金额"></Input>
                        <!--<span>{{userInfo.user_account}}</span>-->
                    </FormItem>
                    <FormItem label="补发/扣除">
                        <RadioGroup v-model="rechargeType" type="button">
                            <Radio label="plus">加</Radio>
                            <Radio label="subtract">减</Radio>
                        </RadioGroup>
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
    data () {
        return {
            nickName: '',
            coinType: 'consumeCoin',
            rechargeType: 'plus',
            rechargeNumber: '',
            formItem: {
                input: '',
                select: '',
                radio: 'male',
                checkbox: [],
                switch: true,
                date: '',
                time: '',
                slider: [20, 50],
                textarea: ''
            }
        };
    },
    methods: {
        rechargeConfirm: function () {
            if (Number(this.rechargeNumber) > 0) {
                this.$Modal.confirm({
                    title: '确定充值?',
                    content: '为用户' + this.userInfo.user_account + '充值' + this.coinTypeCN + ' ' + this.rechargeNumber + ' ?',
                    onOk: function () {
                        console.log('recharge success');
                    }
                });
            } else {
                this.$Modal.warning({
                    title: '提示!',
                    content: '请输入正确的充值金额'
                });
                this.rechargeNumber = '';
            }

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