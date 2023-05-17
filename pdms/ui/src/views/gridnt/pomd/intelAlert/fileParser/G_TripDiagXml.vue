<template>
  <div>
    <el-button :class="{missing: isMissing}"
               type="text"
               @click.native.prevent="showDetail(alertItem)">
      {{ statusParser(alertItem) }}
    </el-button>
    <el-dialog  title="跳合闸返校诊断" :visible.sync="fileDialogVisible">
            <div v-if="fileDetails.warning.length > 0" v-for="(item,indexwarning) in fileDetails.warning">
              <div v-if="item.ied.length > 0" v-for="(itemied,indexiedg) in item.ied">
                <div class="el-divcenter">告警时间:{{item.time}}</div>
                <div>
                  <el-row class="el-listname">
                    <el-col :span="1" ><div>序号</div></el-col>
                    <el-col :span="3" ><div>装置名称</div></el-col>
                    <el-col :span="3" ><div>装置描述</div></el-col>
                    <el-col :span="4" ><div>参引</div></el-col>
                    <el-col :span="5" ><div>描述</div></el-col>
                    <el-col :span="1" ><div>值</div></el-col>
                    <el-col :span="3" ><div>时间</div></el-col>
                    <el-col :span="4" ><div>品质</div></el-col>
                  </el-row>
                  <el-row class="el-row-inline" v-if="itemied.point.length>0" v-for="(itempoint,indexpoint) in itemied.point">
                    <el-col :span="1" ><div>{{indexpoint+1}}</div></el-col>
                    <el-col :span="3" ><div>{{itemied.name}}</div></el-col>
                    <el-col :span="3" ><div>{{itemied.desc}}</div></el-col>
                    <el-col :span="4" ><div>{{itempoint.path}}</div></el-col>
                    <el-col :span="5" ><div>{{itempoint.desc}}</div></el-col>
                    <el-col :span="1" ><div>{{itempoint.value}}</div></el-col>
                    <el-col :span="3" ><div>{{itempoint.time}}</div></el-col>
                    <el-col :span="4" ><div>{{itempoint.quality}}</div></el-col>
                  </el-row>
                </div>
              </div>
              <div style="height: 80px;margin-bottom: 7px;">
                <div class="w-50">
                  <div class="alarm_warning">录波文件名:</div>
                  <div class="alarm_warning_div">{{item.faultFile.comtrade}}</div>
                </div>
                <div class="w-50">
                  <div class="alarm_warning">中间节点文件名:</div>
                  <div class="alarm_warning_div">{{item.faultFile.middle}}</div>
                </div>
                <div class="w-50">
                  <div class="alarm_warning">结论:</div>
                  <div class="alarm_warning_div">{{item.result}}</div>
                </div>
                <div class="w-50" v-if="item.suggestion!=null">
                  <div class="alarm_warning">结论描述:</div>
                  <div class="alarm_warning_div">{{item.suggestion}}</div>
                </div>
              </div>
            </div>
    </el-dialog>
  </div>
</template>
<script>
import {getTripDiagReportDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "G_TripDiagXml",
  props: ['alertItem'],
  data() {
    return {
      isMissing: false,
      fileDialogVisible: false,
      fileHead: '跳合闸返校诊断',
      fileDetails:[],
      nowalertType:0,
    };
  },
  methods: {
    statusParser(row) {
      this.isMissing = row.status !== 1;
      return row.status === 1 ? "查看" : "召唤文件失败";
    },
    policyParser(row) {
      let result = '';
      row.policy.forEach((item) => {
        result += item.desc + ":" + item.value + "s\r\n";
      });
      return result;
    },
    showDetail(row) {
      if (row.status !== 1) {
        return;
      }

      this.fileHead = '';
      this.fileDetails = [];
      getTripDiagReportDetail({fileId: row.fileId, type: row.alertType}).then(resp => {

        //this.fileHead = resp.data.desc;
        this.fileDetails = resp.data.queryTime;
        this.fileDialogVisible = true;
      });
    },
    Gettype(ref){
      // console.log(ref);
      switch (ref)
      {
        case "1":
          return "模拟量通道";
          break;
        case "2":
          return "开关量通道";
          break;
      }
    },
    GetcompareTemplate(ref,path){
      if(ref.points.length>0)
      {
        return  ref.points.find(rc=>rc.path == path).value;
      }
      else
      {
        return "-";
      }
    },
  }
}
</script>

<style lang="scss" scoped>
.el-row-inline {
  display: flex;
  flex-wrap: wrap;
}

.el-card {
  min-width: 100%;
  height: 100%;
}
/* el-descriptions-item 文字居中 */
.el-descriptions__body .el-descriptions__table .el-descriptions-item__cell {
  text-align: center;
}

.el-divcenter
{
  width: 100%;
  color: white;
  text-align: center;
  //padding: 3px 0px 3px 0px;
  background: #1682e6;
  font-size: 13px;
}
.el-dialog__header {
  padding: 20px;
  padding-bottom: 0px;
}


.el-row{
  /*border: 1px solid #dddddd;align="middle"*/
  font-size: 12px;
  color: #FFFFFF;

}
.el-col{
  border: 1px solid #394d63;
  padding: 3px 0px 3px 0px;

  display: flex;
  justify-content: center;
  align-items: center;
}
/*.el-col div{*/
/*  //align-self: center;*/
/*  padding:3px;*/
/*}*/
.el-listname{
  font-size: 14px;
  color: #c0c4cc;

}
.el-col-center{
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid #394d63;
}
.el-col-dor{
  border: 1px solid #394d63;
}
.el-row-inline .el-col div{
  padding: 3px;
}

.alarm_warning{
  padding: 10px 0px 10px 0px;
  text-align: right;
  font-size: 14px;
  width: 20%;
  float: left;
  color: white;
}
.alarm_warning_div{
  padding: 10px 0px 10px 0px;
  font-size: 14px;
  float: left;
  padding-left: 10px;
  width: 70%;
  color: #39F3FC;
  text-align: left;
}
.w-50{
  width: 50%;
  display:flex;
  float: left;
}
</style>
