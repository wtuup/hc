<template>
  <div class="login-bg">
    <el-card class="login-card">
      <h2 style="text-align:center;margin-bottom:24px;color:#409EFF">🥬 生鲜销售系统</h2>
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" show-password
                    placeholder="请输入密码" prefix-icon="Lock" @keyup.enter="login" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width:100%" :loading="loading" @click="login">登 录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button style="width:100%" @click="showRegister=true">没有账号？立即注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>

  <el-dialog title="注册账号" v-model="showRegister" width="400px">
    <el-form :model="regForm" label-width="80px">
      <el-form-item label="用户名"><el-input v-model="regForm.username" /></el-form-item>
      <el-form-item label="密码"><el-input v-model="regForm.password" type="password" show-password /></el-form-item>
      <el-form-item label="昵称"><el-input v-model="regForm.nickname" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="showRegister=false">取消</el-button>
      <el-button type="primary" @click="register">注册</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import api from '@/api'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const showRegister = ref(false)
const form = ref({ username: '', password: '' })
const regForm = ref({ username: '', password: '', nickname: '' })

const login = async () => {
  loading.value = true
  try {
    const res = await api.post('/auth/login', form.value)
    userStore.setToken(res.data.token)
    await userStore.fetchUserInfo()
    ElMessage.success('登录成功')
    router.push(userStore.isAdmin() ? '/admin' : '/home')
  } finally {
    loading.value = false
  }
}

const register = async () => {
  await api.post('/auth/register', regForm.value)
  ElMessage.success('注册成功，请登录')
  showRegister.value = false
}
</script>

<style scoped>
.login-bg {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea, #764ba2);
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-card {
  width: 400px;
  border-radius: 12px;
}
</style>