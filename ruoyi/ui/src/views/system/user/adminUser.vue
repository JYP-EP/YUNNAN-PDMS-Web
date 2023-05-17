<template>
  <div>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="账号">
            <el-input
              v-model="form.username"
              placeholder="请输入账号"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input
              v-model="form.password"
              placeholder="请输入密码" type="password"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm(userInfo.userId)">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import {checkUser} from '@/api/system/user'

export default {
  name: 'adminUser',
  props: {
    userInfo: {
      type: Object
    }
  },
  data() {
    return {
      form:{
        username: '',
        password: '',
      },
      // 表单校验
      rules:{
        username: [
          { required: true, message: '管理员账号不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '管理员密码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods:{
    submitForm(userId){
      this.$refs.form.validate(valid => {
        if(valid){
          checkUser(this.form.username,this.form.password).then(result=>{
            this.$emit('userCallBack',{status:'1',userId:userId})
          }).catch(result=>{
          })
        }
      });
    },
    cancel(){
     this.$emit('userCallBack',{status:'0'})
    }
  }
}
</script>

<style scoped>

</style>
