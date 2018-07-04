<style lang="less">
</style>
<template>
    <div>
        <Row :gutter="16">
            <Col span="24">
                <Card>
                    <p slot="title">
                        <Icon type="android-add"></Icon>
                        新增公告
                    </p>
                    <Form  :label-width="80" ref="formValidate" :model="formValidate" :rules="ruleValidate">
                        <FormItem label="公告标题" prop="title">
                            <Input style="width: 350px" v-model="formValidate.title" placeholder="输入公告的标题"></Input>
                        </FormItem>
                        <FormItem label="公告内容" prop="content">
                            <Input style="width: 350px" type="textarea" :autosize="{minRows: 3,maxRows: 5}" v-model="formValidate.content" placeholder="输入公告的详细内容"></Input>
                        </FormItem>
                        <FormItem label="显示状态">
                            <i-switch v-model="showStatus" size="large">
                                <span slot="open">显示</span>
                                <span slot="close">隐藏</span>
                            </i-switch>
                        </FormItem>
                        <FormItem>
                            <Button type="ghost" style="margin-left: 8px; margin-right: 10px;" @click="resetInput()">重填</Button>
                            <Button type="primary"  @click="handleSubmit('formValidate')">添加</Button>
                        </FormItem>
                    </Form>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
export default {
    name: 'addNotice',
    data () {
        return {
            formValidate: {
                title: '',
                content: ''
            },
            showStatus: true,
            ruleValidate: {
                title: [
                    {required: true, message: '标题不能为空', trigger: 'blur'},
                ],
                content: [
                    {required: true, message: '公告详细内容不能为空', trigger: 'blur'},
                ]
            },
        };
    },
    methods: {
        resetInput () {
            this.formValidate.title = '';
            this.formValidate.content = '';
        },
        handleSubmit (name) {
            this.$refs[name].validate((valid) => {
                if (valid) {
                    this.doPost({
                        url: this.APIServer + '/notice/addNotice',
                        params: {
                            title: this.formValidate.title,
                            content: this.formValidate.content,
                            showStatus: this.showStatus
                        }
                    }).then(result => {
                        if (result.success) {
                            this.resetInput();
                            this.$Message.success(result.message);
                        } else {
                            this.$Message.error(result.message);
                        }
                    });
                }
            });
        }
    }
};
</script>