<template>
  <!--  智能告警总览-->
  <div style="display: flex; height: 100%;">
    <el-row :gutter="24" style="width: 100%">
      <el-col :span="17">
        <el-table :data="tableData" class="df_border" max-height="700">
          <el-table-column type="index"></el-table-column>
          <el-table-column align="center" label="告警时间" prop="triggerTime"></el-table-column>
          <el-table-column :formatter="alertTypeParser" align="center" label="告警类型"
                           prop="alertType"></el-table-column>
          <el-table-column align="center" label="运维单位" prop="operator"></el-table-column>
          <el-table-column align="center" label="厂站" prop="substationName"></el-table-column>
          <el-table-column align="center" label="装置" prop="iedName"></el-table-column>
          <el-table-column :formatter="sourceParser" align="center" label="告警来源" prop="source"></el-table-column>


          <!--              根据类型判断操作有哪些-->
          <el-table-column align="center" label="详情" prop="status">
            <template slot-scope="scope">
              <G_SecCircuitFaultDiagXml v-if="scope.row.alertType === 101" :alert-item="scope.row"></G_SecCircuitFaultDiagXml>
              <G_StatusMonitorXml v-if="scope.row.alertType === 102" :alert-item="scope.row"></G_StatusMonitorXml>
              <G_SamCompXml v-if="scope.row.alertType === 103" :alert-item="scope.row"></G_SamCompXml>
              <G_SettingXml v-if="scope.row.alertType === 104" :alert-item="scope.row"></G_SettingXml>
              <G_DevAlaLocationXml v-if="scope.row.alertType === 105" :alert-item="scope.row"></G_DevAlaLocationXml>
              <G_TripDiagXml v-if="scope.row.alertType === 106" :alert-item="scope.row"></G_TripDiagXml>
              <G_RelayBlkInfoXml v-if="scope.row.alertType === 107" :alert-item="scope.row"></G_RelayBlkInfoXml>
              <G_SecCircuitFaultDiagXml v-if="scope.row.alertType === 108" :alert-item="scope.row"></G_SecCircuitFaultDiagXml>
              <G_SettingXml v-if="scope.row.alertType === 109" :alert-item="scope.row"></G_SettingXml>
              <G_RelayBlkInfoXml v-if="scope.row.alertType === 110" :alert-item="scope.row"></G_RelayBlkInfoXml>

              <check-report-xml v-if="scope.row.alertType === 6" :alert-item="scope.row"></check-report-xml>
            </template>
          </el-table-column>
        </el-table>


        <pagination
          v-show="total>0"
          :limit.sync="queryParams.page.size"
          :page.sync="queryParams.page.current"
          :total="total"
          @pagination="requestAlert"
        />
      </el-col>
      <el-col :span="7">
        <div style="text-align: center; font-weight: bolder;  alignment: center;color:white;">
          <!--          所有告警统计-->
          <div ref="chartPie" style="height: 810px;">Pie Here</div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination';
import frequentXml from "@/views/gridnt/pomd/intelAlert/fileParser/frequentXml";
import {getIntelAlertData, getIntelAlertStat} from '@/api/gridnt/intelAlert';
import * as echarts from "echarts";
import TunnelXml from "@/views/gridnt/pomd/intelAlert/fileParser/tunnelXml";
import CommRatioXml from "@/views/gridnt/pomd/intelAlert/fileParser/commRatioXml";
import SecCircuitXml from "@/views/gridnt/pomd/intelAlert/fileParser/secCircuitXml";
import AnalogXml from "@/views/gridnt/pomd/intelAlert/fileParser/analogXml";
import DoubleAnalogXml from "@/views/gridnt/pomd/intelAlert/fileParser/doubleAnalogXml";
import CompareXml from "@/views/gridnt/pomd/intelAlert/fileParser/compareXml";
import CheckReportXml from "@/views/gridnt/pomd/intelAlert/fileParser/checkReportXml";
import InspectReportXml from "@/views/gridnt/pomd/intelAlert/fileParser/inspectionReportXml";
import G_SettingValueAlert from "@/views/gridnt/pomd/intelAlert/fileParser/G_SettingValueAlert";
import G_SecCircuit from "@/views/gridnt/pomd/intelAlert/fileParser/G_SecCircuit";

