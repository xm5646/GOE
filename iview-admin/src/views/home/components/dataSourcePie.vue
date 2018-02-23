<template>
    <div style="width:100%;height:100%;" id="data_source_con"></div>
</template>

<script>
import echarts from 'echarts';

export default {
    name: 'dataSourcePie',
    data () {
        return {
            //
        };
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
                            {value: 303456, name: '发放奖金', itemStyle: {normal: {color: '#9bd598'}}},
                            {value: 105923, name: '剩余资金', itemStyle: {normal: {color: '#ffd58f'}}},
                            {value: 10250, name: '管理费', itemStyle: {normal: {color: '#e14f60'}}},
                            {value: 28403, name: '产品积分', itemStyle: {normal: {color: '#ab8df2'}}}
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
