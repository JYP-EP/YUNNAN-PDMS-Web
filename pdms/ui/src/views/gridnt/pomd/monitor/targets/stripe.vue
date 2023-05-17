<template>
  <el-container style="height: 100%;">
    <el-table class="flow-tree" max-height="700" :key="tableKey" :data="tableData">
      <el-table-column type="index"/>
      <el-table-column align="center" label="描述" prop="description"/>
      <el-table-column align="center" label="Reference" prop="reference"/>
      <el-table-column align="center" label="基准" prop="baseval"/>
<!--      <el-table-column align="center" label="状态" prop="value"/>-->
      <el-table-column align="center" label="状态">
        <template slot-scope="scope">
          <div v-if="scope.row.value == 0">
            <span class="status-circle status-color2"></span>
            退出
          </div>
          <div v-if="scope.row.value == 1">
            <span class="status-circle status-color0"></span>
            投入
          </div>
        </template>
      </el-table-column>
    </el-table>
  </el-container>
</template>

<script>
import {getOtherStatusInfo} from "@/api/gridnt/monitor";
import {query} from "@/api/gridnt/websocket";

export default {
  name: "stripe",
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
      // 600 <= type < 699 status
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
            cols: ["time", "value", "baseval"]
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
          row.baseval = data['baseval'];
        }
      }

      this.tableKey++;
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
