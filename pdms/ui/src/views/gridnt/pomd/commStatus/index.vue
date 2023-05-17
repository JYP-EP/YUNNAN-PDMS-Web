<template>
  <el-container style="height: 100%;">
    <el-aside style="background-color: rgb(6, 16, 41);; padding: 0px; margin-right: 10px; height: 100%" width="350px">
      <substation-ied-tree ref="substationTree" @treeSelected="selectedIed"/>
    </el-aside>
    <el-container style="background-color: rgb(3, 13, 40);">
      <el-main style="padding: 5px; background-color: transparent;">
        <div class="app-container" style="height: 100%;">
          <comm-status ref="commStatus"></comm-status>
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

//引入新的组件
import commStatus from "@/views/gridnt/pomd/commStatus/comminfo/commStatus.vue";


export default {
  name: 'intelAlert',
  components: {
    CrcTable, CircuitDiagram, SubstationIedTree, PomdMenu, PhysicsDiagram,
    StatusInfoDiagram, Opticalfiber, Logic,


    //原intelAlert\Index.vue 引用
    //注册新组件
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
      iedActiveName: 'showStatus',
      sendNode: {},
      //svgTabs:[]
      alertType: 0,
      queryDateRange: '',
      showAlert:true,
      showStatus:false,
    };
  },
  methods: {
    selectedIed(node) {
      this.$nextTick(() => {
        this.$refs.commStatus.showSubComm(node);

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
  },
  created() {
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
