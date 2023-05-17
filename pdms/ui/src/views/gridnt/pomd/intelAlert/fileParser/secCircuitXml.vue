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
          <el-col>{{"告警时间: " + warning.time + " 告警类型: " + warning.subtype}}</el-col>
          <el-table style="width: 100%"
                    :data="points(warning)"
                    :span-method="spanMethod(warning)">
            <el-table-column label="板卡信息" v-if="warning.board">
              <el-table-column label="板卡号" prop="boardID"></el-table-column>
              <el-table-column label="优先级" prop="boardPriority"></el-table-column>
            </el-table-column>
            <el-table-column label="端口信息" v-if="warning.port">
              <el-table-column label="端口号" prop="portID"></el-table-column>
              <el-table-column label="优先级" prop="portPriority"></el-table-column>
            </el-table-column>
            <el-table-column label="光纤信息" v-if="warning.cable">
              <el-table-column label="光纤号" prop="cableID"></el-table-column>
              <el-table-column label="优先级" prop="cablePriority"></el-table-column>
            </el-table-column>
            <el-table-column label="告警信息点">
              <el-table-column label="Reference" prop="path"></el-table-column>
              <el-table-column label="描述" prop="desc"></el-table-column>
              <el-table-column label="其它" prop="other"></el-table-column>
              <el-table-column label="值" prop="value"></el-table-column>
              <el-table-column label="变位时间" prop="time"></el-table-column>
              <el-table-column label="品质" prop="quality"></el-table-column>
            </el-table-column>
            <el-table-column label="录波文件与中间节点文件" v-if="warning.faultFile">
              <el-table-column label="录波文件" :formatter="comtrade(warning)"></el-table-column>
              <el-table-column label="中间节点文件" :formatter="middleFile(warning)"></el-table-column>
            </el-table-column>
            <el-table-column label="结论" :formatter="conclusion(warning)"></el-table-column>
            <el-table-column label="建议" v-if="warning.suggestion" :formatter="suggestion(warning)"></el-table-column>
          </el-table>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getSTATDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "secCircuitXml",
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
      let result = '二次回路可视化';
      if(this.fileContext){
        result = "二次回路可视化: " + this.fileContext.queryTime.start + " to " + this.fileContext.queryTime.end;
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
        console.log(this.fileContext);

        this.fileDialogVisible = true;
      })
    },
    points(warning){
      let result = warning.points;
      result.forEach(row =>{
        if(warning.board){
          row.boardID = warning.board[0].slot;
          row.boardPriority = warning.board[0].priority;
        }
        if(warning.port){
          row.portID = warning.port[0].id;
          row.portPriority = warning.port[0].priority;
        }
        if(warning.cable){
          row.cableID = warning.cable[0].id;
          row.cablePriority = warning.cable[0].priority;
        }
      });
      return result;
    },
    comtrade(warning){
      return function (){
        return warning.faultFile.comTrade;
      }
    },
    middleFile(warning){
      return function (){
        return warning.faultFile.middle;
      }
    },
    conclusion(warning){
      return function (){
        return warning.result;
      }
    },
    suggestion(warning){
      return function (){
        return warning.suggestion;
      }
    },
    spanMethod(warning){
      return function ({ row, column, rowIndex, columnIndex }){
        if(column.label.indexOf('文件') != -1
            || column.label.indexOf('结论') != -1
            || column.label.indexOf('建议') != -1){
          if(rowIndex === 0){
            return [warning.points.length, 1]
          }
          else{
            return [0, 0];
          }
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
