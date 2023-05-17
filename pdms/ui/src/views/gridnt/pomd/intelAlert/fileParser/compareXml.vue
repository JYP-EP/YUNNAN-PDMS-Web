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
        <div v-for="warning in ied.warning">
          <el-col>{{"告警时间: " + warning.time + " 告警类型: " + warning.type}}</el-col>
          <el-table :data="dataPoints(warning)"
                    :span-method="spanMethod(warning.points.length)">
            <el-table-column label="Reference" prop="path"></el-table-column>
            <el-table-column label="描述" prop="desc"></el-table-column>
            <el-table-column label="其它" prop="other"></el-table-column>
            <el-table-column label="告警点值" prop="value"></el-table-column>
            <el-table-column label="模板值" prop="templateValue"></el-table-column>
            <el-table-column label="告警点时间" prop="time"></el-table-column>
            <el-table-column label="模板时间" prop="templateTime"></el-table-column>
            <el-table-column label="结论描述" :formatter="(row)=>{return warning.result;}"></el-table-column>
          </el-table>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getSTATDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "compareXml",
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
      let result = '定值和版本校核告警';
      if(this.fileContext){
        result = "定值和版本校核告警: " + this.fileContext.queryTime.start + " to " + this.fileContext.queryTime.end;
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

      getSTATDetail({fileId: row.fileId, type: row.alertType}).then(resp=>{
        this.fileContext = resp.data;

        this.fileDialogVisible = true;

      })
    },
    dataPoints(warning){
      if(warning.type === 'Setting'){
        warning.points.forEach((point)=>{
          let template = warning.compareTemplate.points.find((item)=>{
                                return item.path === point.path;
                          });
          if(template){
            point.templateValue = template.value;
            point.templateTime = template.time;
          }
          else{
            point.templateValue = '';
            point.templateTime = '';
          }
        });
      }
      else{
        warning.points.forEach((point)=>{
          point.templateValue = warning.compareTemplate.value;
          point.templateTime = warning.compareTemplate.time;
        });
      }
      return warning.points;
    },
    spanMethod(length){
      return function ({ row, column, rowIndex, columnIndex }){
        if(column.label === '结论描述') {
          if (rowIndex === 0) {
            return [length, 1];
          } else {
            return [0, 0];
          }
        }
      };
    }
  }
}
</script>

<style scoped>
.missing{
  color: red;
}
</style>
