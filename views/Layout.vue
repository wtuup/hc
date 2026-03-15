<template>
  <el-container style="min-height:100vh">
    <el-header style="background:#409EFF;display:flex;align-items:center;justify-content:space-between;padding:0 20px">
      <span style="color:white;font-size:20px;font-weight:bold;cursor:pointer" @click="$router.push('/home')">
        🥬 生鲜销售系统
      </span>
      <el-menu mode="horizontal" background-color="#409EFF" text-color="#fff" active-text-color="#ffd04b" :router="true">
        <el-menu-item index="/home">首页</el-menu-item>
        <el-menu-item index="/cart">购物车</el-menu-item>
        <el-menu-item index="/orders">我的订单</el-menu-item>
        <el-menu-item index="/profile">个人中心</el-menu-item>
        <el-menu-item v-if="userStore.isAdmin()" index="/admin">管理后台</el-menu-item>
      </el-menu>
      <div>
        <span v-if="userStore.isLoggedIn()" style="color:white;margin-right:12px">
          {{ userStore.userInfo?.nickname || userStore.userInfo?.username }}
        </span>
        <el-button v-if="userStore.isLoggedIn()" size="small" @click="logout">退出</el-button>
        <el-button v-else size="small" @click="$router.push('/login')">登录</el-button>
      </div>
    </el-header>
    <el-main style="background:#f5f7fa;padding:20px">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const logout = () => {
  userStore.logout()
  ElMessage.success('已退出')
  router.push('/login')
}
</script>