<template>
  <el-container style="height: 100%;">
    <el-row style="width: 100%">
<!--      <el-row>-->
<!--        <el-button>定值组个数: {{numOfSG}}</el-button>-->
<!--        <el-button>当前定值区号: {{numOfSG}}</el-button>-->
<!--        <el-select v-model="selectedSG">-->
<!--          <el-option v-for="item in sgList" :key="item.id" :label="item.name" :value="item.id">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--        <el-button @click="sendCommand">召唤</el-button>-->
<!--      </el-row>-->
      <el-table class="flow-tree" max-height="700" :data="tableData">
        <el-table-column type="index"/>
        <el-table-column align="center" label="描述" prop="description"/>
        <el-table-column align="center" label="定值" prop="value"/>
        <el-table-column align="center" label="基准值" prop="defaultVal"/>
        <el-table-column align="center" label="单位" prop="unit"/>
        <el-table-column align="center" label="步长" prop="stepSize"/>
        <el-table-column align="center" label="最小值" prop="minVal"/>
        <el-table-column align="center" label="最大值" prop="maxVal"/>
      </el-table>
    </el-row>
  </el-container>
</template>

<script>
import {getSettingInfo} from "@/api/gridnt/monitor";
import {query, rpc} from "@/api/gridnt/websocket";
import {Message} from "element-ui";

export default {
  name: "setting",
  data(){
    return{
      websocket: null,
      tableKey: 0,
      numOfSG: 0,
      actSG: 0,
      selectedSG: 0,
      selectedDevice: "",
      tableData: [],
      tableDataMap: {},
    }
  },
  computed: {
    sgList(){
      let sgList = [];
      for(let i = 1; i <= this.numOfSG; i++){
        sgList.push({
          id: i,
          name: '定值组' + i
        });
      }
      return sgList;
    }
  },
  methods:{
    selectChanged(selection){
      console.log('setting unSelectChanged');
    },
    deviceClicked(data){
      if(data.type !== 'I'){
        return;
      }
      this.selectedDevice = data.id;
      this.numOfSG = 0;
      this.actSG = 0;
      this.selectedSG = 0;
      getSettingInfo(data.id).then(res => {
        this.tableData = res.data;
        this.tableDataMap = {};
        for(let i = 0; i < this.tableData.length; i++){
          this.tableDataMap[this.tableData[i].id] = this.tableData[i];
        }

        let queryParam = {
          device: data.id,
          type: 'group'
        };
        //console.log(12345);
        this.websocket = query(this.websocket, queryParam, this.refreshGroup);
      });
    },
    refreshGroup(event){
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
      this.numOfSG = result.numOfSG;
      this.actSG = result.actSG;
      this.selectedSG = result.actSG;
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
    },
    sendCommand(){
      if(this.selectedSG === 0){
        Message({
          message: '未选择定值组',
          type: 'error',
          duration: 5 * 1000
        })
        return;
      }
      let queryParam = {
        device: this.selectedDevice,
        type: 'setting',
        editSG: this.selectedSG.toString(),
      };

      this.websocket = rpc(this.websocket, queryParam, this.refresh);
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
