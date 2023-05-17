<template>
  <el-container style="height: 100%;">
    <el-table max-height="700" :key="tableKey" :data="tableData">
      <el-table-column type="index"/>
      <el-table-column align="center" label="IED名" prop="f1046Name"/>
      <el-table-column align="center" label="描述" prop="f1046Desc"/>
      <el-table-column align="center" label="生产厂家" prop="f1046Manufacturor"/>
      <el-table-column align="center" label="IED型号" prop="f1046Model"/>
      <el-table-column align="center" label="IED版本" prop="f1046Configversion"/>
      <el-table-column align="center" label="CRC" prop="crcval"/>
      <el-table-column align="center" label="软件版本" prop="softVersion"/>
      <el-table-column align="center" label="运行状态">
        <template slot-scope="scope">
          <div v-if="scope.row.runstatus == 0"><span class="status-circle status-color0"></span>运行</div>
          <div v-if="scope.row.runstatus == 1"><span class="status-circle status-color4"></span>检修</div>
          <div v-if="scope.row.runstatus == 2"><span class="status-circle status-color1"></span>跳闸</div>
          <div v-if="scope.row.runstatus == 3"><span class="status-circle status-color5"></span>闭锁</div>
          <div v-if="scope.row.runstatus == 4"><span class="status-circle status-color3"></span>告警</div>
          <div v-if="scope.row.runstatus == 5"><span class="status-circle status-color2"></span>停运</div>
          <div v-if="scope.row.runstatus == 127">未知</div>
          <div v-if="scope.row.runstatus == 255">--</div>
        </template>
      </el-table-column>
<!--      <el-table-column align="center" label="站控层A网" prop="commstatusA"/>-->
      <el-table-column align="center" label="站控层A网">
        <template slot-scope="scope">
          <div v-if="scope.row.commstatusA == 0"><span class="status-circle status-color1"></span>中断</div>
          <div v-if="scope.row.commstatusA == 1"><span class="status-circle status-color0"></span>正常</div>
          <div v-if="scope.row.commstatusA == 127">未知</div>
          <div v-if="scope.row.commstatusA == 255">--</div>
        </template>
      </el-table-column>
<!--      <el-table-column align="center" label="站控层B网" prop="commstatusB"/>-->
      <el-table-column align="center" label="站控层B网">
        <template slot-scope="scope">
          <div v-if="scope.row.commstatusB == 0"><span class="status-circle status-color1"></span>中断</div>
          <div v-if="scope.row.commstatusB == 1"><span class="status-circle status-color0"></span>正常</div>
          <div v-if="scope.row.commstatusB == 127">未知</div>
          <div v-if="scope.row.commstatusB == 255">--</div>
        </template>
      </el-table-column>
<!--      <el-table-column align="center" label="GOOSE通信状态" prop="goosestatus"/>-->
      <el-table-column align="center" label="GOOSE通信状态">
        <template slot-scope="scope">
          <div v-if="scope.row.goosestatus == 0"><span class="status-circle status-color1"></span>中断</div>
          <div v-if="scope.row.goosestatus == 1"><span class="status-circle status-color0"></span>正常</div>
          <div v-if="scope.row.goosestatus == 127">未知</div>
          <div v-if="scope.row.goosestatus == 255">--</div>
        </template>
      </el-table-column>
<!--      <el-table-column align="center" label="SV通信状态" prop="svstatus"/>-->
      <el-table-column align="center" label="SV通信状态">
        <template slot-scope="scope">
          <div v-if="scope.row.svstatus == 0"><span class="status-circle status-color1"></span>中断</div>
          <div v-if="scope.row.svstatus == 1"><span class="status-circle status-color0"></span>正常</div>
          <div v-if="scope.row.svstatus == 127">未知</div>
          <div v-if="scope.row.svstatus == 255">--</div>
        </template>
      </el-table-column>
<!--      <el-table-column align="center" label="定值状态" prop="sgstatus"/>-->
      <el-table-column align="center" label="定值状态">
        <template slot-scope="scope">
          <div v-if="scope.row.sgstatus == 0"><span class="status-circle status-color0"></span>一致</div>
          <div v-if="scope.row.sgstatus == 1"><span class="status-circle status-color1"></span>不一致</div>
          <div v-if="scope.row.sgstatus == 127">未知</div>
          <div v-if="scope.row.sgstatus == 255">--</div>
        </template>
      </el-table-column>
<!--      <el-table-column align="center" label="CRC状态" prop="crcstatus"/>-->
      <el-table-column align="center" label="CRC状态">
        <template slot-scope="scope">
          <div v-if="scope.row.crcstatus == 0"><span class="status-circle status-color0"></span>一致</div>
          <div v-if="scope.row.crcstatus == 1"><span class="status-circle status-color1"></span>不一致</div>
          <div v-if="scope.row.crcstatus == 127">未知</div>
          <div v-if="scope.row.crcstatus == 255">--</div>
        </template>
      </el-table-column>
