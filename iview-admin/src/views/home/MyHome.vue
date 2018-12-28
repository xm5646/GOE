<style lang="less">
    @import "./home.less";
    @import "../../styles/common.less";
</style>
<template>
    <div class="home-main">
        <Row :gutter="10">
            <Col :md="24" :lg="8">
            <Row class-name="home-page-row1" :gutter="10">
                <Col :md="12" :lg="24" :style="{marginBottom: '10px'}">
                <Card>
                    <Row type="flex" class="user-infor">
                        <Col span="8">
                        <Row class-name="made-child-con-middle" type="flex" align="middle">
                            <img class="avator-img" :src="avatorPath"/>
                        </Row>
                        </Col>
                        <Col span="16" style="padding-left:6px;">
                        <Row class-name="made-child-con-middle" type="flex" align="middle">
                            <div>
                                <b class="card-user-infor-name">{{userAccount}}</b>
                                <p>管理员</p>
                            </div>
                        </Row>
                        </Col>
                    </Row>
                </Card>
                </Col>
                <Col :md="24" :lg="24" :style="{marginBottom: '10px'}">
                <Card>
                    <p slot="title" class="card-title">
                        <Icon type="android-checkbox-outline"></Icon>
                        待办事项
                    </p>
                    <div v-if="hasToDolist" class="to-do-list-con">
                        <ul>

                            <li v-if="getCashCount" style="margin-bottom: 6px;" @click="goToToDoView('getCash')">
                                <a style="color: #495060">提现待处理订单</a>
                                <Badge class="message-count-badge-outer" class-name="message-count-badge"
                                       :count="getCashCount"
                                       overflow-count="99"></Badge>
                            </li>
                            <li v-if="reConsumeOrder" style="margin-bottom: 6px;" @click="goToToDoView('reConsumeOrder')">
                                <a style="color: #495060">重销待发货订单</a>
                                <Badge class="message-count-badge-outer"
                                       class-name="message-count-badge"
                                       :count="reConsumeOrder"
                                       overflow-count="99"></Badge>
                            </li>
                            <li v-if="productCoinOrder" style="margin-bottom: 6px;" @click="goToToDoView('productCoinOrder')">
                                <a style="color: #495060">积分兑换待发货订单</a>
                                <Badge class="message-count-badge-outer"
                                       class-name="message-count-badge"
                                       :count="productCoinOrder"
                                       overflow-count="99"></Badge>
                            </li>
                        </ul>
                    </div>
                    <div v-else="!hasToDolist" class="to-do-list-con">
                        暂无待办
                    </div>
                </Card>
                </Col>
            </Row>
            </Col>
            <Col :md="24" :lg="16">
            <Row :gutter="4">
                <Col :xs="24" :sm="12" :md="8" :style="{marginBottom: '10px'}">
                <infor-card
                        id-name="user_created_count"
                        :end-val="count.todayNewUser"
                        iconType="android-person-add"
                        color="#2d8cf0"
                        intro-text="今日新增用户"
                ></infor-card>
                </Col>

                <Col :xs="24" :sm="12" :md="8" :style="{marginBottom: '10px'}">
                <infor-card
                        id-name="collection_count"
                        :end-val="count.dayInMoney"
                        iconType="upload"
                        color="#ffd572"
                        intro-text="本日累计收入"
                ></infor-card>
                </Col>
                <Col :xs="24" :sm="12" :md="8" :style="{marginBottom: '10px'}">
                <infor-card
                        id-name="visit_count"
                        :end-val="count.dayOutMoney"
                        iconType="ios-eye"
                        color="#64d572"
                        :iconSize="50"
                        intro-text="本日累计支出"
                ></infor-card>
                </Col>
            </Row>
            <Row :gutter="4">
                <Col :xs="24" :sm="12" :md="8" :style="{marginBottom: '10px'}">
                <infor-card
                        id-name="user_created_count_month"
                        :end-val="count.monthCreateUser"
                        iconType="android-person-add"
                        color="#2d8cf0"
                        intro-text="本月新增用户"
                ></infor-card>
                </Col>
                <Col :xs="24" :sm="12" :md="8" :style="{marginBottom: '10px'}">
                <infor-card
                        id-name="month_in_money"
                        :end-val="count.monthInMoney"
                        iconType="upload"
                        color="#ffd572"
                        :iconSize="50"
                        intro-text="本月累计收入"
                ></infor-card>
                </Col>
                <Col :xs="24" :sm="12" :md="8" :style="{marginBottom: '10px'}">
                <infor-card
                        id-name="month_out_money"
                        :end-val="count.monthOutMoney"
                        iconType="ios-eye"
                        color="#64d572"
                        :iconSize="50"
                        intro-text="本月累计支出"
                ></infor-card>
                </Col>
            </Row>
            <Row :gutter="5" class="margin-top-10">
                <Col :md="24" :lg="12" :style="{marginBottom: '10px'}">
                <Card>
                    <p slot="title" class="card-title">
                        <Icon type="ios-pulse-strong"></Icon>
                        公司累计收入拨出比例
                    </p>
                    <div class="data-source-row">
                        <data-source-pie :numbers="bochubi"></data-source-pie>
                    </div>
                </Card>
                </Col>
                <Col :md="24" :lg="12" :style="{marginBottom: '10px'}">
                <Card>
                    <p slot="title" class="card-title">
                        <Icon type="android-map"></Icon>
                        上周每日新增会员数量
                    </p>
                    <div class="data-source-row">
                        <visite-volume :counts="weekNewUsers"></visite-volume>
                    </div>
                </Card>
                </Col>
            </Row>
            </Col>
        </Row>
    </div>
