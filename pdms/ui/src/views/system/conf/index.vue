<template>
  <div class="wrapper">
    <el-row class="buttonGroup">
      <el-button type="primary" size="mini" @click="submitForm">保存</el-button>
    </el-row>
    <el-form ref="form" class="confForm" :model="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="登录失败次数:" prop="failTimes">
            <el-input v-model="form.failTimes" placeholder="请输入登录失败次数"/>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="登录方式:" prop="loginWay">
            <el-checkbox-group v-model="form.loginWay">
              <el-checkbox label="1">账号</el-checkbox>
              <el-checkbox label="2">手机号</el-checkbox>
              <el-checkbox label="3">邮箱</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
        <el-col :span="8"></el-col>
      </el-row>
    </el-form>

  </div>

</template>
<style scoped>
.buttonGroup {
  padding: 15px;
  margin: 10px;
  border: 1px solid #f5e6e6;
}

.wrapper {
  font-weight: normal;
}

.confForm {
  padding: 15px;
  border: 1px solid #f5e6e6;
  margin: 10px;
}
</style>
<script>
import {getConf,addConf,updateConf} from '@/api/system/conf'
export default {
  name: 'Conf',
  data() {
    return {
      // 表单参数
      form: {
        id:'',
        loginWay: [
          '1'
        ],
        failTimes: ''
      },
      operationType:'C',
      // 表单校验
      rules: {
        failTimes: [
          { required: true, message: '请输入失败次数', trigger: 'blur' }
        ],
        loginWay: [
          { required: true, message: '请选择登录方式 ', trigger: 'blur' }
        ]

      }
    }
  },
  created() {
    this.getConf()
  },

  methods: {
    /**获取配置信息**/
    getConf(){
      const result = getConf().then(response=>{
        if(!!response.data) {
          this.operationType = 'U'
          Object.assign(this.form, response.data)
          const tempWay = response.data.loginWay
          const loginWay = []
          for(let i =0;i<tempWay.length;i++){
            const index = i+1;
            if(tempWay[i]==='1'){
              loginWay.push(index+'');
            }
            this.form.loginWay = loginWay;
          }
        }
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          const tmpWay = this.form.loginWay
          let a = '0', p = '0', m = '0'
          let loginWay =''
          tmpWay.forEach(item => {
            console.log(item)
            switch (item) {
              case '1':
                a = '1'
                break
              case '2':
                p = '1'
                break
              case '3':
                m = '1'
                break
            }
            loginWay= `${a}${p}${m}`
          })
          const failTimes = this.form.failTimes;
          if(this.operationType==='C'){
            addConf({failTimes,loginWay})
          }else{
            updateConf({failTimes ,loginWay,id:this.form.id})
          }
          location.reload()
        }
      })
    }
  }
}
</script>
