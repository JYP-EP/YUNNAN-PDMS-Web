<template>
  <div>
    <el-button :class="{missing: isMissing}"
      @click.native.prevent="showDetail(alertItem)"
      type="text">
      {{statusParser(alertItem)}}
    </el-button>
    <el-dialog :title="title" :visible.sync="fileDialogVisible">
      <el-row>{{info}}}</el-row>
      <div v-for="ied in devices">
        <el-row>{{ ied.deviceId + " " + ied.deviceName}}</el-row>
        <el-row>{{ iedStatus(ied) }}</el-row>
        <el-table :data="tableItems(ied)">
          <el-table-column label="巡视项" prop="itemName"></el-table-column>
          <el-table-column label="是否巡视" :formatter="checkStatus"></el-table-column>
          <el-table-column label="未巡视原因" :formatter="checkReason"></el-table-column>
          <el-table-column label="巡视结果" :formatter="checkResult"></el-table-column>
          <el-table-column label="信号点ID" prop="pointRef"></el-table-column>
          <el-table-column label="信号点名称" prop="pointDesc"></el-table-column>
          <el-table-column label="标准值" prop="refValue"></el-table-column>
          <el-table-column label="实际值" prop="realValue"></el-table-column>
          <el-table-column label="模拟量上限" prop="upperLimit"></el-table-column>
          <el-table-column label="模拟量下限" prop="lowerLimit"></el-table-column>
          <el-table-column label="定值区" prop="zoneNO"></el-table-column>
          <el-table-column label="巡视时间" prop="checkTime"></el-table-column>
          <el-table-column label="是否差异" prop="isDiff"></el-table-column>
        </el-table>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getCheckReportDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "checkReportXml",
  props: ['alertItem'],
  data(){
    return{
      isMissing: false,
      fileDialogVisible: false,
      fileContext: null
    }
  },
  computed:{
    title(){
      let result = '巡视报告';
      if(this.fileContext){
        result = this.fileContext.system.subStation + " " +
                  this.fileContext.system.time +
                  (this.fileContext.system.reason === '1' ? '(自动巡视)' : '(手动巡视)');
      }
      return result;
    },
    info(){
      let result = "";
      if(this.fileContext){
        result = "设备总数: " + this.fileContext.system.deviceNumber +
                " 巡视个数: " + this.fileContext.system.checkedNumber +
                " 异常个数: " + this.fileContext.system.abnormalNumber;
      }
      return result;
    },
    devices(){
      let result = [];
      if(this.fileContext){
        result = this.fileContext.ied.items;
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

      getCheckReportDetail({fileId: row.fileId, type: row.alertType}).then(resp=>{
        this.fileContext = resp.data;
        console.log(this.fileContext);

        this.fileDialogVisible = true;
      })
    },
    checkStatus(ied){
      return ied.isChecked === '0' ? '未巡视' : '已巡视';
    },
    checkReason(ied){
      let result = '()';
      switch (ied.unCheckReason){
        case '0':
          result = '';
          break;
        case '1':
          result = '(装置不在巡视范围)';
          break;
        case '2':
          result = '(装置通信中断)';
          break;
        case '3':
          result = '(装置检修)';
          break;
        case '4':
          result = '(未下发标准值)';
          break;
      }
      return result;
    },
    checkResult(ied){
      let result = '未知';
      switch (ied.result){
        case '0':
          result = '未知状态';
          break;
        case '1':
          result = '正常不需检修';
          break;
        case '2':
          result = '异常需关注';
          break;
        case '3':
          result = '严重需检修';
          break;
        case '4':
          result = '需人工确认';
          break;
      }
      return result;
    },
    iedStatus(ied){
      let result = this.checkStatus(ied);
      result += this.checkReason(ied);
      if(ied.isChecked === '1'){
        result += ": " + this.checkResult(ied);
      }
      return result;
    },
    tableItems(ied){
      let result = [];
      let itemName = {
        'zone': "定值区",
        'setting': '定值',
        'softPlate': '软压板',
        'hardPlate': '硬压板',
        'discrete': '开入状态',
        'clock': '时钟',
        'commStatus': '通信状态',
        'secCircuit': '二次回路',
        'softVersion': '软件版本',
        'selfAlarm': '自检告警',
        'gpsAlarm': 'GPS告警',
        'wave': '录波',
        'loop': '回路',
        'analog': '其它模拟量',
        'channel': '光纤',
        'onSwitch': '转换开关'
      };
      for(let key in itemName){
        console.log(this.fileContext[key]);
        if(this.fileContext[key] == null){
          continue;
        }
        let row = this.fileContext[key].itemDetail.find((item)=>{
          return item.deviceId === ied.deviceId;
        });
        if(row){
          row['itemName'] = itemName[key];
          row['isChecked'] = ied.isChecked;
          row['unCheckReason'] = ied.unCheckReason;
          row['result'] = ied.result;
          result.push(row);
        }
      }
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
