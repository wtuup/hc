<template>
  <el-card>
    <template #header>
      <span>购物车</span>
      <el-button style="float:right" type="danger" text @click="clearAll" :disabled="cartItems.length===0">清空</el-button>
    </template>
    <el-empty v-if="cartItems.length===0" description="购物车空空如也">
      <el-button type="primary" @click="$router.push('/home')">去逛逛</el-button>
    </el-empty>
    <el-table v-else :data="cartItems" @selection-change="handleSelect" v-loading="loading">
      <el-table-column type="selection" width="55" />
      <el-table-column label="图片" width="80">
        <template #default="{row}">
          <img :src="row.product?.image||'https://via.placeholder.com/60'" style="width:60px;height:60px;object-fit:cover;border-radius:4px" />
        </template>
      </el-table-column>
      <el-table-column label="商品名称">
        <template #default="{row}">
          <span style="cursor:pointer;color:#409EFF" @click="$router.push('/product/'+row.productId)">{{ row.product?.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单价" width="100">
        <template #default="{row}">¥{{ row.product?.price }}</template>
      </el-table-column>
      <el-table-column label="数量" width="160">
        <template #default="{row}">
          <el-input-number v-model="row.quantity" :min="1" size="small" @change="(v)=>updateQty(row,v)" />
        </template>
      </el-table-column>
      <el-table-column label="小计" width="100">
        <template #default="{row}">
          <span style="color:#e74c3c;font-weight:bold">¥{{ (row.product?.price * row.quantity).toFixed(2) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="80">
        <template #default="{row}">
          <el-button type="danger" link @click="removeItem(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-if="cartItems.length>0" style="margin-top:20px;display:flex;justify-content:flex-end;align-items:center;gap:16px">
      <span>已选 <b>{{ selected.length }}</b> 件</span>
      <span>合计：<span style="color:#e74c3c;font-size:20px;font-weight:bold">¥{{ selectedTotal }}</span></span>
      <el-button type="primary" size="large" :disabled="selected.length===0" @click="goCheckout">去结算</el-button>
    </div>
  </el-card>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '@/api'

const router = useRouter()
const cartItems = ref([])
const selected = ref([])
const loading = ref(false)

onMounted(loadCart)

async function loadCart() {
  loading.value = true
  try {
    cartItems.value = (await api.get('/cart')).data || []
  } finally {
    loading.value = false
  }
}

const selectedTotal = computed(() =>
    selected.value.reduce((s, i) => s + (i.product?.price ?? 0) * i.quantity, 0).toFixed(2)
)

const handleSelect = (val) => {
  selected.value = val
}

const updateQty = async (row, val) => {
  await api.put(`/cart/${row.id}`, { quantity: val })
}

const removeItem = async (id) => {
  await api.delete(`/cart/${id}`)
  ElMessage.success('已删除')
  loadCart()
}

const clearAll = async () => {
  await ElMessageBox.confirm('确认清空购物车？', '提示', { type: 'warning' })
  for (const item of cartItems.value) {
    await api.delete(`/cart/${item.id}`)
  }
  ElMessage.success('已清空')
  loadCart()
}

const goCheckout = () => {
  router.push({
    path: '/checkout',
    query: { ids: selected.value.map(i => i.id).join(',') }
  })
}
</script>