<template>
  <el-card>
    <template #header><span>确认订单</span></template>
    <el-form :model="form" label-width="100px">
      <el-form-item label="收货地址" required>
        <el-select v-model="form.addressId" placeholder="请选择地址" style="width:320px">
          <el-option v-for="a in addresses" :key="a.id" :label="`${a.receiverName} ${a.phone} | ${a.detail}`" :value="a.id" />
        </el-select>
        <el-button text type="primary" style="margin-left:8px" @click="addAddrVisible=true">+ 新增地址</el-button>
      </el-form-item>
      <el-form-item label="配送方式">
        <el-radio-group v-model="form.deliveryMethod">
          <el-radio value="EXPRESS">普通快递</el-radio>
          <el-radio value="NEXT_DAY">次日达（+5元）</el-radio>
          <el-radio value="SAME_DAY">当日达（+10元）</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="支付方式">
        <el-radio-group v-model="form.paymentMethod">
          <el-radio value="ALIPAY">💙 支付宝（模拟）</el-radio>
          <el-radio value="WECHAT">💚 微信支付（模拟）</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="large" :loading="submitting" @click="submit">立即下单</el-button>
        <el-button @click="$router.push('/cart')">返回购物车</el-button>
      </el-form-item>
    </el-form>
  </el-card>

  <el-dialog title="新增收货地址" v-model="addAddrVisible" width="400px">
    <el-form :model="newAddr" label-width="80px">
      <el-form-item label="收货人"><el-input v-model="newAddr.receiverName" /></el-form-item>
      <el-form-item label="手机号"><el-input v-model="newAddr.phone" /></el-form-item>
      <el-form-item label="详细地址"><el-input v-model="newAddr.detail" type="textarea" /></el-form-item>
      <el-form-item><el-checkbox v-model="newAddr.isDefault">设为默认地址</el-checkbox></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="addAddrVisible=false">取消</el-button>
      <el-button type="primary" @click="saveAddr">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import api from '@/api'

const router = useRouter()
const route = useRoute()
const addresses = ref([])
const submitting = ref(false)
const addAddrVisible = ref(false)
const form = ref({
  addressId: null,
  deliveryMethod: 'EXPRESS',
  paymentMethod: 'ALIPAY',
  cartItemIds: route.query.ids ? route.query.ids.split(',').map(Number) : null
})
const newAddr = ref({ receiverName: '', phone: '', detail: '', isDefault: false })

onMounted(loadAddresses)

async function loadAddresses() {
  addresses.value = (await api.get('/addresses')).data || []
  const def = addresses.value.find(a => a.isDefault)
  form.value.addressId = def ? def.id : (addresses.value[0]?.id || null)
}

const saveAddr = async () => {
  await api.post('/addresses', newAddr.value)
  ElMessage.success('地址已添加')
  addAddrVisible.value = false
  await loadAddresses()
}

const submit = async () => {
  if (!form.value.addressId) return ElMessage.warning('请选择收货地址')
  submitting.value = true
  try {
    await api.post('/orders', form.value)
    ElMessage.success('🎉 下单成功！支付模拟完成')
    router.push('/orders')
  } finally {
    submitting.value = false
  }
}
</script>