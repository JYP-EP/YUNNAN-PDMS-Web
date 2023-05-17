<template>
  <div>
    <el-button
      @click.native.prevent="showDetail(alertItem)"
      type="text">
      {{statusParser(alertItem)}}
    </el-button>
    <el-dialog title="保护通道信息检查" :visible.sync="fileDialogVisible">
      <el-table :data="fileHead" :show-header=false>
        <el-table-column property="headInfo" align="center"></el-table-column>
      </el-table>
      <el-table :data="fileDetail">
        <el-table-column property="points" label="告警点信息" align="center">
          <template slot-scope="scope">
            <el-table :data="scope.row.points">
              <el-table-column label="Reference" prop="path"></el-table-column>
              <el-table-column label="描述" prop="desc"></el-table-column>
              <el-table-column label="告警次数" prop="counter"></el-table-column>
            </el-table>
          </template>
        </el-table-column>
        <el-table-column property="policy" label="诊断规则" :formatter="policyParser" width="120px"></el-table-column>
        <el-table-column property="result" label="结论描述" :formatter="resultParer" width="100px"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {getTunnelAlertDetail} from "@/api/gridnt/intelAlert";

export default {
  name: "tunnelXml",
  props: ['alertItem'],
  data(){
    return {
      fileDialogVisible: false,
      fileHead: [],
      fileDetail: []
    }
  },
  methods:{
    statusParser(row){
      return row.status === 1 ? "查看" : "无文件";
    },
    policyParser(row){
      let result = '';

      row.policy.forEach((item) =>{
        result += "危机等级" + item.level + "阈值:" + item.value + ";  ";
      });
      return result;
    },
    resultParer(row){
      let result = "告警次数: " + row.result.counter + "; 告警等级: " + row.result.level;
      return result;
    },
    showDetail(row){
      if(row.status != 1){
        return;
      }
      this.fileHead = [];
      this.fileDetail = [];
      getTunnelAlertDetail({fileId: row.fileId, type: row.alertType}).then(resp=>{
        console.log(resp.data);
        this.fileDialogVisible = true;
        let response = resp.data;
        this.fileHead.push({
          headInfo: "[" + response.queryTime.ied.name + "]" + response.queryTime.ied.desc
        });
        this.fileHead.push({
          headInfo: "告警时间段 告警时间" + response.queryTime.ied.warning.time
        });
        this.fileDetail.push({
          points: response.queryTime.ied.warning.points,
          policy: response.queryTime.ied.warning.policy.conditions,
          result: response.queryTime.ied.warning.result
        });
      });
    }
  }
}
</script>

<style scoped>

</style>
