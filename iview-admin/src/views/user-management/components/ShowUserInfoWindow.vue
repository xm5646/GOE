<template>
    <div>
        <Form :model="userInfo" :label-width="90" inline>
            <FormItem label="用户编号:">
                <!--<Input style="width: 200px" disabled v-model="userInfo.user_account" placeholder="Enter something..."></Input>-->
                <span style="color: #2b85e4">{{userInfo === null ? '' : userInfo.userNodeVO.account}}</span>
            </FormItem>
            <FormItem label="姓名:">
                <span style="color: #2b85e4">{{userInfo === null ? '' : userInfo.userNodeVO.nickName}}</span>
            </FormItem>
            <FormItem label="手机号码:">
                <span style="color: #2b85e4">{{userInfo === null ? '' : userInfo.userNodeVO.userPhone}}</span>
            </FormItem>
            <br>
            <FormItem label="奖金余额:">
                <span style="color: #2b85e4">{{userInfo === null ? '' : userInfo.userNodeVO.bonusCoin}}</span>
            </FormItem>
            <FormItem label="报单币余额:">
                <span style="color: #2b85e4">{{userInfo === null ? '' : userInfo.userNodeVO.consumeCoin}}</span>
            </FormItem>
            <FormItem label="产品积分余额:">
                <span style="color: #2b85e4">{{userInfo === null ? '' : userInfo.userNodeVO.productCoin}}</span>
            </FormItem>
            <br>
            <FormItem label="激活状态:">
                <span style="color: #2b85e4">{{userInfo === null ? '' : userInfo.userNodeVO.userStatus}}</span>
            </FormItem>
            <FormItem label="考核状态:">
                <span style="color: #2b85e4">{{userInfo === null ? '' : userInfo.userNodeVO.assessStatus}}</span>
            </FormItem>
            <FormItem label="是否完善资料:">
                <span style="color: #2b85e4">{{userInfo === null ? '' : userInfo.userNodeVO.passwordReset}}</span>
            </FormItem>
            <br>
            <FormItem label="累计业绩:" style="margin-right: -60px;">
                A:<span style="color: #2b85e4">&nbsp;{{userInfo === null ? '' : userInfo.performanceVO.departAcount}}&nbsp;&nbsp;</span>  B:<span style="color: #2b85e4">&nbsp;{{userInfo === null ? '' : userInfo.performanceVO.departBcount}}&nbsp;&nbsp;</span>  C:<span style="color: #2b85e4">&nbsp;{{userInfo === null ? '' : userInfo.performanceVO.departCcount}}&nbsp;&nbsp;</span>
            </FormItem>
            <br>
            <FormItem label="新增业绩:" style="margin-right: -60px;">
                A:<span style="color: #2b85e4">&nbsp;{{userInfo === null ? '' : userInfo.performanceVO.addDepartAcount}}&nbsp;&nbsp;</span>  B:<span style="color: #2b85e4">&nbsp;{{userInfo === null ? '' : userInfo.performanceVO.addDepartBcount}}&nbsp;&nbsp;</span>  C:<span style="color: #2b85e4">&nbsp;{{userInfo === null ? '' : userInfo.performanceVO.addDepartCcount}}&nbsp;&nbsp;</span>
            </FormItem>
        </Form>
    </div>
</template>

<script>
    export default {
        name: 'page',
        props: {
            userInfo: {
                type: Object,
                default: null
            }
        },
        data () {
            return {};
        },
        methods: {}
    };
</script>