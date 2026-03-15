<template>
  <el-card>
    <template #header><span>我的订单</span></template>
    <el-empty v-if="orders.length===0" description="暂无订单">
      <el-button type="primary" @click="$router.push('/home')">去购物</el-button>
    </el-empty>
    <el-table :data="orders" v-loading="loading">
      <el-table-column prop="id" label="订单号" width="80" />
      <el-table-column label="金额" width="120">
        <template #default="{row}"><span style="color:#e74c3c;font-weight:bold">¥{{ row.totalAmount }}</span></template>
      </el-table-column>
      <el-table-column label="状态" width="120">
        <template #default="{row}">
          <el-tag :type="{PENDING:'warning',PAID:'primary',SHIPPED:'',COMPLETED:'success',CANCELLED:'info'}[row.status]">
            {{ {PENDING:'待支付',PAID:'已支付',SHIPPED:'已发货',COMPLETED:'已完成',CANCELLED:'已取消'}[row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="支付方式" width="100">
        <template #default="{row}">{{ row.paymentMethod==='ALIPAY'?'支付宝':'微信支付' }}</template>
      </el-table-column>
      <el-table-column prop="createTime" label="下单时间" />
      <el-table-column label="操作" width="180">
        <template #default="{row}">
          <el-button v-if="row.status==='PENDING'||row.status==='PAID'" type="danger" size="small" @click="cancel(row.id)">取消</el-button>
          <el-button v-if="row.status==='COMPLETED'" type="warning" size="small" @click="applyAfterSale(row.id)">申请售后</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '@/api'

const orders = ref([])
const loading = ref(false)

onMounted(load)

async function load() {
  loading.value = true
  try {
    orders.value = (await api.get('/orders/my')).data || []
  } finally {
    loading.value = false
  }
}

const cancel = async (id) => {
  await ElMessageBox.confirm('确认取消该订单？', '提示', { type: 'warning' })
  await api.put(`/orders/${id}/cancel`)
  ElMessage.success('已取消')
  load()
}

const applyAfterSale = async (id) => {
  const { value: reason } = await ElMessageBox.prompt('请输入售后原因', '申请售后', {
    inputPlaceholder: '如：商品损坏、与描述不符...',
    confirmButtonText: '提交',
    cancelButtonText: '取消'
  })
  await api.post('/after-sales', { orderId: id, reason, type: 'RETURN' })
  ElMessage.success('售后申请已提交')
}
</script>