//区外故障和主变变中
import OuterRimXml from "@/views/gridnt/pomd/intelAlert/fileParser/outerRimXml";
import TransCurrentXml from "@/views/gridnt/pomd/intelAlert/fileParser/transCurrentXml";
import G_analogXml from "@/views/gridnt/pomd/intelAlert/fileParser/G_analogXml";
import G_SecCircuitFaultDiagXml from "@/views/gridnt/pomd/intelAlert/fileParser/G_SecCircuitFaultDiagXml.vue";
import {red} from "chalk";
import G_StatusMonitorXml from "@/views/gridnt/pomd/intelAlert/fileParser/G_StatusMonitorXml.vue";
import G_SettingXml from "@/views/gridnt/pomd/intelAlert/fileParser/G_SettingXml.vue";
import G_DevAlaLocationXml from "@/views/gridnt/pomd/intelAlert/fileParser/G_DevAlaLocationXml.vue";
import G_RelayBlkInfoXml from "@/views/gridnt/pomd/intelAlert/fileParser/G_RelayBlkInfoXml.vue";
import G_SamCompXml from "@/views/gridnt/pomd/intelAlert/fileParser/G_SamCompXml.vue";
import G_TripDiagXml from "@/views/gridnt/pomd/intelAlert/fileParser/G_TripDiagXml.vue";

