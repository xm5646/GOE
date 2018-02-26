<style lang="less">
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="person"></Icon>
                    会员列表
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
                    <vue-table :tdata="userList"
                               :tcolumns="columns1"
                               showHandle="true"
                               :tdHeight="40"
                               handleFixed="true"
                               :page="pageObj"
                               @changePage="changePage"
                               >
                        <template slot="operations" scope="scope">
                            <Button size="large" type="primary" @click="edit(scope.item)">&nbsp;修改信息&nbsp;</Button>
                        </template>
                    </vue-table>
                </Row>
            </Card>
            </Col>
        </Row>
        <edit-user-modal :showStatus="showCurrentTableData" :userInfo="showUser"></edit-user-modal>
    </div>
</template>

<script>
    import * as table from '../tables/data/search';
    import EditUserModal from './components/EditUserModal';
    import vueTable from 'vue-table2';
    export default {
        name: 'searchable-table',
        components: {
            vueTable,
            EditUserModal
        },
        data () {
            return {
                searchAccount: '',
                searchNickName: '',
                showUser: '',
                showCurrentTableData: false,
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
                        key: 'user_nickName',
                        title: '用户姓名',
                        width: 120
                    },
                    {
                        key: 'user_account',
                        title: '用户编号',
                        width: 150
                    },
                    {
                        key: 'user_phone',
                        title: '手机号码',
                        width: 150
                    },
                    {
                        key: 'user_level',
                        title: '级别',
                        width: 120
                    },
                    {
                        key: 'user_createTime',
                        title: '加入时间',
                        width: 150
                    },
                    {
                        key: 'user_status',
                        title: '激活状态',
                        width: 120
                    },
                    {
                        key: 'user_accessStatus',
                        title: '当前考核状态',
                        width: 120
                    },
                    {
                        key: 'user_bonusCoin',
                        title: '奖金',
                        width: 120
                    },
                    {
                        key: 'user_consumeCoin',
                        title: '报单币',
                        width: 120
                    },
                    {
                        key: 'user_productCoin',
                        title: '产品积分',
                        width: 120
                    }
                ],
                userList: [
                    {
                        user_nickName: '李晓明1',
                        user_account: 'lixiaoming',
                        user_phone: '13520580169',
                        user_level: '组长',
                        user_createTime: '2017-12-21',
                        user_status: '已激活',
                        user_accessStatus: '已通过',
                        user_bonusCoin: '1000',
                        user_consumeCoin: '1000',
                        user_productCoin: '1000'
                    },
                    {
                        user_nickName: '李晓明2',
                        user_account: 'lixiaoming',
                        user_phone: '13520580169',
                        user_level: '组长',
                        user_createTime: '2017-12-21',
                        user_status: '已激活',
                        user_accessStatus: '已通过',
                        user_bonusCoin: '1000',
                        user_consumeCoin: '1000',
                        user_productCoin: '1000'
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
            edit (item) {
                this.showUser = item
                this.showCurrentTableData = true
            },
            lockConfirm (item) {
                this.$Modal.confirm({
                    title: '确认冻结?',
                    content: '确定是否要冻结' + item.user_nickName + '?',
                    onOk: function () {
                        alert('冻结成功');
                    }
                });

            },
            changePage (pageNum) {
                console.log('now at page: ' + pageNum);
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
