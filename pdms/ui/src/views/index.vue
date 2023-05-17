<template>
  <div style="display: flex; height: 100%; position: relative">
    <div style="flex:1">
      <div style="position: absolute;top: 5%;right: 30%;z-index: 99">
        <el-button type="primary" @click="handleClick">站所列表</el-button>
      </div>
      <main-map ></main-map>
    </div>
    <el-row style="position: absolute; width: 100%; height: 100%; z-index: 333;pointer-events: none;">
      <el-col class="left-div" :span="6" style="pointer-events: auto;">
        <div style="background-color: #212936bb" class="box-card">
          <div class="module-title">
            子站状态统计
          </div>
          <div>
            <div class="div-corner corner-lt"></div>
            <div class="div-corner corner-rt"></div>
            <div class="div-corner corner-lb"></div>
            <div class="div-corner corner-rb"></div>
          </div>
          <AlarmStation :styles="alarmBarStyle"/>
        </div>
        <div style="background-color: #212936bb" class="box-card">
          <div class="module-title">
            巡视报告
          </div>
          <div>
            <div class="div-corner corner-lt"></div>
            <div class="div-corner corner-rt"></div>
            <div class="div-corner corner-lb"></div>
            <div class="div-corner corner-rb"></div>
          </div>
          <div style="padding: 10px;height: 10%;width: 100%">
            <p class="visitRate">巡视率：{{visitRate}}</p>
          </div>
          <NotVisitStation  :data="notVisitReportData"></NotVisitStation>
        </div>
      </el-col>
      <el-col class="right-div" :offset="12" style="pointer-events: auto; " :span="6">
        <div style="background-color: #212936bb" class="box-card">
          <div class="module-title">
            告警统计
          </div>
          <div>
            <div class="div-corner corner-lt"></div>
            <div class="div-corner corner-rt"></div>
            <div class="div-corner corner-lb"></div>
            <div class="div-corner corner-rb"></div>
          </div>
            <alarm-statistics :data = "alarmData"></alarm-statistics>
        </div>
        <div style="background-color: #212936bb" class="box-card">
          <div class="module-title">
            诊断报告
          </div>
          <div>
            <div class="div-corner corner-lt"></div>
            <div class="div-corner corner-rt"></div>
            <div class="div-corner corner-lb"></div>
            <div class="div-corner corner-rb"></div>
          </div>
          <diagnose-report :data="reportData"></diagnose-report>
        </div>
      </el-col>
    </el-row>
    <el-dialog title="子站列表" :visible.sync="dialogTableVisible">
      <el-table :data="stationList" class="customer-table"  max-height="350">
        <el-table-column   label="序号" align="center" width="80" type="index"></el-table-column>
        <el-table-column property="f1041Dqdesc" label="区域" width="280"></el-table-column>
        <el-table-column property="f1041Desc" label="站点名"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="gotoDetail(scope.row)" type="primary" size="small">进入</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import DsfhHeader from '../layout/components/DsfhHeader.vue';
import AlarmChart from './dashboardcomponent/AlarmBar.vue';
import AlarmPie from '@/views/dashboardcomponent/AlarmPie';
import DeviceBar from '@/views/dashboardcomponent/DeviceBar';
import AlarmTable from '@/views/dashboardcomponent/AlarmTable';
import MainMap from '@/views/dashboardcomponent/MainMap';
import DiagnoseReport from '@/views/dashboardcomponent/DiagnoseReport'
import NotVisitStation from "@/views/dashboardcomponent/NotVisitStation";
import AlarmStatistics from "@/views/dashboardcomponent/AlarmStatistics"
import '../assets/styles/gridnt/dashboard.css';
import '../assets/styles/gridnt/corner.css';
import AlarmStation from "@/views/dashboardcomponent/AlarmStation";
import AlarmRose from "@/views/dashboardcomponent/AlarmRose";
import {getVisitRate, getNotVisitStationList, getJudgeReportStatistics,getSubstationList} from "@/api/gridnt/intelAlert";
import {getWarningStatisticsList} from "@/api/gridnt/dashboard";

