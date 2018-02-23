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
                                <b class="card-user-infor-name">Admin</b>
                                <p>super admin</p>
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
                    <div class="to-do-list-con">
                        <ul>
                            <li style="margin-bottom: 6px;"><span>提现待处理订单</span>
                                <Badge class="message-count-badge-outer" class-name="message-count-badge"
                                       count="20"></Badge>
                            </li>
                            <li style="margin-bottom: 6px;"><span>重销待发货订单  <Badge class="message-count-badge-outer"
                                                                                  class-name="message-count-badge"
                                                                                  count="20"></Badge></span></li>
                            <li style="margin-bottom: 6px;"><span>积分兑换待发货订单  <Badge class="message-count-badge-outer"
                                                                                    class-name="message-count-badge"
                                                                                    count="20"></Badge></span></li>
                        </ul>
                    </div>
                </Card>
                </Col>
            </Row>
            </Col>
            <Col :md="24" :lg="16">
            <Row :gutter="5">
                <Col :xs="24" :sm="12" :md="8" :style="{marginBottom: '10px'}">
                <infor-card
                        id-name="user_created_count"
                        :end-val="count.createUser"
                        iconType="android-person-add"
                        color="#2d8cf0"
                        intro-text="今日新增用户"
                ></infor-card>
                </Col>
                <Col :xs="24" :sm="12" :md="8" :style="{marginBottom: '10px'}">
                <infor-card
                        id-name="visit_count"
                        :end-val="count.visit"
                        iconType="ios-eye"
                        color="#64d572"
                        :iconSize="50"
                        intro-text="本月累计支出"
                ></infor-card>
                </Col>
                <Col :xs="24" :sm="12" :md="8" :style="{marginBottom: '10px'}">
                <infor-card
                        id-name="collection_count"
                        :end-val="count.collection"
                        iconType="upload"
                        color="#ffd572"
                        intro-text="本月累计收入"
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
                        <data-source-pie></data-source-pie>
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
                        <visite-volume></visite-volume>
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
                toDoList: [
                    {
                        title: '去iView官网学习完整的iView组件'
                    },
                    {
                        title: '去iView官网学习完整的iView组件'
                    },
                    {
                        title: '去iView官网学习完整的iView组件'
                    },
                    {
                        title: '去iView官网学习完整的iView组件'
                    },
                    {
                        title: '去iView官网学习完整的iView组件'
                    }
                ],
                count: {
                    createUser: 496,
                    visit: 3122264,
                    collection: 4389305,
                    transfer: 39503498
                },
                cityData: cityData,
                showAddNewTodo: false,
                newToDoItemValue: ''
            };
        },
        computed: {
            avatorPath() {
                return localStorage.avatorImgPath;
            }
        },
        methods: {
            addNewToDoItem() {
                this.showAddNewTodo = true;
            },
            addNew() {
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
            cancelAdd() {
                this.showAddNewTodo = false;
                this.newToDoItemValue = '';
            }
        }
    };
</script>
