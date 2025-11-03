<template>
  <div class="container">
    <div style="display: flex; width: 400px; height: 350px; margin: auto;
                position: absolute;
	              top: 0;
	              left: 0;
	              right: 0;
	              bottom: 0;">
      <div style="flex: 1; background-color: rgba(250,250,250,0.8);">
        <div style="text-align: center; font-size: 25px; font-weight: 600; margin: 25px; color: #222">作 业 管 理 系 统</div>
        <el-form :model="user"
                 :rules="rules"
                 ref="userForm"
                 label-position="left"
                 label-width="70px"
                 style="margin: 20px;">
        <el-form-item style="margin-bottom: 45px;" prop="username" label="用户名">
          <el-input size="medium" placeholder="请输入用户名" prefix-icon="iconfont icon-r-user2" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 45px;" prop="password" label="密码">
          <el-input size="medium" placeholder="请输入密码" prefix-icon="iconfont icon-r-lock" show-password v-model="user.password"></el-input>
        </el-form-item>

          <el-button type="primary" autocomplete="off" style="font-size: 16px; float: right; width: 100%" @click="login">登 录</el-button>
          <br/>
          <el-button type="text" class="bot" style="float: left; color: #222222;" autocomplete="off" @click="$router.push('/register')">注册账号</el-button>
          <el-button type="text" class="bot" style="float: right; color: #222222;" autocomplete="off" @click="handlePass">找回密码</el-button>
      </el-form>
      </div>
    </div>


    <el-dialog title="找回密码" :visible.sync="dialogFormVisible" width="30%" :append-to-body="true">
      <el-form label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="pass.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="pass.phone" autocomplete="off"></el-input>
        </el-form-item>
        <div style="width: 100%;text-align: center;">

          密码将重置为:123<br>登录系统后请尽快修改
        </div>
      </el-form>
        <el-button type="info" @click="dialogFormVisible = false"> 取 消</el-button>
        <el-button type="primary" @click="passwordBack"> 重置密码</el-button>
    </el-dialog>
  </div>
</template>

<script>
import { resetRouter, setRoutes } from "@/router";
import Identify from "@/components/Identify";

export default {
  name: "Login",
  data() {
    return {
      user: {},
      pass: {},
      code: '',
      dialogFormVisible: false,
      // 图片验证码
      identifyCode: '',
      // 验证码规则
      identifyCodes: '3456789ABCDEFGHGKMNPQRSTUVWXY',
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  components: { Identify },
  mounted() {
    // 重置路由
    resetRouter()
    this.refreshCode()
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.request.post("/user/login", this.user).then(res => {
            if (res.code === '200') {
              localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
              localStorage.setItem("menus", JSON.stringify(res.data.menus))  // 存储用户信息到浏览器

              // 动态设置当前用户的路由
              setRoutes()
              this.$router.push("/")
              this.$message.success("登录成功")
            } else {
              this.$message.error(res.msg)
            }
          }).catch((e) => {
            console.log(e);
            if (
              e.response == undefined ||
              e.response.data == undefined
            ) {
              this.$message({
                showClose: true,
                message: e,
                type: "error",
                duration: 20000,
              });
            } else {
              this.$message({
                showClose: true,
                message: e.response.data,
                type: "error",
                duration: 20000,
              });
            }
          })
        }
      });
    },
    // 切换验证码
    refreshCode() {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    // 生成随机验证码
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[Math.floor(Math.random() * (this.identifyCodes.length))]
      }
    },
    handlePass() {
      this.dialogFormVisible = true
      this.pass = {}
    },
    passwordBack() {
      this.request.put("/user/reset", this.pass).then(res => {
        if (res.code === '200') {
          this.$message.success("重置密码成功，新密码为：123，请尽快修改密码")
          this.dialogFormVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style>
.container {
  height: 100vh;
  overflow: hidden;
  /*background-color: #FFCA84;*/
  background-image: url("../assets/作业.jpg");
  background-size: 100%;
  color: #666;
}
.bot {
  font-size: 16px !important;
}
</style>
