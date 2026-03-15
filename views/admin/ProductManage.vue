<template>
  <el-card>
    <template #header>
      <span>商品管理</span>
      <el-button type="primary" style="float:right" @click="openDialog(null)">+ 新增商品</el-button>
    </template>
    <el-table :data="products" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column label="图片" width="80">
        <template #default="{row}">
          <img :src="row.image||'https://via.placeholder.com/60'" style="width:50px;height:50px;object-fit:cover;border-radius:4px" />
        </template>
      </el-table-column>
      <el-table-column prop="name" label="商品名称" />
      <el-table-column label="价格" width="100"><template #default="{row}">¥{{ row.price }}</template></el-table-column>
      <el-table-column label="库存" width="80">
        <template #default="{row}"><el-tag :type="row.stock<10?'danger':'success'">{{ row.stock }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="sales" label="销量" width="80" />
      <el-table-column label="操作" width="160">
        <template #default="{row}">
          <el-button size="small" @click="openDialog(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="del(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <el-dialog :title="form.id?'编辑商品':'新增商品'" v-model="visible" width="500px">
    <el-form :model="form" label-width="80px">
      <el-form-item label="商品名称"><el-input v-model="form.name" /></el-form-item>
      <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
      <el-form-item label="价格"><el-input-number v-model="form.price" :precision="2" :min="0" /></el-form-item>
      <el-form-item label="库存"><el-input-number v-model="form.stock" :min="0" /></el-form-item>
      <el-form-item label="分类ID"><el-input-number v-model="form.categoryId" :min="1" /></el-form-item>
      <el-form-item label="商品图片">
        <el-upload action="/api/products/upload" :headers="headers" :on-success="onUpload" :show-file-list="false">
          <el-button>点击上传</el-button>
        </el-upload>
        <img v-if="form.image" :src="form.image" style="width:80px;margin-left:12px;border-radius:4px" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible=false">取消</el-button>
      <el-button type="primary" @click="save">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '@/api'

const products = ref([])
const loading = ref(false)
const visible = ref(false)
const form = ref({})
const headers = { Authorization: 'Bearer ' + localStorage.getItem('token') }

onMounted(load)

async function load() {
  loading.value = true
  try {
    products.value = (await api.get('/products')).data || []
  } finally {
    loading.value = false
  }
}

const openDialog = (row) => {
  form.value = row ? { ...row } : { name: '', description: '', price: 0, stock: 0, categoryId: 1, image: '' }
  visible.value = true
}

const onUpload = (res) => {
  form.value.image = res.data
  ElMessage.success('图片上传成功')
}

const save = async () => {
  if (form.value.id) {
    await api.put(`/products/${form.value.id}`, form.value)
  } else {
    await api.post('/products', form.value)
  }
  ElMessage.success('保存成功')
  visible.value = false
  load()
}

const del = async (id) => {
  await ElMessageBox.confirm('确认删除此商品？', '警告', { type: 'warning' })
  await api.delete(`/products/${id}`)
  ElMessage.success('已删除')
  load()
}
</script>