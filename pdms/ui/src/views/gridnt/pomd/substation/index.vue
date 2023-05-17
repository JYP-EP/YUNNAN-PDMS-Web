<template>
  <el-container style="height: 100%;">
    <el-aside width="350px" style="background-color: #061029; padding: 0px; margin-right: 10px; height: 100%">
      <substation-ied-tree @treeSelected="selectedIed" ref="substationTree"  />
    </el-aside>
    <el-container style="background-color: #030d28;">
      <el-main style="padding: 5px; background-color: transparent;">
        <subs-status-list v-show="node.type == 'D'" ref="subsStatusList"></subs-status-list>
        <el-tabs v-model="activeName" @tab-click="showContent" v-show="node.type == 'S'">
          <el-tab-pane v-for="(item ,index) in svgTabs" :label="item['tabName']" :name="'tab' + index" lazy>
          </el-tab-pane>
        </el-tabs>
        <!-- IED  -->
        <el-tabs v-model="iedActiveName" v-show="node.type == 'I'"  @tab-click="showIedContent">
          <el-tab-pane label="虚回路图" name="circuit" >
            <circuit-diagram ref="circuit" :node-type="node.type" :active-tab="iedActiveName"></circuit-diagram>
          </el-tab-pane>
          <el-tab-pane label="物理回路关系图" name="physics" >
            <physics-diagram ref="physics" :node-type="node.type" :active-tab="iedActiveName"></physics-diagram>
          </el-tab-pane>
          <el-tab-pane label="状态信息图" name="status" >
            <status-info-diagram ref="status" :node-type="node.type" :active-tab="iedActiveName"></status-info-diagram>
          </el-tab-pane>
        </el-tabs>
        <!-- 间隔 -->
        <el-tabs v-model="activeBayName"  v-show="node.type == 'B'" @tab-click="showBayContent" >
          <el-tab-pane label="光纤回路一览表" name="opticalfiber"   >
            <opticalfiber ref="opticalfiber" :node-type="node.type" :active-tab="activeBayName" lazy></opticalfiber>
          </el-tab-pane>
          <el-tab-pane label="逻辑链路一览表" name="logic" >
            <logic ref="logic" :node-type="node.type" :active-tab="activeBayName"  lazy></logic>
          </el-tab-pane>
        </el-tabs>
        <svg-wrapper  :init-svg-name="svgPath" ref="svgWrapper" v-show="node.type == 'S'" :node-type="node.type"
                      :svg-background="'transparent'"></svg-wrapper>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import PomdMenu from '@/views/gridnt/pomd/menu';
import SubstationIedTree from '@/views/gridnt/pomd/common/substationIedTree';
import SvgWrapper from '@/views/gridnt/common/svgWrapper';
import CircuitDiagram from '@/views/gridnt/pomd/diagrams/circuit/index';
import PhysicsDiagram from '@/views/gridnt/pomd/diagrams/physics/index';
import StatusInfoDiagram from '@/views/gridnt/pomd/diagrams/statusinfo/index';
import CrcTable from '@/views/gridnt/pomd/substation/crc';
import SubsStatusList from '@/views/gridnt/pomd/substation/subsstatus';
import Opticalfiber from '@/views/gridnt/pomd/substation/opticalfiber'
import Logic from '@/views/gridnt/pomd/substation/logic'
import * as iedJs from '@/api/gridnt/ied'

export default {
  name: 'SubstationSummary',
  components: { CrcTable, SvgWrapper, CircuitDiagram, SubstationIedTree, PomdMenu, PhysicsDiagram,
    StatusInfoDiagram,SubsStatusList,Opticalfiber,Logic },
  data() {
    return {
      activeName: 'tab0',
      activeBayName:'opticalfiber',
      substation: {},
      ied: null,
      bay: null,
      node: {},
      svgPath: '',
      iedActiveName:'circuit',
      sendNode: {},
      svgTabs:[]
    };
  },
  methods: {
    selectedIed(node) {
      this.sendNode = this.$route.params.node
      switch (node.type) {
        //变电站
        case 'S':
          this.node = node;
          this.activeName = 'tab0';
          this.substation = node;
          this.svgTabs = []
          this.loadSvgTab()
          break;
        //地区
        case 'D':
          this.node = node;
          this.$nextTick(()=>{
            this.$refs.subsStatusList.getList(this.node)
          })
          break;
        //间隔
        case 'B':
          this.node = node;
          this.$nextTick(() => {
            this.$refs[this.activeBayName].getList(this.node);
          });
          break;
        case 'I':
          this.node = node;
          this.ied = node;
          this.$nextTick(() => {
            this.$refs[this.iedActiveName].loadData(this.ied);
          });
          break;
        default:;
      }
    },
    loadSvgTab(){
      let param = {
        subsName:this.substation.label
      }
      iedJs.loadSvgTabs(param).then((resp) =>{
        this.svgTabs = resp.data
        if(this.svgTabs  != null && this.svgTabs.length > 0){
          let svgInfo = this.svgTabs[0]
          this.svgPath = this.getSvgUrl(svgInfo['filePath'])
        }
      })
    },
    showContent(tab) {
      let tabName = tab.name
      let dataIndex = tabName.replace("tab","")
      let svgInfo = this.svgTabs[dataIndex]
      this.svgPath = this.getSvgUrl(svgInfo['filePath'])
    },
    showIedContent(tab){
      this.$nextTick(() => {
        this.$refs[tab.name].loadData(this.ied);
      });
    },
    showBayContent(tab){
      this.$nextTick(() => {
        this.$refs[tab.name].getList(this.node)
      });
    },
    getSvgUrl(svgPath){
      // if(process.env.NODE_ENV === "production" ) {
      //   let svgPrefix = window.location.protocol + "//" + window.location.host + "/" + process.env.VUE_APP_BASE_API+"/";
      //   svgPath = svgPath.substring(svgPath.indexOf("iotoss/"), svgPath.length);
      //   svgPath = svgPrefix + svgPath
      // }
      return svgPath
    }
  }
};
</script>
<style lang="scss" scoped>
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


</style>
<style type="text/css" >
/*设置鼠标经过table列背景色*/
.el-table__body tr:hover>td{
  background-color: #619ac3 !important;
}
.el-tabs--border-card {
  background-color: rgb(6, 16, 41) !important;
  border: 0px solid #DCDFE6;
  -webkit-box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12), 0 0 6px 0 rgba(0, 0, 0, 0.04);
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12), 0 0 6px 0 rgba(0, 0, 0, 0.04);
}

.df_border {
  border: 1px solid #2c3340;
}

.el-table th.el-table__cell.is-leaf, .el-table td.el-table__cell {
  border-bottom: 1px solid #2c3340;
  border-right: 1px solid #2c3340;
}

</style>