</template>

<script>
    import cityData from './map-data/get-city-value.js';
    import homeMap from './components/map.vue';
    import dataSourcePie from './components/dataSourcePie.vue';
    import visiteVolume from './components/visiteVolume.vue';
    import serviceRequests from './components/serviceRequests.vue';
    import userFlow from './components/userFlow.vue';
    import countUp from './components/countUp.vue';
    import inforCard from './components/inforCard.vue';
    import mapDataTable from './components/mapDataTable.vue';
    import toDoListItem from './components/toDoListItem.vue';
    import Badge from '../../../node_modules/iview/src/components/badge/badge.vue';
    import Cookie from 'js-cookie';
    export default {
        name: 'home',
        components: {
            Badge,
            homeMap,
            dataSourcePie,
            visiteVolume,
            serviceRequests,
            userFlow,
            countUp,
            inforCard,
            mapDataTable,
            toDoListItem
        },
        data() {
            return {
                userAccount: '',
                getCashCount: 0,
                reConsumeOrder: 0,
                productCoinOrder: 0,
                weekNewUsers: [0, 0, 0, 0, 0, 0, 0],
                count: {
                    todayNewUser: 96,
                    monthCreateUser: 501,
                    dayInMoney: 0,
                    dayOutMoney: 0,
                    monthInMoney: 0,
                    monthOutMoney: 0
                },
                bochubi: {
                    accumulateEarning: 0,
                    bonusPaymentCost: 0,
                    managementCost: 20,
                    productCoinCost: 10,
                    repeatCoinCost: 0
                },
                cityData: cityData,
                showAddNewTodo: false,
                newToDoItemValue: ''
            };
        },
        computed: {
            avatorPath() {
                return localStorage.avatorImgPath;
            },
            hasToDolist () {
                return (this.getCashCount > 0 || this.reConsumeOrder > 0 || this.productCoinOrder > 0);
            }
        },
        methods: {
            init () {
                this.userAccount = Cookie.get('user');
                this.getHomePageData();
            },
            getHomePageData () {
                this.doGet({url: this.APIServer + '/goeIndex/newUsersOfNowMonth'}).then(result => {
                    if (result.success) {
                        this.count.monthCreateUser = result.data;
                    } else {
                        this.$Message.error('加载月新增用户数据失败!');
                    }
                });
                this.doGet({url: this.APIServer + '/goeIndex/newUsersOfNowDay'}).then(result => {
                    if (result.success) {
                        this.count.todayNewUser = result.data;
                    } else {
                        this.$Message.error('加载日新增用户数据失败!');
                    }
                });
                this.doGet({url: this.APIServer + '/goeIndex/financeOfDay'}).then(result => {
                    if (result.success) {
                        this.count.dayInMoney = result.data.accumulateEarningOfDay;
                        this.count.dayOutMoney = result.data.accumulateCostOfDay;
                    } else {
                        this.$Message.error('加载本日收入和支出数据失败!');
                    }
                });
                this.doGet({url: this.APIServer + '/goeIndex/financeOfMonth'}).then(result => {
                    if (result.success) {
                        console.log('result: ' + result)
                        this.count.monthInMoney = result.data.accumulateEarningOfMonth;
                        this.count.monthOutMoney = result.data.accumulateCostOfMonth;
                    } else {
                        this.$Message.error('加载本日收入和支出数据失败!');
                    }
                });
                this.doGet({url: this.APIServer + '/goeIndex/newUsersOfLastWeek'}).then(result => {
                    if (result.success) {
                        this.weekNewUsers = result.data;
                    } else {
                        this.$Message.error('加载周新增用户数据失败!');
                    }
                });
                this.doGet({url: this.APIServer + '/goeIndex/financeOfMonth'}).then(result => {
                    if (result.success) {

                    } else {
                        this.$Message.error('加载周新增用户数据失败!');
                    }
                });
                this.doGet({url: this.APIServer + '/goeIndex/financeOfAll'}).then(result => {
                    if (result.success) {
                        this.bochubi = result.data;
                    } else {
                        this.$Message.error('加载周新增用户数据失败!');
                    }
                });
                this.doGet({url: this.APIServer + '/goeIndex/countOfAuditWait'}).then(result => {
                    if (result.success) {
                        this.getCashCount = result.data;
                    } else {
                        this.$Message.error('加载待提现数量数据失败!');
                    }
                });
                this.doGet({url: this.APIServer + '/goeIndex/countOfOrderStatus?consumeTypeCode=6&deliveryStatusCode=1'}).then(result => {
                    if (result.success) {
                        this.reConsumeOrder = result.data;
                    } else {
                        this.$Message.error('加载待提现数量数据失败!');
                    }
                });
                this.doGet({url: this.APIServer + '/goeIndex/countOfOrderStatus?consumeTypeCode=5&deliveryStatusCode=1'}).then(result => {
                    if (result.success) {
                        this.productCoinOrder = result.data;
                    } else {
                        this.$Message.error('加载待提现数量数据失败!');
                    }
                });
            },

            addNewToDoItem() {
                this.showAddNewTodo = true;
            },
            addNew () {
                if (this.newToDoItemValue.length !== 0) {
                    this.toDoList.unshift({
                        title: this.newToDoItemValue
                    });
                    setTimeout(() => {
                        this.newToDoItemValue = '';
                    }, 200);
                    this.showAddNewTodo = false;
                } else {
                    this.$Message.error('请输入待办事项内容');
                }
            },
            cancelAdd () {
                this.showAddNewTodo = false;
                this.newToDoItemValue = '';
            },
            goToToDoView (view) {
                if (view === 'getCash') {
                    this.$router.push({
                        name: 'getCashManagement_cashManagement'
                    });
                } else if (view === 'reConsumeOrder') {
                    this.$router.push({
                        name: 'reConsumeWaitSendList_orderManagement'
                    });
                } else if (view === 'productCoinOrder') {
                    this.$router.push({
                        name: 'exchangeProductWaitSendList_orderManagement'
                    });
                }
            }
        },
        created () {
            this.init();
        }
    };
</script>
