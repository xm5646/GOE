<style lang="less">
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="ios-list-outline"></Icon>
                    奖金发放记录
                </p>
                <Row>
                    <div>
                        <Input v-model="searchAccount" placeholder="请输入用户编号搜搜..." style="width: 200px"/>
                        <span @click="handleSearch2" style="margin: 0 10px;"><Button type="primary"
                                                                                     icon="search">搜索</Button></span>
                        <Button @click="handleCancel2" type="ghost">取消</Button>
                    </div>
                </Row>
                <Row class="margin-top-10 searchable-table-con1">
                    <!--<Table :columns="columns1" :data="userList"></Table>-->
                    <div style="height: 20px"></div>
                    <div v-if="allList.length>0">
                        <vue-table :tdata="allList"
                                   :tcolumns="columns1"
                                   :tdHeight="40"
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
    import vueTable from 'vue-table2';

    export default {
        name: 'searchable-table',
        components: {
            vueTable
        },
        data() {
            return {
                searchAccount: '',
                isNotFindData: false,
                pageObj: {
                    totalPage: 1,
                    maxSize: 5
                },
                columns1: [
                    {
                        key: 'account',
                        title: '用户编号',
                        width: 120
                    },
                    {
                        key: 'totalMoney',
                        title: '发放总额',
                        width: 120
                    },
                    {
                        key: 'bonusNumber',
                        title: '奖金币',
                        width: 120
                    },
                    {
                        key: 'productCoinNumber',
                        title: '产品积分',
                        width: 120
                    },
                    {
                        key: 'manageCost',
                        title: '综合费',
                        width: 120
                    },
                    {
                        key: 'payTime',
                        title: '发放时间',
                        width: 120
                    }
                ],
                allList: []
            };
        },
        methods: {
            init () {
                this.getAllListByPage(0);
            },
            getAllListByPage (page) {
                this.doGet({url: this.APIServer + '/goeIndexBonus/findAllBonusByPage?pageNum=' + page}).then(result => {
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
            handleSearch2 () {
                this.allList = [];
                this.doGet({
                    url: this.APIServer + '/goeIndexBonus/findBonusPageByAccount?account=' + this.searchAccount
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
            handleCancel2 () {
                this.searchAccount = '';
                this.getAllListByPage(0);
            }
        },
        mounted () {
            this.init();
        }
    };
</script>
