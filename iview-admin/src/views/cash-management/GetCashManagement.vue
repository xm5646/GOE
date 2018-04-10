<style lang="less">
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="ios-list-outline"></Icon>
                    待处理提现列表
                </p>
                <Row>
                    <div style="margin-right: 5%; float: left">
                        <Input v-model="searchAccount" placeholder="请输入用户编号搜搜..." style="width: 200px"/>
                        <span @click="handleSearch3" style="margin: 0 10px;"><Button type="primary"
                                                                                     icon="search">搜索</Button></span>
                        <Button @click="handleCancel3" type="ghost">取消</Button>
                    </div>
                    <div>
                        <span @click="exportExcel" style="margin: 0 10px;"><Button type="primary"
                                                                                   icon="archive">导出到表格</Button></span>
                    </div>
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
                            <template slot="operations" slot-scope="scope">
                                <Button size="large" type="error" @click="rejectPay(scope.item)">&nbsp;不通过&nbsp;
                                </Button>
                                <Button size="large" type="success" @click="donePay(scope.item)">&nbsp;已打款&nbsp;
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
                    totalPage: 50,
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
                        width: 150
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
                    }
                ],
                allList: []
            };
        },
        methods: {
            init() {
                this.getAllListByPage(0);
            },
            rejectPay (item) {
                let that = this
                this.$Modal.confirm({
                    title: '提示',
                    content: '是否要拒绝 ' + item.account + ' 的提现申请?',
                    onOk: function () {
                        that.doPost({
                            url: that.APIServer + '/goeIndexDrawCash/updateDrawCashStaus',
                            params: {
                                drawId: item.drawId,
                                drawStatus: '不通过'
                            }
                        }).then(result => {
                            if (result.success) {
                                that.getAllListByPage(0);
                                that.$Message.success(result.data);
                            } else {
                                that.$Message.error(result.message);
                            }
                        });
                    }
                });
            },
            donePay (item) {
                let that = this
                this.$Modal.confirm({
                    title: '提示',
                    content: '是否已完成 ' + item.account + ' 的提现打款?',
                    onOk: function () {
                        that.doPost({
                            url: that.APIServer + '/goeIndexDrawCash/updateDrawCashStaus',
                            params: {
                                drawId: item.drawId,
                                drawStatus: '已打款'
                            }
                        }).then(result => {
                            if (result.success) {
                                that.getAllListByPage(0);
                                that.$Message.success(result.data);
                            } else {
                                that.$Message.error(result.message);
                            }
                        });
                    }
                });
            },
            exportExcel () {
                window.open(this.APIServer + '/excel/exportDraw', '_self');
            },
            getAllListByPage (page) {
                this.doGet({url: this.APIServer + '/goeIndexDrawCash/findByDrawStatusOfAuditWait?pageNum=' + page}).then(result => {
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
                if (this.searchAccount === '') {
                    this.getAllListByPage(0);
                } else {
                    this.doGet({
                        url: this.APIServer + '/goeIndexDrawCash/findDrawStatusOfAuditWaitByAccount?account=' + this.searchAccount
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
