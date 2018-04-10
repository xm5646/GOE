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
                            <template slot="operations" slot-scope="scope">
                                <!--<Button size="large" type="error" @click="edit(scope.item)">&nbsp;不通过&nbsp;</Button>-->
                                <Button size="large" type="success" @click="doneSend(scope.item)">&nbsp;完成发货&nbsp;
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
    import value2name from '../../libs/value2name';
    import ChinaAddressV4Data from '../../libs/china_address_v4.json';
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
                        key: 'orderId',
                        title: '订单编号',
                        width: 80
                    },
                    {
                        key: 'account',
                        title: '用户编号',
                        width: 100
                    },
                    {
                        key: 'createTime',
                        title: '创建时间',
                        width: 100
                    },
                    {
                        key: 'receiverName',
                        title: '收件人',
                        width: 100
                    },
                    {
                        key: 'phone',
                        title: '手机号码',
                        width: 120
                    },
                    {
                        key: 'showAddress',
                        title: '收货地址',
                        width: 300
                    },
                    {
                        key: 'description',
                        title: '描述',
                        width: 160
                    }
                ],
                allList: []
            };
        },
        methods: {
            init() {
                this.getAllListByPage(0);
            },
            getAllListByPage (page) {
                this.doGet({url: this.APIServer + '/goeIndexOrderController/findAllOrdersOfReconsume?pageNum=' + page}).then(result => {
                    if (result.success) {
                        let tmpList = result.data.content;
                        for (let i = 0; i < result.data.content.length; i++) {
                            let addArray = new Array(3);
                            let detailAdd = result.data.content[i].addressInfo[3];
                            addArray = result.data.content[i].addressInfo;
                            addArray.length = 3;
                            tmpList[i]['showAddress'] = value2name(addArray, ChinaAddressV4Data) + ' ' + detailAdd;
                        }
                        this.pageObj.totalPage = result.data.totalPages;
                        this.pageObj.totalCount = result.data.totalElements;
                        this.allList = tmpList;
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            },
            doneSend (item) {
                let that = this
                this.$Modal.confirm({
                    title: '提示',
                    content: '是否已完成 ' + item.account + ' 的重销订单发货?',
                    onOk: function () {
                        that.doPost({
                            url: that.APIServer + '/goeIndexOrderController/updateOrderInfo',
                            params: {
                                orderId: item.orderId
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
            changePage (pageNum) {
                this.getAllListByPage(pageNum - 1);
            },
            handleSearch3 () {
                this.allList = [];
                if (this.searchAccount === '') {
                    this.getAllListByPage(0);
                } else {
                    this.doGet({
                        url: this.APIServer + '/goeIndexOrderController/findAllOrdersOfReconsumeByAccount?account=' + this.searchAccount
                    }).then(result => {
                        if (result.success) {
                            let tmpList = result.data.content;
                            for (let i = 0; i < result.data.content.length; i++) {
                                let addArray = new Array(3);
                                let detailAdd = result.data.content[i].addressInfo[3];
                                addArray = result.data.content[i].addressInfo;
                                addArray.length = 3;
                                tmpList[i]['showAddress'] = value2name(addArray, ChinaAddressV4Data) + ' ' + detailAdd;
                            }
                            this.pageObj.totalPage = result.data.totalPages;
                            this.pageObj.totalCount = result.data.totalElements;
                            this.allList = tmpList;
                        } else {
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
        mounted () {
            this.init();
        }
    };
</script>
