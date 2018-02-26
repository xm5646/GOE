<template>
    <div>
        <Modal :width="900" v-model="showStatus" @on-ok="submitEdit" title="修改会员信息">
                <Form :model="userInfo" :label-width="80">
                    <FormItem label="姓名">
                        <Input style="width: 200px" v-model="userInfo.user_nickName" placeholder="Enter something..."></Input>
                    </FormItem>
                    <FormItem label="手机号码">
                        <Input style="width: 200px" v-model="userInfo.user_phone" placeholder="Enter something..."></Input>
                    </FormItem>
                    <FormItem label="激活状态">
                        <i-switch v-model="formItem.switch" size="large">
                            <span slot="open">激活</span>
                            <span slot="close">冻结</span>
                        </i-switch>
                    </FormItem>
                    <FormItem label="考核状态">
                        <i-switch v-model="formItem.switch" size="large">
                            <span slot="open">通过</span>
                            <span slot="close">未完</span>
                        </i-switch>
                    </FormItem>
                </Form>
                <Form :model="userInfo" :label-width="80" inline>
                    <FormItem label="登陆密码" style="margin-right: -60px;">
                        <Input style="width: 200px" v-model="userInfo.user_password" placeholder="重置登陆密码6-20位字符"></Input>
                    </FormItem>
                    <FormItem>
                        <Button type="primary">重置</Button>
                        <Button type="ghost" style="margin-left: 8px">取消</Button>
                    </FormItem>
                </Form>
                <Form :model="userInfo" :label-width="80" inline>
                    <FormItem label="交易密码" style="margin-right: -60px;">
                        <Input style="width: 200px" v-model="userInfo.user_password" placeholder="重置6位数字交易密码"></Input>
                    </FormItem>
                    <FormItem>
                        <Button type="primary">重置</Button>
                        <Button type="ghost" style="margin-left: 8px">取消</Button>
                    </FormItem>
                </Form>
        </Modal>
    </div>
</template>

<script>
export default {
    name: 'page',
    props: {
        showStatus: Boolean,
        userInfo: Object
    },
    data () {
        return {
            nickName: '',
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
        submitEdit () {
//            alert('new value' + this.userInfo.user_nickName);
        }
    }
};
</script>