<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
  <el-form-item label="账号" prop="userName">
        <el-input v-model="user.userName" />
      </el-form-item>
    <el-form-item label="姓名" prop="realName">
      <el-input v-model="user.realName" :disabled="true"/>
    </el-form-item>
    <el-form-item label="手机号码" prop="phonenumber">
      <el-input v-model="user.phonenumber" maxlength="11" />
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
          <el-input v-model="user.createTime" :disabled="true"/>
        </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">保存</el-button>
      <el-button type="danger" size="mini" @click="close">关闭</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserProfile } from "@/api/system/user";
import {mapState} from 'vuex'

export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      // 表单校验
      phonenumber:'',
      rules: {
      userName: [
                { required: true, message: "姓名不能为空", trigger: "blur" }
              ],
              createTime: [
                { required: true, message: "创建时间不能为空", trigger: "blur" }
              ],
        realName: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        email: [
          { required: true, message: "邮箱地址不能为空", trigger: "blur" },
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.phonenumber = this.user.phonenumber;
  },
  watch:{
    'user.phonenumber'(v1,v2){
      if(v2===undefined){
        this.phonenumber = v1;
      }
    }
  },
  computed:{
    ...mapState(['loginWay'])
  },
  methods: {
    submit() {
      console.log(this.phonenumber,this.user.phonenumber)
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.user).then(response => {
            const loginWay = this.$store.state.loginWay;

            if(loginWay==='2'&&this.phonenumber!==this.user.phonenumber){
              this.msgSuccess("个人信息修改成功,因已修改手机号，请重新登录",1500,()=>{
                this.$store.dispatch("LogOut").then(() => {
                  location.href = "/index";
                });
              })
            }else {
              this.msgSuccess("修改成功");
            }
          });
        }
      });
    },
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/index" });
    }
  }
};
</script>
