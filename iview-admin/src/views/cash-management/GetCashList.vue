<style lang="less">
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="ios-list-outline"></Icon>
                    提现记录
                </p>
                <Row>
                    <div style="margin-right: 5%; float: left">
                        <Input v-model="searchNickName" placeholder="请输入姓名搜搜..." style="width: 200px"/>
                        <span @click="handleSearch3" style="margin: 0 10px;"><Button type="primary"
                                                                                     icon="search">搜索</Button></span>
                        <Button @click="handleCancel3" type="ghost">取消</Button>
                    </div>
                    <div style="margin-right: 5%; float: left">
                        <Input v-model="searchAccount" placeholder="请输入用户编号搜搜..." style="width: 200px"/>
                        <span @click="handleSearch3" style="margin: 0 10px;"><Button type="primary"
                                                                                     icon="search">搜索</Button></span>
                        <Button @click="handleCancel3" type="ghost">取消</Button>
                    </div>
                    <!--<div>-->
                        <!--<span @click="handleSearch3" style="margin: 0 10px;"><Button type="primary"-->
                                                                                     <!--icon="archive">导出到表格</Button></span>-->
                    <!--</div>-->
                </Row>
                <Row class="margin-top-10 searchable-table-con1">
                    <!--<Table :columns="columns1" :data="userList"></Table>-->
                    <div style="height: 20px"></div>
                    <vue-table :tdata="userList"
                               :tcolumns="columns1"
                               :showHandle="false"
                               :tdHeight="40"
                               :handleFixed="false"
                               :page="pageObj"
                               @changePage="changePage"
                    >
                        <!--<template slot="operations" scope="scope">-->
                            <!--<Button size="large" type="error" @click="edit(scope.item)">&nbsp;不通过&nbsp;</Button>-->
                            <!--<Button size="large" type="success" @click="recharge(scope.item)">&nbsp;已打款&nbsp;</Button>-->
                        <!--</template>-->
                    </vue-table>
                </Row>
            </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import * as table from '../tables/data/search';
    import vueTable from 'vue-table2';

    export default {
        name: 'searchable-table',
        components: {
            vueTable
        },
        data() {
            return {
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
                },
                searchAccount: '',
                searchNickName: '',
                userInfo: {},
                showEditModal: false,
                showRechargeModal: false,
                toNum: '',
                pageObj: {
                    totalPage: 50,
                    maxSize: 5
                },
                searchConTel2: '',
                searchConName3: '',
                data1: [
                    {
                        user_nickName: '李晓明',
                        user_account: 'lixiaoming',
                        user_level: '组长',
                        user_createTime: '2017-12-21',
                        user_status: '已激活',
                        user_accessStatus: '已通过',
                        user_bonusCoin: '1000',
                        user_consumeCoin: '1000',
                        user_productCoin: '1000'
                    },
                    {
                        user_nickName: '李晓明',
                        user_account: 'lixiaoming',
                        user_level: '组长',
                        user_createTime: '2017-12-21',
                        user_status: '已激活',
                        user_accessStatus: '已通过',
                        user_bonusCoin: '1000',
                        user_consumeCoin: '1000',
                        user_productCoin: '1000'
                    }
                ],
                initTable1: [],
                data2: [],
                initTable2: [],
                data3: [],
                initTable3: [],
                columns1: [
                    {
                        key: 'getcash_id',
                        title: '提现编号',
                        width: 100
                    },
                    {
                        key: 'user_account',
                        title: '用户编号',
                        width: 100
                    },
                    {
                        key: 'getNumber',
                        title: '打款金额',
                        width: 100
                    },
                    {
                        key: 'cardOwner',
                        title: '持卡人',
                        width: 100
                    },
                    {
                        key: 'user_phone',
                        title: '手机号码',
                        width: 120
                    },
                    {
                        key: 'bankName',
                        title: '开户行',
                        width: 140
                    },
                    {
                        key: 'cardNumber',
                        title: '银行卡号',
                        width: 160
                    },
                    {
                        key: 'status',
                        title: '状态',
                        width: 120
                    },
                    {
                        key: 'payTime',
                        title: '打款时间',
                        width: 130
                    }
                ],
                userList: [
                    {
                        getcash_id: '1',
                        user_account: 'lixiaoming',
                        user_phone: '13520580169',
                        bankName: '中国邮政储蓄银行',
                        getNumber: '690',
                        status: '待审核',
                        cardOwner: '李晓明',
                        cardNumber: '2031334581843456909',
                        payTime: '2017-02-01 18:00:00'
                    }
                ]
            };
        },
        methods: {
            init() {
                this.data2 = this.initTable2 = table.searchTable2;
                this.data3 = this.initTable3 = table.searchTable3;
            },
            search(data, argumentObj) {
                let res = data;
                let dataClone = data;
                for (let argu in argumentObj) {
                    if (argumentObj[argu].length > 0) {
                        res = dataClone.filter(d => {
                            return d[argu].indexOf(argumentObj[argu]) > -1;
                        });
                        dataClone = res;
                    }
                }
                return res;
            },
            edit (item) {
                console.log('item type: ' + typeof item)
                this.userInfo = item
                this.showEditModal = true;
            },
            recharge (item) {
                this.userInfo = item
                this.showRechargeModal = true;
            },
            lockConfirm(item) {
                this.$Modal.confirm({
                    title: '确认冻结?',
                    content: '确定是否要冻结' + item.user_nickName + '?',
                    onOk: function () {
                        alert('冻结成功');
                    }
                });

            },
            changePage(pageNum) {
                console.log('now at page: ' + pageNum);
            },
            submitEdit () {
                console.log('new value' + this.userInfo.user_nickName);
            },
            handleSearch1() {
                this.data1 = this.initTable1;
                this.data1 = this.search(this.data1, {name: this.searchConName1});
            },
            handleSearch2() {
                this.data2 = this.initTable2;
                this.data2 = this.search(this.data2, {name: this.searchConName2, tel: this.searchConTel2});
            },
            handleSearch3() {
                this.userList = this.initTable3;
                this.userList = this.search(this.userList, {user_nickName: this.searchNickName});
            },
            handleCancel3() {
                this.userList = this.data1;
            }
        },
        mounted() {
            this.init();
        }
    };
</script>
