<template>
  <div>
    <el-button :class="{missing: isMissing}"
      @click.native.prevent="showDetail(alertItem)"
      type="text">
      {{statusParser(alertItem)}}
    </el-button>
    <el-dialog :title="info" :visible.sync="fileDialogVisible">
      <div v-for="ied in device">
        <el-col>{{ied.name + " " + ied.desc}}</el-col>
        <div v-for="warning in ied.warnings">
          <el-col>{{"告警时间: " + warning.time + " 告警类型: " + warning.subtype}}</el-col>
          <el-table style="width: 100%" :data="[warning.point]">
            <el-table-column label="Reference" prop="path"></el-table-column>
            <el-table-column label="描述" prop="desc"></el-table-column>
            <el-table-column label="其它" prop="other"></el-table-column>
            <el-table-column label="数据组" width="500" style="padding:0 0">
              <template slot-scope="scope">
              <el-table style="width: 100%"
                        :data="dataPoints(warning.point.dataGroups)"
                        :span-method="spanMethod">
                <el-table-column label="个数" prop="num"></el-table-column>
                <el-table-column label="值" prop="value"></el-table-column>
                <el-table-column label="时间" prop="datetime"></el-table-column>
                <el-table-column label="品质" prop="quality"></el-table-column>
              </el-table>
              </template>
            </el-table-column>
            <el-table-column label="上阈值" prop="upperLimit.value"></el-table-column>
            <el-table-column label="下阈值" prop="lowerLimit.value"></el-table-column>
            <el-table-column label="告警使能" prop="alarm"></el-table-column>
            <el-table-column label="诊断规则" :formatter="policyParser(warning.policy)"></el-table-column>
            <el-table-column label="结果描述" :formatter="(row)=>{return warning.result;}"></el-table-column>
          </el-table>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getSTATDetail_G} from "@/api/gridnt/intelAlert";

export default {
  name: "G_analogXml",
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
      let result = '模拟量告警';
      if(this.fileContext){
        result = "模拟量告警: " + this.fileContext.queryTime.start + " to " + this.fileContext.queryTime.end;
      }
      return result;
    },
    device(){
      let result = [];
      if(this.fileContext){
        result = this.fileContext.queryTime.ied;
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

      getSTATDetail_G({fileId: row.fileId, type: row.alertType}).then(resp=>{
        this.fileContext = resp.data;
        console.log(JSON.stringify(resp.data))
        this.fileDialogVisible = true;
      })
    },
    policyParser(policy){
      return function (row){
        let result = '';
        policy.conditions.forEach((item)=>{
          result += item.desc + ": " + item.value + "; ";
        });
        return result;
      };
    },
    dataPoints(group){
      let result = [];
      group.forEach((row)=>{
        let internalIndex = 0;
        row.analogData.forEach((data)=>{
          data.num = row.num;
          data.index = internalIndex;
          internalIndex++;
        });
        result.push.apply(result, row.analogData);
      });
      return result;
    },
    spanMethod({ row, column, rowIndex, columnIndex }){
      if(columnIndex === 0){
        if(row.index === 0){
          return [row.num, 1];
        }
        else{
          return [0, 0];
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
