<template>
  <MainLayout>
    <div class="product-list container">
      <div class="filter-bar">
        <a-space size="large">
          <a-select
            v-model:value="categoryId"
            placeholder="选择分类"
            style="width: 150px"
            @change="handleFilterChange"
          >
            <a-select-option :value="undefined">全部分类</a-select-option>
            <a-select-option v-for="cat in categories" :key="cat.id" :value="cat.id">
              {{ cat.name }}
            </a-select-option>
          </a-select>
          
          <a-input-search
            v-model:value="keyword"
            placeholder="搜索商品"
            style="width: 300px"
            @search="handleSearch"
          />
        </a-space>
      </div>

      <div class="product-grid">
        <div
          v-for="product in products"
          :key="product.id"
          class="product-card card"
          @click="goToDetail(product.id)"
        >
          <div class="product-image">
            <img :src="product.mainImage" :alt="product.name" @error="handleImageError" />
          </div>
          <div class="product-info">
            <h3 class="product-name text-ellipsis">{{ product.name }}</h3>
            <p class="product-subtitle text-ellipsis">{{ product.subtitle }}</p>
            <div class="product-footer">
              <span class="price">¥{{ product.price }}</span>
              <span class="sales">已售{{ product.sales }}</span>
            </div>
          </div>
        </div>
      </div>

      <div class="pagination">
        <a-pagination
          v-model:current="current"
          v-model:page-size="pageSize"
          :total="total"
          show-size-changer
          @change="fetchProducts"
        />
      </div>
    </div>
  </MainLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import MainLayout from '@/components/MainLayout.vue'
import { getProductPage } from '@/api/product'
import { message } from 'ant-design-vue'

const route = useRoute()
const router = useRouter()

const products = ref([])
const categories = ref([
  { id: 1, name: '家居生活' },
  { id: 2, name: '数码电器' },
  { id: 3, name: '服饰鞋包' },
  { id: 4, name: '食品生鲜' },
  { id: 5, name: '美妆个护' },
])

const current = ref(1)
const pageSize = ref(12)
const total = ref(0)
const categoryId = ref(undefined)
const keyword = ref('')

// 获取商品列表
const fetchProducts = async () => {
  try {
    const params = {
      current: current.value,
      size: pageSize.value,
      categoryId: categoryId.value,
      keyword: keyword.value,
    }
    
    const res = await getProductPage(params)
    products.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    message.error('获取商品列表失败')
  }
}

// 筛选变化
const handleFilterChange = () => {
  current.value = 1
  fetchProducts()
}

// 搜索
const handleSearch = () => {
  current.value = 1
  fetchProducts()
}

// 跳转到详情
const goToDetail = (productId) => {
  router.push(`/product/${productId}`)
}

// 图片加载失败处理
const handleImageError = (e) => {
  e.target.src = 'https://picsum.photos/seed/default/400/400'
}

onMounted(() => {
  // 从URL参数中获取筛选条件
  if (route.query.categoryId) {
    categoryId.value = parseInt(route.query.categoryId)
  }
  if (route.query.keyword) {
    keyword.value = route.query.keyword
  }
  
  fetchProducts()
})
</script>

<style scoped>
.product-list {
  min-height: calc(100vh - 70px);
}

.filter-bar {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.product-card {
  cursor: pointer;
  transition: all 0.3s;
}

.product-card:hover {
  transform: translateY(-5px);
}

.product-image {
  width: 100%;
  height: 250px;
  overflow: hidden;
}

.product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.product-card:hover .product-image img {
  transform: scale(1.05);
}

.product-info {
  padding: 15px;
}

.product-name {
  font-size: 16px;
  margin-bottom: 8px;
  color: #333;
}

.product-subtitle {
  font-size: 14px;
  color: #999;
  margin-bottom: 10px;
}

.product-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.sales {
  font-size: 12px;
  color: #999;
}

.pagination {
  text-align: center;
  padding: 20px 0;
}
</style>
