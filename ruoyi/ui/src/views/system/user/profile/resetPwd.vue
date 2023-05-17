<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="旧密码" prop="oldPassword">
      <el-input v-model="user.oldPassword" placeholder="请输入旧密码" type="password"/>
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword">
      <el-input v-model="user.newPassword" placeholder="请输入新密码"
                @focus.capture.native='changePasswordTip(isNewPwdShowTip)'
                @blur.capture.native='changePasswordTip(isNewPwdShowTip)'
                auto-complete="new-password" type="password"/>
      <div style="position: absolute">
        <verify-pass-word-tip :password="user.newPassword" :isShowTip='isNewPwdShowTip'></verify-pass-word-tip>
      </div>
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmPassword">
      <el-input v-model="user.confirmPassword"
                placeholder="请确认密码"
                @focus.capture.native='changePasswordTip(isConfirmPwdShowTip)'
                @blur.capture.native='changePasswordTip(isConfirmPwdShowTip)'
                auto-complete="new-password"
                type="password"/>
      <div style="position: absolute">
        <verify-pass-word-tip :password="user.confirmPassword" :isShowTip='isConfirmPwdShowTip'></verify-pass-word-tip>
      </div>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">保存</el-button>
      <el-button type="danger" size="mini" @click="close">关闭</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {updateUserPwd} from "@/api/system/user";
import verifyPassWordTip from './verifyPassWordTip'

export default {
  components: {
    verifyPassWordTip
  },
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      password: '',
      isNewPwdShowTip: false,
      isConfirmPwdShowTip: false,
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
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(
            response => {
              if (response.code === 200) {
                this.msgSuccess("已修改密码,3秒后跳转到登录页,请重新登录");
                setTimeout(() => {
                  this.$store.dispatch('LogOut').then(() => {
                    location.reload()
                  })
                }, 3000)
              }
            },
          );
        }
      });
    },
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({path: "/index"});
    },
    /**
     * 改变密码提示是否显示
     **/
    changePasswordTip(isShow) {
      if (isShow) {
        this.isNewPwdShowTip = false;
        this.isConfirmPwdShowTip = false
      } else {
        this.isConfirmPwdShowTip = false
        this.isNewPwdShowTip = false;
      }
    }
  }
}
</script>
