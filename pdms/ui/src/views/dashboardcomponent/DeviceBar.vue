<template>
  <div id="device-bar" :style="styles"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'DeviceBar',
  props: {
    styles: String
  },
  mounted() {
    this.deviceBar = echarts.init(document.getElementById('device-bar'));
    this.initChart();
  },
  data() {
    return {};
  },
  methods: {
    initChart() {
      let firstdata = [77, 59, 90];
      let lastdata = [20, 49, 170];
      this.deviceBar.setOption({
        color: ['#1471fb', '#fb4643'],
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          show: false,
          data: ['设备数量'],
          bottom: '0%',
          textStyle: {
            color: '#fff'
          },
          itemGap: 30
        },
        toolbox: {
          show: false
        },
        xAxis: {
          type: 'value',
          axisLabel: {
            show:false,
          },
          axisLine: {
            show:false,
            lineStyle: {
              color: '#999'
            }
          },
          splitLine:{
            show:false,
          },
          axisTick: {
            show: false
          },
        },
        yAxis: {
          type: 'category',
          data: ['保护装置', '智能终端', '合并单元'],
          axisLine: {
            show:false,
            lineStyle: {
              color: '#fff'
            }
          },
          axisTick: {
            show: false
          }
        },
        grid: {
          bottom: '10%',
          top: '18%',
          left:'70'
        },
        series: [
          {
            name: '设备数量',
            type: 'bar',
            data: firstdata,
            barWidth: '33%',
            barGap: 0,
            label: {
              show: true,
              position: 'top',
              color: '#fff',
              // offset: [15,0],
              fontSize: '15',
              formatter: '{c}'
              // formatter:function(param){
              //   if(param.value < lastdata[param.dataIndex]){
              //     return ''
              //   }else{
              //     return param.value+"/{redspan|"+lastdata[param.dataIndex]+'}'
              //   }
              // },
              // rich:{
              //   redspan:{
              //     color:'#ff0000',
              //     fontSize:'15',
              //   }
              // }
            }
          }
          // {
          //   name: '异常',
          //   type: 'bar',
          //   data: lastdata,
          //   barWidth: '17%',
          //   barGap:0,
          //   label: {
          //     show: true,
          //     position: "top",
          //     offset: [-5,0],
          //     fontSize:'15',
          //     formatter:function(param){
          //       if(param.value < firstdata[param.dataIndex]){
          //         return ''
          //       }else{
          //         return "{whitespan|"+firstdata[param.dataIndex]+"/}"+param.value
          //       }
          //     },
          //     rich:{
          //       whitespan:{
          //         color:'#fff',
          //         fontSize:'15',
          //       }
          //     }
          //   }
          // }
        ]
      });
    }
  }
};
</script>

<style scoped></style>
