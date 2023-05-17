<template>
  <div>
    <el-button :class="{missing: isMissing}"
               type="text"
               @click.native.prevent="showDetail(alertItem)">
      {{ statusParser(alertItem) }}
    </el-button>
    <el-dialog  :title="fileHead" :visible.sync="fileDialogVisible">
      <div v-if="fileDetails.warning.length > 0" v-for="(item,indexwarning) in fileDetails.warning">
        <div v-if="item.group.length > 0" v-for="(itemgroup,indexgroup) in item.group">
          <div class="el-divcenter">告警时间:{{item.time}}</div>
          <div>
            <el-row class="el-listname">
              <el-col :span="8" ><div>录波通道类型</div></el-col>
              <el-col :span="8" ><div>采样时刻</div></el-col>
              <el-col :span="8" ><div>比对结果</div></el-col>
            </el-row>
            <el-row class="el-row-inline">
              <el-col :span="8" ><div>{{Gettype(itemgroup.type)}}</div></el-col>
              <el-col :span="8" ><div>{{itemgroup.time}}</div></el-col>
              <el-col :span="8" ><div>{{itemgroup.result.desc}}</div></el-col>
            </el-row>

            <el-row class="el-listname">
              <el-col :span="1" ><div>序号</div></el-col>
              <el-col :span="5" ><div>参引</div></el-col>
              <el-col :span="5" ><div>描述</div></el-col>
              <el-col :span="5" ><div>保护装置</div></el-col>
              <el-col :span="4" ><div>值</div></el-col>
              <el-col :span="4" ><div>角度</div></el-col>
            </el-row>
            <el-row class="el-row-inline" v-if="itemgroup.channel.length>0" v-for="(itemchannel,indexchannel) in itemgroup.channel">
              <el-col :span="1" ><div>{{indexchannel+1}}</div></el-col>
              <el-col :span="5" ><div>{{itemchannel.path}}</div></el-col>
              <el-col :span="5" ><div>{{itemchannel.desc}}</div></el-col>
              <el-col :span="5" ><div>{{itemchannel.iedName}}</div></el-col>
              <el-col :span="4" ><div>{{itemchannel.data.value}}{{itemchannel.data.unit}}</div></el-col>
              <el-col :span="4" ><div>{{itemchannel.data.ang}}</div></el-col>

            </el-row>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {getSamCompReportDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "G_SamCompXml",
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

      this.fileHead = '';
      this.fileDetails = [];
      getSamCompReportDetail({fileId: row.fileId, type: row.alertType}).then(resp => {

        this.fileHead = resp.data.desc;
        this.fileDetails = resp.data.queryTime;
        console.log(this.fileDetails);
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
</style>
