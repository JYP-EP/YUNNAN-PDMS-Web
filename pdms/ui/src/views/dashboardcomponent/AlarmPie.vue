<template>
  <div 　ref="alarm-pie" :style="styles"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'AlarmPie',
  props: {
    styles: String
  },
  mounted() {
    this.alarmChart = echarts.init(this.$refs['alarm-pie']);
    this.initChart();
  },
  data() {
    return {};
  },
  methods: {
    initChart() {
      this.alarmChart.setOption({
        color: ['#4e5fe8', '#b71fc9', '#fc873c','#00c8b5'],
        tooltip: {
          trigger: 'item'
        },
        legend: {
          show:false,
          bottom: '2%',
          left: 'center',
          textStyle: {
            color: '#fff',
          },
          itemGap: 15
        },
        series: [
          {
            type: 'pie',
            radius: ['5%', '70%'],
            avoidLabelOverlap: false,
            label: {
              color: '#28c8f9',
              fontSize:'14'
            },
            labelLine: {
              lineStyle: {
                color: '#28c8f9'
              }
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '14',
                fontWeight: 'bold'
              }
            },
            data: [
              { value: 1048, name: '线路保护' },
              { value: 735, name: '母线保护' },
              { value: 735, name: '主变保护' },
              { value: 735, name: '母联保护' }
            ]
          }
        ]
      });
    }
  }
};
</script>

<style scoped></style>
