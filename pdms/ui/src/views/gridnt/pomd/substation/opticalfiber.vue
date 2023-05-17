<template>
  <el-table :data="tableData" :span-method="objectSpanMethod"  border height="calc(100vh - 160px)">
    <el-table-column type="index" ></el-table-column>
    <el-table-column label="装置">
      <template slot-scope="scope">
        {{scope.row.TYPE == '发送' ? scope.row['F1046_NAME_A']+':'+scope.row['F1046_DESC_A']
        :scope.row['F1046_NAME_B']+':'+scope.row['F1046_DESC_B'] }}
      </template>
    </el-table-column>
    <el-table-column label="类型"　prop="TYPE" width="50">
    </el-table-column>
    <el-table-column label="光缆编号"　prop="F1051_NAME"　width="150">
    </el-table-column>
    <el-table-column label="回路状态"　width="100">
      <template slot-scope="scope">
          <span :class=" 'status-circle status-color'+statusData[scope.row['F1053_STATUSCODE']]" v-show="statusData[scope.row['F1053_STATUSCODE']] == '0' || statusData[scope.row['F1053_STATUSCODE']] == '1'"></span>
        {{statusData[scope.row['F1053_STATUSCODE']] == '0'? '正常':
        (statusData[scope.row['F1053_STATUSCODE']] == '1' ? '中断': statusData[scope.row['F1053_STATUSCODE']])}}
      </template>
    </el-table-column>
    <el-table-column label="板卡"　width="100">
      <template slot-scope="scope">
        {{scope.row.TYPE == '发送' ? scope.row['F1047_DESC_A']:scope.row['F1047_DESC_B'] }}
      </template>
    </el-table-column>
    <el-table-column label="端口"　width="100">
      <template slot-scope="scope">
        {{scope.row.TYPE == '发送' ? scope.row['F1048_DESC_A']:scope.row['F1048_DESC_B'] }}
      </template>
    </el-table-column>
    <el-table-column label="光强"　width="100">
      <template slot-scope="scope">
        {{scope.row.TYPE == '发送' ? statusData[scope.row['F1006_CODE_A']]:statusData[scope.row['F1006_CODE_B']]}}
      </template>
    </el-table-column>
    <el-table-column label="对端装置">
      <template slot-scope="scope">
        {{scope.row.TYPE == '发送' ? scope.row['F1046_NAME_B']+':'+scope.row['F1046_DESC_B']
        :scope.row['F1046_NAME_A']+':'+scope.row['F1046_DESC_A'] }}
      </template>
    </el-table-column>
    <el-table-column label="对端板卡"　width="100">
      <template slot-scope="scope">
        {{scope.row.TYPE == '发送' ? scope.row['F1047_DESC_B']:scope.row['F1047_DESC_A'] }}
      </template>
    </el-table-column>
    <el-table-column label="对端端口"　width="100">
      <template slot-scope="scope">
        {{scope.row.TYPE == '发送' ? scope.row['F1048_DESC_B']:scope.row['F1048_DESC_A'] }}
      </template>
    </el-table-column>
    <el-table-column label="对端光强"　width="100">
      <template slot-scope="scope">
        {{scope.row.TYPE == '发送' ? statusData[scope.row['F1006_CODE_B']]:statusData[scope.row['F1006_CODE_A']]}}
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import * as bayJs from '@/api/gridnt/bay'
import { subscribe, unsubscribe } from '@/api/gridnt/websocket';

export default {
  name: 'Opticalfiber',
  props: {
    nodeType: {
      type: String
    },
    activeTab: {
      type: String
    }
  },
  watch: {
    nodeType: function(val) {
      if (val != 'B') {
        this.clearAll();
      }
    },
    activeTab: function(val) {
      if (val != 'opticalfiber') {
        this.clearAll();
      }
    }
  },
  data(){
    return{
      tableData:[],
      statusData:{},
      subsData:[],
      subsId: null
    }
  },
  methods:{
    clearAll(){
      if(this.subsId != null){
        unsubscribe(this.subsId)
      }
      this.subsId = null
      this.tableData = []
      this.statusData ={}
      this.subsData = []
    },
    getList(bayInfo){
      this.clearAll()
      let self = this
      let tempdata = {}
      bayJs.loadOpticalfiberList(bayInfo.id).then(resp =>{
        if(resp.data != null){
          for(let i = 0;i<resp.data.length;i++){
            let item = resp.data[i]
            if(item['F1053_STATUSCODE'] != undefined){
              tempdata[item['F1053_STATUSCODE']] = '未知'
              self.subsData.push({"table":'status',"key":item['F1053_STATUSCODE'],cols:["value"]})
            }
            if(item['F1006_CODE_A'] != undefined){
              tempdata[item['F1006_CODE_A']] = '未知'
              self.subsData.push({"table":'analog',"key":item['F1006_CODE_A'],cols:["value"]})
            }
            if(item['F1006_CODE_B'] != undefined){
              tempdata[item['F1006_CODE_B']] = '未知'
              self.subsData.push({"table":'analog',"key":item['F1006_CODE_B'],cols:["value"]})
            }
          }
          self.statusData = Object.assign({},tempdata)
        }
        self.tableData = resp.data
        //订阅数据
        self.subsStateData()
      })
    },
    //订阅数据
    subsStateData(){
      let paramId = Math.ceil((Math.random()*100))
      //组装数据
      let paramData = {
        "method": "subs",
        "params":this.subsData,
        "id": paramId
      }
      let subsCmd = {
        subsParam :paramData,
        onData:this.refreshData
      }
      this.subsId = "svg"+paramId
      subscribe(subsCmd)
    },
    //刷新订阅数据
    refreshData(valList){
      if(valList.length > 0){
        this.$nextTick(()=>{
          for(let i = 0;i<valList.length;i++){
            let dataItem = valList[i]
            if(this.statusData[dataItem.key] != undefined && this.statusData[dataItem.key] != dataItem.value){
              this.statusData[dataItem.key] = dataItem.value
            }
          }
        })
      }
    },
    //合并单元格
    objectSpanMethod({ row, column, rowIndex, columnIndex }){
      if(columnIndex == 1 ){
        if(row.rowspan != undefined){
          return {
            rowspan: row.rowspan ,
            colspan: 1
          };
        }else{
          return {
            rowspan: 0,
            colspan: 0
          }
        }
      }else if(columnIndex == 2){
        if(row.typerowspan != undefined){
          return {
            rowspan: row.typerowspan ,
            colspan: 1
          };
        }else{
          return {
            rowspan: 0,
            colspan: 0
          }
        }
      }
    }
  }
};
</script>

<style scoped>
/deep/ th,/deep/ td{
  text-align: center;
}
.status-circle{
  display: inline-block;
  width: 15px;
  height: 15px;
  border-radius: 15px;
  border: 1px solid #000;
}

.status-color0{
  background-color: #1edc0e;
  /*border-color: #1edc0e;*/
}
.status-color1{
  background-color: #ff0000;
  /*border-color: #1edc0e;*/
}
</style>
