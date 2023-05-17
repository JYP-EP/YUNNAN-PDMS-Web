<template>
  <!-- 添加或修改【请填写功能名称】对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form"  label-width="180px" inline style="width: 100%">
      <el-form-item label="描述" prop="f1006Desc">
        <el-input v-model.trim="form.f1006Desc" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="所属区域" prop="f1040Code">
        <el-input v-model.trim="form.f1040Code" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="所属变电站" prop="f1041Code">
        <el-input v-model.trim="form.f1041Code" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="所属间隔" prop="f1042Code">
        <el-input v-model.trim="form.f1042Code" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="所属设备代码" prop="f1006Parent">
        <el-input v-model.trim="form.f1006Parent" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="所属IED设备" prop="f1046Code">
        <el-input v-model.trim="form.f1046Code" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="所属IED设备Name" prop="f1046Name">
        <el-input v-model.trim="form.f1046Name" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="采集设备" prop="f1006Daq">
        <el-input v-model.trim="form.f1006Daq" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="参引" prop="f1006Ref">
        <el-input v-model.trim="form.f1006Ref" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="类型" prop="f1011No">
        <el-input v-model.trim="form.f1011No" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="来源" prop="f1006Source">
        <el-input v-model.trim="form.f1006Source" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="计算量标志" prop="f1006Calcflag">
        <el-input v-model.trim="form.f1006Calcflag" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="斜率" prop="f1006K">
        <el-input v-model.trim="form.f1006K" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="截距" prop="f1006B">
        <el-input v-model.trim="form.f1006B" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="零漂" prop="f1006Zerodband">
        <el-input v-model.trim="form.f1006Zerodband" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="上溢值" prop="f1006Overflow">
        <el-input v-model.trim="form.f1006Overflow" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="下溢值" prop="f1006Lowflow">
        <el-input v-model.trim="form.f1006Lowflow" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="最大增量" prop="f1006Maxinc">
        <el-input v-model.trim="form.f1006Maxinc" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="警报上限" prop="f1006Hialarm">
        <el-input v-model.trim="form.f1006Hialarm" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="警报下限" prop="f1006Loalarm">
        <el-input v-model.trim="form.f1006Loalarm" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="采样周期" prop="f1006Saveperiod">
        <el-input v-model.trim="form.f1006Saveperiod" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="死数时间限" prop="f1006Deadtime">
        <el-input v-model.trim="form.f1006Deadtime" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="处理越限级别" prop="f1006Alarmlevel">
        <el-input v-model.trim="form.f1006Alarmlevel" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="同期比较最大增量" prop="f1006Samplecpmaxinc">
        <el-input v-model.trim="form.f1006Samplecpmaxinc" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="采样周期" prop="f1006Sampleperiod">
        <el-input v-model.trim="form.f1006Sampleperiod" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="采集点个数" prop="f1006Sampleptnum">
        <el-input v-model.trim="form.f1006Sampleptnum" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="变化率" prop="f1006Changerate">
        <el-input v-model.trim="form.f1006Changerate" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="备用标志" prop="f1006Reserve">
        <el-input v-model.trim="form.f1006Reserve" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="告警持续时间门槛" prop="f1006Alarmduration">
        <el-input v-model.trim="form.f1006Alarmduration" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="值/幅值" prop="f1017Val">
        <el-input v-model.trim="form.f1017Val" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="采集值" prop="f1017Revval">
        <el-input v-model.trim="form.f1017Revval" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="相角" prop="f1017Angle">
        <el-input v-model.trim="form.f1017Angle" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="数据质量" prop="f1017Quality">
        <el-input v-model.trim="form.f1017Quality" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="时标" prop="f1017Time">
        <el-input v-model.trim="form.f1017Time" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="死数时间" prop="f1017Deadtime">
        <el-input v-model.trim="form.f1017Deadtime" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="上次处理时间" prop="f1017Preproctime">
        <el-input v-model.trim="form.f1017Preproctime" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="非法标志" prop="f1017Invalid">
        <el-input v-model.trim="form.f1017Invalid" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="死数标志" prop="f1017Deadflag">
        <el-input v-model.trim="form.f1017Deadflag" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="抑制告警" prop="f1017Noalarm">
        <el-input v-model.trim="form.f1017Noalarm" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="更新标志" prop="f1017Update">
        <el-input v-model.trim="form.f1017Update" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="置入标志" prop="f1017Manset">
        <el-input v-model.trim="form.f1017Manset" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="越限标志" prop="f1017Overlimitflag">
        <el-input v-model.trim="form.f1017Overlimitflag" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="同期比较标志" prop="f1017Samplecpflag">
        <el-input v-model.trim="form.f1017Samplecpflag" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="趋势比较标志" prop="f1017Trendcpflag">
        <el-input v-model.trim="form.f1017Trendcpflag" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="缺陷标志" prop="f1017Defectflag">
        <el-input v-model.trim="form.f1017Defectflag" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="告警次数" prop="f1017Limitalarmtime">
        <el-input v-model.trim="form.f1017Limitalarmtime" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="旧值" prop="f1017Oldval">
        <el-input v-model.trim="form.f1017Oldval" readonly = "readonly" />
      </el-form-item>
