<template>
  <!--  保护异常闭锁告警-->
  <div style="display: flex; height: 100%; position: relative">
    <el-row :gutter="24" style="width: 100%">
      <el-col :span="17">
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
          <el-select v-model="queryParams.entity.device" clearable placeholder="装置">
            <el-option
              v-for="item in deviceOptions"
              :key="item.id"
              :label="item.label"
              :value="item.id">
            </el-option>
          </el-select>
          <el-select
            v-model="queryParams.entity.alertLevel"
            style="width: 110px"
            placeholder="闭锁类型">
            <el-option
              v-for="item in alertLevelOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-button @click="filterQuery">筛选</el-button>
        </el-row>
        <el-table max-height="700" :data="tableData" style="width: 100%">
          <el-table-column type="index"></el-table-column>
          <el-table-column align="center" prop="triggerTime" label="告警时间"></el-table-column>
          <el-table-column align="center" prop="operator" label="运维单位"></el-table-column>
          <el-table-column align="center" prop="substationName" label="厂站"></el-table-column>
          <el-table-column align="center" prop="iedName" label="装置"></el-table-column>
          <el-table-column align="center" prop="detail" label="告警点"></el-table-column>
          <el-table-column align="center" prop="alertLevel" label="闭锁类型"
                           :formatter="alertLevelParser"></el-table-column>
        </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.page.current"
          :limit.sync="queryParams.page.size"
          @pagination="requestAlert"
        />
      </el-col>
      <el-col :span="7">
        <div style="text-align: center; font-weight: bolder;  alignment: center">
          保护异常闭锁告警统计
          <div ref="chartPie" style="width: 550px; height: 400px;">Pie Here</div>
        </div>
        <div ref="chartLine" style="width: 500px; height: 350px; alignment: center">Line Here</div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import Pagination from '@/components/Pagination';
  import {getBlockingAlert, getBlockingStat} from "@/api/gridnt/intelAlert";
  import * as echarts from "echarts";

  export default {
    name: 'blockingAlert',
    props: ['datePickerOptions', 'divisionOptions'],
    components: {Pagination},
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
            alertLevel: 0,
            operator: "",
            station: "",
            device: ""
          }
        },
        total: 0,
        tableData: [],
        queryDateRange: '',
        alertLevelOptions: [{
          value: 0,
          label: "全部"
        }, {
          value: 1,
          label: "I类闭锁"
        }, {
          value: 2,
          label: "Ⅱ类闭锁"
        }, {
          value: 3,
          label: "Ⅲ类闭锁"
        }]
      };
    },
    computed: {
      operatorOptions() {
        return this.divisionOptions;
      },
      stationOptions() {
        let result = [];
        for (let index in this.divisionOptions) {
          if (this.divisionOptions[index].id === this.queryParams.entity.operator) {
            return this.divisionOptions[index].children;
          } else {
            result.push.apply(result, this.divisionOptions[index].children);
          }
        }
        return result;
      },
      deviceOptions() {
        let result = [];
        for (let index in this.stationOptions) {
          if (this.stationOptions[index].id === this.queryParams.entity.station) {
            return this.stationOptions[index].children;
          } else {
            result.push.apply(result, this.stationOptions[index].children);
          }
        }
        return result;
      }
    },
    mounted() {
      let pieChart = echarts.init(this.$refs['chartPie']);
      let lineChart = echarts.init(this.$refs['chartLine']);
      this.pieChart = pieChart;
      this.lineChart = lineChart;
      this.pieChart.on('updateAxisPointer', function (event) {
        const xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
          const dimension = xAxisInfo.value + 1;
          pieChart.setOption({
            series: {
              id: 'pie',
              label: {
                formatter: '{@[0]}: {@[' + dimension + ']} ({d}%)'
              },
              encode: {
                value: dimension,
                tooltip: dimension
              }
            }
          });
        }
      });
      this.initEcharts();
    },
    methods: {
      alertLevelParser(row) {
        if (row.alertLevel == 0) return "未知";
        let alertLevel = this.alertLevelOptions.find((item) => item.value === row.alertLevel);
        return alertLevel ? alertLevel.label : "未知";
      },
      requestAlert() {
        getBlockingAlert(this.queryParams).then(resp => {
          if (resp.data) {
            this.tableData = resp.data.records;
            this.total = resp.data.total;
          }
        })
      },
      requestAlertStat() {
        getBlockingStat(this.queryParams).then(resp => {
          if (resp.data) {
            this.updateEcharts(resp.data);
          }
        });
      },
      filterQuery() {
        // debugger
        if (this.queryDateRange) {
          this.queryParams.entity.startTime = this.queryDateRange[0];
          this.queryParams.entity.endTime = this.queryDateRange[1];
        }
        this.requestAlertStat();
        this.requestAlert();
      },
      updateEcharts(statData) {
        let dateList = ['data'];
        let dateSet = new Set();
        let levelSet = new Set();
        statData.forEach(item => {
          dateSet.add(item.triggerDate);
          levelSet.add(item.level);
        });
        let levelMap = new Map();
        levelSet.forEach(item => {
          levelMap.set(item, [item]);
        });
        dateSet.forEach(item => {
          dateList.push(item);
          levelMap.forEach((value, key) => {
            value.push(0);
          });
        });



        statData.forEach(item => {
          console.log(JSON.stringify(item))
          let index = dateList.indexOf(item.triggerDate);
          levelMap.get(item.level)[index] = item.frequency;
        });

        let dataSource = [dateList];
        levelMap.forEach((value, key) => {
          dataSource.push(value);
        });
        for (let i = 0; i < dataSource.length; i++) {
          if (dataSource[i][0] == 1) {
            dataSource[i][0] = "I类闭锁"
          }
          if (dataSource[i][0] == 2) {
            dataSource[i][0] = "Ⅱ类闭锁"
          }
          if (dataSource[i][0] == 3) {
            dataSource[i][0] = "Ⅲ类闭锁"
          }
        }
        var tem_data = []
        for (let i = 1; i < dataSource.length; i++) {
          tem_data.push(dataSource[i])
        }

        let pieData = []
        for (let i = 0; i < tem_data.length; i++) {
          var tem = tem_data[i]
          var item = {}
          var sum = 0
          item.name = tem[0]
          for (let j = 1; j < tem.length; j++) {
            sum += tem[j]
          }
          item.value = sum
          pieData.push(item)
        }
        this.pieChart.setOption({
          legend: {
            bottom: 50,
            data: (function () {
              var res = []
              for (let i = 0; i < pieData.length; i++) {
                var alert_level = pieData[i]
                res.push(alert_level.name)
              }

              return res;
            })(), //图例的数据数组
          },
          series: [
            {
              name: '闭锁类型',
              type: 'pie',    // 设置图表类型为饼图
              data: pieData
            }
          ]
        });
        this.lineChart.setOption({
          legend: {
            data: (function () {
              var res = []
              for (let i = 1; i < dataSource.length; i++) {
                var alert_type_data = dataSource[i]
                res.push(alert_type_data[0])
              }
              return res;
            })(), //图例的数据数组
            //展示单项
          },
          xAxis: {
            data: (function () {
              // 这个数组的第一个元素是所有的时间
              var datas = dataSource[0]
              var res = []
              // datas[0]的数据是字符串，不是数据
              for (let i = 1; i < datas.length; i++) {
                res.push(datas[i])
              }
              return res;
            })()
          },
          series: function () {
            var serie = [];
            for (var i = 1; i < dataSource.length; i++) {
              var dataArr = dataSource[i]
              var item = {
                // itemStyle: {normal: {label: {show: true}}},
                name: dataArr[0],
                type: 'line',
                data: (function () {
                  // 这个数组的第一个元素是所有的时间
                  var res = []
                  // datas[0]的数据是字符串，不是数据
                  for (let i = 1; i < dataArr.length; i++) {
                    res.push(dataArr[i])
                  }
                  return res;
                })()
              }
              serie.push(item);
            }
            return serie;
          }()
        })
      },
      initEcharts() {
        this.pieChart.setOption({
          tooltip: {
            trigger: 'axis',
            showContent: false
          },
          dataset: {
            source: [
              ['date', '0'],
              ['I类闭锁', 0],
              ['Ⅱ类闭锁', 0],
              ['Ⅲ类闭锁', 0]
            ]
          },
          grid: {top: '55%'},
          series: [
            {
              type: 'pie',
              id: 'pie',
              radius: '50%',
              center: ['50%', '30%'],
              emphasis: {
                focus: 'self'
              },
              label: {
                formatter: '{b}: ({d}%)'
              }
            }
          ]
        });
        this.lineChart.setOption({
          xAxis: {
            data: []
          },
          yAxis: {},
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
