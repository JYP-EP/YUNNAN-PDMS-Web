<template>
  <div>
    <el-button :class="{missing: isMissing}"
      @click.native.prevent="showDetail(alertItem)"
      type="text">
      {{statusParser(alertItem)}}
    </el-button>
    <el-dialog :title="title" :visible.sync="fileDialogVisible">
      <el-row>{{info}}</el-row>
      <div v-for="bay in bays">
        <el-row>{{bay.name}}</el-row>
        <div v-for="device in bay.devices">
          <el-row>{{device.id + ' ' + device.name + ' ' + device.nextTime}}</el-row>
          <div v-for="fun in device.functions">
            <el-row>{{fun.name + " 周期: " + fun.period + " =>> " + resultParser(fun)}}</el-row>
            <el-table :data="fun.item" v-if="fun.item != null">
              <el-table-column label="子功能名" :formatter="subFunParser(fun)"></el-table-column>
              <el-table-column label="Reference" prop="reference"></el-table-column>
              <el-table-column label="描述" prop="name"></el-table-column>
              <el-table-column label="关联点1" prop="attribute1"></el-table-column>
              <el-table-column label="关联点2" prop="attribute2"></el-table-column>
              <el-table-column label="参考值" prop="refValue"></el-table-column>
              <el-table-column label="实际值" prop="realValue"></el-table-column>
              <el-table-column label="基准值" prop="benVal"></el-table-column>
              <el-table-column label="下限值" prop="lowerLimit"></el-table-column>
              <el-table-column label="上限值" prop="upperLimit"></el-table-column>
              <el-table-column label="结果" prop="result"></el-table-column>
              <el-table-column label="定检时间" prop="checkTime"></el-table-column>
              <el-table-column label="下次定检时间" prop="nextTime"></el-table-column>
              <el-table-column label="出现次数" prop="quantum"></el-table-column>
              <el-table-column label="容忍范围" prop="ratio"></el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getInspectionDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "inspectReportXml",
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
      let result = '定检报告';
      if(this.fileContext){
        let station = this.fileContext.system;
        if (station) {
          result = station.station + "(" + station.startTime
            + " to " + station.endTime + ")";
        }
      }
      return result;
    },
    info(){
      let result = '';
      if(this.fileContext){
        result = '电压等级: ' + this.fileContext.level.name;
      }
      return result;
    },
    bays(){
      let result = [];
      if(this.fileContext){
        result = this.fileContext.level.bay;
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

      getInspectionDetail({fileId: row.fileId, type: row.alertType}).then(resp=>{
        this.fileContext = resp.data;
        console.log(this.fileContext);

        this.fileDialogVisible = true;
      })
    },
    resultParser(row){
      let result = '未知';
      switch (row.result){
        case '0':
          result = '未检修';
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
    subFunParser(fun){
      return function (row){
        let result = '';
        switch(fun.id){
          case '1':
            switch(row.id){
              case '1':
                result = '版本号';
                break;
              case '2':
                result = '校验玛';
                break;
            }
           break;
          case '2':
            switch(row.id){
              case '1':
                result = '逆变电源电压输出';
                break;
              case '2':
                result = '逆变电源正常启动';
                break;
            }
            break;
          case '9':
            switch(row.id){
              case '1':
                result = '失灵启动回路检查';
                break;
              case '2':
                result = '其他关联回路检查';
                break;
            }
            break;
          case '10':
            switch(row.id){
              case '1':
                result = '信号回路检查';
                break;
              case '2':
                result = '录波回路检查';
                break;
            }
            break;
          case '11':
            switch(row.id){
              case '1':
                result = '光纤电流差动通道';
                break;
              case '2':
                result = '本侧通道核对';
                break;
              case '3':
                result = '对侧通道核对';
                break;
              case '4':
                result = '对侧电流及差流';
                break;
              case '5':
                result = '仅投通道一（退出通道二）区内故障模拟';
                break;
              case '6':
                result = '仅投通道二（退出通道一）区内故障模拟';
                break;
              case '7':
                result = '双通道区内故障模拟';
                break;
              case '8':
                result = '仅投通道一（退出通道二）区内模拟故障';
                break;
              case '9':
                result = '仅投通道二（退出通道一）区内模拟故障';
                break;
              case '10':
                result = '双通道区内模拟故障';
                break;
              case '11':
                result = '模拟区外反向故障';
                break;
              case '12':
                result = '模拟区外正向故障';
                break;
              case '13':
                result = '辅助保护通道告警自动转换回路模拟通道一告警';
                break;
              case '14':
                result = '辅助保护通道告警自动转换回路模拟通道二告警';
                break;
              case '15':
                result = '模拟辅A过压保护动作仅起动发信Ⅰ(或远跳通道一)';
                break;
              case '16':
                result = '模拟辅A过压保护动作仅起动发信II(或远跳通道二)';
                break;
              case '17':
                result = '模拟辅A过压保护动作起动发信I和II(或远跳通道一、二)';
                break;
              case '18':
                result = '模拟辅B过压保护动作仅起动发信Ⅰ(或远跳通道一)';
                break;
              case '19':
                result = '模拟辅B过压保护动作仅起动发信II(或远跳通道二)';
                break;
              case '20':
                result = '模拟辅B过压保护动作起动发信I和II(或远跳通道一、二)';
                break;
            }
            break;
          case '13':
            switch(row.id){
              case '1':
                result = '屏柜及装置标识检查';
                break;
              case '2':
                result = '外部观感检查';
                break;
              case '3':
                result = '端子箱检查';
                break;
              case '4':
                result = '二次回路接线检查';
                break;
            }
            break;
          case '15':
            switch(row.id){
              case '1':
                result = '交流电压回路对地';
                break;
              case '2':
                result = '交流电压回路之间';
                break;
              case '3':
                result = '交流电流回路对地';
                break;
              case '4':
                result = '交流电流回路之间';
                break;
              case '5':
                result = '直流回路对地';
                break;
              case '6':
                result = '直流回路之间';
                break;
              case '7':
                result = '信号回路对地';
                break;
              case '8':
                result = '信号回路之间';
                break;
            }
            break;
          case '16':
          case '17':
            switch(row.id){
              case '1':
                result = '一点接地';
                break;
              case '2':
                result = '接地位置';
                break;
            }
            break;
          case '18':
            switch(row.id){
              case '1':
                result = '复用光纤通道一测试';
                break;
              case '2':
                result = '复用光纤通道二测试';
                break;
              case '3':
                result = '专用光纤通道一测试';
                break;
              case '4':
                result = '专用光纤通道二测试';
                break;
              case '5':
                result = '软件版本检查';
                break;
              case '6':
                result = '逆变电源检验';
                break;
              case '7':
                result = '发光功率和接收功率测试';
                break;
              case '8':
                result = '自环测试';
                break;
              case '9':
                result = '开入检查';
                break;
              case '10':
                result = '开出量检查';
                break;
              case '11':
                result = '通道传输延时及展宽测试';
                break;
            }
            break;
          case '19':
            switch(row.id){
              case '1':
                result = '开关汇控柜端子紧固';
                break;
              case '2':
                result = 'CT端子箱端子紧固';
                break;
              case '3':
                result = 'PT端子箱、接口屏端子紧固';
                break;
              case '4':
                result = '保护屏内端子紧固(包括背板端子)';
                break;
            }
            break;
        }
        return result;
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
