<style lang="less">
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="ios-list-outline"></Icon>
                    批量处理提现
                </p>
                <Row>
                    <Alert>
                        批量处理提现说明
                        <template slot="desc">
                            1.使用[提现管理] - [待处理提现] 页面中的 "导出到表格" 按钮,将所有未处理的提现申请导出到表格文件<br>
                            2.根据表格信息,对用户进行审核、打款，并更新表格中最后一列处理状态<br>
                            3.将完成处理的表格文件上传,进行批量处理
                        </template>
                    </Alert>
                </Row>
                <Row>
                    <div>
                        <Upload
                                :before-upload="handleUpload"
                                action="//jsonplaceholder.typicode.com/posts/">
                            <Button type="ghost" icon="ios-cloud-upload-outline">选择已处理的Excel表格</Button>
                        </Upload>
                        <div v-if="file !== null">已选择文件: {{ file.name }} <Button  @click="upload" :loading="loadingStatus">{{ loadingStatus ? 'Uploading' : '点击上传' }}</Button></div>
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
                file: null,
                loadingStatus: false
            };
        },
        methods: {
            handleUpload (file) {
                this.file = file;
                return false;
            },
            upload () {
                this.loadingStatus = true;
                let formData = new FormData();
                formData.append('fileName',this.file)
                this.doPost({
                    url: this.APIServer + '/excel/uploadExcel',
                    params: formData
                }).then(result => {
                    this.loadingStatus = false;
                    if (result.success) {
                        this.$Message.success(result.message);
                    } else {
                        this.$Message.error(result.message);
                    }
                });
            }
        }
    };
</script>
