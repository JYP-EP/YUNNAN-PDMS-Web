<template>
<el-container style="height: 100%;">
  <el-aside style="background-color: rgb(6, 16, 41);; padding: 0px; margin-right: 10px; height: 100%" width="350px">
    <substation-ied-tree ref="substationTree" @treeSelected="selectedIed"/>
  </el-aside>
  <el-container style="background-color: rgb(3, 13, 40);">
    <el-main style="padding: 5px; background-color: transparent;">
      <div class="app-container" style="height: 100%;">
        <el-tabs ref="monitorTabs" v-model="activeName" @tab-click="setalertType" style="height: 100%;">
          <el-tab-pane label="设备台账" name="account" :lazy="true">
            <account v-if="!isIed" ref="account"></account>
            <iedaccount v-if="isIed" ref="iedaccount"></iedaccount>
          </el-tab-pane>
          <el-tab-pane label="装置参数" name="parameter" :lazy="true">
            <parameter  ref="parameter"></parameter>
          </el-tab-pane>
          <el-tab-pane label="定值管理" name="setting" :lazy="true">
            <setting  ref="setting"></setting>
          </el-tab-pane>
          <el-tab-pane label="压板状态" name="stripe" :lazy="true">
            <stripe  ref="stripe"></stripe>
          </el-tab-pane>
          <el-tab-pane label="运行工况" name="condition" :lazy="true">
            <condition  ref="condition"></condition>
          </el-tab-pane>
          <el-tab-pane label="装置告警" name="warning" :lazy="true">
            <warning  ref="warning"></warning>
          </el-tab-pane>
          <el-tab-pane label="开关量" name="status" :lazy="true">
            <status  ref="status"></status>
          </el-tab-pane>
          <el-tab-pane label="其它状态量" name="others" :lazy="true">
            <others  ref="others"></others>
          </el-tab-pane>
          <el-tab-pane label="模拟量" name="analog" :lazy="true">
            <analog  ref="analog"></analog>
          </el-tab-pane>
          <el-tab-pane label="录波" name="record" :lazy="true">
            <record  ref="record"></record>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-main>
  </el-container>
</el-container>
</template>

<script>
import account from "@/views/gridnt/pomd/monitor/targets/account";
import iedaccount from "@/views/gridnt/pomd/monitor/targets/iedaccount";
import parameter from "@/views/gridnt/pomd/monitor/targets/parameter";
import setting from "@/views/gridnt/pomd/monitor/targets/setting";
import stripe from "@/views/gridnt/pomd/monitor/targets/stripe";
import warning from "@/views/gridnt/pomd/monitor/targets/warning";
import status from "@/views/gridnt/pomd/monitor/targets/status";
import others from "@/views/gridnt/pomd/monitor/targets/others";
import analog from "@/views/gridnt/pomd/monitor/targets/analog";
import condition from "@/views/gridnt/pomd/monitor/targets/condition";
import record from "@/views/gridnt/pomd/monitor/targets/record";
import SubstationIedTree from '@/views/gridnt/pomd/common/substationIedTree.vue';
import { saveAs } from 'file-saver'

export default {
  name: "monitor",
  components: {status, warning, stripe, account,iedaccount,parameter, setting, condition, others, analog, record,SubstationIedTree},
  data(){
    return {
      activeName: 'account',
      selectnode:{},
      isIed:false,
    }
  },
  created(){
  },
  methods: {
    // tab switch
    setalertType(tab, event) {

      if(this.activeName == "account" && this.selectnode.type == "I")
      {
        this.isIed = true;
        this.$nextTick(() => {
          this.$refs.iedaccount.deviceClicked(this.selectnode)
        });
      }
      else
      {
        //this.activeName = "account";
        this.isIed = false;
        this.runSelect();
      }
    },
    selectedIed(node) {
      this.selectnode = node;

      if(this.activeName == "account" && node.type == "I")
      {
        //this.activeName = "iedaccount";
        this.isIed = true;
        this.$nextTick(() => {
          this.$refs.iedaccount.deviceClicked(this.selectnode)
        });
      }
      else
      {
        //this.activeName = "account";
        this.isIed = false;
        this.runSelect();
      }
      //this.runSelect();
    },
    runSelect()
    {
      this.$nextTick(() => {
        this.$refs[this.activeName].deviceClicked(this.selectnode)
      });
    },
  }
}
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
<style type="text/css" >
/*设置鼠标经过table列背景色*/
.el-table__body tr:hover>td{
  background-color: #619ac3 !important;
}
.el-table th.el-table__cell.is-leaf, .el-table td.el-table__cell {
  border: 1px solid #2c3340;
}
</style>