export default {
  name: 'genericAlert',
  props: [],
  components: {
    G_TripDiagXml,
    G_SamCompXml,
    G_RelayBlkInfoXml,
    G_DevAlaLocationXml,
    G_SettingXml,
    G_StatusMonitorXml,
    G_SecCircuitFaultDiagXml,
    //注册区外故障和主变变中
    TransCurrentXml,
    OuterRimXml,
    InspectReportXml,
    CheckReportXml,
    G_SettingValueAlert, G_SecCircuit, G_analogXml,
    CompareXml, DoubleAnalogXml, AnalogXml, SecCircuitXml, CommRatioXml, TunnelXml, Pagination, frequentXml,
  },
  data() {
    return {
      queryParams: {
        page: {
          current: 1,
          size: 20
        },
        entity: {
          startTime: '',
          endTime: '',
          alertType: 0,
          minType: 0,
          operator: "",
          station: "",
          device: ""
        }
      },
      total: 0,
      tableData: [],
      queryDateRange: '',
      Selectdata: [],
      alterTypeOptions: [{
        value: 0,
        label: '所有告警'
      }, {
        value: 101,
        label: '二次回路故障诊断'
      }, {
        value: 102,
        label: '状态监测预警'
      }, {
        value: 103,
        label: '同源数据比对'
      }, {
        value: 104,
        label: '定值和版本校核'
      }, {
        value: 105,
        label: '装置异常告警定位'
      }, {
        value: 106,
        label: '跳合闸返校诊断'
      }, {
        value: 107,
        label: '保护闭锁告警信息'
      },{
        value: 108,
        label: '二次回路故障消失'
      },{
        value: 109,
        label: '定值和版本校核消失'
      },{
        value: 110,
        label: '保护闭锁告警信息消失'
      }],
      filterAlertType: 0,
      chartDatas: [],
      chartTitle: ''
    };
  },
  computed: {},
  mounted() {
    let pieChart = echarts.init(this.$refs['chartPie']);
    //let lineChart = echarts.init(this.$refs['chartLine']);
    this.pieChart = pieChart;
    //this.lineChart = lineChart;

    let option;

    option = {
      title: {
        text: '所有告警统计',
        left: 'center',
        textStyle: {
          color:'#fff'
        },
      },
      tooltip: {
        trigger: 'item',
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        textStyle: {
          color:'#ddd'
        },
        top:30
      },
      series: [
        {
          name: '',
          type: 'pie',
          radius: '50%',
          data: [
            {value: 0, name: '二次回路故障诊断'},
            {value: 0, name: '状态监测预警'},
            {value: 0, name: '同源数据比对'},
            {value: 0, name: '定值和版本校核'},
            {value: 0, name: '装置异常告警定位'},
            {value: 0, name: '跳合闸返校诊断'},
            {value: 0, name: '保护闭锁告警信息'},
            {value: 0, name: '二次回路故障消失'},
            {value: 0, name: '定值和版本校核消失'},
            {value: 0, name: '保护闭锁告警信息消失'},
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

    this.pieChart.setOption(option);
  },
  methods: {
    SelectAlerts() {
      let queryPa = this.queryParams;

      if (this.queryDateRange) {
        queryPa.entity.startTime = this.queryDateRange[0];
        queryPa.entity.endTime = this.queryDateRange[1];
      }

      if (this.Selectdata != NaN && this.Selectdata != undefined) {
        switch (this.Selectdata.type) {
          case "all":
            queryPa.entity.station = "";
            queryPa.entity.device = "";
            queryPa.entity.operator = "";
            break;
          case "D":
            queryPa.entity.station = "";
            queryPa.entity.device = "";
            queryPa.entity.operator = this.Selectdata.id;
            break;
          case "S":
            queryPa.entity.operator = "";
            queryPa.entity.device = "";
            queryPa.entity.station = this.Selectdata.id;
            break;
          case "B":
            break;
          case 'I':
            queryPa.entity.operator = "";
            queryPa.entity.station = "";
            queryPa.entity.device = this.Selectdata.id;
            break;
          default:
            break;
        }
      }

      this.filterQuery();
    },
    alertTypeParser(row) {
      /*let typeNumber = (row.alertType > 8) ? 8 : row.alertType;*/
      let typeNumber = row.alertType;
      let alterType = this.alterTypeOptions.find((item) => item.value === typeNumber);
      return alterType.label;
    },
    sourceParser(row) {
      return row.source === 0 ? "主站诊断" : "文件解析";
    },
    requestAlert() {

      this.queryParams.entity.minType = 0;
      switch (this.queryParams.entity.alertType) {
        case "101":
          this.queryParams.entity.minType = 108;
          break;
        case "104":
          this.queryParams.entity.minType = 109;
          break;
        case "107":
          this.queryParams.entity.minType = 110;
          break;
        // case "108":
        //   this.queryParams.entity.minType = 0;
        //   break;
        // case "109":
        //   this.queryParams.entity.minType = 0;
        //   break;
        // case "110":
        //   this.queryParams.entity.minType = 0;
        //   break;
      }

      getIntelAlertData(this.queryParams).then(resp => {

        this.chartTitle = ""
        let queryPa = this.queryParams
        let c_start_time = queryPa.entity.startTime
        let c_end_time = queryPa.entity.endTime
        let c_operator = queryPa.entity.operator

        let c_time = ''
        if (c_start_time !== '' && c_end_time !== '') {
          c_time = c_start_time + '-' + c_end_time
          this.chartTitle += c_time
        }
        if (c_operator !== '') {
          this.chartTitle += c_operator
        }


        if (resp.data) {
          this.tableData = resp.data.records;
          this.total = resp.data.total;
        }
      })
    },
    requestAlertStat() {
      getIntelAlertStat(this.queryParams).then(resp => {
        if (resp.data) {
          this.updateEcharts(resp.data);
        }
      })
    },
    filterQuery() {
      this.requestAlertStat();
      this.requestAlert();
    },
    updateEcharts(statData) {
      let elist = [
        {
          type: 101,
          name: '二次回路故障诊断',
          value: 0
        }, {
          type: 102,
          name: '状态监测预警',
          value: 0
        }, {
          type: 103,
          name: '同源数据比对',
          value: 0
        }, {
          type: 104,
          name: '定值和版本校核',
          value: 0
        }, {
          type: 105,
          name: '装置异常告警定位',
          value: 0
        }, {
          type: 106,
          name: '跳合闸返校诊断',
          value: 0
        }, {
          type: 107,
          name: '保护闭锁告警信息',
          value: 0
        }, {
          type: 108,
          name: '二次故障回路消失',
          value: 0
        },{
          type: 109,
          name: '定值和版本校核消失',
          value: 0
        },{
          type: 110,
          name: '保护闭锁告警信息消失',
          value: 0
        }
      ];

      if (statData.length > 0) {
        for (let i = 0; i < statData.length; i++) {
          // if (statData[i].alertType < 101)
          //   elist.find(e=>e.name=="其他").value += statData[i].frequency;
          // else
          if (statData[i].alertType > 100)
            elist.find(e=>e.type==statData[i].alertType).value = statData[i].frequency;
        }
      }
        this.pieChart.setOption({ series: [ { data: elist } ] });
    },
  },
  created() {
    /*this.requestAlert();
    this.requestAlertStat();*/
  }
};
</script>

<style lang="scss" scoped>

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
