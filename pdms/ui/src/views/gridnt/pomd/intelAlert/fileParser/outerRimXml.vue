<template>
  <div>
    <el-button @click.native.prevent="showDetail(alertItem)" type="text">
      <!--      此处目的是让那一行出现查看或者无文件按钮，并且绑定点击查看文件事件-->
      {{statusParser(alertItem)}}
    </el-button>

    <!--对话框-->
    <!--    在关闭对话框时需要清除该对话框内所有数据,否则会出现数据差错-->
    <el-dialog title="区外故障分析"
               :visible.sync="fileDialogVisible"
               @close="closeDia"
               style="font-weight: bolder;font-size: 25px">
      <el-col style="font-weight: bolder;font-size: 18px">{{ this.deviceInformation.iedDesc}}</el-col>
      <br><br>
      <!--头部信息-->
      <div align="left">
        <el-row class="e-header">
          <el-col :span="8">
            <div class="grid-content bg-purple">变电站名称: {{ this.deviceInformation.stationDesc}}</div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple">电压等级: {{ this.deviceInformation.volDesc}}</div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple">线路名称: {{ this.deviceInformation.equipmentName}}</div>
          </el-col>
        </el-row>
        <el-row class="e-header">
          <el-col :span="8">
            <div class="grid-content bg-purple">装置名称: {{ this.deviceInformation.iedName}}</div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple">录波日期: {{ this.deviceInformation.recordingDate}}</div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple">启动时间: {{ this.deviceInformation.faultStartTime}}</div>
          </el-col>
        </el-row>
        <br>
      </div>

      <el-container class="e-container">
        <!--左侧菜单-->
        <el-aside style="width: 18%;background-color: rgb(238, 241, 246)">
          <el-menu>
            <el-menu-item-group title="类型">
              <el-menu-item class="e-menu" @click.native="getChannel">电压/电流</el-menu-item>
              <el-menu-item class="e-menu" @click="getDigitalInfo">压板/开入</el-menu-item>
              <el-menu-item class="e-menu" @click="getSettingInfo">定值</el-menu-item>
              <el-menu-item class="e-menu" @click="getResultInfo">其它</el-menu-item>
            </el-menu-item-group>
          </el-menu>
        </el-aside>
        <!--右侧表格-->
        <el-container style="padding: 0">
          <el-main style="padding: 0">
            <!--channel表格-->
            <template v-if="this.isVisible==='channel'">
              <el-col :span="12">
                <el-row class="e-channelTitle">录波启动前一周波</el-row>
                <el-table border class="e-channelTable"
                  :data="channelData_bef"
                  :span-method="objectSpanMethod">
                  <el-table-column prop="desc" label="类型" width="70"></el-table-column>
                  <el-table-column prop="subDesc" label="描述" width="60"></el-table-column>
                  <el-table-column label="值">
                    <template slot-scope="scope">
                      <span style="font-size: 13px">{{scope.row.value+scope.row.unit}}</span>
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>
              <el-col :span="12">
                <el-row class="e-channelTitle">录波启动后0.5-1.5周波</el-row>
                <el-table border :data="channelData_aft" class="e-channelTable" :span-method="objectSpanMethod">
                  <el-table-column prop="desc" label="类型" width="70"></el-table-column>
                  <el-table-column prop="subDesc" label="描述" width="60"></el-table-column>
                  <el-table-column label="值">
                    <template slot-scope="scope">
                      <span style="font-size: 13px">{{scope.row.value+scope.row.unit}}</span>
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>
            </template>

            <!--digital表格-->
            <el-table border v-if="this.isVisible==='digital'" class="e-channelTable" :data="tableData">
              <el-table-column type="index" width="75"></el-table-column>
              <el-table-column prop="name" label="描述" width="180"></el-table-column>
              <el-table-column prop="value" label="值" width="220"></el-table-column>
              <el-table-column prop="validate" label="是否正确" width="75" :formatter="isValidate"></el-table-column>
            </el-table>
            <!--setting表格-->
            <el-table border v-if="this.isVisible==='setting'" class="e-channelTable" :data="tableData">
              <el-table-column type="index" width="75"></el-table-column>
              <el-table-column prop="name" label="描述" width="180"></el-table-column>
              <el-table-column prop="value" label="值" width="220"></el-table-column>
              <el-table-column prop="unit" label="单位" width="100"></el-table-column>
              <el-table-column prop="validate" label="是否正确" width="75"></el-table-column>
            </el-table>
            <el-table border v-if="this.isVisible==='result'" class="e-channelTable" :data="tableData">
              <el-table-column type="index" width="75"></el-table-column>
              <el-table-column prop="item" label="--" width="300"></el-table-column>
              <el-table-column prop="detail" label="--" width="300"></el-table-column>
            </el-table>
          </el-main>
        </el-container>
      </el-container>
    </el-dialog>
  </div>
