<template>
  <div class="login">
    <div class="login_form">
      <p>UAC用户统一登录认证服务</p>
      <el-tabs v-model="activeName" @tab-click="handleClick" >
        <el-tab-pane label="登录" name="first">
          <el-form
              :model="loginForm"
              :rules="rules"
              ref="loginForm"
              @submit.native.prevent
          >
            <el-form-item label="" prop="account" class="elItem">
              <el-input
                  type="text"
                  autocomplete="off"
                  v-model="loginForm.account"
                  prefix-icon="el-icon-user-solid"
                  placeholder="请输入用户名"
              ></el-input>
            </el-form-item>
            <el-form-item label="" prop="password">
              <el-input
                  type="password"
                  autocomplete="off"
                  v-model="loginForm.password"
                  prefix-icon="el-icon-lock"
                  placeholder="请输入密码"
              ></el-input>
            </el-form-item>
            <el-form-item label="" prop="imageCode">
              <el-input
                  type="text"
                  autocomplete="off"
                  v-model="loginForm.imageCode"
                  prefix-icon="el-icon-lock"
                  placeholder="请输入图片验证码"
              ></el-input>

            </el-form-item>
            <el-form-item label="">
              <div v-if="isReloadData">
                <el-image :src="url"></el-image>
                <!--      定义一个点击事件，点击调用刷新方法-->
                <a href="javascript:;" title="刷新" class="refresh" @click="captchaUpdte()"><i class="el-icon-refresh-left"></i></a>
              </div>
            </el-form-item>

            <el-form-item class="btns">
              <el-button type="primary" @click="goToLogin" native-type="submit">登录</el-button>
              <el-button @click="resetLoginForm">重置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="验证码登录" name="second">
<!--          //注册组件-->
          <phoneLogin></phoneLogin>
        </el-tab-pane>

      </el-tabs>
    </div>
  </div>
</template>

<script>
//引入注册组件
import phoneLogin from '@/components/login/phoneLogin';
export default {
  data() {
    var validateAccount = (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("账号不能为空"));
      } else {
        callback();
      }
    };
    var validatePassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        // callback(new Error("请输入正确的密码"));
        callback();
      }
    };

    var validateImageCode = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入图片验证码"));
      } else {
        callback();
      }
    };
    return {
      // 刷新标识
      isReloadData: true,
      url: "http://localhost:30002/uac/code/image?width=80",
      loginForm: {
        account: "",
        password: "",
        imageCode: ""
      },
      activeName:'first',//默认显示登录页面
      rules: {
        account: [
          {
            validator: validateAccount,
            trigger: "blur",
          },
        ],
        password: [
          {
            validator: validatePassword,
            trigger: "blur",
          },
        ],
        imageCode: [
          {
            validator: validateImageCode,
            trigger: "blur",
          }
        ]
      },
    };
  },
  methods: {
    captchaUpdte() {
      console.log("captchaUpdte")
      this.url = "http://localhost:30002/uac/code/image?width=80"
      this.$router.go(0)
      this.isReloadData = false
      this.$nextTick(() => {
        this.isReloadData = true
      })
    },
    goToLogin() {
      this.$refs["loginForm"].validate((valid) => {
        if (valid) {
          this.$http.post('/uac/user/login',{
            'userName':this.loginForm.account,
            'userPassword':this.loginForm.password,
            'code': this.loginForm.imageCode
          }).then((result) => {//

            if (result.data.code == 0) {
              sessionStorage.setItem("token",result.data.data)
              this.$router.push('/library');//跳转页面
            } else {
              this.$message.error(result.data.msg);
            }
            this.loading = false;
            console.log(result)
          }).catch((error) => {//提交失败
            console.log('Error', error.message);
            this.$message.error("登录失败: " + error.message);
          });
        } else {
          this.$message.error("登陆信息不全");
          return false;
        }
      });
    },
    resetLoginForm() {
      this.$refs["loginForm"].resetFields();
    },
    handleClick(){}
  },
  components:{
    phoneLogin
  }
};
</script>

<style scoped lang='less'>
.login {
  width: 100%;
  height: 100vh;
  //background-image: url("@/assets/logo.phg");//背景图
  background-size: 100% 100%;
  background-position: center center;
  overflow: auto;
  position: relative;
  .login_form {
    width: 400px;
    height: 450px;
    position: absolute;
    left: 50%;
    top: 20%;
    margin-left: -200px;
    margin-top: -150px;
    padding: 10px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 0 10px #ddd;
    .btns {
      display: flex;
      justify-content: center;
    }
  }
  p {
    font-size: 24px;
    text-align: center;
    font-weight: 600;
  }
}
</style>
