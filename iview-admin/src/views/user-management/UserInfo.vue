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
                        <Input v-model="searchNickName" placeholder="请输入姓名搜搜..." style="width: 200px"/>
                        <span @click="handleSearch3" style="margin: 0 10px;"><Button type="primary"
                                                                                     icon="search">搜索</Button></span>
                        <Button @click="handleCancel3" type="ghost">取消</Button>
                    </div>
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
                    <div v-if="userList.length>0">
                        <vue-table :tdata="userList"
                                   :tcolumns="columns1"
                                   :showHandle="true"
                                   :tdHeight="40"
                                   :handleFixed="true"
                                   :page="pageObj"
                                   @changePage="changePage"
                        >
                            <template slot="operations" slot-scope="scope">
                                <Button size="large" type="primary" @click="edit(scope.item)">&nbsp;修改&nbsp;</Button>
                                <Button size="large" type="primary" @click="recharge(scope.item)">&nbsp;充币&nbsp;
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
        <Modal :width="700" v-model="showEditModal" @on-ok="submitEdit" title="修改会员信息">
            <edit-user-window :userInfo="userInfo"></edit-user-window>
        </Modal>
        <Modal :width="700" v-model="showRechargeModal" title="电子币充值">
            <user-recharge-window :userInfo="userInfo"></user-recharge-window>
        </Modal>
    </div>
</template>

<script>
    import * as table from '../tables/data/search';
    import EditUserWindow from './components/EditUserWindow.vue';
    import UserRechargeWindow from './components/UserRechargeWindow.vue';
    import vueTable from 'vue-table2';

    export default {
        name: 'searchable-table',
        components: {
            vueTable,
            EditUserWindow,
            UserRechargeWindow
        },
        data() {
            return {
                searchAccount: '',
                searchNickName: '',
                userInfo: {},
                showEditModal: false,
                showRechargeModal: false,
                pageObj: {
                    totalPage: 0,
                    totalCount: 7,
                    maxSize: 5
                },
                columns1: [
                    {
                        key: 'account',
                        title: '用户编号',
                        width: 150
                    },
                    {
                        key: 'nickName',
                        title: '用户姓名',
                        width: 100
                    },
                    {
                        key: 'userType',
                        title: '用户类型',
                        width: 100
                    },
                    {
                        key: 'userPhone',
                        title: '手机号码',
                        width: 120
                    },
                    {
                        key: 'userLevel',
                        title: '级别',
                        width: 100
                    },
                    {
                        key: 'createTime',
                        title: '加入时间',
                        width: 150
                    },
                    {
                        key: 'userStatus',
                        title: '激活状态',
                        width: 120
                    },
                    {
                        key: 'assessStatus',
                        title: '当前考核状态',
                        width: 120
                    },
                    {
                        key: 'assessDate',
                        title: '下次考核时间',
                        width: 120
                    },
                    {
                        key: 'bonusCoin',
                        title: '奖金',
                        width: 120
                    },
                    {
                        key: 'consumeCoin',
                        title: '报单币',
                        width: 120
                    },
                    {
                        key: 'productCoin',
                        title: '产品积分',
                        width: 120
                    },
                    {
                        key: 'accountA',
                        title: 'A市场用户',
                        width: 120
                    },
                    {
                        key: 'accountB',
                        title: 'B市场用户',
                        width: 120
                    },
                    {
                        key: 'accountC',
                        title: 'C市场用户',
                        width: 120
                    },
                    {
                        key: 'passwordReset',
                        title: '是否初始化密码',
                        width: 120
                    }
                ],
                userList: []
            };
        },
        methods: {
            init() {
                this.getAllUserListByPage(0);
            },
            getAllUserListByPage(page) {
                this.doGet({url: this.APIServer + '/goeIndexUserManagement/findAllUsers?pageNum=' + page}).then(result => {
                    if (result.success) {
                        this.pageObj.totalPage = result.data.totalPages;
                        this.pageObj.totalCount = result.data.totalElements;
                        this.userList = result.data.content;
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            },
            edit (item) {
                console.log('item type: ' + typeof item)
                this.userInfo = item
                this.showEditModal = true;
            },
            recharge(item) {
                this.userInfo = item
                this.showRechargeModal = true;
            },
            changePage(pageNum) {
                if (pageNum >= 0 || pageNum < this.pageObj.totalPage) {
                    this.getAllUserListByPage(pageNum - 1);
                }
            },
            submitEdit () {
                console.log('do update user info' + this.userInfo);
                this.doPost({
                    url: this.APIServer + '/goeIndexUserManagement/updateUserInfo',
                    params: {
                        account: this.userInfo.account,
                        nickName: this.userInfo.nickName,
                        phone: this.userInfo.userPhone
                    }
                }).then(result => {
                    if (result.success) {
                        this.$Message.success(result.message);
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            },
            handleSearch3() {
                this.userList = [];
                if (this.searchNickName === '') {
                    this.getAllListByPage(0);
                } else {
                    this.doGet({
                        url: this.APIServer + '/goeIndexUserManagement/findUsersByNickNameOrAccountLike?type=nickName&value=' + this.searchNickName
                    }).then(result => {
                        if (result.success) {
                            this.pageObj.totalPage = result.data.totalPages;
                            this.pageObj.totalCount = result.data.totalElements;
                            this.userList = result.data.content;
                        } else {
                            this.$Message.error(result.message);
                        }
                    });
                }
            },
            handleCancel3() {
                this.searchNickName = '';
                this.getAllUserListByPage(0);
            },
            handleSearch2() {
                this.userList = [];
                if (this.searchAccount === '') {
                    this.getAllListByPage(0);
                } else {
                    this.doGet({
                        url: this.APIServer + '/goeIndexUserManagement/findUsersByNickNameOrAccountLike?type=account&value=' + this.searchAccount
                    }).then(result => {
                        if (result.success) {
                            this.pageObj.totalPage = result.data.totalPages;
                            this.pageObj.totalCount = result.data.totalElements;
                            this.userList = result.data.content;
                        } else {
                            this.$Message.error(result.message);
                        }
                    });
                }
            },
            handleCancel2() {
                this.searchAccount = '';
                this.getAllUserListByPage(0);
            }
        },
        mounted() {
            this.init();
        }
    };
</script>
