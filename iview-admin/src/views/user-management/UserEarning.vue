<style lang="less">
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="social-yen"></Icon>
                    会员收入查询
                </p>
                <Row>
                    <div>
                        <Input v-model="searchAccount" placeholder="请输入用户编号搜搜..." style="width: 200px" />
                        <span @click="handleSearch2" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                        <Button @click="handleCancel2" type="ghost" >取消</Button>
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
                        <div v-if="isNotFindData">
                            <Alert type="warning" show-icon>
                                未找到数据!
                            </Alert>
                        </div>
                        <div v-else>
                            <Alert show-icon>
                                请输入准确的用户编号进行查询
                            </Alert>
                        </div>

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
        data () {
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
                        key: 'userLevel',
                        title: '用户级别',
                        width: 120
                    },
                    {
                        key: 'touchType',
                        title: '收益类型',
                        width: 120
                    },
                    {
                        key: 'dayMoney',
                        title: '每日发放金额',
                        width: 120
                    },
                    {
                        key: 'createTime',
                        title: '触发时间',
                        width: 120
                    },
                    {
                        key: 'endTime',
                        title: '发放结束时间',
                        width: 120
                    },
                    {
                        key: 'surplusNumber',
                        title: '剩余发放天数',
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
                this.doGet({url: this.APIServer + '/goeIndexUserManagement/findAllEarnings?pageNum=' + page}).then(result => {
                    if (result.success) {
                        this.pageObj.totalPage = result.data.totalPages;
                        this.pageObj.totalCount = result.data.totalElements;
                        this.allList = result.data.content;
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            },
            search (data, argumentObj) {
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
            changePage (pageNum) {
                this.getAllListByPage(pageNum - 1);
            },
            handleSearch2 () {
                this.userList = [];
                this.doGet({
                    url: this.APIServer + '/goeIndexUserManagement/findEarningsByAccountLike?account=' + this.searchAccount
                }).then(result => {
                    if (result.success) {
                        this.pageObj.totalPage = result.data.totalPages;
                        this.pageObj.totalCount = result.data.totalElements;
                        this.allList = result.data.content;
                    } else {
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
