<template>
  <div>
    <el-row class="common-header">
      <div class="systemLogo">国资国企动态监管平台</div>
    </el-row>
    <el-row class="container">
      <el-form ref="form" :model="user" :rules="rules" label-width="80px">
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input v-model="user.oldPassword" placeholder="请输入旧密码" type="password" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="user.newPassword" placeholder="请输入新密码" type="password" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="user.confirmPassword" placeholder="请确认密码" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" @click="submit">保存</el-button>
          <el-button type="danger" size="mini" @click="close">关闭</el-button>
        </el-form-item>
      </el-form>
    </el-row>
    <el-row class="footer">
      <p>Copyright © 1998 - 2021 GridNt. All Rights Reserved.</p>
    </el-row>
  </div>

</template>

<style>
.common-header {
  width: 100%;
  height: 60px;
  display: inline-block;
  background-color: #107bf8;
}
.container {
  width:500px;
  margin:0px auto;
  margin-top:150px;
  text-align: center;
}
.systemLogo {
  color:#FFFFFF;
  width:250px;
  font-size:25px;
  height:60px;
  line-height:59px;
  vertical-align: middle;
  text-align: center;
  margin-left:150px;
}
.footer {
  font-family: "微软雅黑";
  display: block;
  position: fixed;
  bottom: 0;
  background-color: #ececec;
  margin: 0 auto;
  clear: both;
  width: 100%;
  text-align: center;
  padding: 15px 0;
  overflow: hidden;
  color:#7b7070;
}
</style>
<script>
import { updateUserPwdOnUnlogin } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      test: "1test",
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      // 表单校验
      rules: {
        oldPassword: [
          {required: true, message: "旧密码不能为空", trigger: "blur"}
        ],
        newPassword: [
          {required: true, message: '新密码不能为空', trigger: 'blur'},
          {
            pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}/g,
            message: '密码长度必须大于8个字符,密码强度为:数字+字母大小写+特殊字符'
          }
        ],
        confirmPassword: [
          {required: true, message: "确认密码不能为空", trigger: "blur"},
          {required: true, validator: equalToPassword, trigger: "blur"}
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwdOnUnlogin(this.$route.query.username,this.user.oldPassword, this.user.newPassword).then(
            response => {
              this.msgSuccess("修改成功",1000,()=>{
                this.$router.push({path:'/login'})
              });
            }
          );
        }
      });
    },
    close() {
      this.$router.push({ path: "/login" });
    }
  }
};
</script>
