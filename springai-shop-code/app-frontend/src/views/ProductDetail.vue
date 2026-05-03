<template>
  <MainLayout>
    <div class="product-detail container" v-if="product">
      <div class="detail-card card">
        <div class="detail-content">
          <!-- 商品图片 -->
          <div class="product-images">
            <div class="main-image">
              <img :src="product.mainImage" :alt="product.name" @error="handleImageError" />
            </div>
          </div>

          <!-- 商品信息 -->
          <div class="product-info">
            <h1 class="product-name">{{ product.name }}</h1>
            <p class="product-subtitle">{{ product.subtitle }}</p>
            
            <div class="price-section">
              <span class="price-label">价格:</span>
              <span class="price">¥{{ product.price }}</span>
            </div>

            <div class="info-item">
              <span class="label">销量:</span>
              <span>{{ product.sales }}</span>
            </div>

            <div class="info-item">
              <span class="label">库存:</span>
              <span>{{ product.stock > 0 ? '有货' : '缺货' }}</span>
            </div>

            <div class="quantity-section">
              <span class="label">数量:</span>
              <a-input-number
                v-model:value="quantity"
                :min="1"
                :max="product.stock"
                style="width: 100px"
              />
            </div>

            <div class="action-buttons">
              <a-button type="primary" size="large" @click="handleAddToCart">
                加入购物车
              </a-button>
              <a-button size="large" @click="handleBuyNow">
                立即购买
              </a-button>
            </div>
          </div>
        </div>

        <!-- 商品详情 -->
        <div class="detail-section">
          <h2>商品详情</h2>
          <div class="detail-html" v-html="product.detail || '暂无详情'"></div>
        </div>
      </div>
    </div>
  </MainLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import MainLayout from '@/components/MainLayout.vue'
import { getProductDetail } from '@/api/product'
import { useCartStore } from '@/stores/cart'
import { message } from 'ant-design-vue'

const route = useRoute()
const router = useRouter()
const cartStore = useCartStore()

const product = ref(null)
const quantity = ref(1)

// 获取商品详情
const fetchProductDetail = async () => {
  try {
    const res = await getProductDetail(route.params.id)
    product.value = res.data
  } catch (error) {
    message.error('获取商品详情失败')
  }
}

// 加入购物车
const handleAddToCart = async () => {
  if (!cartStore.token) {
    message.warning('请先登录')
    router.push('/login')
    return
  }

  try {
    await cartStore.addToCart(product.value.id, quantity.value)
    message.success('已添加到购物车')
  } catch (error) {
    message.error('添加失败')
  }
}

// 立即购买
const handleBuyNow = async () => {
  if (!cartStore.token) {
    message.warning('请先登录')
    router.push('/login')
    return
  }

  // 先加入购物车,然后跳转到购物车页面
  try {
    await cartStore.addToCart(product.value.id, quantity.value)
    router.push('/cart')
  } catch (error) {
    message.error('操作失败')
  }
}

// 图片加载失败处理
const handleImageError = (e) => {
  e.target.src = 'https://picsum.photos/seed/default/400/400'
}

onMounted(() => {
  fetchProductDetail()
})
</script>

<style scoped>
.product-detail {
  padding: 30px 20px;
}

.detail-card {
  padding: 30px;
}

.detail-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
  margin-bottom: 40px;
}

.product-images .main-image {
  width: 100%;
  height: 500px;
  border-radius: 8px;
  overflow: hidden;
}

.product-images img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.product-info {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.product-name {
  font-size: 28px;
  color: #333;
  margin: 0;
}

.product-subtitle {
  font-size: 16px;
  color: #999;
  margin: 0;
}

.price-section {
  display: flex;
  align-items: baseline;
  gap: 10px;
}

.price-label {
  font-size: 16px;
  color: #666;
}

.info-item {
  display: flex;
  gap: 10px;
  font-size: 14px;
}

.label {
  color: #999;
}

.quantity-section {
  display: flex;
  align-items: center;
  gap: 10px;
}

.action-buttons {
  display: flex;
  gap: 15px;
  margin-top: 20px;
}

.detail-section {
  border-top: 1px solid #eee;
  padding-top: 30px;
}

.detail-section h2 {
  font-size: 20px;
  margin-bottom: 20px;
}

.detail-html {
  line-height: 1.8;
  color: #666;
}
</style>
