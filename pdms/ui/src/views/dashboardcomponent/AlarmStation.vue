<template>
  <div id="alarm-station" :style="styles"></div>
</template>

<script>
import * as echarts from 'echarts';
import {getAllStationStatusData} from "@/api/gridnt/dashboard";

export default {
  name: 'AlarmStation',
  props: {
    styles: String
  },
  mounted() {
    this.deviceBar = echarts.init(document.getElementById('alarm-station'));
    this.getAllStationRate()
  },
  data() {
    return {
      rateData: {
        onlineRate: '',
        mmsRate: '',
        gooseRate: '',
        smvRate: ''
      }
    };
  },
  methods: {
    getAllStationRate(){
      getAllStationStatusData().then(res =>{
        this.rateData = res.data
        this.initChart();
      })
    },
    initChart() {
      const colors = ['#0072f4', '#f8619b','#A5A5A5','#00ff66']
      this.deviceBar.setOption({
        color: ['#1471fb', '#fb4643'],
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          show: false,
          data: ['比率'],
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
          type: 'category',
          data: ['在线率', 'MMS通信率', 'GOOSE通信率','SMV通信率'],
          axisLabel: {
            color: '#fff',
            fontSize: '12',
            interval: 0
          },
          axisLine: {
            lineStyle: {
              color: '#999'
            }
          },
          axisTick: {
            show: false
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            lineStyle: {
              color: '#fff'
            }
          },
          axisTick: {
            show: false
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.09)'
            }
          }
        },
        grid: {
          bottom: '10%',
          top: '18%'
        },
        series: [
          {
            name: '比率',
            type: 'bar',
            data: [this.rateData.onlineRate, this.rateData.mmsRate, this.rateData.gooseRate,this.rateData.smvRate],
            barWidth: '20%',
            barGap: 0,
            label: {
              show: true,
              position: 'top',
              color: '#fff',
              // offset: [15,0],
              fontSize: '15',
              formatter: '{c}'
            },
            itemStyle:{
              color: function(params){
                return colors[params.dataIndex];
                // 取每条数据的 index 对应 colors 中的 index 返回这个颜色
              }
            }
          }
        ]
      });
    }
  }
};
</script>

<style scoped></style>
