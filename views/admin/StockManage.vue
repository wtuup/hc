<template>
  <el-card>
    <template #header>
      <span>库存管理</span>
      <span style="float:right;color:#666;font-size:14px">
        预警阈值：<el-input-number v-model="threshold" :min="1" size="small" style="width:80px" /> 件
      </span>
    </template>
    <el-table :data="products" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="name" label="商品名称" />
      <el-table-column label="当前库存" width="150">
        <template #default="{row}">
          <el-tag :type="row.stock<threshold?'danger':'success'" size="large">
            {{ row.stock }} 件 {{ row.stock<threshold?'⚠️ 库存不足':'' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="sales" label="总销量" width="80" />
      <el-table-column label="修改库存" width="200">
        <template #default="{row}">
          <el-input-number v-model="newStocks[row.id]" :min="0" size="small" style="width:120px" />
          <el-button size="small" type="primary" style="margin-left:8px" @click="updateStock(row.id)">更新</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import api from '@/api'

const products = ref([])
const loading = ref(false)
const newStocks = ref({})
const threshold = ref(10)

onMounted(load)

async function load() {
  loading.value = true
  try {
    products.value = (await api.get('/products')).data || []
    products.value.forEach(p => {
      newStocks.value[p.id] = p.stock
    })
  } finally {
    loading.value = false
  }
}

const updateStock = async (id) => {
  await api.put(`/products/${id}/stock`, null, { params: { stock: newStocks.value[id] } })
  ElMessage.success('库存已更新')
  load()
}
</script>