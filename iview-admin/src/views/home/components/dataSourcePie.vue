<template>
    <div style="width:100%;height:100%;" id="data_source_con"></div>
</template>

<script>
import echarts from 'echarts';

export default {
    name: 'dataSourcePie',
    props: ['numbers'],
    data () {
        return {
            //
        };
    },
    watch: {
        numbers (val) {
            console.log(val)
            this.initPage();
        }
    },
    methods: {
        initPage: function () {
            var dataSourcePie = echarts.init(document.getElementById('data_source_con'));
            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'right',
                    data: ['奖金', '管理费', '产品积分', '重销奖金', '剩余资金']
                },
                series: [
                    {
                        name: '资金占比',
                        type: 'pie',
                        radius: '66%',
                        center: ['50%', '60%'],
                        data: [
                            {value: this.numbers.bonusPaymentCost, name: '奖金', itemStyle: {normal: {color: '#9bd598'}}},
                            {value: this.freeMoney, name: '剩余资金', itemStyle: {normal: {color: '#ffd58f'}}},
                            {value: this.numbers.managementCost, name: '管理费', itemStyle: {normal: {color: '#e14f60'}}},
                            {value: this.numbers.productCoinCost, name: '产品积分', itemStyle: {normal: {color: '#ab8df2'}}},
                            {value: this.numbers.repeatCoinCost, name: '重销奖金', itemStyle: {normal: {color: '#3399FF'}}}
                        ],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            dataSourcePie.setOption(option);
            window.addEventListener('resize', function () {
                dataSourcePie.resize();
            });
        }
    },
    computed: {
        freeMoney: function () {
            return Number(this.numbers.accumulateEarning) - Number(this.numbers.bonusPaymentCost) - Number(this.numbers.managementCost) - Number(this.numbers.productCoinCost)
        }
    },
    mounted () {
        this.$nextTick(() => {
            var dataSourcePie = echarts.init(document.getElementById('data_source_con'));
            const option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'right',
                    data: ['发放奖金', '管理费', '产品积分', '剩余资金']
                },
                series: [
                    {
                        name: '资金占比',
                        type: 'pie',
                        radius: '66%',
                        center: ['50%', '60%'],
                        data: [
                            {value: this.numbers.bonusPaymentCost, name: '发放奖金', itemStyle: {normal: {color: '#9bd598'}}},
                            {value: this.freeMoney, name: '剩余资金', itemStyle: {normal: {color: '#ffd58f'}}},
                            {value: this.numbers.managementCost, name: '管理费', itemStyle: {normal: {color: '#e14f60'}}},
                            {value: this.numbers.productCoinCost, name: '产品积分', itemStyle: {normal: {color: '#ab8df2'}}}
                        ],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            dataSourcePie.setOption(option);
            window.addEventListener('resize', function () {
                dataSourcePie.resize();
            });
        });
    }
};
</script>
