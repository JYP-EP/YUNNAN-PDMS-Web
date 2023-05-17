<template>
  <div class="login">
    <div class="systemName">智能变电站保护设备在线监视与诊断系统</div>
    <div class="loginLeft">

    </div>
    <div class="formWrapper">
      <div class="div-corner corner-lt"></div>
      <div class="div-corner corner-rt"></div>
      <div class="div-corner corner-lb"></div>
      <div class="div-corner corner-rb"></div>
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <h3 class="title">用户登录</h3>
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" type="text" auto-complete="off" :placeholder="accountPlaceHolder">
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            placeholder="密码"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-input
            v-model="loginForm.code"
            auto-complete="off"
            placeholder="验证码"
            style="width: 63%"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" @click="getCode" class="login-code-img"/>
          </div>
        </el-form-item>
        <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
        <el-form-item style="width:100%;">
          <el-button
            :loading="loading"
            size="medium"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleLogin"
          >
            <span v-if="!loading">登 录</span>
            <span v-else>登 录 中...</span>
          </el-button>
        </el-form-item>
      </el-form>

    </div>
    <div class="el-login-footer">
      <span>Copyright © 2018-2025 gridnt.com All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import {getCodeImg, getLoginWay} from "@/api/login";
import Cookies from "js-cookie";
import {encrypt, decrypt} from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      cookiePassword: "",
      accountPlaceHolder: '账号',
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          {required: true, trigger: "blur", message: "用户名不能为空"}
        ],
        password: [
          {required: true, trigger: "blur", message: "密码不能为空"}
        ],
        code: [{required: true, trigger: "change", message: "验证码不能为空"}]
      },
      loading: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
    this.getLoginWay()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = "data:image/gif;base64," + res.img;
        this.loginForm.uuid = res.uuid;
      });
    },
    getLoginWay() {
      getLoginWay().then(res => {
        let placeHolder = ''
        let a = '', b = '', c = ''
        const loginWay = res.data
        for (let i = 0; i < loginWay.length; i++) {
          const temp = loginWay[i]
          const index = i + 1
          if (temp === '1') {
            switch (index) {
              case 1:
                a = '账号/'
                break
              case 2:
                b = '手机/'
                break
              case 3:
                c = '邮箱/'
                break
            }
          }
        }
        placeHolder = `${a}${b}${c}`
        if (placeHolder.endsWith('/')) {
          placeHolder = placeHolder.substring(0, placeHolder.lastIndexOf('/'))
        }
        this.accountPlaceHolder = placeHolder
      })

    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLoginWay(username) {
      const emailRegex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/
      const mobileRegex = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
      let loginWay = ''
      //账号登录方式
      if (emailRegex.test(username)) {
        loginWay = '1'
        //手机登录方式
      } else if (mobileRegex.test(username)) {
        loginWay = '2'
        //邮箱登录方式
      } else {
        loginWay = '3'
      }
      //this.SET_LOGIN_WAY(loginWay)
      this.$store.state.loginWay = loginWay
    },
    handleLogin() {
      //前端记录当前的登录方式
      this.handleLoginWay(this.loginForm.username)
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, {expires: 30});
            Cookies.set("password", encrypt(this.loginForm.password), {expires: 30});
            Cookies.set('rememberMe', this.loginForm.rememberMe, {expires: 30});
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({path: this.redirect || "/"}).catch(() => {
            });
          }).catch((err) => {
            this.$router.push({path: '/'});
            this.loading = false;
            this.getCode();
            if (!!err.message && err.message.includes("初始密码")) {
              this.msgError(err.message, 1500, () => {
                this.$router.push({path: '/resetPwd', query: {username: this.loginForm.username}});
              });
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  //background-image: url("../assets/images/b1.png");
  background: linear-gradient(
      180deg, rgba(0, 5, 11, 1) 0%, rgba(0, 5, 11, 1) 32%, rgba(1, 23, 64, 1) 56%, rgba(2, 8, 16, 1) 99%, rgba(2, 8, 16, 1) 100%);
  background-size: cover;

  .systemName {
    color: #FFFFFF;
    font-size: 30px;
    font-weight: bold;
    color: #ffffff;
    font-size: 35px;
    top: 70px;
    left: 100px;
    position: absolute;
    font-family: 'Microsoft YaHei UI', 'Microsoft YaHei', DengXian, SimSun, 'Segoe UI', Tahoma, Helvetica, sans-serif;
  }

  .loginLeft {
    background-image: url("../assets/images/thing.png");
    width: 580px;
    height: 406px;
    position: absolute;
    //left: 400px;
    right: 35%;
  }

  .formWrapper {
    position: absolute;
    //left: 1200px;
    right: 5%;
    color: #69ca62;
    .div-corner {
      position: absolute;
      height: 38px;
      width: 38px;
    }
    .corner-lt {
      top: 0%;
      left: -0.5%;
      background-image: url("../assets/images/gridnt/login/llt.png");
    }

    .corner-rt {
      background-image: url("../assets/images/gridnt/login/lrt.png");
      top: 0%;
      right: -0.5%;
    }

    .corner-lb {
      background-image: url("../assets/images/gridnt/login/llb.png");
      bottom: 0%;
      left: -0.5%;
    }

    .corner-rb {
      background-image: url("../assets/images/gridnt/login/lrb.png");
      bottom: 0%;
      right: -0.5%;
    }

    .login-form {
      width: 400px;
      height: 406px;
      padding: 25px 25px 5px 25px;
      border: 1px solid #77c8f1;
      border-image: -webkit-linear-gradient(#77c8f1, #003c7f) 20 20;
      border-image: -moz-linear-gradient(#77c8f1, #003c7f) 20 20;
      border-image: linear-gradient(#77c8f1, #003c7f) 20 20;

      .title {
        margin: 0px auto 30px auto;
        text-align: center;
        color: #d6e5ef;
      }

      .el-input {
        height: 38px;
        background-color: #d6e5ef !important;
        input {
          height: 38px;
          background-color: #d6e5ef !important;
          color: #000;
        }
      }

      .input-icon {
        height: 39px;
        width: 14px;
        margin-left: 2px;
      }

      .login-tip {
        font-size: 13px;
        text-align: center;
        color: #bfbfbf;
      }

      .login-code {
        width: 33%;
        height: 38px;
        float: right;

        img {
          cursor: pointer;
          vertical-align: middle;
        }
      }

      .login-code-img {
        height: 38px;
      }
    }
  }

  .el-login-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
  }
}

</style>
