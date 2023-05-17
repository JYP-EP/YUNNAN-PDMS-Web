<template>
  <div>
    <el-button @click.native.prevent="showDetail(alertItem)" type="text">
      <!--      此处目的是让那一行出现查看或者无文件按钮，并且绑定点击查看文件事件-->
      {{statusParser(alertItem)}}
    </el-button>

    <el-dialog class="title" title="主变变中告警" :visible.sync="fileDialogVisible">
      <!--      <el-col style="font-weight: bolder;font-size: 16px">{{ this.deviceInfo.name}}</el-col>-->
      <!--头部信息-->
      <div align="left">
        <el-row style="width: 100%;font-weight: bold;font-size: 15px">
          <el-col :span="24">
            <div class="grid-content bg-purple mainName">名称： {{ this.deviceInfo.desc}}</div>
            <div class="grid-content bg-purple conclude">结论： {{ this.mainResult}}</div>
          </el-col>
        </el-row>
        <br>
      </div>

      <div v-for="(stationItem) in this.station">
        <el-row class="stationName">变电站名称：{{stationItem.desc}}</el-row>
        <el-container v-for="(ied) in stationItem.ied">
          <el-aside class="iedSide" width="240px"><span class="iedName">主变名称：{{ied.name}}</span> <br> <span
            class="iedDesc">{{ied.desc}}</span></el-aside>
          <el-main style="padding-top: 0px">

            <el-table :data="ied.phs"
                      :span-method="objectSpanMethod"
                      style="font-size: 15px"
                      border>


              <el-table-column
                label="描述"
                width="170">
                <template slot-scope="scope">
                  <span>{{scope.row.desc}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="类型"
                width="150">
                <template slot-scope="scope">
                  <span>{{scope.row.subDesc}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="值"
                width="300">
                <template slot-scope="scope">
                  <span>{{scope.row.value+scope.row.unit}}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-main>
        </el-container>
      </div>


    </el-dialog>


  </div>
</template>

<script>
  import {getTransformerCurrentAlertDetail} from "@/api/gridnt/intelAlert";

  export default {
    name: "transCurrentXml",
    props: ['alertItem'],
    computed: {},
    data() {
      return {
        spanArr: [],
        pos: 0,
        fileDialogVisible: false,
        mainResult: "",
        deviceInfo: {},
        station: [],
        phs: "",
        phsCount: 0
      };
    },
    methods: {
      statusParser(row) {
        return row.status === 1 ? "查看" : "无文件";
      },
      showDetail(row) {//此处是点击查看按钮，显示解析后的文件
        // debugger
        if (row.status != 1) {
          return;
        }
        getTransformerCurrentAlertDetail({fileId: row.fileId, type: row.alertType}).then(resp => {
          this.mainResult = resp.data.result == true ? '可进行线路强送点' : '建议线路强送电'
          this.deviceInfo = resp.data.deviceInfo
          this.station = resp.data.station
          this.fileDialogVisible = true;

          this.getSpanArr(this.station)
        })

      },
      //由于数据是动态的，所以页面加载时需要调用下面的方法，根据后台数据处理以知道要合并的行/列
//得到的spanArr数组表示某一行所需要合并的列数
      getSpanArr: function (data) {
        this.spanArr = [];//定义在vue的data中
        if (data == null) {
          return;
        }
        for (var i = 0; i < data.length; i++) {
          this.spanArr.push(data[i].ied.ph)

          if (i === 0) {
            this.spanArr.push(1);
            this.pos = 0;
          } else {
            if (data[i].ied.phs.subDesc === data[i - 1].ied.phs.subDesc) {
              this.spanArr[this.pos] += 1;
              this.spanArr.push(0);
            } else {
              this.spanArr.push(1);
              this.pos = i;
            }
          }
        }
      },
      objectSpanMethod({row, column, rowIndex, columnIndex}) {


        if (columnIndex === 0) {
          if (rowIndex % 3 === 0) {
            // console.log(JSON.stringify(row) + ":" + rowIndex + "--" + columnIndex)
            return {
              rowspan: 3,
              colspan: 1
            };
          } else {
            return {
              rowspan: 0,
              colspan: 0
            };
          }
        }
      }
    },

  }
</script>
<style scoped>
  .mainName {
    font-size: 18px;
    color: black;
  }

  .conclude {
    font-size: 17px;
  }

  .stationName {
    font-size: 16px;
    color: black;
  }

  .iedName {
    font-size: 15px;
    color: black;
  }

  .iedDesc {
    font-size: 15px;
  }

  .title {
    font-size: large;
    font-weight: bolder;
    color: black;
  }

  .iedSide {
    vertical-align: middle;
  }

</style>
