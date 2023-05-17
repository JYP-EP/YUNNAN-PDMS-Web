<template>
<!--  智能定检文件-->
  <div style="display: flex; height: 100%; position: relative">
    <el-row :gutter="24" style="width: 100%">
      <el-col :span="18">
        <el-row>
          <el-date-picker
            v-model="queryDateRange"
            type="daterange"
            align="right"
            unlink-panels
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            :picker-options="datePickerOptions">
          </el-date-picker>
          <el-select v-model="queryParams.entity.operator" clearable placeholder="运维单位">
            <el-option
              v-for="item in operatorOptions"
              :key="item.id"
              :label="item.label"
              :value="item.id">
            </el-option>
          </el-select>
          <el-select v-model="queryParams.entity.station" clearable placeholder="厂站">
            <el-option
              v-for="item in stationOptions"
              :key="item.id"
              :label="item.label"
              :value="item.id">
            </el-option>
          </el-select>
          <el-button @click="filterQuery">筛选</el-button>
        </el-row>
        <el-table max-height="700" :data="tableData" stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column align="center" prop="triggerTime" label="告警时间"></el-table-column>
          <el-table-column align="center" prop="alertType" label="告警类型" :formatter="alertTypeParser"></el-table-column>
          <el-table-column align="center" prop="operator" label="运维单位"></el-table-column>
          <el-table-column align="center" prop="substationName" label="厂站"></el-table-column>
          <el-table-column align="center" prop="iedName" label="装置"></el-table-column>
          <el-table-column align="center" prop="source" label="告警来源" :formatter="sourceParser"></el-table-column>
          <el-table-column align="center" prop="status" label="操作">
            <template slot-scope="scope">
              <inspect-report-xml :alert-item="scope.row"></inspect-report-xml>
            </template>
          </el-table-column>
          <el-table-column align="center" label="原始文件">
            <template slot-scope="scope">
              <raw-file-content :alert-item="scope.row"></raw-file-content>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.page.current"
          :limit.sync="queryParams.page.size"
          @pagination="requestAlert"
        />
      </el-col>
<!--      <el-col :span="6">-->
<!--        <div ref="chartPie" style="width: 400px; height: 400px;">Pie Here</div>-->
<!--        <div ref="chartLine" style="width: 400px; height: 400px;">Line Here</div>-->
<!--      </el-col>-->
    </el-row>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination';
import {getInspectionAlert} from '@/api/gridnt/intelAlert';
import * as echarts from "echarts";
import InspectReportXml from "@/views/gridnt/pomd/intelAlert/fileParser/inspectionReportXml";
import RawFileContent from "@/views/gridnt/pomd/intelAlert/fileParser/rawFileContent";

