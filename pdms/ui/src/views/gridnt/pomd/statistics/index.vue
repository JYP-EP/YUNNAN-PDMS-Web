<template>
<div class="app-container full-container">
  <el-tabs type="border-card">
    <el-tab-pane label="告警日志">
      <warning :datePickerOptions="datePickerOptions" :divisionOptions="divisionOptions"></warning>
    </el-tab-pane>
    <el-tab-pane label="通信">通信</el-tab-pane>
  </el-tabs>
</div>
</template>

<script>
import {getSubstation} from "@/api/gridnt/intelAlert";
import Warning from "@/views/gridnt/pomd/statistics/warning";

export default {
  name: "statistics",
  components: {Warning},
  data(){
    return{
      divisionOptions:[],
      datePickerOptions: {
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
          text: '前一天',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
    }
  },
  created() {
    getSubstation().then(res => {
      this.divisionOptions = res.data.data;
    })
  }
}
</script>

<style scoped>
.full-container {
  height: 100%;
  width: 100%;
}

el-tabs {
  height: 100%;
  width: 100%;
}
</style>
