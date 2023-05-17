<template>
  <div>
    <el-button :class="{missing: isMissing}"
               type="text"
               @click.native.prevent="showDetail(alertItem)">
      {{ statusParser(alertItem) }}
    </el-button>
    <el-dialog  :title="fileHead" :visible.sync="fileDialogVisible">
      <div v-if="fileDetails.warning.length>0" v-for="(item,index) in fileDetails.warning">
        <div class="el-divcenter">告警时间:{{item.time}}</div>
        <el-row>
          <el-col :span="24" class="el-listname"><div>告警信息点</div></el-col>
            <!--IED节点-->
<!--          <div class="el-listname">-->
<!--            <el-col :span="12"><div>装置名称</div></el-col>-->
<!--            <el-col :span="12"><div>装置描述</div></el-col>-->
<!--          </div>-->
<!--           &lt;!&ndash;IED列表&ndash;&gt;-->
<!--            <div v-if="item.ied.length>0" v-for="(itemied,index) in item.ied">-->
<!--              <el-col :span="12"><div>{{ itemied.name }}</div></el-col>-->
<!--              <el-col :span="12"><div>{{ itemied.desc }}</div></el-col>-->
<!--            </div>-->

<!--          Point-->
          <div class="el-listname">
            <el-col :span="2" ><div>装置名称</div></el-col>
            <el-col :span="8"><div>参引</div></el-col>
            <el-col :span="5"><div>描述</div></el-col>
            <el-col :span="2"><div>值</div></el-col>
            <el-col :span="4"><div>变位时间</div></el-col>
            <el-col :span="3"><div>品质信息</div></el-col>
          </div>
<!--          >=1-->
          <div v-if="item.ied.length>0" v-for="(itemied,index) in item.ied">
            <div v-if="itemied.point.length>0" v-for="(itempoint,index1) in itemied.point">
              <el-col :span="2"><div>{{ itemied.name }}</div></el-col>
              <el-col :span="8"><div>{{itempoint.path}}</div></el-col>
              <el-col :span="5"><div>{{ itempoint.desc }}</div></el-col>
              <el-col :span="2"><div>{{ itempoint.value }}</div></el-col>
              <el-col :span="4"><div>{{itempoint.time}}</div></el-col>
              <el-col :span="3"><div>{{itempoint.quality}}</div></el-col>
            </div>
          </div>

          <!--Locations节点-->
          <el-col :span="24"><div>故障定位信息（{{Getlocation(item.locations)}}）</div></el-col>
          <!--Ied装置故障信息-->
          <div v-if="item.locations.ied.length>0 && item.locations.slot.length==0 && item.locations.port.length==0 && item.locations.cable.length==0">
            <div class="el-listname">
              <el-col :span="4"><div>故障装置名称</div></el-col>
              <el-col :span="4"><div>故障装置描述</div></el-col>
              <el-col :span="8"><div>故障影响</div></el-col>
              <el-col :span="8"><div>处理建议</div></el-col>
            </div>
            <div  v-for="(itemied,index) in item.locations.ied">
              <el-col :span="4"><div>{{itemied.iedName}}</div></el-col>
              <el-col :span="4"><div>{{itemied.iedDesc}}</div></el-col>
              <el-col :span="8"><div>{{Geteffect(item.effects,itemied.resultId)}}</div></el-col>
              <el-col :span="8"><div>{{Getsuggestion(item.suggestions,itemied.resultId)}}</div></el-col>
            </div>
          </div>

          <!--Slot板卡故障信息-->
          <div v-if="item.locations.slot.length>0 && item.locations.port.length==0 && item.locations.cable.length==0">
            <div class="el-listname">
              <el-col :span="1"><div>板卡编号</div></el-col>
              <el-col :span="3"><div>所属装置</div></el-col>
              <el-col :span="3"><div>装置描述</div></el-col>
              <el-col :span="7"><div>故障影响</div></el-col>
              <el-col :span="8"><div>处理建议</div></el-col>
              <el-col :span="2"><div>优先级</div></el-col>
            </div>

            <div v-for="(itemslot,index) in item.locations.slot">
              <el-col :span="1"><div>{{itemslot.id}}</div></el-col>
              <el-col :span="3"><div>{{itemslot.iedName}}</div></el-col>
              <el-col :span="3"><div>{{itemslot.iedDesc}}</div></el-col>
              <el-col :span="7"><div>{{Geteffect(item.effects,itemslot.resultId)}}</div></el-col>
              <el-col :span="8"><div>{{Getsuggestion(item.suggestions,itemslot.resultId)}}</div></el-col>
              <el-col :span="2"><div>{{itemslot.priority}}</div></el-col>
            </div>
          </div>

          <!--Port端口故障信息-->
          <div v-if="item.locations.port.length>0 && item.locations.cable.length==0">
            <div class="el-listname">
              <el-col :span="2"><div>端口编号</div></el-col>
              <el-col :span="2"><div>端口类型</div></el-col>
              <el-col :span="3"><div>所属装置</div></el-col>
              <el-col :span="3"><div>装置描述</div></el-col>
              <el-col :span="6"><div>故障影响</div></el-col>
              <el-col :span="6"><div>处理建议</div></el-col>
              <el-col :span="2"><div>优先级</div></el-col>
            </div>
            <div v-for="(itemport,index) in item.locations.port">
              <el-col :span="2"><div>{{itemport.id}}</div></el-col>
              <el-col :span="2"><div>{{itemport.plug}}</div></el-col>
              <el-col :span="3"><div>{{itemport.iedName}}</div></el-col>
              <el-col :span="3"><div>{{itemport.iedDesc}}</div></el-col>
              <el-col :span="6"><div>{{Geteffect(item.effects,itemport.resultId)}}</div></el-col>
              <el-col :span="6"><div>{{Getsuggestion(item.suggestions,itemport.resultId)}}</div></el-col>
              <el-col :span="2"><div>{{itemport.priority}}</div></el-col>
            </div>
          </div>

          <!--Cable光纤故障信息-->
          <div v-if="item.locations.cable.length>0">
            <div class="el-listname">
              <el-col :span="2"><div>光缆编号</div></el-col>
              <el-col :span="2"><div>起始装置</div></el-col>
              <el-col :span="2"><div>终止装置</div></el-col>
              <el-col :span="2"><div>起始装置描述</div></el-col>
              <el-col :span="2"><div>终止装置描述</div></el-col>
              <el-col :span="2"><div>光纤长度</div></el-col>
              <el-col :span="6"><div>故障影响</div></el-col>
              <el-col :span="4"><div>处理建议</div></el-col>
              <el-col :span="2"><div>优先级</div></el-col>
            </div>
            <div v-for="(itemcable,index) in item.locations.cable">
              <el-col :span="2"><div>{{itemcable.id}}</div></el-col>
              <el-col :span="2"><div>{{itemcable.startIedName}}</div></el-col>
              <el-col :span="2"><div>{{itemcable.endIedName}}</div></el-col>
              <el-col :span="2"><div>{{itemcable.startIedDesc}}</div></el-col>
              <el-col :span="2"><div>{{itemcable.endIedDesc}}</div></el-col>
              <el-col :span="2"><div>{{itemcable.cableLen}}</div></el-col>
              <el-col :span="6"><div>{{Geteffect(item.effects,itemcable.resultId)}}</div></el-col>
              <el-col :span="4"><div>{{Getsuggestion(item.suggestions,itemcable.resultId)}}</div></el-col>
              <el-col :span="2"><div>{{itemcable.priority}}</div></el-col>
            </div>
          </div>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template>


