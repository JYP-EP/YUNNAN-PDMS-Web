<template>
  <div>
    <el-button :class="{missing: isMissing}"
      @click.native.prevent="showDetail(alertItem)"
      type="text">
      {{statusParser(alertItem)}}
    </el-button>
    <el-dialog :title="info" :visible.sync="fileDialogVisible">
      <div v-for="warning in warnings">
        <el-col>{{"告警时间: " + warning.time}}</el-col>
        <el-table :data="[warning]">
          <el-table-column label="比较信息段" min-width="800">
            <div v-for="pair in warning.pairs">
              <el-table :data="pair.points" style="width: 100%">
                <el-table-column label="Reference" prop="path"></el-table-column>
                <el-table-column label="描述" prop="desc"></el-table-column>
                <el-table-column label="其它" prop="other"></el-table-column>
                <el-table-column label="值" prop="value"></el-table-column>
                <el-table-column label="上限" prop="highlimit"></el-table-column>
                <el-table-column label="下限" prop="lowlimit"></el-table-column>
                <el-table-column label="时间" prop="time"></el-table-column>
                <el-table-column label="品质" prop="quality"></el-table-column>
                <el-table-column label="装置" prop="ied"></el-table-column>
              </el-table>
            </div>
          </el-table-column>
          <el-table-column label="诊断规则" :formatter="policyParser"></el-table-column>
          <el-table-column label="结果描述" prop="result"></el-table-column>
        </el-table>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getSTATDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "doubleAnalogXml",
  props: ['alertItem'],
  data(){
    return{
      isMissing: false,
      fileDialogVisible: false,
      fileContext: null
    }
  },
  computed:{
    info(){
      let result = '双重化保护模拟量告警';
      if(this.fileContext){
        result = "双重化保护模拟量告警: " + this.fileContext.queryTime.start + " to " + this.fileContext.queryTime.end;
      }
      return result;
    },
    warnings(){
      let result = [];
      if(this.fileContext){
        result = this.fileContext.queryTime.warning;
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
      if(row.status != 1){
        return;
      }

      getSTATDetail({fileId: row.fileId, type: row.alertType}).then(resp=>{
        this.fileContext = resp.data;
        console.log(this.fileContext);

        this.fileDialogVisible = true;
      })
    },
    policyParser(row){
        let result = '';
        row.policy.conditions.forEach((item)=>{
          result += item.desc + ": " + item.value + "; ";
        });
        return result;
    }
  }
}
</script>

<style scoped>
.missing{
  color: red;
}
</style>
