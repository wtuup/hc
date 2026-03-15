<template>
  <el-card>
    <template #header><span>用户管理</span></template>
    <el-table :data="users" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column label="角色" width="100">
        <template #default="{row}"><el-tag :type="row.role==='ADMIN'?'danger':'primary'">{{ row.role }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="createdAt" label="注册时间" />
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api'

const users = ref([])
const loading = ref(false)

onMounted(async () => {
  loading.value = true
  try {
    users.value = (await api.get('/admin/users')).data || []
  } finally {
    loading.value = false
  }
})
</script>