<script>
import {getSecCircuitFaultReportDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "G_SecCircuitFaultDiagXml",
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
      let alertType = 101;
      if(row.alertType == 108)
      {
        this.nowalertType = row.alertType;
      }
      else
      {
        this.nowalertType = 0;
      }
      getSecCircuitFaultReportDetail({fileId: row.fileId, type: alertType}).then(resp => {
        this.fileHead = resp.data.desc;
        this.fileDetails = resp.data.queryTime;
        this.fileDialogVisible = true;
      });
    },
    Geteffect(ref,resultId)
    {
      if(ref.effect.length > 0)
      {
        return ref.effect.find(r=>r.resultId == resultId).desc;
      }
      else
      {
        return "无影响";
      }
    },
    Getsuggestion(ref,resultId)
    {
      if(ref.suggestion.length > 0)
      {
        return ref.suggestion.find(r=>r.resultId == resultId).desc;
      }
      else
      {
        return "无建议";
      }
    },
    Getlocation(ref){
      if(ref.cable.length>0)
      {
        return "光纤信息";
      }
      else if (ref.port.length>0&&ref.cable.length==0)
      {
        return "端口信息";
      }
      else if (ref.slot.length>0 &&ref.port.length==0&&ref.cable.length==0)
      {
        return "板卡信息";
      }
      else if (ref.ied.length>0 &&ref.slot.length==0 &&ref.port.length==0&&ref.cable.length==0)
      {
        return "装置信息";
      }
      else
      {
        return "装置信息";
      }
    }
  }
}
</script>

<style scoped>

</style>
<style lang="scss" scoped>
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
.el-dialog {
  border: 1px solid #239aee;
  background-color: #0c3764;

  width: 100vw;
  min-width: 1500px;
  margin-bottom: 50px;
}

.el-row{
  //border: 1px solid #dddddd;
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
.el-listname{
  font-size: 14px;
  color: #c0c4cc;
}


.el-row-inline {
  display: flex;
  flex-wrap: wrap;
}

.el-card {
  min-width: 100%;
  height: 100%;
}

</style>
