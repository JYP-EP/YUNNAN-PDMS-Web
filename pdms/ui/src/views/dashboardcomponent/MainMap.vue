<template>
  <div id="main-map" ></div>
</template>

<script>
import echarts from "echarts"
import {getSubstationListForMap,getSubstationStatusForMap} from "@/api/gridnt/dashboard";
export default {
  name: 'MainMap',
  mounted() {
    this.initMap();

  },
  created() {
    this.getSubstationList();
    this.getSubstationStatus();
  },
  destroyed() {
    this.mapChart = null
    this.mapValue = []
    this.subsStatus = null
  },
  data() {
    return {
      mapChart: null,
      mapValue:[],
      subsStatus:null,
      mapJson: require('@/api/gridnt/371700_full.json'),
    };
  },
  methods: {
    getSubstationList(){
      let self = this
      getSubstationListForMap().then(res=>{
        self.mapValue = res.data
        if(self.mapChart == null){
          self.initMap()
        }
        self.mapChart.setOption({
          series: [
            {
              type: "scatter",
              name: "变电站分布",
              coordinateSystem: "geo",
              itemStyle: {
                color:"#999999",
              },
              data:self.mapValue
            }
          ],
        })
      })
    },
    getColors(params){
      let f1041Code = params.data.f1041Code
      let color = '#999999'
      if(this.subsStatus[f1041Code] != undefined){
        let status = this.subsStatus[f1041Code].status
        if(status != undefined){
          switch(status){
            case '运行':　color='#00ff00';break;
            case '告警':　color='#ffff00';break;
            case '闭锁':　color='#ff9900';break;
            case '跳闸':　color='#ff0000';break;
            case '检修':　color='#3366ff';break;
            default:;
          }
        }
      }
      return color
    },
    getSubstationStatus(){
      let self = this
      getSubstationStatusForMap().then(resp =>{
        self.subsStatus = resp.data
        // self.subsStatus['JSBSubstation0000000102'].status='检修'
        self.$nextTick(()=>{
          if(self.mapChart != null){
            self.mapChart.setOption({
              series:[{
                itemStyle: {
                  color:function(params){
                    return self.getColors(params)
                  },
                },
              }]
            })
          }
        })

      })
    },
    initMap() {
      let self = this
      this.mapChart = echarts.init(document.getElementById('main-map'));
      echarts.registerMap('sds', this.mapJson, {});  //加载自定义边界数据地图
      var option = {
        title:{
          show:false,
          text:"变电站分布情况",
          left:'45%',
          top:'30',
          textStyle:{
            color:'#ffffff',
            fontSize:'30'
          }
        },
        geo: {
          id: 'sds',
          map: 'sds',
          show: true,
          zoom:1.0,
          label:{
            show:true,
            color:'#ffffff',
            fontSize:'16'
          },
          emphasis: {
            itemStyle: {
                areaColor: '#517ea7', //地图区域的颜色 也可以颜色渐变如下的 areaColor
                borderColor: '#0CD5DD', //边界线颜色
                borderWidth: '1', //边界线的宽度
            },
            label:{
              show:true,
              color:'#ffffff'
            }
          },
          itemStyle: {
            areaColor: '#031525', //地图区域的颜色 也可以颜色渐变如下的 areaColor
            borderColor: '#0CD5DD', //边界线颜色
            borderWidth: '1', //边界线的宽度
            color:'#ffffff',
            opacity:0.5

          },
        },
        series: [],
        tooltip: {
          trigger: "item",
          borderColor: 'rgba(0, 0, 0, 0.16);',
          padding:0,
          triggerOn: 'mousemove',
          formatter:function (params){
            return self.showStatus(params)
          }
        }
      }
      self.mapChart.setOption(option);
      self.mapChart.on('click', function (params) {
        if(params&&params.seriesType==='scatter'){
          self.$store.commit("changeCurrentNode",null)
          self.$store.commit("changeIed",null)
          self.$store.commit("changeSubsCode",params.data.f1041Code)
          self.$router.push({
            name:'状态监测',
          })
        }
      })
    },
    showStatus(params){
      let f1041Code = params.data.f1041Code
      let f1041Status = {}
      if(this.subsStatus !=null){
        f1041Status = this.subsStatus[f1041Code] != undefined ? this.subsStatus[f1041Code]:{}
      }
      let statusClass=''
      switch(f1041Status.status){
        case '运行':　statusClass='1';break;
        case '告警':　statusClass='2';break;
        case '闭锁':　statusClass='3';break;
        case '跳闸':　statusClass='4';break;
        case '检修':　statusClass='5';break;
        default:;
      }
      return  `<div class="status-tooltip" >
            <div style="display: flex;flex-direction: row">
                <span style="font-weight: bolder;margin-right: 15px">`+params.data.f1041Desc +`</span>运行状态
                <span class="station-status station-status-color`+statusClass+`" >`+(f1041Status.status == undefined? '':f1041Status.status )+`</span>
            </div>
            <div class="status-row">
              <div class="tooltip-col">
              <span>装置MMS通信率</span>
              <span class="status-val-blue">`+(f1041Status.mmsRate== undefined? '':f1041Status.mmsRate ) +`</span>
              </div>
              <div class="tooltip-col">
                <span>GOOSE通信率</span>
                <span class="status-val-blue">`+(f1041Status.gooseRate== undefined? '':f1041Status.gooseRate ) +`</span>
               </div>
               <div class="tooltip-col">
                <span>SMV通信率</span>
                <span class="status-val-blue">`+(f1041Status.smvRate== undefined? '':f1041Status.smvRate ) +`</span>
                </div>
            </div>
            <div class="status-row">
                 <div class="tooltip-col">
                <span>告警统计</span>
                <span class="status-val-red">`+(f1041Status.warningNumber== undefined? '':f1041Status.warningNumber ) +`</span>
                </div>
                 <div class="tooltip-col">
                <span>闭锁统计</span>
                <span class="status-val-red">`+(f1041Status.lockNumber == undefined? '':f1041Status.lockNumber )+`</span>
                </div>
                <div class="tooltip-col">
                <span>保护状态统计</span>
                <span class="status-val-red">`+(f1041Status.procStatusNumber == undefined? '':f1041Status.procStatusNumber )+`</span>
                </div>
            </div>
          <div>`
    }
  }
};
</script>

<style scoped>
#main-map {
  width: 100%;
  height: 100%;
  height: calc(100% - 50px);
  position: relative;
  padding-top: 30px;
}

/deep/.tooltip-col{
  display: flex;
  flex-direction: column;
  width: 33%
}

/deep/.station-status{
  color: #1e1e1e;
  margin-left: 15px;
  padding: 0px 12px
}
/deep/.station-status-color{
  background: #999999;
}
/deep/.station-status-color1{
  background: #00ff00;
}
/deep/.station-status-color2{
  background: #ffff00;
}
/deep/.station-status-color3{
  background: #ff9900;
}
/deep/.station-status-color4{
  background: #ff0000;
}
/deep/.station-status-color5{
  background: #3366ff;
}

/deep/.status-tooltip{
  width: 450px;
  padding: 15px;
  font-size: 16px;
  background-color: rgba(2,20,100,0.6);
}
/deep/.status-row{
  display: flex;
  flex-direction: row;
  margin-top: 30px
}
/deep/.status-col{
  display: flex;
  flex-direction: column;
  width: 33%
}
/deep/.status-val-blue,/deep/.status-val-red{
  font-size: 30px;
  margin-top: 10px;
  font-weight: bold
}
/deep/.status-val-blue{
  color: #1eb0fc;
}
/deep/.status-val-red{
  color: #e41e1e;
}
</style>
