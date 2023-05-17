<template>
  <el-container style="height: 100%">
    <el-table class="flow-tree" max-height="700"  :key="tableKey" :data="tableData">
      <el-table-column type="index"/>
      <el-table-column align="center" label="描述" prop="description"/>
      <el-table-column align="center" label="Reference" prop="reference"/>
      <el-table-column align="center" label="类型" prop="fcdaType"/>
      <el-table-column align="center" label="时标" :formatter="timeFormat"/>
<!--      <el-table-column align="center" label="品质" :formatter="qualityFormat"/>-->
      <el-table-column align="center" label="品质">
        <template slot-scope="scope">
          <div v-if="scope.row.inconsistf == 0">
            <span class="status-circle status-color0"></span>
            有效
          </div>
          <div v-if="scope.row.inconsistf == 1">
            <span class="status-circle status-color2"></span>
            无效
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="值" prop="value"/>
    </el-table>
  </el-container>
</template>

<script>
import {getOtherStatusInfo, localDateStringFromEpoch} from "@/api/gridnt/monitor";
import {query} from "@/api/gridnt/websocket";

export default {
  name: "others",
  data(){
    return{
      isActive: true,
      websocket: null,
      queryTimer: null,
      tableKey: 0,
      tableData: [],
      tableDataMap: {},
    }
  },
  methods:{
    selectChanged(selection){
      this.isActive = selection;
    },
    initTarget(){
      // 200 < type < 600 , or type >= 700 status
    },
    deviceClicked(data){
      if(data.type !== 'I'){
        return;
      }
      getOtherStatusInfo(data.id).then(res => {
        this.tableData = res.data;
        this.tableDataMap = {};
        for(let i = 0; i < this.tableData.length; i++){
          this.tableDataMap[this.tableData[i].id] = this.tableData[i];
        }

        if(this.queryTimer){
          clearInterval(this.queryTimer);
        }

        let queryParam = [];
        for(let i = 0; i < this.tableData.length; i++){
          queryParam.push({
            table: "analog",
            key: this.tableData[i].id,
            cols: ["time", "value", "inconsistf"]
          });
        }

        this.websocket = query(this.websocket, queryParam, this.refresh);

        this.queryTimer = setInterval(() => {
          if(!this.isActive){
            return;
          }

          this.websocket = query(this.websocket, queryParam, this.refresh);
        }, 10000);
      });
    },
    refresh(event){
      let message = JSON.parse(event.data);
      if(message.error){
        console.log(message.error);
        return;
      }

      let result = message.result;
      for(let i = 0; i < result.length; i++){
        let data = result[i];
        let row = this.tableDataMap[data.key];
        if(row){
          row.time = data['time'];
          row.value = data['value'];
          row.inconsistf = data['inconsistf'];
        }
      }

      this.tableKey++;
    },
    timeFormat(row){
      return localDateStringFromEpoch(row.time);
    },
    qualityFormat(row){
      switch (row.inconsistf) {
        case 0:
          return "有效";
        case 1:
          return "无效";
        default:
          return row.inconsistf;
      }
    }
  }
}
</script>

<style scoped>
.el-tree>.el-tree-node{
  height: 700px;
  min-width: 100%;
  display:inline-block;
}
.status-circle{
  display: inline-block;
  width: 15px;
  height: 15px;
  border-radius: 15px;
  border: 1px solid #000;
}
/deep/ th,/deep/ td{
  text-align: center;
}
.status-color0{
  background-color: #1edc0e;
  /*border-color: #1edc0e;*/
}
.status-color1{
  background-color: #ff0000;
  /*border-color: #1edc0e;*/
}
.status-color2{
  background-color: #909399;
  /*border-color: #1edc0e;*/
}
.status-color3{
  background-color: #e6a23c;
  /*border-color: #1edc0e;*/
}
</style>
