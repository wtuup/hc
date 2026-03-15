<template>
  <div>
    <el-card style="margin-bottom:20px">
      <el-form inline>
        <el-form-item label="搜索">
          <el-input v-model="filters.keyword" placeholder="商品名称" clearable style="width:200px" />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="filters.categoryId" placeholder="全部" clearable style="width:120px">
            <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="filters.minPrice" placeholder="最低" style="width:80px" />
          <span style="margin:0 8px">~</span>
          <el-input v-model="filters.maxPrice" placeholder="最高" style="width:80px" />
        </el-form-item>
        <el-form-item label="排序">
          <el-select v-model="filters.sortBy" style="width:100px">
            <el-option label="默认" value="" />
            <el-option label="价格" value="price" />
            <el-option label="销量" value="sales" />
          </el-select>
          <el-button-group style="margin-left:8px">
            <el-button :type="!filters.asc?'primary':''" @click="filters.asc=false">↓降序</el-button>
            <el-button :type="filters.asc?'primary':''" @click="filters.asc=true">↑升序</el-button>
          </el-button-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search" :loading="loading">搜索</el-button>
          <el-button @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <div v-loading="loading">
      <el-empty v-if="products.length===0" description="暂无商品" />
      <el-row :gutter="16">
        <el-col :span="6" v-for="item in products" :key="item.id" style="margin-bottom:16px">
          <el-card :body-style="{padding:'0'}" hoverable>
            <img :src="item.image||'https://via.placeholder.com/300x200?text=生鲜'" class="product-img"
                 @click="$router.push('/product/'+item.id)" style="cursor:pointer" />
            <div style="padding:14px">
              <div style="font-weight:bold;margin-bottom:8px;cursor:pointer" @click="$router.push('/product/'+item.id)">{{ item.name }}</div>
              <div style="color:#e74c3c;font-size:18px;font-weight:bold">¥{{ item.price }}</div>
              <div style="color:#999;font-size:12px;margin:4px 0">销量：{{ item.sales }}</div>
              <el-button type="primary" style="width:100%;margin-top:8px" @click="addToCart(item)">加入购物车</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import api from '@/api'

const router = useRouter()
const userStore = useUserStore()
const products = ref([])
const categories = ref([])
const loading = ref(false)
const filters = ref({
  keyword: '',
  categoryId: null,
  sortBy: 'sales',
  asc: false,
  minPrice: '',
  maxPrice: ''
})

onMounted(() => {
  loadCategories()
  search()
})

const loadCategories = async () => {
  categories.value = (await api.get('/categories')).data || []
}

const search = async () => {
  loading.value = true
  try {
    const params = { ...filters.value }
    if (!params.minPrice) delete params.minPrice
    if (!params.maxPrice) delete params.maxPrice
    if (!params.categoryId) delete params.categoryId
    if (!params.sortBy) delete params.sortBy
    products.value = (await api.get('/products', { params })).data || []
  } finally {
    loading.value = false
  }
}

const reset = () => {
  filters.value = {
    keyword: '',
    categoryId: null,
    sortBy: 'sales',
    asc: false,
    minPrice: '',
    maxPrice: ''
  }
  search()
}

const addToCart = async (product) => {
  if (!userStore.isLoggedIn()) return router.push('/login')
  await api.post('/cart/add', { productId: product.id, quantity: 1 })
  ElMessage.success('已加入购物车 🛒')
}
</script>

<style scoped>
.product-img {
  width: 100%;
  height: 200px;
  object-fit: cover;
}
</style>