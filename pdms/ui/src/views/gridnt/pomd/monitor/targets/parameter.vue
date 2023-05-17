<template>
  <el-container style="height: 100%;">
    <el-table class="flow-tree" max-height="700" :key="tableKey" :data="tableData">
      <el-table-column type="index"/>
      <el-table-column align="center" label="描述" prop="description"/>
      <el-table-column align="center" label="Reference" prop="reference"/>
      <el-table-column align="center" label="值" prop="value"/>
    </el-table>
  </el-container>
</template>

<script>
import {getParameterInfo} from "@/api/gridnt/monitor";
import {query, rpc} from "@/api/gridnt/websocket";
import {Message} from "element-ui";

export default  {
  name: "parameter",
  data(){
    return{
      websocket: null,
      tableKey: 0,
      tableData: [],
      tableDataMap: {},
    }
  },
  methods:{
    selectChanged(selection){
      console.log('parameter unSelectChanged');
    },
    deviceClicked(data){
      console.log("getParameterInfo");
      if(data.type !== 'I'){
        return;
      }
      getParameterInfo(data.id).then(res => {
        this.tableData = res.data;
        this.tableDataMap = {};
        for(let i = 0; i < this.tableData.length; i++){
          this.tableDataMap[this.tableData[i].id] = this.tableData[i];
        }

        let queryParam = {
          device: data.id,
          type: 'parameter'
        };
        this.websocket = query(this.websocket, queryParam, this.refresh);
      });
    },
    refresh(event) {
      let message = JSON.parse(event.data);
      if (message.error) {
        Message({
          message: message.error,
          type: 'error',
          duration: 5 * 1000
        })
        return;
      }

      let result = message.result;
      for(let i = 0; i < result.length; i++){
        let item = result[i];
        let data = this.tableDataMap[item.id];
        if(data){
          data.value = item.value;
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
</style>
