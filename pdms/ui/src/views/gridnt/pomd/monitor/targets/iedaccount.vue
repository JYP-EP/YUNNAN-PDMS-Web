<template>
  <el-container style="height: 100%;">
    <el-form :inline="true"  class="demo-form-inline" label-width="108px">
      <div class="form_heard">通用参数</div>
      <el-form-item label="装置名称:">
        <div>{{IedInfo.f1046Name}}</div>
      </el-form-item>
      <el-form-item label="装置描述:">
        <div>{{IedInfo.f1046Desc}}</div>
      </el-form-item>
      <el-form-item label="装置类型:">
        <div>{{getIedType(IedInfo.f1046Type)}}</div>
      </el-form-item>
      <el-form-item label="装置型号:">
        <div>{{IedInfo.f1046Model}}</div>
      </el-form-item>
      <el-form-item label="生产厂家:">
        <div>{{IedInfo.f1046Manufacturor}}</div>
      </el-form-item>
      <el-form-item label="生产国家:">
        <div>{{IedInfo.f1046Country}}</div>
      </el-form-item>
      <el-form-item label="出厂编号:">
        <div>{{IedInfo.f1046ProductionNo}} </div>
      </el-form-item>
      <el-form-item label="出厂日期:">
        <div>{{IedInfo.f1046ProductionDate}} </div>
      </el-form-item>
      <el-form-item label="所属区域:">
        <div>{{IedInfo.f1049Desc}}</div>
      </el-form-item>
      <el-form-item label="所属屏柜:">
        <div>{{IedInfo.f1050Desc}}</div>
      </el-form-item>
      <el-form-item label="实物ID:">
        <div>{{IedInfo.f1046Id}}</div>
      </el-form-item>

      <div class="form_heard">技术参数</div>
      <div class="tab_ied">
        <div>装置CRC</div>
        <div>软件版本</div>
        <div>装置识别码</div>
      </div>
      <el-form-item label="基准值:">
        <div>{{IedInfo.f1046CrcBase}}</div>
      </el-form-item>
      <el-form-item label="基准值:">
        <div>{{IedInfo.f1046Configversion}}</div>
      </el-form-item>
      <el-form-item label="采集值:">
        <div style="font-size: 10px;padding-left: 1px;">{{IedInfo.f1046Sn}}</div>
      </el-form-item>
      <el-form-item label="采集值:">
        <div>{{IedInfo.crcval}}</div>
      </el-form-item>
      <el-form-item label="采集值:">
        <div>{{IedInfo.f1046SoftVersion}}</div>
      </el-form-item>

      <div class="form_heard">设备状态</div>
      <el-form-item label="运行状态:">
        <div v-if="IedInfo.runstatus == 0"><span class="status-circle status-color0"></span>运行</div>
        <div v-if="IedInfo.runstatus == 1"><span class="status-circle status-color4"></span>检修</div>
        <div v-if="IedInfo.runstatus == 2"><span class="status-circle status-color1"></span>跳闸</div>
        <div v-if="IedInfo.runstatus == 3"><span class="status-circle status-color5"></span>闭锁</div>
        <div v-if="IedInfo.runstatus == 4"><span class="status-circle status-color3"></span>告警</div>
        <div v-if="IedInfo.runstatus == 5"><span class="status-circle status-color2"></span>停运</div>
        <div v-if="IedInfo.runstatus == 127">未知</div>
        <div v-if="IedInfo.runstatus == 255">--</div>
<!--        <div>{{IedInfo.f1046Manufacturor}}</div>-->
      </el-form-item>
      <el-form-item label="通信状态:">
        <div v-if="IedInfo.commstatusA == 0"><span class="status-circle status-color1"></span>中断</div>
        <div v-if="IedInfo.commstatusA == 1"><span class="status-circle status-color0"></span>正常</div>
        <div v-if="IedInfo.commstatusA == 127">未知</div>
        <div v-if="IedInfo.commstatusA == 255">--</div>
      </el-form-item>
      <el-form-item label="GOOSE通信状态:">
        <div v-if="IedInfo.goosestatus == 0"><span class="status-circle status-color1"></span>中断</div>
        <div v-if="IedInfo.goosestatus == 1"><span class="status-circle status-color0"></span>正常</div>
        <div v-if="IedInfo.goosestatus == 127">未知</div>
        <div v-if="IedInfo.goosestatus == 255">--</div>
