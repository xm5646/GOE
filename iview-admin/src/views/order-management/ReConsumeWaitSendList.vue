<style lang="less">
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="ios-list-outline"></Icon>
                    待处理重销订单列表
                </p>
                <Row>
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
                    <div v-if="allList.length>0">
                        <vue-table :tdata="allList"
                                   :tcolumns="columns1"
                                   :showHandle="true"
                                   :tdHeight="40"
                                   :handleFixed="true"
                                   :page="pageObj"
                                   @changePage="changePage"
                        >
                            <template slot="operations" scope="scope">
                                <!--<Button size="large" type="error" @click="edit(scope.item)">&nbsp;不通过&nbsp;</Button>-->
                                <Button size="large" type="success" @click="recharge(scope.item)">&nbsp;完成发货&nbsp;
                                </Button>
                            </template>
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
    import vueTable from 'vue-table2';

    export default {
        name: 'searchable-table',
        components: {
            vueTable
        },
        data() {
            return {
                searchAccount: '',
                pageObj: {
                    totalPage: 50,
                    maxSize: 5
                },
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
                this.doGet({url: this.APIServer + '/goeIndexOrderController/findAllDrawCashRecord?pageNum=' + page}).then(result => {
                    if (result.success) {
                        this.pageObj.totalPage = result.data.totalPages;
                        this.pageObj.totalCount = result.data.totalElements;
                        this.allList = result.data.content;
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            },
            changePage (pageNum) {
                this.getAllListByPage(pageNum - 1);
            },
            handleSearch3 () {
                this.allList = [];
                this.doGet({
                    url: this.APIServer + '/goeIndexDrawCash/findAllDrawCashRecordByAccount?account=' + this.searchAccount
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
