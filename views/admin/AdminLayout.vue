<template>
  <el-container style="min-height:100vh">
    <el-aside width="200px" style="background:#304156">
      <div style="color:white;text-align:center;padding:20px;font-size:18px;font-weight:bold">🛠 管理后台</div>
      <el-menu router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF" :default-active="$route.path">
        <el-menu-item index="/admin/products"><el-icon><Goods /></el-icon>商品管理</el-menu-item>
        <el-menu-item index="/admin/orders"><el-icon><List /></el-icon>订单管理</el-menu-item>
        <el-menu-item index="/admin/stock"><el-icon><Box /></el-icon>库存管理</el-menu-item>
        <el-menu-item index="/admin/stats"><el-icon><TrendCharts /></el-icon>数据统计</el-menu-item>
        <el-menu-item index="/admin/users"><el-icon><User /></el-icon>用户管理</el-menu-item>
        <el-menu-item index="/home"><el-icon><House /></el-icon>前台首页</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="background:white;box-shadow:0 1px 4px rgba(0,21,41,.08);display:flex;align-items:center;justify-content:flex-end">
        <span style="margin-right:12px">{{ userStore.userInfo?.nickname }}（管理员）</span>
        <el-button @click="logout" size="small">退出</el-button>
      </el-header>
      <el-main style="background:#f0f2f5;padding:20px"><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import { Goods, List, Box, TrendCharts, User, House } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const logout = () => {
  userStore.logout()
  ElMessage.success('已退出')
  router.push('/login')
}
</script>