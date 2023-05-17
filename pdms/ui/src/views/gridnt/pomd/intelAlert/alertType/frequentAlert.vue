<template>
  <!--  频繁告警-->
  <div style="height: 100%">
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
          <el-button @click="filterQuery">筛选</el-button>
        </el-row>
        <el-table max-height="700" :data="tableData" style="width: 100%">
          <el-table-column type="index"></el-table-column>
          <el-table-column align="center" prop="triggerTime" label="告警时间"></el-table-column>
          <el-table-column align="center" prop="operator" label="运维单位"></el-table-column>
          <el-table-column align="center" prop="substationName" label="厂站"></el-table-column>
          <el-table-column align="center" prop="iedName" label="装置"></el-table-column>
          <el-table-column align="center" prop="detail" label="告警信息"></el-table-column>
          <el-table-column align="center" prop="frequency" label="次数"></el-table-column>
          <el-table-column align="center" prop="status" label="操作">
            <template slot-scope="scope">
              <frequent-xml :alert-item="scope.row"></frequent-xml>
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
      <el-col :span="7">
        <div
          style="text-align: center; font-weight: bolder;  alignment: center">
          频繁告警统计
          <div ref="chartPie" style="width: 550px; height: 400px; margin-top: 10px">Pie Here</div>
        </div>
        <div ref="chartLine" style="width: 500px; height: 350px; alignment: center">Line Here</div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import Pagination from '@/components/Pagination';
  import {getFrequentAlert, getFrequentStat} from '@/api/gridnt/intelAlert';
  import frequentXml from "@/views/gridnt/pomd/intelAlert/fileParser/frequentXml";
  import * as echarts from "echarts";

  export default {
    name: 'frequentAlert',
    props: ['datePickerOptions', 'divisionOptions'],
    components: {Pagination, frequentXml},
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
            operator: "",
            station: "",
            device: ""
          }
        },
        total: 0,
        tableData: [],
        queryDateRange: '',
        isShowChart: false
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
      requestAlert() {
        getFrequentAlert(this.queryParams).then(resp => {
          if (resp.data) {
            this.tableData = resp.data.records;
            this.total = resp.data.total;
          }
        })
      },
      requestAlertStat() {
        getFrequentStat(this.queryParams).then(resp => {
          if (resp.data) {
            this.updateEcharts(resp.data);
          }
        })
      },
      filterQuery() {
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
        let operatorSet = new Set();
        statData.forEach(item => {
          dateSet.add(item.triggerDate);
          operatorSet.add(item.operator);
        });
        let operatorMap = new Map();
        operatorSet.forEach(item => {
          operatorMap.set(item, [item]);
        });
        dateSet.forEach(item => {
          dateList.push(item);
          operatorMap.forEach((value, key) => {
            value.push(0);
          });
        });


        statData.forEach(item => {
          let index = dateList.indexOf(item.triggerDate);
          operatorMap.get(item.operator)[index] = item.frequency;
        });

        let dataSource = [dateList];
        operatorMap.forEach((value, key) => {
          dataSource.push(value);
        });

        let other = ["其他", 0]
        let newChartData = []
        let allQuis = 0
        for (let i = 1; i < dataSource.length; i++) {
          let areaStations = dataSource[i]
          allQuis += areaStations[1]
        }
        newChartData.push(dataSource[0])
        for (let i = 1; i < dataSource.length; i++) {
          let areaStations = dataSource[i]
          if (areaStations[1] * 1.0 / allQuis < 0.1) {
            other[1] += areaStations[1]
          } else {
            newChartData.push(areaStations)
          }
        }
        newChartData.push(other)
        if (newChartData.length < 3) {
          this.isShowChart = false
        } else {
          this.isShowChart = true
        }
        this.pieChart.setOption({
          legend: {
            bottom: 50,
            data: (function () {
              var res = []
              for (let i = 1; i < newChartData.length ; i++) {
                var alert_type_data = newChartData[i]
                res.push(alert_type_data[0])
              }
              return res;
            })(), //图例的数据数组
          },
          dataset: {
            source:
            newChartData
          },
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
        let sourceData = [['date', '0']];
        let seriesData = [];
        this.divisionOptions.forEach(item => {
          sourceData.push([item.id, 0]);
        });
        seriesData.push({
          type: 'pie',
          id: 'pie',
          radius: '50%',
          center: ['50%', '40%'],
          emphasis: {
            focus: 'self'
          },
          label: {
            formatter: '{b}: ({d}%)'
          }
        });
        this.pieChart.setOption({
          tooltip: {
            trigger: 'axis',
            showContent: false
          },
          dataset: {
            source: sourceData
          },
          series: seriesData
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