<!--        <div>{{IedInfo.f1046Manufacturor}}</div>-->
      </el-form-item>
      <el-form-item label="SV通信状态:">
        <div v-if="IedInfo.svstatus == 0"><span class="status-circle status-color1"></span>中断</div>
        <div v-if="IedInfo.svstatus == 1"><span class="status-circle status-color0"></span>正常</div>
        <div v-if="IedInfo.svstatus == 127">未知</div>
        <div v-if="IedInfo.svstatus == 255">--</div>
<!--        <div>{{IedInfo.f1046Manufacturor}}</div>-->
      </el-form-item>
      <el-form-item label="CRC状态:">
        <div v-if="IedInfo.crcstatus == 0"><span class="status-circle status-color0"></span>一致</div>
        <div v-if="IedInfo.crcstatus == 1"><span class="status-circle status-color1"></span>不一致</div>
        <div v-if="IedInfo.crcstatus == 127">未知</div>
        <div v-if="IedInfo.crcstatus == 255">--</div>
<!--        <div>{{IedInfo.f1046Manufacturor}}</div>-->
      </el-form-item>
      <el-form-item label="软件版本状态:">
        <div v-if="IedInfo.verstatus == 0"><span class="status-circle status-color0"></span>一致</div>
        <div v-if="IedInfo.verstatus == 1"><span class="status-circle status-color1"></span>不一致</div>
        <div v-if="IedInfo.verstatus == 127">未知</div>
        <div v-if="IedInfo.verstatus == 255">--</div>
<!--        <div>{{IedInfo.f1046Manufacturor}}</div>-->
      </el-form-item>
      <el-form-item label="定值状态:">
        <div v-if="IedInfo.sgstatus == 0"><span class="status-circle status-color0"></span>一致</div>
        <div v-if="IedInfo.sgstatus == 1"><span class="status-circle status-color1"></span>不一致</div>
        <div v-if="IedInfo.sgstatus == 127">未知</div>
        <div v-if="IedInfo.sgstatus == 255">--</div>
<!--        <div>{{IedInfo.f1046Manufacturor}}</div>-->
      </el-form-item>
    </el-form>
  </el-container>
</template>

<script>
import {getIEDListView} from "@/api/gridnt/ied";
import {query} from "@/api/gridnt/websocket";

export default {
  name: "iedaccount",
  data(){
    return{
      isActive: true,
      websocket: null,
      queryTimer: null,
      tableKey: 0,
      tableData: [],
      tableDataMap: {},
      substations: [],
      IedInfo:{},
      IedType:window.WebConfig.IedType,
    }
  },
  created(){
  },
  methods: {
    selectChanged(selection){
      console.log("iedaccount selectChanged:", selection);
      this.isActive = selection;
    },
    deviceClicked(data) {
      if(data.type == 'D'){
        this.tableData = [];
        this.IedInfo ={};
        return;
      }

      getIEDListView({Code: data.id, Codetype: data.type}).then(result =>{
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
        this.websocket = query(this.websocket, queryParam, this.refresh);

        this.queryTimer = setInterval(()=>{
          if(!this.isActive){
            return;
          }
          this.websocket = query(this.websocket, queryParam, this.refresh);
        }, 10000);


        if(this.tableData.length > 0)
        {
          this.IedInfo = this.tableData[0];
        }
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

      if(this.tableData.length > 0)
      {
        this.IedInfo = this.tableData[0];
        //console.log(JSON.stringify(this.IedInfo))
        this.$forceUpdate();
      }
    },
    getIedType(ref)
    {
      return this.IedType.find(x=>x.type == ref).name;
    },
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
.form_heard{
  color: #FFF;
  font-size: 16px;
  margin: 15px;
  margin-left: 0px;
}
.tab_ied{
  color: white;
  font-size: 14px;
  width: 100%;
}
.tab_ied div{
  width: 33%;
  float: left;
  padding-left: 40px;
  padding-bottom: 9px;
}
</style>
<style type="text/css">
.el-form-item--mini .el-form-item__content {
  line-height: 28px;
  width: 293px;
}
.el-form-item--mini .el-form-item__content div{
  padding-left: 5px;
  color: #39F3FC;
}
.el-form-item--mini.el-form-item {
  margin-bottom: 18px;
  background-color: #08173a;
}
.el-form-item__label {
  color: #fff;
  background: #030d28;
  font-size: 12px;
}
</style>


