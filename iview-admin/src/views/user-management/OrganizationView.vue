<template>
    <div>
        <!--<h1>组织结构图</h1>-->
        <div class="col-md-10 col-md-offset-1">
            <!--<div class="page-header">-->
                <!--<h3>基于Vue的组织架构树组件</h3>-->
            <!--</div>-->
            <!--<div class="row">-->
                <!--<div class="col-md-8 col-md-offset-2">-->
                    <!--<form class="form-horizontal row">-->
                        <!--<div class="col-md-3">-->
                            <!--<div class="checkbox">-->
                                <!--<label>-->
                                    <!--<input type="checkbox" v-model="horizontal"> horizontal-->
                                <!--</label>-->
                            <!--</div>-->
                        <!--</div>-->
                        <!--<div class="col-md-3">-->
                            <!--<div class="checkbox">-->
                                <!--<label>-->
                                    <!--<input type="checkbox" v-model="collapsable"> collapsable-->
                                <!--</label>-->
                            <!--</div>-->
                        <!--</div>-->
                        <!--<div class="col-md-6">-->
                            <!--<div class="form-group">-->
                                <!--<label class="control-label col-md-5">labelClassName:</label>-->
                                <!--<div class="col-md-7">-->
                                    <!--<select class="form-control" v-model="labelClassName">-->
                                        <!--<option value="bg-white">bg-white</option>-->
                                        <!--<option value="bg-orange">bg-orange</option>-->
                                        <!--<option value="bg-gold">bg-gold</option>-->
                                        <!--<option value="bg-gray">bg-gray</option>-->
                                        <!--<option value="bg-lightpink">bg-lightpink</option>-->
                                        <!--<option value="bg-chocolate">bg-chocolate</option>-->
                                        <!--<option value="bg-tomato">bg-tomato</option>-->
                                    <!--</select>-->
                                <!--</div>-->
                            <!--</div>-->
                        <!--</div>-->
                    <!--</form>-->
                <!--</div>-->
            <!--</div>-->
            <!--<p><br></p>-->
            <div class="text-center">
                <org-tree style="color: #fff;"
                        :data="data"
                        :horizontal="horizontal"
                        :collapsable="collapsable"
                        :label-class-name="labelClassName"
                        :render-content="renderContent"
                        @on-expand="onExpand"
                        @on-node-click="onNodeClick"
                >
                </org-tree>
            </div>
        </div>
        <Modal :width="600" v-model="showInfoModal" title="会员信息" cancel-text="">
            <show-user-info :userInfo="userInfo" v-if="userInfo"></show-user-info>
        </Modal>
    </div>
</template>

<script>
    /* eslint-disable semi */

    import OrgTree from './org-tree';
    import ShowUserInfo from './components/ShowUserInfoWindow';

    export default {
        name: 'page',
        props: ['data'],
        components: {
            OrgTree,
            ShowUserInfo
        },
        data () {
            return {
                data1: {},
                horizontal: false,
                collapsable: true,
                showInfoModal: false,
                userInfo: null,
                labelClassName: 'bg-blue'
            }
        },
        methods: {
            renderContent (h, data) {
                return data.label
            },
            addData () {
                console.log(this.data.children);
                this.data.children.push({
                    id: 15,
                    label: '李晓明'
                })
            },
            onExpand (data) {
                this.$emit('loadData', data.children)
                if ('expand' in data) {
                    data.expand = !data.expand

                    if (!data.expand && data.children) {
                        this.collapse(data.children)
                    }
                } else {
                    this.$set(data, 'expand', true)
                }
            },
            onNodeClick (e, data) {
                this.userInfo = data
                this.showInfoModal = true
                console.log(data)
            },
            collapse (list) {
                list.forEach(child => {
                    if (child.expand) {
                        child.expand = false
                    }
                    child.children && this.collapse(child.children)
                })
            }
        }
    }
</script>
<style>
    .bg-white {
        background-color: white;
    }
    .bg-orange {
        background-color: orange;
    }
    .bg-blue {
        background-color: #2d8cf0;
    }
    .bg-gold {
        background-color: gold;
    }
    .bg-gray {
        background-color: gray;
    }
    .bg-lightpink {
        background-color: lightpink;
    }
    .bg-chocolate {
        background-color: chocolate;
    }
    .bg-tomato {
        background-color: tomato;
    }
    .text-center {
        width: 100%;
        display: table;
        overflow-x: scroll;
    }
</style>