export default {
  name: 'inspectionAlert',
  props: ['datePickerOptions', 'divisionOptions'],
  components:{
    RawFileContent,
    InspectReportXml, Pagination},
  data() {
    return {
      queryParams: {
        page: {
          current: 1,
          size: 20
        },
        entity: {
          startTime: '',
          endTime:'',
          alertType: 0,
          operator: "",
          station: "",
          device: ""
        }
      },
      total: 0,
      tableData: [],
      queryDateRange: '',
      alterTypeOptions:[{
        value: 0,
        label: '所有告警'
      }, {
        value: 1,
        label: '频繁告警'
      }, {
        value: 2,
        label: '保护通道信息检查'
      }, {
        value: 3,
        label: '保护异常闭锁告警'
      }, {
        value: 4,
        label: '区外故障分析'
      },{
        value: 5,
        label: '主变变中电流告警'
      },{
        value: 6,
        label: '智能巡视文件'
      },{
        value: 7,
        label: '智能定检文件'
      },{
        value: 8,
        label: '智能运维文件'
      }],
      filterAlertType: 0
    };
  },
  computed: {
    operatorOptions(){
      return this.divisionOptions;
    },
    stationOptions(){
      let result = [];
      for(let index in this.divisionOptions){
        if(this.divisionOptions[index].id === this.queryParams.entity.operator){
          return this.divisionOptions[index].children;
        }
        else{
          result.push.apply(result, this.divisionOptions[index].children);
        }
      }
      return result;
    },
    deviceOptions(){
      let result = [];
      for(let index in this.stationOptions){
        if(this.stationOptions[index].id === this.queryParams.entity.station){
          return this.stationOptions[index].children;
        }
        else{
          result.push.apply(result, this.stationOptions[index].children);
        }
      }
      return result;
    }
  },
  mounted() {
    // this.initData()
    // this.pieChart = echarts.init(this.$refs['chartPie']);
    // this.lineChart = echarts.init(this.$refs['chartLine']);
    // this.initEcharts();
  },
  methods:{
    alertTypeParser(row){
      let typeNumber = (row.alertType > 8) ? 8 : row.alertType;
      let alterType = this.alterTypeOptions.find((item) => item.value === typeNumber);
      return alterType.label;
    },
    sourceParser(row) {
      return row.source === 0 ? "主站诊断" : "文件解析";
    },
    requestAlert(){
      getInspectionAlert(this.queryParams).then(resp=>{
        if(resp.data){
          this.tableData = resp.data.records;
          this.total = resp.data.total;

          // this.updateEcharts();
        }
      })
    },
    filterQuery(){
      if(this.queryDateRange){
        this.queryParams.entity.startTime = this.queryDateRange[0];
        this.queryParams.entity.endTime = this.queryDateRange[1];
      }
      this.requestAlert();
    },
    updateEcharts(){
      let alertTypeMap = new Map();
      this.tableData.forEach((row) =>{
        let typeCounter = 1;
        if(alertTypeMap.has(row.alertType)){
          typeCounter = alertTypeMap.get(row.alertType) + 1
        }
        alertTypeMap.set(row.alertType, typeCounter);
      });
      this.pieChart.setOption({
        series: [
          {
            data: [
              {
                value: alertTypeMap.has(1) ? alertTypeMap.get(1) : 0,
                name: '频繁告警'
              },{
                value: alertTypeMap.has(2) ? alertTypeMap.get(2) : 0,
                name: '保护通道信息检查'
              },{
                value: alertTypeMap.has(3) ? alertTypeMap.get(3) : 0,
                name: '保护异常闭锁告警'
              },{
                value: alertTypeMap.has(4) ? alertTypeMap.get(4) : 0,
                name: '区外故障分析'
              },{
                value: alertTypeMap.has(5) ? alertTypeMap.get(5) : 0,
                name: '主变变中电流告警'
              }
            ]
          }
        ]
      });

      alertTypeMap.clear();
      for(let i = 1; i <= 13; i++){
        alertTypeMap.set(i, new Map());
      }
      this.tableData.forEach((row) => {
        let itemDate = row.triggerTime.substr(0, 10);
        if(alertTypeMap.has(row.alertType)){
          let typeCounter = 1;
          if(alertTypeMap.get(row.alertType).has(itemDate)){
            typeCounter = alertTypeMap.get(row.alertType).get(itemDate) + 1;
          }
          alertTypeMap.get(row.alertType).set(itemDate, typeCounter);
        }
      });

      let dateArray = [];
      let Alert01Array = [];
      alertTypeMap.get(1).forEach((value, key)=>{
        dateArray.push(key);
        Alert01Array.push(value);
      });
      let Alert02Array = [];
      alertTypeMap.get(2).forEach((value, key)=>{
        Alert02Array.push(value);
      });
      let Alert03Array = [];
      alertTypeMap.get(3).forEach((value, key)=>{
        Alert03Array.push(value);
      });
      let Alert04Array = [];
      alertTypeMap.get(4).forEach((value, key)=>{
        Alert04Array.push(value);
      });
      let Alert05Array = [];
      alertTypeMap.get(5).forEach((value, key)=>{
        Alert05Array.push(value);
      });

      this.lineChart.setOption({
        xAxis: {
          boundaryGap: false,
          data: dateArray
        },
        series:[
          {
            type: 'line',
            name: '频繁告警',
            data: Alert01Array
          },{
            type: 'line',
            name: '保护通道信息检查',
            data: Alert02Array
          },{
            type: 'line',
            name: '保护异常闭锁告警',
            data: Alert03Array
          },{
            type: 'line',
            name: '区外故障分析',
            data: Alert04Array
          },{
            type: 'line',
            name: '主变变中电流告警',
            data: Alert05Array
          },
        ]
      });
    },
    initEcharts(){
      this.pieChart.setOption({
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            type: 'pie',
            radius: '60%',
            data: [{ value: 0, name: '智能告警' },]
          }
        ]
      });
      this.lineChart.setOption({
        xAxis: {
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value'
        },
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            data: [],
            type: 'line'
          }
        ]
      });
    }
  }
};
</script>

<style scoped>

</style>
