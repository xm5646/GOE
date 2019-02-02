<template>
    <div>
        <Form :model="notice" :label-width="80">
            <FormItem label="公告标题">
                <Input style="width: 350px"  v-model="notice.title" placeholder="Enter something..."></Input>

            </FormItem>
            <FormItem label="公告内容">
                <Input style="width: 350px" :autosize="{minRows: 3,maxRows: 5}" v-model="notice.content" type="textarea" placeholder="Enter something..."></Input>
            </FormItem>
            <FormItem label="显示状态">
                <i-switch v-model="notice.showStatus" size="large">
                    <span slot="open">显示</span>
                    <span slot="close">隐藏</span>
                </i-switch>
            </FormItem>
        </Form>
    </div>
</template>

<script>
    export default {
        name: 'editNotice',
        props: {
            notice: {
                type: Object,
                default: null
            }
        },
        data () {
            return {};
        },
        methods: {
        }
    };
</script>