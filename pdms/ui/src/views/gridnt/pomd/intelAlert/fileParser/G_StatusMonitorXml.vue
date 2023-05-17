<template>
  <div>
    <el-button :class="{missing: isMissing}"
               type="text"
               @click.native.prevent="showDetail(alertItem)">
      {{ statusParser(alertItem) }}
    </el-button>
    <el-dialog  :title="fileHead" :visible.sync="fileDialogVisible">
      <div v-if="fileDetails.ied.length > 0" v-for="(item,index) in fileDetails.ied">
        <div v-if="item.warning.length > 0" v-for="(itemwarning,index) in item.warning">
          <div class="el-divcenter">{{Getsubtype(itemwarning.subtype)}}</div>
          <el-row class="el-listname"  >
            <el-col :span="1" ><div>序号</div></el-col>
            <el-col :span="2" ><div>装置</div></el-col>
            <el-col :span="2" ><div>告警时间</div></el-col>
            <el-col :span="2" ><div>参引</div></el-col>
            <el-col :span="2" ><div>描述</div></el-col>
            <el-col :span="2"><div>上限</div></el-col>
            <el-col :span="2" ><div>下限</div></el-col>
            <el-col :span="2" ><div>值</div></el-col>
            <el-col :span="2" ><div>数据时间</div></el-col>
            <el-col :span="2" ><div>品质信息</div></el-col>
            <el-col :span="2" ><div>诊断规则</div></el-col>
            <el-col :span="3" ><div>结论</div></el-col>
          </el-row>

          <div v-if="itemwarning.point.length>0" v-for="(itempoint,indexpoint) in itemwarning.point">
            <el-row class="el-row-inline" v-if="itempoint.datagrp.data.length>0" v-for="(itemdata,indexdata) in itempoint.datagrp.data">
              <el-col :span="1"><div>{{indexdata+1}}</div></el-col>
              <el-col :span="2"><div>{{ item.name }}:{{item.desc}}</div></el-col>
              <el-col :span="2"><div>{{itemwarning.time}}</div></el-col>
              <el-col :span="2"><div>{{itempoint.path}}</div></el-col>
              <el-col :span="2"><div>{{itempoint.desc}}</div></el-col>
              <el-col :span="2"><div>{{itempoint.highlimit.value}}</div></el-col>
              <el-col :span="2"><div>{{itempoint.lowlimit.value}}</div></el-col>
              <el-col :span="2"><div>{{itemdata.value}}</div></el-col>
              <el-col :span="2"><div>{{itemdata.datetime}}</div></el-col>
              <el-col :span="2"><div>{{itemdata.quality}}</div></el-col>
              <el-col :span="2"><div>{{Getpolicy(itemwarning.policy)}}</div></el-col>
              <el-col :span="3"><div>{{itemwarning.result}}</div></el-col>
            </el-row>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getStatusMonitorReportDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "G_StatusMonitorXml",
  props: ['alertItem'],
  data() {
    return {
      isMissing: false,
      fileDialogVisible: false,
      fileHead: '',
      fileDetails:[],
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
      getStatusMonitorReportDetail({fileId: row.fileId, type: row.alertType}).then(resp => {
        this.fileHead = resp.data.desc;
        this.fileDetails = resp.data.queryTime;
        this.fileDialogVisible = true;
      });
    },
    Getsubtype(ref){
      // console.log(ref);
      switch (ref)
      {
        case "1":
          return "越限预警";
          break;
        case "2":
          return "同期比对";
          break;
        case "3":
          return "变化趋势";
          break;
        case "4":
          return "突变预警";
          break;
      }
    },
    Getpolicy(ref){
        if(ref.condition.length>0)
        {
          return  "在"+ref.condition.find(rc=>rc.desc == "时间周期").condition+"s内模拟量告警次数大于"+ref.condition.find(rc=>rc.desc == "模拟量告警次数").condition+"次则产生告警,在"+ref.condition.find(rc=>rc.desc == "持续时间").condition+"s内只报告一次";
        }
        else
        {
          return "诊断规则未加载";
        }

    },
  }
}
</script>

<style lang="scss">
.el-dialog {
  border: 1px solid #239aee;
  background-color: #0c3764;

  width: 100vw;
  min-width: 1500px;
  margin-bottom: 20px;
}
</style>
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
  padding: 3px 0px 3px 0px;
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
</style>
