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
                    <div style="margin-right: 5%; float: left">
                        <Input v-model="searchNickName" placeholder="请输入姓名搜搜..." style="width: 200px" />
                        <span @click="handleSearch3" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                        <Button @click="handleCancel3" type="ghost" >取消</Button>
                    </div>
                    <div>
                        <Input v-model="searchAccount" placeholder="请输入用户编号搜搜..." style="width: 200px" />
                        <span @click="handleSearch3" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                        <Button @click="handleCancel3" type="ghost" >取消</Button>
                    </div>
                </Row>
                <Row class="margin-top-10 searchable-table-con1">
                    <!--<Table :columns="columns1" :data="userList"></Table>-->
                    <div style="height: 20px"></div>
                    <div v-if="userList.length>0">
                        <vue-table :tdata="userList"
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
                                请输入用户姓名或者用户编号进行查询
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
                searchNickName: '',
                showUser: '',
                showCurrentTableData: false,
                toNum: '',
                isNotFindData: false,
                pageObj: {
                    totalPage: 1,
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
                        key: 'user_account',
                        title: '用户编号',
                        width: 120
                    },
                    {
                        key: 'user_nickName',
                        title: '用户编号',
                        width: 120
                    },
                    {
                        key: 'earn_type',
                        title: '收益类型',
                        width: 120
                    },
                    {
                        key: 'earn_createTime',
                        title: '触发时间',
                        width: 120
                    },
                    {
                        key: 'earn_dayMoney',
                        title: '每日发放金额',
                        width: 120
                    },
                    {
                        key: 'earn_surplusDays',
                        title: '剩余发放天数',
                        width: 120
                    }
                ],
                userList: [
                    {
                        user_account: 'lixiaoming',
                        user_nickName: '李晓明',
                        earn_type: '累积',
                        earn_createTime: '2017-12-21',
                        earn_dayMoney: '140',
                        earn_surplusDays: '20'
                    }
                ]
            };
        },
        methods: {
            init () {
                this.data2 = this.initTable2 = table.searchTable2;
                this.data3 = this.initTable3 = table.searchTable3;
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
                console.log('now at page: ' + pageNum);
            },
            submitEdit () {
                console.log('new value' + this.userInfo.user_nickName);
            },
            handleSearch1 () {
                this.data1 = this.initTable1;
                this.data1 = this.search(this.data1, {name: this.searchConName1});
            },
            handleSearch2 () {
                this.data2 = this.initTable2;
                this.data2 = this.search(this.data2, {name: this.searchConName2, tel: this.searchConTel2});
            },
            handleSearch3 () {
                this.userList = this.initTable3;
                this.userList = this.search(this.userList, {user_nickName: this.searchNickName});
            },
            handleCancel3 () {
                this.userList = this.data1;
            }
        },
        mounted () {
            this.init();
        }
    };
</script>
