<template>
  <div>
    <el-button :class="{missing: isMissing}"
               type="text"
               @click.native.prevent="showDetail(alertItem)">
      {{ statusParser(alertItem) }}
    </el-button>
    <el-dialog  :title="fileHead" :visible.sync="fileDialogVisible">
            <div v-if="fileDetails.ied.length > 0" v-for="(item,indexied) in fileDetails.ied">
              <div v-if="item.warning.length > 0" v-for="(itemwarning,indexwarning) in item.warning">
                <div class="el-divcenter">{{Getsubtype(itemwarning.subType)}}</div>
                 <div>
                  <el-row class="el-listname">
                    <el-col :span="12" ><div>装置</div></el-col>
                    <el-col :span="12" ><div>结论</div></el-col>
                  </el-row>
                  <el-row class="el-row-inline">
                    <el-col :span="12" ><div>{{ item.name }}:{{item.desc}}</div></el-col>
                    <el-col :span="12" ><div>{{itemwarning.result}}</div></el-col>
                  </el-row>

                  <el-row class="el-listname">
                    <el-col :span="1" ><div>序号</div></el-col>
                    <el-col :span="5" ><div>告警时间</div></el-col>
                    <el-col :span="5" ><div>参引</div></el-col>
                    <el-col :span="5" ><div>描述</div></el-col>
                    <div v-if="itemwarning.subType ==1">
                    <el-col :span="4" ><div>值</div></el-col>
                    <el-col :span="4" ><div>基准值</div></el-col>
                    </div>
                    <div v-else-if="itemwarning.subType ==2">
                      <el-col :span="4" ><div>装置版本</div></el-col>
                      <el-col :span="4" ><div>基准版本</div></el-col>
                    </div>
                    <div v-else-if="itemwarning.subType ==3">
                    <el-col :span="4" ><div>装置CRC</div></el-col>
                    <el-col :span="4" ><div>基准CRC</div></el-col>
                    </div>
                  </el-row>
                  <el-row class="el-row-inline" v-if="itemwarning.points.length>0" v-for="(itempoints,indexpoints) in itemwarning.points">
                    <el-col :span="1" ><div>{{indexpoints+1}}</div></el-col>
                    <el-col :span="5" ><div>{{itemwarning.time}}</div></el-col>
                    <el-col :span="5" ><div>{{itempoints.path}}</div></el-col>
                    <el-col :span="5" ><div>{{itempoints.desc}}</div></el-col>
                    <el-col :span="4" ><div>{{itempoints.value}}</div></el-col>
                    <el-col :span="4" v-if="itemwarning.subType ==1"><div>{{GetcompareTemplate(itemwarning.compareTemplate,itempoints.path)}}</div></el-col>
                    <el-col :span="4" v-if="itemwarning.subType !=1"><div>{{itemwarning.compareTemplate.value}}</div></el-col>
                  </el-row>
                   <el-row class="el-row-inline" v-if="itemwarning.points==null && nowalertType!=0" >
                     <el-col :span="1" ><div>{{indexwarning+1}}</div></el-col>
                     <el-col :span="5" ><div>{{itemwarning.time}}</div></el-col>
                     <el-col :span="5" ><div>-</div></el-col>
                     <el-col :span="5" ><div>-</div></el-col>
                     <el-col :span="4" ><div>-</div></el-col>
                     <el-col :span="4" ><div>-</div></el-col>
                   </el-row>
                 </div>
              </div>
            </div>
    </el-dialog>
  </div>
</template>
<script>
import {getSettingReportDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "G_SettingXml",
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
      let alertType = 104;
      if(row.alertType == 109)
      {
        this.nowalertType = row.alertType;
      }
      else
      {
        this.nowalertType = 0;
      }

      this.fileHead = '';
      this.fileDetails = [];
      getSettingReportDetail({fileId: row.fileId, type: alertType}).then(resp => {

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
</style>
