<style lang="less">
    @import '../../styles/common.less';
    @import '../tables/components/table.less';
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
                    <can-edit-table refs="table2" v-model="userList" :columns-list="columns1" @on-change="userUpdate"></can-edit-table>
                </Row>
                <Row>
                    <div style="text-align: center;margin-top: 8px;">
                        <Button type="default">首页</Button>
                        <Button type="default">上一页</Button>
                        <Button type="default">下一页</Button>
                        <Button type="default" disabled="true">末页</Button>
                        <span style="margin-right: 5px;margin-left: 5px;"> 第 1 页 /共 1 页</span>
                        <span style="margin-right: 5px;margin-left: 5px;"> 跳转到 <input type="number" v-model="toNum" style="width: 50px;"/> 页</span>
                        <Button type="default">跳转</Button>
                    </div>
                </Row>
            </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import * as table from '../tables/data/search';
    import canEditTable from '../tables/components/canEditTable.vue';
    export default {
        name: 'searchable-table',
        components: {
            canEditTable
        },
        data () {
            return {
                searchConName1: '',
                searchConName2: '',
                searchAccount: '',
                searchNickName: '',
                toNum: '',
                searchConTel2: '',
                searchConName3: '',
                data1: [
                    {
                        user_id: 1,
                        user_nickName: '李晓明',
                        user_account: 'lixiaoming',
                        user_level: '组长',
                        user_createTime: '2017-12-21',
                        user_status: '已激活'
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
                        editable: true
                    },
                    {
                        key: 'user_account',
                        title: '用户编号',
                    },
                    {
                        key: 'user_level',
                        title: '级别'
                    },
                    {
                        key: 'user_createTime',
                        title: '加入时间'
                    },
                    {
                        key: 'user_status',
                        title: '激活状态'
                    },
                    {
                        key: 'user_accessStatus',
                        title: '当前考核状态'
                    },
                    {
                        key: 'user_bonusCoin',
                        title: '奖金'
                    },
                    {
                        key: 'user_consumeCoin',
                        title: '报单币'
                    },
                    {
                        key: 'user_productCoin',
                        title: '产品积分'
                    },
                    {
                        title: '操作',
                        align: 'center',
                        width: 190,
                        key: 'handle',
                        handle: ['edit']
                    }
                ],
                userList: [
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
                ]
            };
        },
        methods: {
            init () {
//                this.data1 = this.initTable1 = table.searchTable1;
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
            userUpdate () {
              alert('user info updated!');
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