export default {
  name: 'Index',
  components: {
    DsfhHeader,
    AlarmChart,
    AlarmPie,
    DeviceBar,
    AlarmTable,
    AlarmStatistics,
    MainMap,AlarmStation,AlarmRose,
    DiagnoseReport,NotVisitStation
  },
  created() {
    this.getVisitRate();
    this.getNotVisitStations();
    this.getJudgeReportStatistics();
    this.getSubstations();
    this.getWarningList();
  },
  data() {
    return {
      positionSrc: require('@/assets/images/gridnt/dashboard/position.png'),
      countImgSrc: require('@/assets/images/gridnt/dashboard/count-bk.png'),
      alarmBarStyle: 'height:100%',
      buildingCount: 0,
      reportData:[],
      notVisitReportData:[],
      visitRate: "",
      dialogTableVisible:false,
      stationList:[],
      mapValue:[],
      alarmData:[]
    };
  },
  methods: {
    handleClick(){
     this.dialogTableVisible = true;
    },
    gotoDetail(row){
      this.$store.commit("changeCurrentNode",null)
      this.$store.commit("changeIed",null)
      this.$store.commit("changeSubsCode",row.f1041Code)
      this.$router.push({
        path:'substation',
      })
    },
    getWarningList(){
      getWarningStatisticsList().then(res =>{
        this.alarmData = res.data
      })
    },
    getSubstations() {
      getSubstationList().then(res=>{
        this.stationList = res.data
      })
    },
    getVisitRate(){
      getVisitRate().then(res=>{
        this.visitRate = res.data
      })
    },
    getNotVisitStations(){
      getNotVisitStationList().then(res=>{
        this.notVisitReportData = res.data
      })
    },
    getJudgeReportStatistics(){
      getJudgeReportStatistics().then(res=>{
        this.reportData = res.data
      })
    }
  }
};
</script>

<style scoped lang="scss">
#app .main-container {
  margin-left: 0px;
}
.visitRate{
  font-size: 18px;
  color: #0e95d2;
  text-align: right;
  font-weight: bold
}
::v-deep .el-dialog__body{
  background-color: #081a52;
}
::v-deep .el-dialog__header{
  background-color: #081a52;
}
::v-deep .el-dialog__title{
  color: #fff;
}
::v-deep .el-table th{
  background-color: #081a52;
  color: #fff;
  font-size: 16px;
}
::v-deep .el-table tr{
  background-color: #081a52;
  color: #fff;
}
//::v-deep .el-table td,.building-top .el-table th.is-leaf {
//  border-bottom:  1px solid #007ACC;
//}
::v-deep .el-table__row > td {
  /* 去除表格线 */
  border: none;
}
::v-deep .table-wrapper >>> .el-table__inner-wrapper::before{
  /* 去除下边框 */
  height: 0;
}
/* 用来设置当前页面element全局table 鼠标移入某行时的背景色*/
::v-deep .el-table__body tr.hover-row > td {
  background-color: #242f70 !important;
  color: #fff;  /* 设置文字颜色，可以选择不设置 */
}
::v-deep .el-table--border, .el-table--group{
  border: none;
}
// 表格最外层边框-底部边框
::v-deep .el-table--border::after, .el-table--group::after{
  width: 0;
}
::v-deep .customer-table::before{
  width: 0;
}
::v-deep .customer-table .el-table__fixed-right::before,.el-table__fixed::before{
  width: 0;
}
// 头部边框
::v-deep .customer-table thead tr th.is-leaf{
  border:none;
}
::v-deep .customer-table thead tr th:nth-last-of-type(2){
  border-right:none;
}
::v-deep .el-table__fixed-right-patch{
  background: transparent!important;
}
</style>
<style type="text/css" >
/*设置鼠标经过table列背景色*/
.el-table__body tr:hover>td{
  background-color: #619ac3 !important;
}
.el-tabs--border-card {
  background-color: rgb(6, 16, 41) !important;
  border: 0px solid #DCDFE6;
  -webkit-box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12), 0 0 6px 0 rgba(0, 0, 0, 0.04);
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12), 0 0 6px 0 rgba(0, 0, 0, 0.04);
}

.df_border {
  border: 1px solid #2c3340;
}

.el-table th.el-table__cell.is-leaf, .el-table td.el-table__cell {
  border-bottom: 1px solid #2c3340;
  border-right: 1px solid #2c3340;
}

</style>
