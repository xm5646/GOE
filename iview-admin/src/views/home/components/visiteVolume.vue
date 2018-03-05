<template>
    <div style="width:100%;height:100%;" id="visite_volume_con"></div>
</template>

<script>
import echarts from 'echarts';
export default {
    name: 'visiteVolume',
    props: ['counts'],
    data () {
        return {
            //
        };
    },
    watch: {
        counts (val) {
            this.initPage();
        }
    },
    methods: {
        initPage: function () {
            let visiteVolume = echarts.init(document.getElementById('visite_volume_con'));
            let xAxisData = [];
            let data1 = [];
            let data2 = [];
            for (let i = 0; i < 20; i++) {
                xAxisData.push('类目' + i);
                data1.push((Math.sin(i / 5) * (i / 5 - 10) + i / 6) * 5);
                data2.push((Math.cos(i / 5) * (i / 5 - 10) + i / 6) * 5);
            }

            const option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                grid: {
                    top: 0,
                    left: '2%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01]
                },
                yAxis: {
                    type: 'category',
                    data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
                    nameTextStyle: {
                        color: '#c3c3c3'
                    }
                },
                series: [
                    {
                        name: '新增会员',
                        type: 'bar',
                        data: [
                            {value: this.counts[6], name: 'Mon', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[5], name: 'Tues', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[4], name: 'Wed', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[3], name: 'Thur', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[2], name: 'Fri', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[1], name: 'Sat', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[0], name: 'Sun', itemStyle: {normal: {color: '#2d8cf0'}}}
                        ]
                    }
                ]
            };

            visiteVolume.setOption(option);

            window.addEventListener('resize', function () {
                visiteVolume.resize();
            });
        }
    },
    mounted () {
        this.$nextTick(() => {
            let visiteVolume = echarts.init(document.getElementById('visite_volume_con'));
            let xAxisData = [];
            let data1 = [];
            let data2 = [];
            for (let i = 0; i < 20; i++) {
                xAxisData.push('类目' + i);
                data1.push((Math.sin(i / 5) * (i / 5 - 10) + i / 6) * 5);
                data2.push((Math.cos(i / 5) * (i / 5 - 10) + i / 6) * 5);
            }

            const option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                grid: {
                    top: 0,
                    left: '2%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01]
                },
                yAxis: {
                    type: 'category',
                    data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
                    nameTextStyle: {
                        color: '#c3c3c3'
                    }
                },
                series: [
                    {
                        name: '新增会员',
                        type: 'bar',
                        data: [
                            {value: this.counts[6], name: 'Mon', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[5], name: 'Tues', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[4], name: 'Wed', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[3], name: 'Thur', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[2], name: 'Fri', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[1], name: 'Sat', itemStyle: {normal: {color: '#2d8cf0'}}},
                            {value: this.counts[0], name: 'Sun', itemStyle: {normal: {color: '#2d8cf0'}}}
                        ]
                    }
                ]
            };

            visiteVolume.setOption(option);

            window.addEventListener('resize', function () {
                visiteVolume.resize();
            });
        });
    }
};
</script>
