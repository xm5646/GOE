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
                        <div>
                            <Input v-model="searchAccount" placeholder="请输入用户编号搜搜..." style="width: 200px"/>
                            <span @click="handleSearch2" style="margin: 0 10px;"><Button type="primary"
                                                                                         icon="search">搜索</Button></span>
                            <Button @click="handleCancel2" type="ghost">取消</Button>
                        </div>
                    </Row>
                    <Row class="margin-top-10 searchable-table-con1">

                        <organ-view :data="data"  @loadData="loadChildrenData"></organ-view>

                    </Row>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    /* eslint-disable semi */
    import organView from './OrganizationView.vue'

    export default {
        name: 'page',
        components: {
            organView
        },
        data() {
            return {
                searchAccount: '',
                data: {}
            }
        },
        methods: {
            init () {
                this.doGet({url: this.APIServer + '/goeIndexUserManagement/userLevelInfo?account=admin001'}).then(result => {
                    if (result.success) {
                        this.data = result.data
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            },
            getDataByAccount (account) {
                this.doGet({url: this.APIServer + '/goeIndexUserManagement/userLevelInfo?account=' + account}).then(result => {
                    if (result.success) {
                        this.insertDataToJson(account, result, this.data)
                        console.log(this.data)
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            },
            insertDataToJson(account, result, node) {
                if (!node) {
                    throw new Error('Empty Tree')
                }
                let que = []
                node = this.data
                que.push(node);
                while (que.length !== 0) {
                    node = que.shift();
                    if (account === node.label) {
                        node.children = result.data.children
                        break
                    } else {
                        if (node.children !== null && node.children.length > 0) {
                            for (let i = 0; i < node.children.length; i++) {
                                que.push(node.children[i])
                            }
                        }
                    }
                }
            },
            loadChildrenData (children) {
                if (children !== null && children.length > 0) {
                    for (let i = 0; i < children.length; i++) {
                        this.getDataByAccount(children[i].label)
                    }
                }
            },
            handleSearch2() {
                this.doGet({url: this.APIServer + '/goeIndexUserManagement/userLevelInfo?account=' + this.searchAccount}).then(result => {
                    if (result.success) {
                        this.data = result.data
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            },
            handleCancel2() {
                this.searchAccount = ''
                this.doGet({url: this.APIServer + '/goeIndexUserManagement/userLevelInfo?account=admin001'}).then(result => {
                    if (result.success) {
                        this.data = result.data
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            }
        },
        mounted() {
            this.init();
        }
    }
</script>
<style lang="less">
</style>