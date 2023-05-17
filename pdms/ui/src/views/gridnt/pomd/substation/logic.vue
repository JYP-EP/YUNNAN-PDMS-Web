<template>
  <el-table :data="tableData" :span-method="objectSpanMethod" border height="calc(100vh - 160px)">
    <el-table-column type="index"　></el-table-column>
    <el-table-column label="装置"　>
      <template slot-scope="scope">
        {{scope.row["F1046_NAME_SELF"] +':'+scope.row["F1046_DESC_SELF"]}}
      </template>
    </el-table-column>
    <el-table-column label="接受/发送"　width="80">
      <template slot-scope="scope">
        {{scope.row["F1065_SEND_RECV"] }}
      </template>
    </el-table-column>
    <el-table-column label="对端装置"　width="240">
      <template slot-scope="scope">
        {{scope.row["F1046_NAME_OPPOSITE"] +':'+scope.row["F1046_DESC_OPPOSITE"]}}
      </template>
    </el-table-column>
    <el-table-column label="状态"　>
      <template slot-scope="scope">
        <span :class=" 'status-circle status-color'+statusData[scope.row['F1065_STATUSCODE']]" v-show="statusData[scope.row['F1065_STATUSCODE']] == '0' || statusData[scope.row['F1065_STATUSCODE']] == '1'"></span>
        {{statusData[scope.row['F1065_STATUSCODE']] == '0'? '正常':
        (statusData[scope.row['F1065_STATUSCODE']] == '1' ? '中断': statusData[scope.row['F1065_STATUSCODE']])}}
      </template>

    </el-table-column>
    <el-table-column label="类型"　>
      <template  slot-scope="scope">
        {{ scope.row["F1065_TYPE"] == '1' ? 'GOOSE' :(scope.row["F1065_TYPE"] == '2' ? 'SV': '') }}
      </template>
    </el-table-column>
    <el-table-column label="APPID"　prop="F1065_APPID" >
    </el-table-column>
    <el-table-column label="控制块参引"　prop="F1065_CBREF" width="240">
    </el-table-column>
    <el-table-column label="MAC地址"　prop="F1065_CBREF" width="240">
    </el-table-column>
    <el-table-column label="VLAN ID"　prop="F1065_VLANID">
    </el-table-column>
  </el-table>
</template>

<script>
import * as bayJs from '@/api/gridnt/bay';
import { subscribe, unsubscribe } from '@/api/gridnt/websocket';

export default {
  name: 'Logic',
  props: {
    nodeType: {
      type: String
    },
    activeTab: {
      type: String
    }
  },
  created() {
  },
  watch: {
    nodeType: function(val) {
      if (val != 'B') {
        this.clearAll();
      }
    },
    activeTab: function(val) {
      if (val != 'logic') {
        this.clearAll();
      }
    }
  },
  data(){
    return {
      tableData:[],
      statusData:{},
      subsData:[],
      subsId: null
    }
  },
  methods:{

    getList(bayInfo){
      let self = this
      bayJs.loadLogicList(bayInfo.id).then(resp =>{
        if(resp.data != null){
          let tmpData = {}
          for(let i = 0;i<resp.data.length;i++){
            let item = resp.data[i]
            tmpData[item['F1065_STATUSCODE']] = '未知'
            self.subsData.push({"table":'status',"key":item['F1065_STATUSCODE'],cols:["value"]})
          }
          self.statusData = Object.assign({},tmpData)
        }
        this.tableData = resp.data
        this.subsStateData()
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
    clearAll(){
      if(this.subsId != null){
        unsubscribe(this.subsId)
      }
      this.subsId = null
      this.tableData = []
      this.statusData ={}
      this.subsData = []
    },
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
      }else if(columnIndex == 3){
        if(row.oppositerowspan != undefined){
          return {
            rowspan: row.oppositerowspan ,
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