</template>

<script>
  import {getOutRimAlertDetail} from "@/api/gridnt/intelAlert";

  export default {
    name: "outerRimXml",
    props: ['alertItem'],
    data() {//模态框

      return {
        spanArr: [],
        pos: 0,
        fileDialogVisible: false,
        fileDetail: [],
        // 此处都是解析的xml文档中的数据，为方便使用而单独获取
        deviceInformation: {}, channelValue: {}, digitalInfo: {}, settingInfo: {}, resultInfo:{},
        tableData: [], channelData_bef: [], channelData_aft: [],

        // 控制展示哪个表格
        isVisible: 'channel'
      };
    },
    methods: {
      sortData(a, b) {
        if(a.type === b.type) {
          if(a.subType > b.subType) {
            return 1;
          } else if(a.subType < b.subType) {
            return -1;
          } else {
            return 0;
          }
        } else {
          if(a.type > b.type) {
            return 1;
          } else {
            return -1;
          }
        }
      },
      isValidate(row){
        if(row.validate === 'true') {
          return '是';
        } else {
          return '否';
        }
      },
      closeDia() {
        // 清除所有数据,防止渲染出错
        Object.assign(this.$data, this.$options.data())
      },
      getSpanArr: function (data) {
        for (let i = 0; i < data.length; i++) {
          if (i === 0) {
            this.spanArr.push(1);
            this.pos = 0
          } else {
            // 判断当前元素与上一个元素是否相同
            if (data[i].desc === data[i - 1].desc) {
              this.spanArr[this.pos] += 1;
              this.spanArr.push(0);
            } else {
              this.spanArr.push(1);
              this.pos = i;
            }
          }
          // console.log(this.spanArr)
        }
      },
      objectSpanMethod({row, column, rowIndex, columnIndex}) {
        if (columnIndex === 0) {
          const _row = this.spanArr[rowIndex];
          const _col = _row > 0 ? 1 : 0;
          // console.log(`rowspan:${_row} colspan:${_col}`)
          return { // [0,0] 表示这一行不显示， [2,1]表示行的合并数
            rowspan: _row,
            colspan: _col
          }
        }
      },
      statusParser(row) {
        return row.status === 1 ? "查看" : "无文件";
      },
      showDetail(row) {//此处是点击查看按钮，显示解析后的文件
        if (row.status !== 1) {
          return;
        }
        getOutRimAlertDetail({fileId: row.fileId, type: row.alertType}).then(resp => {
          this.fileDetail = resp.data;
          this.deviceInformation = this.fileDetail.deviceInformation;
          this.channelValue = this.fileDetail.channelValue;
          this.digitalInfo = this.fileDetail.digitalInfo;
          this.settingInfo = this.fileDetail.settingInfo;
          this.resultInfo = this.fileDetail.resultInfo;

          this.fileDialogVisible = true;
          this.getChannel();

          // 排序
          this.channelData_aft.sort(this.sortData);
          this.channelData_bef.sort(this.sortData);

          this.getSpanArr(this.channelData_aft);
        })
      },
      getChannel() {
        this.isVisible = "channel"
        for (let i = 0; i < this.channelValue.length; i++) {
          if (this.channelValue[i].cycle < 0) {
            this.channelData_bef = this.channelValue[i].phs
          } else {
            this.channelData_aft = this.channelValue[i].phs
          }
        }
      },
      getDigitalInfo() {
        this.isVisible = "digital"
        this.tableData = []
        this.tableData = this.digitalInfo.digitalStatus
      },
      getSettingInfo() {
        this.isVisible = "setting"
        this.tableData = []
        this.tableData = this.settingInfo.settingValue
      },
      getResultInfo(){
        this.isVisible = "result"
        this.tableData = [];
        console.log(this.resultInfo);
        for(let i = 0; i < this.resultInfo.resultItem.length; i++){
          this.tableData.push({
            item: this.resultInfo.resultItem[i].desc,
            detail: this.resultInfo.resultItem[i].value
          })
        }
        this.tableData.push({
          item: "结论",
          detail: this.resultInfo.result.value
        })
      }
    },
  }
</script>
<style scoped>
  .e-header {
    width: 100%;
    font-weight: bold;
    font-size: 17px
  }

  .e-container {
    border: 1px solid #eee
  }

  .e-menu {
    font-size: 17px
  }

  .e-channelTitle {
    font-size: 17px
  }

  .e-channelTable {
    width: 100%;
    font-size: 17px;
  }
</style>
