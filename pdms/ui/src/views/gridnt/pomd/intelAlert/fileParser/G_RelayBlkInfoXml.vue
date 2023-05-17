<template>
  <div>
    <el-button :class="{missing: isMissing}"
               type="text"
               @click.native.prevent="showDetail(alertItem)">
      {{ statusParser(alertItem) }}
    </el-button>
    <el-dialog  :title="fileHead" :visible.sync="fileDialogVisible">
      <div class="alarm_time">起始时间:<span style="color: #39F3FC">{{fileDetails.start}}</span></div>
      <div class="alarm_time">结束时间:<span style="color: #39F3FC">{{fileDetails.end}}</span></div>
      <div v-if="fileDetails.warning.length > 0" v-for="(item,indexwarning) in fileDetails.warning">

        <div >
          <div v-if="item.type=='RelayBlk'" class="alarm_time">告警时间:<span style="color: #39F3FC">{{item.time}}</span></div>
          <div v-if="item.type=='RelayBlk'" class="el-divcenter">闭锁告警信号</div>
          <div v-if="item.type=='EventInfo'" class="el-divcenter">变位事件信息</div>
          <el-row class="el-listname">
            <el-col :span="1" ><div>序号</div></el-col>
            <el-col :span="4" ><div>装置名称</div></el-col>
            <el-col :span="4" ><div>装置描述</div></el-col>
            <el-col :span="5" ><div>参引</div></el-col>
            <el-col :span="5" ><div>描述</div></el-col>
            <el-col :span="4" ><div>告警时间</div></el-col>
            <el-col :span="1" ><div>值</div></el-col>
          </el-row>
          <div v-if="item.ied.length>0" v-for="(itemied,indexied) in item.ied">
            <el-row class="el-row-inline" v-if="itemied.point.length>0" v-for="(itempoint,indexpoint) in itemied.point">
              <el-col :span="1" ><div>{{indexpoint+1}}</div></el-col>
              <el-col :span="4" ><div>{{itemied.name}}</div></el-col>
              <el-col :span="4" ><div>{{itemied.desc}}</div></el-col>
              <el-col :span="5" ><div>{{itempoint.path}}</div></el-col>
              <el-col :span="5" ><div>{{itempoint.desc}}</div></el-col>
              <el-col :span="4" ><div>{{itempoint.time}}</div></el-col>
              <el-col :span="1" ><div>{{itempoint.value}}</div></el-col>
            </el-row>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {getRelayBlkInfoReportDetail} from "@/api/gridnt/intelAlert";
export default {
  name: "G_RelayBlkInfoXml",
  props: ['alertItem'],
  data() {
    return {
      isMissing: false,
      fileDialogVisible: false,
      fileHead: '',
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

      let alertType = 107;
      if(row.alertType == 110)
      {
        this.nowalertType = row.alertType;
      }
      else
      {
        this.nowalertType = 0;
      }

      this.fileHead = '';
      this.fileDetails = [];
      getRelayBlkInfoReportDetail({fileId: row.fileId, type: alertType}).then(resp => {
        this.fileHead = resp.data.desc;
        this.fileDetails = resp.data.queryTime;
        this.fileDialogVisible = true;
      });
    },
    Getlevel(ref){
      // console.log(ref);
      switch (ref)
      {
        case "1":
          return "定值比对告警";
          break;
        case "2":
          return "版本检核告警";
          break;
        case "3":
          return "CRC不一致";
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
.alarm_time{
  text-align: left;
  color: white;
  padding-bottom: 5px;
}
</style>


