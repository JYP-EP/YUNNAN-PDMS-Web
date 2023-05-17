<template>
  <div>
    <el-button :class="{missing: isMissing}"
      @click.native.prevent="showDetail(alertItem)"
      type="text">
      {{statusParser(alertItem)}}
    </el-button>
    <el-dialog :title="info" :visible.sync="fileDialogVisible">
      <div v-for="ied in devices">
        <el-col>{{ied.name + " " + ied.desc}}</el-col>
        <el-table :data="ied.points" :span-method="detailTableSapn">
          <el-table-column label="描述" prop="desc"></el-table-column>
          <el-table-column label="值" prop="value"></el-table-column>
          <el-table-column label="时间" prop="duration"></el-table-column>
          <el-table-column label="比率" prop="rate"></el-table-column>
          <el-table-column label="结果" prop="result"></el-table-column>
        </el-table>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getSTATDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "commRatioXml",
  props: ['alertItem'],
  data(){
    return {
      isMissing: false,
      fileDialogVisible: false,
      fileContext: null
    }
  },
  computed:{
    info(){
      let result = '通信状态统计';
      if(this.fileContext){
        result = "通信状态统计: " + this.fileContext.queryTime.start + " to " + this.fileContext.queryTime.end;
      }
      return result;
    },
    devices(){
      let result = [];
      if(this.fileContext){
        result = this.fileContext.queryTime.ied;
        result.forEach(ied => {
          ied.points[0].rate = ied.rate;
          ied.points[0].result = ied.result;
        });
      }
      return result;
    }
  },
  methods:{
    statusParser(row){
      this.isMissing = row.status !== 1;
      return row.status === 1 ? "查看" : "召唤文件失败";
    },
    showDetail(row){
      if(row.status !== 1){
        return;
      }

      getSTATDetail({fileId: row.fileId, type: row.alertType}).then(resp=>{
        this.fileContext = resp.data;
        console.log(this.fileContext);

        this.fileDialogVisible = true;
      });
    },
    detailTableSapn({ row, column, rowIndex, columnIndex }){
      if(columnIndex >= 3){
        if(rowIndex === 0){
          return [2, 1];
        }
        else{
          return [1, 1];
        }
      }
    }
  }
}
</script>

<style scoped>
.missing{
  color: red;
}
</style>