<!--      <el-table-column align="center" label="软件版本状态" prop="verstatus"/>-->
      <el-table-column align="center" label="软件版本状态">
        <template slot-scope="scope">
          <div v-if="scope.row.verstatus == 0"><span class="status-circle status-color0"></span>一致</div>
          <div v-if="scope.row.verstatus == 1"><span class="status-circle status-color1"></span>不一致</div>
          <div v-if="scope.row.verstatus == 127">未知</div>
          <div v-if="scope.row.verstatus == 255">--</div>
        </template>
      </el-table-column>
    </el-table>
  </el-container>
</template>

<script>


import {getIEDsBySubstation} from "@/api/gridnt/ied";
import {query} from "@/api/gridnt/websocket";

export default {
  name: "account",
  data(){
    return{
      isActive: true,
      websocket: null,
      queryTimer: null,
      tableKey: 0,
      tableData: [],
      tableDataMap: {},
      substations: [],
    }
  },
  created(){
  },
  methods: {
    selectChanged(selection){
      console.log("account selectChanged:", selection);
      this.isActive = selection;
    },
    deviceClicked(data) {
      if(data.type == 'D'){
        this.tableData = [];
        return;
      }

      getIEDsBySubstation({Code: data.id, Codetype: data.type}).then(result =>{
        this.tableData = result.data;
        for(let i = 0; i < this.tableData.length; i++){
          this.tableData[i].crcval = "--";
          this.tableData[i].softVersion = "--";
          this.tableData[i].runstatus = "--";
          this.tableData[i].commstatusA = "--";
          this.tableData[i].commstatusB = "--";
          this.tableData[i].goosestatus = "--";
          this.tableData[i].svstatus = "--";
          this.tableData[i].sgstatus = "--";
          this.tableData[i].crcstatus = "--";
          this.tableData[i].verstatus = "--";
          this.tableDataMap[this.tableData[i].f1046Code] = this.tableData[i];
        }

        if(this.queryTimer){
          clearInterval(this.queryTimer);
        }

        let queryParam = [];
        for(let i = 0; i < this.tableData.length; i++){
          queryParam.push({
            table: "iedlist",
            key: this.tableData[i].f1046Code,
            cols: ["crcval", "runstatus", "commstatusA", "commstatusB", "goosestatus", "svstatus", "sgstatus", "crcstatus", "verstatus"]
          });
        }

        //定时请求前需要获取一次数据
        this.websocket =  query(this.websocket, queryParam, this.refresh);

        this.queryTimer = setInterval(()=>{
          if(!this.isActive){
            return;
          }
          this.websocket = query(this.websocket, queryParam, this.refresh);
        }, 10000);
      })
    },
    refresh(event){
      let message = JSON.parse(event.data);
      // console.log(message);

      if(message.error) {
        console.log(message.error);
        return;
      }

      let result = message.result;
      let Cr = "--";
      for(let i = 0; i < result.length; i++){
        let data = result[i];
        let ied = this.tableDataMap[data.key];
        if(ied){
          Cr = data['crcval'];

          if(Cr.search('--') != -1)
          {
            ied.crcval = "--";
          }
          else if (Cr.length==1 && Cr == null)
          {
            ied.crcval = "";
          }
          // ied.crcval = Cr;
          // console.log(Cr);
          ied.softVersion = data['softVersion'];
          ied.runstatus = data['runstatus'];
          ied.commstatusA = data['commstatusA'];
          ied.commstatusB = data['commstatusB'];
          ied.goosestatus = data['goosestatus'];
          ied.svstatus = data['svstatus'];
          ied.sgstatus = data['sgstatus'];
          ied.crcstatus = data['crcstatus'];
          ied.verstatus = data['verstatus'];
        }
      }

      this.tableKey++;
    }
  }
}
</script>

<style scoped>
.status-circle{
  display: inline-block;
  width: 15px;
  height: 15px;
  border-radius: 15px;
  border: 1px solid #000;
}
/deep/ th,/deep/ td{
  text-align: center;
}
.status-color0{
  background-color: #1edc0e;
  /*border-color: #1edc0e;*/
}
.status-color1{
  background-color: #ff0000;
  /*border-color: #1edc0e;*/
}
.status-color2{
  background-color: #909399;
  /*border-color: #1edc0e;*/
}
.status-color3{
  background-color: #e6a23c;
  /*border-color: #1edc0e;*/
}
.status-color4{
  background-color: #409eff;
  /*border-color: #1edc0e;*/
}
.status-color5{
  background-color: #ffa200;
  /*border-color: #1edc0e;*/
}
</style>


