<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="help-buoy"></Icon>
                    会员列表
                </p>
                <Row>
                    <Input v-model="searchConName3" placeholder="请输入姓名搜搜..." style="width: 200px" />
                    <span @click="handleSearch3" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                    <Button @click="handleCancel3" type="ghost" >取消</Button>
                </Row>
                <Row class="margin-top-10 searchable-table-con1">
                    <Table :columns="columns1" :data="data2"></Table>
                </Row>
            </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
export default {
    name: 'page'
};
</script>