<template>
<div class="full-container">
  <el-row :gutter="24">
    <el-col :span="18">
      <el-row>
        <el-date-picker
          v-model="queryDateRange"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="To"
          start-placeholder="Start date"
          end-placeholder="End date"
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
        <el-select v-model="queryParams.entity.device" clearable placeholder="装置">
          <el-option
            v-for="item in deviceOptions"
            :key="item.id"
            :label="item.label"
            :value="item.id">
          </el-option>
        </el-select>
        <el-button @click="filterQuery">筛选</el-button>
      </el-row>
      <el-table stripe :data="tableData" align="center" max-height="700">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="时间" prop="eventTime"></el-table-column>
        <el-table-column label="运维单位" prop="operator"></el-table-column>
        <el-table-column label="厂站" prop="substationName"></el-table-column>
        <el-table-column label="装置" prop="deviceName"></el-table-column>
        <el-table-column label="告警对象" prop="targetName"></el-table-column>
        <el-table-column label="告警描述" prop="description"></el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.page.current"
        :limit.sync="queryParams.page.size"
        @pagination="queryWarning"
      />
    </el-col>
    <el-col :span="6">
      <div class="chart-block" ref="chartPie"></div>
      <div class="chart-block" ref="chartBar"></div>
    </el-col>
  </el-row>
</div>
</template>

<script>
import {getWarning, getWarningStat} from "@/api/gridnt/intelAlert";
import Pagination from "@/components/Pagination";
import * as echarts from "echarts";

export default {
  name: "warning",
  props: ['datePickerOptions', 'divisionOptions'],
  components:{Pagination},
  data(){
    return{
      queryParams: {
        page: {
          current: 1,
          size: 20
        },
        entity: {
          startTime: '',
          endTime:'',
          operator: "",
          station: "",
          device: ""
        }
      },
      total: 0,
      tableData: [],
      queryDateRange: '',
    }
  },
  computed:{
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
    this.pieChart = echarts.init(this.$refs['chartPie']);
    this.barChart = echarts.init(this.$refs['chartBar']);
    this.initEcharts();
  },
  methods:{
    queryWarning(page){
      if(page.page !== this.queryParams.page.current){
        this.queryParams.page.current = page.page;
      }
      getWarning(this.queryParams).then(res => {
        if(res.data){
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
      });
    },
    queryWarningStat(){
      getWarningStat(this.queryParams).then(res => {
        if(res.data){
          this.updateEcharts(res.data);
        }
      });
    },
    filterQuery(){
      if(this.queryDateRange){
        this.queryParams.entity.startTime = this.queryDateRange[0];
        this.queryParams.entity.endTime = this.queryDateRange[1];
      }
      else{
        this.queryParams.entity.startTime = '';
        this.queryParams.entity.endTime = '';
      }
      // query from the first page
      this.queryWarningStat();
      this.queryWarning({page: 1});
    },
    updateEcharts(statData){
      let pieData = [];
      let barXAxis = [];
      let barData = [];
      for(let index in statData){
        pieData.push({
          name: statData[index].substation,
          value: statData[index].frequency
        });
        barXAxis.push(statData[index].substation);
        barData.push(statData[index].frequency);
      }
      this.pieChart.setOption({
        series: [{
          data: pieData
        }]
      });
      this.barChart.setOption({
        xAxis: {
          data: barXAxis
        },
        series: [{
          data: barData
        }]
      });
    },
    initEcharts(){
      this.pieChart.setOption({
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      });
      this.barChart.setOption({
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '警告次数',
            type: 'bar',
            data: []
          }
        ]
      });
    }
  },
}
</script>

<style scoped>
.full-container{
  height: 100%;
  width: 100%;
  position: relative;
}
el-row{
  width: 100%;
}
.chart-block{
  width: 400px;
  height: 400px;
}
</style>
