<template>
  <el-card>
    <template #header><span>订单管理</span></template>
    <el-form inline style="margin-bottom:16px">
      <el-form-item label="状态">
        <el-select v-model="filter.status" clearable placeholder="全部" style="width:120px">
          <el-option label="已支付" value="PAID" />
          <el-option label="已发货" value="SHIPPED" />
          <el-option label="已完成" value="COMPLETED" />
          <el-option label="已取消" value="CANCELLED" />
        </el-select>
      </el-form-item>
      <el-form-item label="用户ID"><el-input v-model="filter.userId" style="width:120px" clearable /></el-form-item>
      <el-button type="primary" @click="load">查询</el-button>
    </el-form>
    <el-table :data="orders" v-loading="loading" stripe>
      <el-table-column prop="id" label="订单号" width="80" />
      <el-table-column prop="userId" label="用户ID" width="80" />
      <el-table-column label="金额" width="100"><template #default="{row}"><span style="color:#e74c3c">¥{{ row.totalAmount }}</span></template></el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="{row}">
          <el-tag :type="{PENDING:'warning',PAID:'primary',SHIPPED:'',COMPLETED:'success',CANCELLED:'info'}[row.status]">
            {{ {PENDING:'待支付',PAID:'已支付',SHIPPED:'已发货',COMPLETED:'已完成',CANCELLED:'已取消'}[row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="下单时间" />
      <el-table-column label="操作" width="180">
        <template #default="{row}">
          <el-button v-if="row.status==='PAID'" type="success" size="small" @click="openShip(row.id)">发货</el-button>
          <el-button v-if="row.status==='SHIPPED'" type="primary" size="small" @click="updateStatus(row.id,'COMPLETED')">完成</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <el-dialog title="录入物流信息" v-model="shipVisible" width="400px">
    <el-form :model="shipForm" label-width="90px">
      <el-form-item label="物流公司"><el-input v-model="shipForm.company" placeholder="如：顺丰" /></el-form-item>
      <el-form-item label="运单号"><el-input v-model="shipForm.trackingNumber" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="shipVisible=false">取消</el-button>
      <el-button type="primary" @click="confirmShip">确认发货</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import api from '@/api'

const orders = ref([])
const loading = ref(false)
const filter = ref({ status: '', userId: '' })
const shipVisible = ref(false)
const shipForm = ref({ orderId: null, company: '', trackingNumber: '' })

onMounted(load)

async function load() {
  loading.value = true
  try {
    const params = {}
    if (filter.value.status) params.status = filter.value.status
    if (filter.value.userId) params.userId = filter.value.userId
    orders.value = (await api.get('/admin/orders', { params })).data || []
  } finally {
    loading.value = false
  }
}

const openShip = (id) => {
  shipForm.value = { orderId: id, company: '', trackingNumber: '' }
  shipVisible.value = true
}

const confirmShip = async () => {
  if (!shipForm.value.company || !shipForm.value.trackingNumber) {
    return ElMessage.warning('请填写完整物流信息')
  }
  await api.post('/admin/logistics', {
    ...shipForm.value,
    orderId: String(shipForm.value.orderId)
  })
  ElMessage.success('发货成功')
  shipVisible.value = false
  load()
}

const updateStatus = async (id, status) => {
  await api.put(`/admin/orders/${id}/status`, null, { params: { status } })
  ElMessage.success('状态已更新')
  load()
}
</script>