<template>
  <el-card>
    <template #header><span>个人中心</span></template>
    <el-tabs v-model="tab">
      <el-tab-pane label="个人信息" name="info">
        <el-form :model="infoForm" label-width="100px" style="max-width:400px">
          <el-form-item label="用户名"><el-input v-model="infoForm.username" disabled /></el-form-item>
          <el-form-item label="昵称"><el-input v-model="infoForm.nickname" /></el-form-item>
          <el-form-item label="手机号"><el-input v-model="infoForm.phone" /></el-form-item>
          <el-form-item><el-button type="primary" @click="saveInfo">保存信息</el-button></el-form-item>
        </el-form>
        <el-divider>修改密码</el-divider>
        <el-form :model="pwdForm" label-width="100px" style="max-width:400px">
          <el-form-item label="原密码"><el-input type="password" v-model="pwdForm.oldPassword" show-password /></el-form-item>
          <el-form-item label="新密码"><el-input type="password" v-model="pwdForm.newPassword" show-password /></el-form-item>
          <el-form-item><el-button type="primary" @click="changePwd">修改密码</el-button></el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="收货地址" name="address">
        <el-button type="primary" @click="openAddrDialog(null)" style="margin-bottom:12px">+ 新增地址</el-button>
        <el-table :data="addresses">
          <el-table-column prop="receiverName" label="收货人" width="100" />
          <el-table-column prop="phone" label="手机号" width="130" />
          <el-table-column prop="detail" label="地址" />
          <el-table-column label="默认" width="80">
            <template #default="{row}"><el-tag v-if="row.isDefault" type="success" size="small">默认</el-tag></template>
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template #default="{row}">
              <el-button size="small" @click="openAddrDialog(row)">编辑</el-button>
              <el-button v-if="!row.isDefault" size="small" type="success" @click="setDefault(row.id)">设默认</el-button>
              <el-button size="small" type="danger" @click="delAddr(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="我的收藏" name="fav">
        <el-empty v-if="favorites.length===0" description="暂无收藏" />
        <el-row :gutter="16">
          <el-col :span="6" v-for="p in favorites" :key="p.id" style="margin-bottom:16px">
            <el-card :body-style="{padding:'0'}">
              <img :src="p.image||'https://via.placeholder.com/200'" style="width:100%;height:160px;object-fit:cover;cursor:pointer"
                   @click="$router.push('/product/'+p.id)" />
              <div style="padding:12px">
                <div>{{ p.name }}</div>
                <div style="color:#e74c3c">¥{{ p.price }}</div>
                <el-button size="small" type="danger" style="margin-top:8px;width:100%" @click="removeFav(p.id)">取消收藏</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>

      <el-tab-pane label="售后服务" name="aftersale">
        <el-empty v-if="afterSales.length===0" description="暂无售后记录" />
        <el-table :data="afterSales">
          <el-table-column prop="orderId" label="订单号" width="80" />
          <el-table-column label="类型" width="80"><template #default="{row}">{{ row.type==='RETURN'?'退货':'退款' }}</template></el-table-column>
          <el-table-column prop="reason" label="原因" />
          <el-table-column label="状态" width="100">
            <template #default="{row}">
              <el-tag :type="{PENDING:'warning',APPROVED:'success',REJECTED:'danger'}[row.status]">
                {{ {PENDING:'待处理',APPROVED:'已批准',REJECTED:'已拒绝'}[row.status] }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="applyTime" label="申请时间" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </el-card>

  <el-dialog :title="addrForm.id?'编辑地址':'新增地址'" v-model="addrVisible" width="400px">
    <el-form :model="addrForm" label-width="80px">
      <el-form-item label="收货人"><el-input v-model="addrForm.receiverName" /></el-form-item>
      <el-form-item label="手机号"><el-input v-model="addrForm.phone" /></el-form-item>
      <el-form-item label="详细地址"><el-input v-model="addrForm.detail" type="textarea" /></el-form-item>
      <el-form-item><el-checkbox v-model="addrForm.isDefault">设为默认</el-checkbox></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="addrVisible=false">取消</el-button>
      <el-button type="primary" @click="saveAddr">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import api from '@/api'

const userStore = useUserStore()
const tab = ref('info')
const addresses = ref([])
const favorites = ref([])
const afterSales = ref([])
const addrVisible = ref(false)
const addrForm = ref({})
const infoForm = ref({
  username: userStore.userInfo?.username || '',
  nickname: userStore.userInfo?.nickname || '',
  phone: userStore.userInfo?.phone || ''
})
const pwdForm = ref({ oldPassword: '', newPassword: '' })

onMounted(() => {
  loadAddresses()
  loadFavorites()
  loadAfterSales()
})

const loadAddresses = async () => {
  addresses.value = (await api.get('/addresses')).data || []
}
const loadFavorites = async () => {
  favorites.value = (await api.get('/favorites')).data || []
}
const loadAfterSales = async () => {
  afterSales.value = (await api.get('/after-sales/my')).data || []
}

const saveInfo = async () => {
  await api.put('/user/info', {
    nickname: infoForm.value.nickname,
    phone: infoForm.value.phone
  })
  ElMessage.success('保存成功')
  await userStore.fetchUserInfo()
}

const changePwd = async () => {
  await api.put('/user/password', pwdForm.value)
  ElMessage.success('密码已修改')
  pwdForm.value = { oldPassword: '', newPassword: '' }
}

const openAddrDialog = (row) => {
  addrForm.value = row ? { ...row } : { receiverName: '', phone: '', detail: '', isDefault: false }
  addrVisible.value = true
}

const saveAddr = async () => {
  if (addrForm.value.id) {
    await api.put(`/addresses/${addrForm.value.id}`, addrForm.value)
  } else {
    await api.post('/addresses', addrForm.value)
  }
  ElMessage.success('保存成功')
  addrVisible.value = false
  loadAddresses()
}

const setDefault = async (id) => {
  await api.put(`/addresses/${id}/default`)
  ElMessage.success('已设为默认')
  loadAddresses()
}

const delAddr = async (id) => {
  await api.delete(`/addresses/${id}`)
  ElMessage.success('已删除')
  loadAddresses()
}

const removeFav = async (productId) => {
  await api.delete(`/favorites/${productId}`)
  ElMessage.success('已取消收藏')
  loadFavorites()
}
</script>