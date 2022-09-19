
<template>
  <el-form
      :model="smsLoginForm"
      :rules="smsLoginRules"
      ref="smsLoginForm"
      class="demo-ruleForm"
  >
    <el-form-item label="" prop="mobile"
    ><el-input
        type="text"
        autocomplete="off"
        v-model="smsLoginForm.mobile"
        prefix-icon="el-icon-user-solid"
        placeholder="请输入手机号"
    ></el-input
    ></el-form-item>
    <el-form-item label="" prop="smsCode"
    ><el-input
        type="password"
        autocomplete="off"
        v-model="smsLoginForm.smsCode"
        prefix-icon="el-icon-lock"
        placeholder="请输入短信验证码"
    ></el-input>

    </el-form-item>
    <el-form-item class="btns">
      <el-button @click="sendSmsCode">发送短信验证码</el-button>
      <el-button type="primary" @click="submitForm">登录</el-button>
      <el-button @click="resetForm">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入短信验证码"));
      } else {
        callback();
      }
    };

    return {
      activeName: "second",
      smsLoginForm: {
        mobile: "",
        smsCode: ""
      },
      smsLoginRules: {
        mobile: [{ required: true, message: '手机号不能为空' },
          { type: 'number',
            message: '手机号格式不正确',
            trigger: 'blur',
            transform(value) {
              var phonereg = 11 && /^((13|14|15|16|17|18|19)[0-9]{1}\d{8})$/
              if (!phonereg.test(value)) {
                return false
              } else {
                return Number(value)
              }
            }
          }],
        smsCode: [{ required: true, validator: validatePass, trigger: "blur" }]
      },
    };
  },

  methods: {
    submitForm() {
      this.$refs['smsLoginForm'].validate((valid) => {
        if (valid) {
          this.$http.post('/uac/user/sms/login?userPhone=' + this.smsLoginForm.mobile +"&smsCode="+this.smsLoginForm.smsCode,{
          }).then((result) => {
            if (result.data.code == 0) {
              sessionStorage.setItem("token",result.data.data)
              this.$router.push('/library');//跳转页面
            } else {
              console.log(result.data)
              this.$message.error(result.data.msg);
            }
            console.log(result)
          }).catch((error) => {//提交失败
            console.log('Error', error.message);
            this.$message.error("登录失败: " + error.message);
          });
        } else {
          console.log("登录信息不全");
          return false;
        }
      });
    },

    sendSmsCode() {
      this.$refs["smsLoginForm"].validate((valid) => {
        if (valid) {
          this.$http.get('/uac/code/sms?userPhone='+ this.smsLoginForm.mobile).then((result) => {

            if (result.data) {
              const h = this.$createElement;
              this.$notify({
                title: '标题名称',
                message: h('i', { style: 'color: teal'}, '收到验证码: ' + result.data)
              });
            } else {
              this.$message.error(result.data.msg);
            }
            console.log(result)
          }).catch((error) => {//提交失败
            console.log('Error', error.message);
            this.$message.error("登录失败: " + error.message);
          });
        } else {
          this.$message.error("登录信息不全");
          return false;
        }

      })
    },
    resetForm() {
      console.log('resetForm')
      console.log(this.$refs['smsLoginForm'].resetFields())
      this.$refs['smsLoginForm'].resetFields();
    },
  },
};
</script>

<style scoped lang="less">
.btns {
  display: flex;
  justify-content: flex-end;
}
</style>
