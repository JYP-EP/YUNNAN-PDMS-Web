<template>
  <div>
    <el-button :class="{missing: isMissing}"
      @click.native.prevent="showDetail(alertItem)"
      type="text">
      {{statusParser(alertItem)}}
    </el-button>
    <el-dialog title="频繁告警" :visible.sync="fileDialogVisible">
      <el-table :data="fileHead" :show-header=false>
        <el-table-column property="headInfo" align="center"></el-table-column>
      </el-table>
      <el-table :data="fileDetail">
        <el-table-column property="points" label="告警点信息" align="center">
          <template slot-scope="scope">
            <el-table :data="scope.row.points">
              <el-table-column label="Reference" prop="path"></el-table-column>
              <el-table-column label="描述" prop="desc"></el-table-column>
              <el-table-column label="值" prop="value"></el-table-column>
              <el-table-column label="变位时间" prop="time"></el-table-column>
              <el-table-column label="品质" prop="quality"></el-table-column>
            </el-table>
          </template>
        </el-table-column>
        <el-table-column property="policy" label="诊断规则" :formatter="policyParser" width="120px"></el-table-column>
        <el-table-column property="result" label="结论描述" align="center" width="100px"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {getFrequentAlertDetail} from "@/api/gridnt/intelAlert";

export default {
  name: 'frequentXml',
  props: ['alertItem'],
  data(){
    return {
      isMissing: false,
      fileDialogVisible: false,
      fileHead: [],
      fileDetail: []
    };
  },
  methods: {
    statusParser(row){
      this.isMissing = row.status !== 1;
      return row.status === 1 ? "查看" : "召唤文件失败";
    },
    policyParser(row){
      let result = '';
      row.policy.forEach((item) =>{
        result += item.desc + ":" + item.value + "s\r\n";
      });
      return result;
    },
    showDetail(row){
      if(row.status !== 1){
        return;
      }
      this.fileHead = [];
      this.fileDetail = [];
      getFrequentAlertDetail({fileId: row.fileId, type: row.alertType}).then(resp=>{
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
          result: "按照策略设置告警"
        });
        this.fileDialogVisible = true;
      });

    }
  }
}

</script>

<style scoped>
.missing{
  color: red;
}
</style>
