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
                        <Input v-model="searchAccount" placeholder="请输入用户编号搜搜..." style="width: 200px"/>
                        <span @click="handleSearch3" style="margin: 0 10px;"><Button type="primary"
                                                                                     icon="search">搜索</Button></span>
                        <Button @click="handleCancel3" type="ghost">取消</Button>
                    </div>
                </Row>
                <Row class="margin-top-10 searchable-table-con1">
                    <!--<Table :columns="columns1" :data="userList"></Table>-->
                    <div style="height: 20px"></div>
                    <div v-if="allList.length>0">
                        <vue-table :tdata="allList"
                                   :tcolumns="columns1"
                                   :showHandle="false"
                                   :tdHeight="40"
                                   :handleFixed="false"
                                   :page="pageObj"
                                   @changePage="changePage"
                        >
                        </vue-table>
                    </div>
                    <div v-else>
                        <Alert type="info" show-icon>
                            未找到记录!
                        </Alert>
                    </div>
                </Row>
            </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import * as table from '../tables/data/search';
    import vueTable from 'vue-table-lxm';

    export default {
        name: 'searchable-table',
        components: {
            vueTable
        },
        data() {
            return {
                searchAccount: '',
                pageObj: {
                    totalPage: 1,
                    maxSize: 5
                },
                columns1: [
                    {
                        key: 'drawId',
                        title: '提现编号',
                        width: 100
                    },
                    {
                        key: 'account',
                        title: '用户编号',
                        width: 100
                    },
                    {
                        key: 'finalNumber',
                        title: '打款金额',
                        width: 100
                    },
                    {
                        key: 'commitTime',
                        title: '申请提现时间',
                        width: 120
                    },
                    {
                        key: 'cardOwnerName',
                        title: '持卡人',
                        width: 100
                    },
                    {
                        key: 'phone',
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
                        key: 'drawStatus',
                        title: '状态',
                        width: 120
                    },
                    {
                        key: 'payTime',
                        title: '打款时间',
                        width: 120
                    }
                ],
                allList: []
            };
        },
        methods: {
            init() {
                this.getAllListByPage(0);
            },
            getAllListByPage(page) {
                this.doGet({url: this.APIServer + '/goeIndexDrawCash/findAllDrawCashRecord?pageNum=' + page}).then(result => {
                    if (result.success) {
                        this.pageObj.totalPage = result.data.totalPages;
                        this.pageObj.totalCount = result.data.totalElements;
                        this.allList = result.data.content;
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            },
            getListByAccountAndPage (page) {
                this.doGet({
                    url: this.APIServer + '/goeIndexDrawCash/findAllDrawCashRecordByAccount?account=' + this.searchAccount + '&pageNum=' + page
                }).then(result => {
                    if (result.success) {
                        this.pageObj.totalPage = result.data.totalPages;
                        this.pageObj.totalCount = result.data.totalElements;
                        this.allList = result.data.content;
                    } else {
                        this.allList = [];
                        this.$Message.error(result.message);
                    }
                });
            },
            changePage (pageNum) {
                if (this.searchAccount === '') {
                    this.getAllListByPage(pageNum - 1);
                } else {
                    this.getListByAccountAndPage(pageNum - 1);
                }
            },
            handleSearch3 () {
                this.allList = [];
                if (this.searchAccount === '') {
                    this.getAllListByPage(0);
                } else {
                    this.getListByAccountAndPage(0);
                }
            },
            handleCancel3 () {
                this.searchAccount = '';
                this.getAllListByPage(0);
            }
        },
        mounted() {
            this.init();
        }
    };
</script>
