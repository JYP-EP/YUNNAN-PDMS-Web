<template>
  <div>
    <el-table :data="tableData" border  height="calc(100vh - 100px)">
      <el-table-column type="index"></el-table-column>
      <el-table-column label="变电站" prop="label"></el-table-column>
      <el-table-column label="运行状态" prop="me.status"></el-table-column>
      <el-table-column label="装置MMS通信率(%)" prop="me.mmsRate"></el-table-column>
      <el-table-column label="GOOSE通信率(%)" prop="me.gooseRate"></el-table-column>
      <el-table-column label="SMV通信率(%)" prop="me.smvRate"></el-table-column>
      <el-table-column label="告警统计" prop="me.warningNumber"></el-table-column>
      <el-table-column label="闭锁统计" prop="me.lockNumber"></el-table-column>
      <el-table-column label="保护状态统计" prop="me.procStatusNumber"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getSubstationsListByDq,getSubsStatusByDq } from '@/api/gridnt/ied';

export default {
  name: 'subsStatusList',
  data() {
    return {
      tableData:[]
    }
  },
  methods:{
    getList(node){
      let self = this
      self.tableData = []
      getSubstationsListByDq({ dq: node.id }).then(resp => {
        if (resp.data) {
          self.tableData = resp.data
          self.getStatus({ dq: node.id });
        }
      });
    },
    getStatus(params){
      let self = this
      getSubsStatusByDq(params).then(resp =>{
        if(resp.data != null){
          for(let i = 0;i<self.tableData.length;i++){
            let tableItem = self.tableData[i]
            if(resp.data[tableItem.id] != undefined){
              self.tableData[i].me = resp.data[tableItem.id]
            }
          }
        }
        // console.log(self.tableData)
      })
    },
  }
};
</script>

<style scoped>
/deep/th, /deep/td{
  text-align: center;
}
</style>
