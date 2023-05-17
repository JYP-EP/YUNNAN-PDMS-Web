<template>
  <el-container class="el-container">
<!--    <el-aside width="380px" style="background-color: #fff; padding: 0px; margin-right: 5px;-->
<!--    height: calc( 100vh - 200px);">-->
<!--      <substation-ied-tree @treeSelected="showSubComm" ref="substationTree"　showAll  />-->
<!--    </el-aside>-->
    <el-main style="margin-top: 0px;padding: 0px;overflow: hidden">
      <el-row :gutter="20">
        <el-col :span="13">
          <el-select transfer="true" :popper-append-to-body="false" v-model="dateType" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.dateType"
              :label="item.label"
              :value="item.dateType">
            </el-option>
          </el-select>
          <template v-if="this.dateType === 'week'">
            <span class="demonstration" style="margin-left: 10px">周</span>
            <el-date-picker
              v-model="date"
              type="week"
              format="yyyy 第 WW 周"
              value-format="yyyy-MM-dd"
              placeholder="选择日期">
            </el-date-picker>
          </template>
          <template v-if="this.dateType === 'month'">
            <span class="demonstration" style="margin-left: 10px">月</span>
            <el-date-picker
              v-model="date"
              type="month"
              value-format="yyyy-MM-dd"
              placeholder="选择日期">
            </el-date-picker>
          </template>
          <template v-if="this.dateType === 'year'">
            <span class="demonstration" style="margin-left: 10px">年</span>
            <el-date-picker
              v-model="date"
              type="year"
              value-format="yyyy-MM-dd"
              placeholder="选择日期">
            </el-date-picker>
          </template>
          <el-button type="info" round @click="getData()">查询</el-button>
          <el-table max-height="800" :data="this.historyTableData">
            <el-table-column align="center" type="index" width="50"></el-table-column>
            <el-table-column
              align="center"
              class="tab-col"
              label="变电站"
              min-width="20%">
              <template slot-scope="scope">
                <span>{{ scope.row.subName }}</span>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              class="tab-col"
              label="对象名称" min-width="20%">
              <template slot-scope="scope">
                <span>{{ scope.row.objectName }}</span>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              class="tab-col"
              label="时间" min-width="20%">
              <template slot-scope="scope">
                <span>{{ scope.row.time }}</span>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              class="tab-col"
              label="通信事项描述" min-width="30%">
              <template slot-scope="scope">
                <span>{{ scope.row.text }}</span>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              class="tab-col"
              label="通信状态" min-width="10%">
              <template slot-scope="scope">
                <span>{{ scope.row.state }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
        <el-col :span="10">
          <div style="margin-left: 10px">
            <div style="text-align: center;width: 100%">
              <span
                style="font-weight:bolder;horiz-align: center;padding: 0;margin-bottom: 5px">{{ comm_table_name }}:子站与保护通信率统计</span>
            </div>
            <el-table max-height="450" height="420" stripe :data="this.commStatTableData">
              <el-table-column
                align="center"
                class="tab-col"
                label="设备描述" min-width="30%">
                <template slot-scope="scope">
                  <span>{{ scope.row.objectName }}</span>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                class="tab-col"
                label="通信时长" min-width="20%">
                <template slot-scope="scope">
                  <span>{{ scope.row.connTime }}</span>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                class="tab-col"
                label="中断时长" min-width="20%">
                <template slot-scope="scope">
                  <span>{{ scope.row.disConnTime }}</span>
                </template>
              </el-table-column>

              <el-table-column
                align="center"
                class="tab-col"
                label="通信率" min-width="10%">
                <template slot-scope="scope">
                        <span>
                          {{ scope.row.connRate }}%</span>
                  <!--              <span>{{scope.row.disConnectionTime * 1.0 / scope.row.connectionTime}}</span>-->
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-row style="max-height: 340px;">
            <div id="higcharts">
            </div>
          </el-row>
          <div style="text-align: center;width: 100%">
            <span
              style=" font-weight:bolder;horiz-align: center;padding: 0">{{ comm_table_name }}:子站与保护通信率统计(Top 10)</span>
          </div>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { getCommStatus, getDeviceTree } from '@/api/gridnt/intelAlert';
import SubstationIedTree from '@/views/gridnt/pomd/common/substationIedTree';

import * as echarts from 'echarts';

export default {
  name: 'commStatus',
  components:{SubstationIedTree},
  data() {
    return {
      deviceTree: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      options: [{
        dateType: 'year',
        label: '按年查询'
      }, {
        dateType: 'month',
        label: '按月查询'
      }, {
        dateType: 'week',
        label: '按周查询'
      }],
      dateType: 'week',
      date: '',
      areas: [],
      comm_stat: [],
      his_event: [],
      historyTableData: [],
      commStatTableData: [],
      screenHeight: document.body.clientHeight,
      histype: 'all',
      hiscode: '',
      charts: null,
      chartData: [],
      comm_table_name: '全网'
    };
  },
  methods: {

    initData() {
      getCommStatus({
        dateType: this.dateType,
        date: this.date,
        histype: 'all',
        hiscode: 'all'
      }).then(resp => {
        console.log(resp.data)

        this.historyTableData = [];
        this.commStatTableData = [];
        this.areas = resp.data.areas;
        this.his_event = resp.data.hisEvent;
        this.comm_stat = resp.data.comm;

        this.historyTableData = this.his_event;
        this.commStatTableData = this.comm_stat;

        this.setChartData(this.commStatTableData);
        this.initCharts();
      });
    },
    getData() {
      getCommStatus({
        dateType: this.dateType,
        date: this.date,
        histype: this.histype,
        hiscode: this.hiscode
      }).then(resp => {
        this.historyTableData = [];
        this.commStatTableData = [];
        this.his_event = resp.data.hisEvent;
        this.comm_stat = resp.data.comm;
        this.historyTableData = this.his_event;
        this.commStatTableData = this.comm_stat;

        this.setChartData(this.commStatTableData);
        this.initCharts();
      });
    },
    showSubComm(node) {
      if(node.type == 'S' || node.type == 'B' || node.type == 'I' || node.type == 'D' || node.type == 'all' ){
        switch (node.type) {
          //全网
          case 'all':
            this.histype = 'all';
            break;
          //区域
          case 'D':
            this.histype = 'area';
            break;
          //变电站
          case 'S':
            this.histype = 'sub';
            break;
          //间隔
          case 'B':
            this.histype = 'bay';
            break;
          //设备
          case 'I':
            this.histype = 'ied';
            break;
          default:;
        }
        this.comm_table_name = node.label;
        this.hiscode = node.id;
        this.getData();
      }

    },
    getFormatDate() {
      const nowDate = new Date();
      const year = nowDate.getFullYear();
      const month = nowDate.getMonth() + 1 < 10 ? '0' + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
      const day = nowDate.getDate() < 10 ? '0' + nowDate.getDate() : nowDate.getDate();
      return year + '-' + month + '-' + day;
    },
    initCharts() {
      this.charts.setOption({
        grid: { left: '10%', bottom: '35%' },
        xAxis: [{
          type: 'category',
          data: this.chartData.xData,
          axisLabel: {
            interval: 0,
            formatter: function(value) {
              var ret = '';//拼接加\n返回的类目项
              var maxLength = 4;//每项显示文字个数
              var valLength = value.length;//X轴类目项的文字个数
              var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数
              if (rowN > 1)//如果类目项的文字大于5,
              {
                for (var i = 0; i < rowN; i++) {
                  var temp = '';//每次截取的字符串
                  var start = i * maxLength;//开始截取的位置
                  var end = start + maxLength;//结束截取的位置
                  //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧
                  temp = value.substring(start, end) + '\n';
                  ret += temp; //凭借最终的字符串
                }
                return ret;
              } else {
                return value;
              }
            }
          }

        }],
        yAxis: {
          type: 'value'
        },
        series: [
          {
            type: 'bar',
            data: this.chartData.yData,
            barWidth: 30,
            itemStyle: {
              normal: {
                label: {
                  show: true,
                  position: 'top'
                }
              }
            }
          }

        ]
      });
    },
    setChartData(comm) {
      comm.sort(this.sortBy('connRate'));
      var xData = [];
      var yData = [];

      comm.forEach(item => {
        if (xData.length < 10) {
          xData.push(item.objectName);
          yData.push(item.connRate);
        }

      });
      this.chartData.xData = xData;
      this.chartData.yData = yData;
    },
    sortBy(property) {
      return function(value1, value2) {
        let a = value1[property];
        let b = value2[property];

        return a < b ? 1 : a > b ? -1 : 0;
      };
    }
  }
  ,
  mounted() {
    this.date = this.getFormatDate();
    this.initData();


    this.screenHeight = document.body.clientHeight;
    window.onresize = () => {
      return (() => {
        this.screenHeight = document.body.clientHeight;
      })();
    };


    this.charts = echarts.init(document.getElementById('higcharts'));
    this.initCharts();
  }
};
</script>
<style scoped>


.menu-sub {
  font-size: 17px;
}

.menu-item {
  font-size: 15px;
}

#higcharts {
  margin: 0 auto;
  width: 700px;
  height: 400px;
}

/deep/ .el-container {
  margin-top: 0px;
  padding: 0px;
  width: 100%;
  overflow: hidden;
  height: calc(80vh);
}

.el-aside-container{
  padding: 0px;
  height: calc( 100vh - 200px);
}


</style>