<!--      下面两个没有注释-->
      <el-form-item label="F1006_SAMPLECPCYCLE" prop="f1006Samplecpcycle">
        <el-input v-model.trim="form.f1006Samplecpcycle" readonly = "readonly" />
      </el-form-item>
      <el-form-item label="F1006_SAMPLECPNUM" prop="f1006Samplecpnum">
        <el-input v-model.trim="form.f1006Samplecpnum" readonly = "readonly" />
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import * as statusJs from '@/api/gridnt/devicestatus'


export default {
  name: "AnalogDetail",
  components: {
  },
  data() {
    return {
      // 弹出层标题
      title: "详细信息",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      code: null
    };
  },
  methods: {
    showDialog(f1006Code){
      this.open = true;
      this.code = f1006Code
      this.reset();
      let self = this
      statusJs.loadDetail(this.code).then((resp) =>{
        self.form = resp.data
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        f1006Code: null,
        f1006Desc: null,
        f1040Code: null,
        f1041Code: null,
        f1042Code: null,
        f1006Parent: null,
        f1046Code: null,
        f1046Name: null,
        f1006Daq: null,
        f1006Ref: null,
        f1011No: null,
        f1006Source: null,
        f1006Calcflag: null,
        f1006K: null,
        f1006B: null,
        f1006Zerodband: null,
        f1006Overflow: null,
        f1006Lowflow: null,
        f1006Maxinc: null,
        f1006Hialarm: null,
        f1006Loalarm: null,
        f1006Saveperiod: null,
        f1006Deadtime: null,
        f1006Alarmlevel: null,
        f1006Samplecpmaxinc: null,
        f1006Sampleperiod: null,
        f1006Sampleptnum: null,
        f1006Changerate: null,
        f1006Reserve: null,
        f1006Alarmduration: null,
        f1006Samplecpcycle: null,
        f1006Samplecpnum: null,
        f1017Val: null,
        f1017Revval: null,
        f1017Angle: null,
        f1017Quality: null,
        f1017Time: null,
        f1017Deadtime: null,
        f1017Preproctime: null,
        f1017Invalid: null,
        f1017Deadflag: null,
        f1017Noalarm: null,
        f1017Update: null,
        f1017Manset: null,
        f1017Overlimitflag: null,
        f1017Samplecpflag: null,
        f1017Trendcpflag: null,
        f1017Defectflag: null,
        f1017Limitalarmtime: null,
        f1017Oldval: null
      };
      this.resetForm("form");
    },



  }
};
</script>

<style scoped>
.el-form--inline .el-form-item{
  margin-right: 0px;
  width: 50%;
}
</style>
