<template>
  <div id="alarm-bar" :style="styles">
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "AlarmChart",
  props:{
    styles:String
  },
  mounted() {
    this.alarmChart = echarts.init(document.getElementById('alarm-bar'));
    this.initChart();
  },
  data(){
    return {
      alarmChart: null
    }
  },
  methods:{
    initChart(){
      this.alarmChart.setOption({
        xAxis: {
          type: 'category',
          data: [ '三级', '二级', '一级'],
          axisLabel: {
            color: '#fff',
            fontSize: '14',
            interval: 0
          },
          axisLine:{
            lineStyle:{
              color: '#999'
            }
          },
          axisTick:{
            show: false
          }
        },
        yAxis: {
          type: 'value',
          axisLine:{
            lineStyle:{
              color: '#999'
            }
          },
          axisTick:{
            show: false
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.09)'
            }
          }
        },
        grid:{
          bottom: '15%',
          top: '18%'
        },
        series: [{
          type: 'bar',
          barWidth: '20%',
          data: [

            {
              value: 160,
              itemStyle: {
                color: '#fbc758'
              }
            },
            {
              value: 50,
              itemStyle: {
                color: '#fd9c55'
              }
            },
            {
              value: 80,
              itemStyle: {
                color: '#f0609e'
              }
            }],
        }]
      })
    }
  }
}
</script>

<style scoped>

</style>
