<template>
  <el-container style="height: 100%;">
    <el-row style="width: 100%">
<!--      <el-row>-->
<!--        <el-button @click="getCurrentList">刷新</el-button>-->
<!--      </el-row>-->
      <el-row style="margin-bottom: 20px;"><el-button type="primary" icon="el-icon-download" @click="getDownload">下载</el-button></el-row>
      <el-table class="flow-tree" max-height="700"  :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index"/>
        <el-table-column align="center" label="文件名" prop="FileName"/>
      </el-table>
    </el-row>
  </el-container>
</template>

<script>
import {getRecordList} from "@/api/gridnt/monitor";
import { Loading } from 'element-ui';
import { downloadZip } from "@/utils/downloadZipFileSaver"
export default {
  name: "record",
  data(){
    return{
      selectedDevice: "",
      tableData: [],
      Dow:[],
      multipleSelection: [],
    }
  },
  methods:{
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
    getDownload(){
      if(this.multipleSelection.length>0)
      {
        let data = [];
        for(let i=0;i<this.multipleSelection.length;i++)
        {
          this.Dow.filter(x=>{
            if(x.FileName == this.multipleSelection[i].FileName)
            { data.push({ url:x.File, file_name:x.FilePath }); }
          });
        }
        let time = downloadZip.GetNowDate("datetime",".");
        downloadZip.handleBatchDownload(data,time + "录波文件.zip");
      }
      else {
        this.$message({
          type: 'warning',
          message: '请选择需要下载的录波文件！'
        });
      }
    },
    deviceClicked(data){
      if(data.type !== 'I'){
        return;
      }
      this.selectedDevice = data.id;
      this.getCurrentList();
      // let queryParam = {
      //   device: data.id,
      //   type: 'record'
      // };
      // this.websocket = rpc(this.websocket, queryParam, this.refresh);
    },
    getCurrentList(){

      const loading = this.$loading({
        lock: true,
        text: '正在检索录波文件...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });

      this.tableData = [];
      this.Dow = [];
      getRecordList(this.selectedDevice).then(res => {

        this.Dow = res.data;
        for(let i = 0; i < res.data.length; i++){
          if (this.tableData.length > 0){
            if(!this.tableData.find(x=>x.FileName == res.data[i].FileName))
            {
              let item = {};
              item["FileName"] = res.data[i].FileName;
              this.tableData.push(item);
            }
          }
          else
          {
            let item = {};
            item["FileName"] = res.data[i].FileName;
            this.tableData.push(item);
          }
        }

        loading.close();
      });
    },
    refresh(event) {
      let message = JSON.parse(event.data);
      let tableData = this.tableDataMap[message.id];
      if(tableData){
        tableData.value = message.value;
      }
    }
  }
}
</script>

<style scoped>

</style>
