<template>
  <div v-loading="loading">
    <el-row :gutter="24" v-if="product">
      <el-col :span="10">
        <img :src="product.image||'https://via.placeholder.com/400x400?text=生鲜'" style="width:100%;border-radius:8px" />
      </el-col>
      <el-col :span="14">
        <h2>{{ product.name }}</h2>
        <div style="color:#e74c3c;font-size:28px;font-weight:bold;margin:12px 0">¥{{ product.price }}</div>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="库存">
            <el-tag :type="product.stock>10?'success':'danger'">{{ product.stock }} 件</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="销量">{{ product.sales }}</el-descriptions-item>
        </el-descriptions>
        <p style="color:#666;margin:16px 0">{{ product.description }}</p>
        <el-input-number v-model="qty" :min="1" :max="product.stock" style="margin-right:12px" />
        <el-button type="primary" size="large" @click="addToCart">加入购物车</el-button>
        <el-button size="large" :type="isFav?'warning':''" @click="toggleFav">
          {{ isFav ? '❤️ 已收藏' : '🤍 收藏' }}
        </el-button>
      </el-col>
    </el-row>

    <el-card style="margin-top:24px">
      <template #header>商品评价（{{ reviews.length }}条）</template>
      <el-empty v-if="reviews.length===0" description="暂无评价" />
      <div v-for="r in reviews" :key="r.id" style="border-bottom:1px solid #eee;padding:12px 0">
        <el-rate :model-value="r.rating" disabled />
        <p style="margin:8px 0">{{ r.comment }}</p>
        <span style="color:#999;font-size:12px">{{ r.createTime }}</span>
      </div>
      <div v-if="userStore.isLoggedIn()" style="margin-top:20px;background:#f9f9f9;padding:16px;border-radius:8px">
        <h4>写评价</h4>
        <el-rate v-model="newReview.rating" style="margin-bottom:12px" />
        <el-input v-model="newReview.comment" type="textarea" :rows="3" placeholder="分享你的购买体验..." />
        <el-button type="primary" style="margin-top:12px" @click="submitReview">提交评价</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import api from '@/api'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const product = ref(null)
const reviews = ref([])
const isFav = ref(false)
const loading = ref(true)
const qty = ref(1)
const newReview = ref({ rating: 5, comment: '' })

onMounted(async () => {
  try {
    const [p, r] = await Promise.all([
      api.get(`/products/${route.params.id}`),
      api.get(`/reviews/product/${route.params.id}`)
    ])
    product.value = p.data
    reviews.value = r.data || []
    if (userStore.isLoggedIn()) {
      isFav.value = (await api.get(`/favorites/check/${route.params.id}`)).data
    }
  } finally {
    loading.value = false
  }
})

const addToCart = async () => {
  if (!userStore.isLoggedIn()) return router.push('/login')
  await api.post('/cart/add', { productId: product.value.id, quantity: qty.value })
  ElMessage.success('已加入购物车')
}

const toggleFav = async () => {
  if (!userStore.isLoggedIn()) return router.push('/login')
  if (isFav.value) {
    await api.delete(`/favorites/${product.value.id}`)
    ElMessage.success('已取消收藏')
  } else {
    await api.post('/favorites', { productId: product.value.id })
    ElMessage.success('收藏成功')
  }
  isFav.value = !isFav.value
}

const submitReview = async () => {
  if (!newReview.value.comment.trim()) return ElMessage.warning('请输入评价内容')
  await api.post('/reviews', {
    ...newReview.value,
    productId: parseInt(route.params.id)
  })
  ElMessage.success('评价成功！')
  newReview.value = { rating: 5, comment: '' }
  reviews.value = (await api.get(`/reviews/product/${route.params.id}`)).data || []
}
</script>