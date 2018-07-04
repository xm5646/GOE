<style lang="less">
</style>
<template>
    <div>
        <Row :gutter="16">
            <Col span="24">
                <Card>
                    <p slot="title">
                        <Icon type="ios-list-outline"></Icon>
                        公告列表
                    </p>
                    <Row class="margin-top-10 searchable-table-con1">
                        <!--<Table :columns="columns1" :data="userList"></Table>-->
                        <div style="height: 20px"></div>
                        <div v-if="noticesList.length>0">
                            <vue-table :tdata="noticesList"
                                       :tcolumns="columns1"
                                       :showHandle="true"
                                       :tdHeight="40"
                                       :handleFixed="true"
                                       :page="pageObj"
                                       @changePage="changePage"
                            >
                                <template slot="operations" slot-scope="scope">
                                    <i-switch v-model="scope.item.showStatus" size="large" @on-change="changeStatus(scope.item)">
                                        <span slot="open">显示</span>
                                        <span slot="close">隐藏</span>
                                    </i-switch>
                                <Button size="large" type="primary" @click="edit(scope.item)">&nbsp;修改&nbsp;</Button>
                                <Button size="large" type="primary" @click="deleteNotice(scope.item)">&nbsp;删除&nbsp;
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
            <edit-notice :notice="updateNoticeObj"></edit-notice>
        </Modal>
    </div>
</template>

<script>
    import vueTable from 'vue-table-lxm';
    import EditNotice from "./EditNotice";

    export default {
        name: 'page',
        mounted () {
            this.getAllNotice(0);
        },
        components: {
            EditNotice,
            vueTable
        },
        data () {
            return {
                pageObj: {
                    totalPage: 0,
                    totalCount: 7,
                    maxSize: 5
                },
                noticesList: [],
                deleteId: -1,
                deleteNoticeObj: {},
                updateNoticeObj: {},
                showEditModal: false,
                columns1: [
                    {
                        key: 'noticeId',
                        title: '公告编号',
                        width: 80
                    },
                    {
                        key: 'createTime',
                        title: '更新时间',
                        width: 100
                    },
                    {
                        key: 'title',
                        title: '公告标题',
                        width: 300
                    },
                    {
                        key: 'content',
                        title: '公告内容',
                        width: 500
                    }
                ]
            };
        },
        methods: {
            submitEdit () {
                this.updateNoticeRequest(this.updateNoticeObj);
            },
            edit (item) {
                this.updateNoticeObj = item;
                this.showEditModal = true;
            },
            deleteNotice (item) {
                this.deleteId = item.noticeId
                this.deleteNoticeObj = item
                this.$Modal.confirm({
                    title: '提示',
                    content: '确定要删除这条公告吗?',
                    onOk: this.deleteNoticeById
                });
            },
            updateNoticeRequest (item) {
                this.doPost({
                    url: this.APIServer + '/notice/updateNotice',
                    params: {
                        noticeId: item.noticeId,
                        title: item.title,
                        content: item.content,
                        showStatus: item.showStatus
                    }
                }).then(result => {
                    if (result.success) {
                        this.$Message.success(result.message);
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            },
            deleteNoticeById () {
                this.doGet({
                    url: this.APIServer + '/notice/deleteNotice?noticeId=' + this.deleteId
                }).then(result => {
                    if (result.success) {
                        this.$Message.success(result.message);
                    } else {
                        this.noticesList = [];
                        this.$Message.error(result.message);
                    }
                });
                this.noticesList.splice(this.getItemIndex(this.deleteNoticeObj), 1);
            },
            getItemIndex (val) {
                for (var i = 0; i < this.noticesList.length; i++) {
                    if (this.noticesList[i] === val) return i;
                }
                return -1;
            },
            changeStatus (item) {
                this.updateNoticeRequest(item);
            },
            changePage (pageNum) {
                this.getAllNotice(pageNum - 1);
            },
            getAllNotice (pageNum) {
                this.doGet({
                    url: this.APIServer + '/notice/getAllNotices?pageNum=' + pageNum
                }).then(result => {
                    if (result.success) {
                        this.pageObj.totalPage = result.data.totalPages;
                        this.pageObj.totalCount = result.data.totalElements;
                        this.noticesList = result.data.content;
                    } else {
                        this.noticesList = [];
                        this.$Message.error(result.message);
                    }
                });
            }
        }
    };
</script>