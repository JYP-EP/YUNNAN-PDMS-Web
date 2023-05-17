<template>
  <el-container style="height: 100%;">
    <el-aside style="background-color: rgb(6, 16, 41);; padding: 0px; margin-right: 10px; height: 100%" width="350px">
      <substation-ied-tree ref="substationTree" @treeSelected="selectedIed"/>
    </el-aside>
    <el-container style="background-color: rgb(3, 13, 40);">
      <el-main style="padding: 5px; background-color: transparent;">
        <el-container class="tm_padding">
          <div class="block">
            <el-date-picker
              v-model="queryDateRange"
              :picker-options="pickerOptions"
              align="right"
              end-placeholder="结束日期"
              range-separator="至"
              start-placeholder="开始日期"
              type="daterange"
              unlink-panels>
            </el-date-picker>
            <el-button class="bt_margin" @click="Queryalert">查询</el-button>
          </div>
        </el-container>
        <div class="app-container" style="height: 100%;">
          <el-tabs style="height: 100%;" @tab-click="setalertType">
            <el-tab-pane label="智能告警总览" style="height: 100%;" name="0">

            </el-tab-pane>
            <el-tab-pane label="二次回路故障诊断" style="height: 100%;" name="101">

            </el-tab-pane>
            <el-tab-pane label="状态监测预警" style="height: 100%;" name="102">

            </el-tab-pane>
            <el-tab-pane label="同源数据比对" style="height: 100%;" name="103">

            </el-tab-pane>
            <el-tab-pane label="定值和版本校核" style="height: 100%;"name="104">

            </el-tab-pane>
            <el-tab-pane label="装置异常告警定位" style="height: 100%;" name="105">

            </el-tab-pane>
            <el-tab-pane label="跳合闸返校诊断" style="height: 100%;" name="106">

            </el-tab-pane>
            <el-tab-pane label="保护闭锁告警信息" style="height: 100%;" name="107">

            </el-tab-pane>
            <genericAlert ref="genericalert"  v-show="showAlert"></genericAlert>


<!--            <el-tab-pane label="通信状态" style="height: 100%;" name="comm-status">-->
<!--              <comm-status  v-show="showStatus"></comm-status>-->
<!--            </el-tab-pane>-->
          </el-tabs>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import PomdMenu from '@/views/gridnt/pomd/menu/index.vue';
import SubstationIedTree from '@/views/gridnt/pomd/common/substationIedTree.vue';
import CircuitDiagram from '@/views/gridnt/pomd/diagrams/circuit/index.vue';
import PhysicsDiagram from '@/views/gridnt/pomd/diagrams/physics/index.vue';
import StatusInfoDiagram from '@/views/gridnt/pomd/diagrams/statusinfo/index.vue';
import CrcTable from '@/views/gridnt/pomd/substation/crc/index.vue';
import Opticalfiber from '@/views/gridnt/pomd/substation/opticalfiber.vue'
import Logic from '@/views/gridnt/pomd/substation/logic.vue'
//import * as iedJs from '@/api/gridnt/ied'


//原intelAlert\Index.vue 引用
import genericAlert from "@/views/gridnt/pomd/intelAlert/alertType/genericAlert.vue";
import frequentAlert from "@/views/gridnt/pomd/intelAlert/alertType/frequentAlert.vue";
import protectAlert from "@/views/gridnt/pomd/intelAlert/alertType/protectAlert.vue";
import blockingAlert from "@/views/gridnt/pomd/intelAlert/alertType/blockingAlert.vue";
import {getSubstation} from "@/api/gridnt/intelAlert";
import InspectionAlert from "@/views/gridnt/pomd/intelAlert/alertType/inspectionAlert.vue";
import CheckAlert from "@/views/gridnt/pomd/intelAlert/alertType/checkAlert.vue";
import StatAlert from "@/views/gridnt/pomd/intelAlert/alertType/statAlert.vue";


//引入新的组件
import mainSubstation from "@/views/gridnt/pomd/intelAlert/alertType/mainSubstation.vue";
import outsideAnalysis from "@/views/gridnt/pomd/intelAlert/alertType/outsideAnalysis.vue";
import commStatus from "@/views/gridnt/pomd/intelAlert/alertType/commStatus.vue";

import G_SecCircuit from "@/views/gridnt/pomd/intelAlert/fileParser/G_SecCircuit.vue";
import G_SettingValueAlert from "@/views/gridnt/pomd/intelAlert/fileParser/G_SettingValueAlert.vue";
import swagger from "@/views/tool/swagger/index.vue";

export default {

  name: 'intelAlert',
  components: {
    CrcTable, CircuitDiagram, SubstationIedTree, PomdMenu, PhysicsDiagram,
    StatusInfoDiagram, Opticalfiber, Logic,


    //原intelAlert\Index.vue 引用
    G_SettingValueAlert,
    G_SecCircuit,
    StatAlert,
    CheckAlert,
    InspectionAlert,
    genericAlert,
    frequentAlert,
    protectAlert,
    blockingAlert,

    //注册新组件
    outsideAnalysis,
    mainSubstation,
    commStatus
  },
  data() {
    return {
      activeName: '0',
      activeBayName: 'opticalfiber',
      substation: {},
      ied: null,
      bay: null,
      node: {},
      //svgPath: '',
      iedActiveName: 'genericAlert',
      sendNode: {},
      //svgTabs:[]
      alertType: 0,
      queryDateRange: '',
      showAlert:true,
      showStatus:false,
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
    };
  },
  methods: {

    selectedIed(node) {
      this.$nextTick(() => {
        this.$refs.genericalert.Selectdata = node;
        this.$refs.genericalert.queryParams.entity.alertType = this.alertType;
        this.$refs.genericalert.queryDateRange = this.queryDateRange;
        this.runSelect();
      });
    },
    Queryalert() {
      this.$refs.genericalert.queryDateRange = this.queryDateRange;
      this.runSelect();
    },
    setalertType(tab, event) {
      this.$nextTick(() => {
        if (tab.name != "comm-status") {
          this.showAlert = true;
          this.showStatus = false;
          this.alertType = tab.name;
          this.$refs.genericalert.queryParams.entity.alertType = tab.name;
          this.runSelect();
        }
        else
        {
          this.showStatus = true;
          this.showAlert = false;
        }
      });
    },
    runSelect()
    {
      this.$nextTick(() => {
        this.$refs.genericalert.SelectAlerts();
      });
    },
    /*showIedContent(tab){
      this.$nextTick(() => {
        this.$refs[tab.name].loadData(this.ied);
      });
    },
    showBayContent(tab){
      this.$nextTick(() => {
        this.$refs[tab.name].getList(this.node)
      });
    },*/
  },
  created() {
    //console.log(this.$route.query.code)
    // this.$nextTick(() => {
    //   this.$refs.substationTree.setTree(this.$route.query.code);
    // });


    const end = new Date();
    const start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
    this.queryDateRange = [start, end];

    // getSubstation().then((resp) => {
    //   this.divisionOptions = resp.data;
    //   //console.log(JSON.stringify(resp.data));
    // });
  }
};
</script>
<style lang="scss" scoped>


.bt_margin
{
  margin-left: 15px;
}
.tagWrapper {
  height: 40px;

  .el-tag {
    margin: 0px 15px;
    cursor: pointer;
  }
}

.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

.active, .inactive {
  cursor: pointer;
}

.active {
  color: #409eff;
  border: 1px solid #d9ecff;
}

.tm_padding {
  padding: 20px 21px 10px;
}

</style>
