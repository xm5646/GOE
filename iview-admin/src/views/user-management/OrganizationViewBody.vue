<template>
    <div style="width: 100%;overflow:auto;" class="11111">

        <Row :gutter="10">
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="person-stalker"></Icon>
                    组织结构
                </p>
                <Row>
                    <organ-view></organ-view>
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
        data () {
            return {
            }
        },
        methods: {
        }
    }
</script>
<style>
</style>