<template>
  <MainLayout>
    <div class="home">
      <!-- Banner区域 -->
      <section class="banner">
        <div class="container">
          <a-carousel autoplay>
            <div v-for="item in banners" :key="item.id" class="banner-item">
              <img :src="item.image" :alt="item.title" />
              <div class="banner-text">
                <h2>{{ item.title }}</h2>
                <p>{{ item.subtitle }}</p>
              </div>
            </div>
          </a-carousel>
        </div>
      </section>

      <!-- 分类展示 -->
      <section class="categories container">
        <h2 class="section-title">热门分类</h2>
        <div class="category-grid">
          <div
            v-for="category in categories"
            :key="category.id"
            class="category-card"
            @click="goToCategory(category.id)"
          >
            <div class="category-icon">{{ category.icon }}</div>
            <div class="category-name">{{ category.name }}</div>
          </div>
        </div>
      </section>

      <!-- 推荐商品 -->
      <section class="products container">
        <h2 class="section-title">精选好物</h2>
        <div class="product-grid">
          <div
            v-for="product in products"
            :key="product.id"
            class="product-card card"
            @click="goToProduct(product.id)"
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
        
        <div class="more-btn">
          <a-button type="primary" size="large" @click="$router.push('/products')">
            查看更多
          </a-button>
        </div>
      </section>

      <!-- AI助手悬浮按钮 -->
      <div class="ai-assistant" @click="showAiChat = true">
        <span style="font-size: 24px">🤖</span>
        <span class="ai-text">AI助手</span>
      </div>

      <!-- AI客服对话框 -->
      <a-modal
        v-model:open="showAiChat"
        title="AI智能客服"
        :footer="null"
        width="600px"
      >
        <div class="chat-container">
          <div class="chat-messages" ref="chatMessagesRef">
            <div
              v-for="(msg, index) in chatMessages"
              :key="index"
              :class="['message', msg.type]"
            >
              {{ msg.content }}
            </div>
          </div>
          <div class="chat-input">
            <a-input
              v-model:value="inputMessage"
              placeholder="输入您的问题..."
              @pressEnter="sendMessage"
            >
              <template #suffix>
                <a-button type="primary" @click="sendMessage">发送</a-button>
              </template>
            </a-input>
          </div>
        </div>
      </a-modal>
    </div>
  </MainLayout>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import MainLayout from '@/components/MainLayout.vue'
import { getProductPage } from '@/api/product'
import { chat } from '@/api/ai'
import { message } from 'ant-design-vue'

const router = useRouter()

// Banner数据
const banners = ref([
  {
    id: 1,
    image: 'https://picsum.photos/seed/banner1/1200/400',
    title: '品质生活优选',
    subtitle: '精选全球好物,打造品质生活',
  },
  {
    id: 2,
    image: 'https://picsum.photos/seed/banner2/1200/400',
    title: '新品首发',
    subtitle: '最新潮流,抢先体验',
  },
])

// 分类数据
const categories = ref([
  { id: 1, name: '家居生活', icon: '🏠' },
  { id: 2, name: '数码电器', icon: '📱' },
  { id: 3, name: '服饰鞋包', icon: '👕' },
  { id: 4, name: '食品生鲜', icon: '🍎' },
  { id: 5, name: '美妆个护', icon: '💄' },
])

// 商品列表
const products = ref([])

// AI客服相关
const showAiChat = ref(false)
const chatMessages = ref([
  { type: 'ai', content: '您好!我是AI客服小优,有什么可以帮您的吗?' },
])
const inputMessage = ref('')
const chatMessagesRef = ref(null)

// 获取商品列表
const fetchProducts = async () => {
  try {
    const res = await getProductPage({ current: 1, size: 8 })
    products.value = res.data.records
  } catch (error) {
    console.error('获取商品失败:', error)
  }
}

// 跳转到分类
const goToCategory = (categoryId) => {
  router.push({
    path: '/products',
    query: { categoryId },
  })
}

// 跳转到商品详情
const goToProduct = (productId) => {
  router.push(`/product/${productId}`)
}

// 图片加载失败处理
const handleImageError = (e) => {
  e.target.src = 'https://picsum.photos/seed/default/400/400'
}

// 发送消息
const sendMessage = async () => {
  if (!inputMessage.value.trim()) return

  // 添加用户消息
  chatMessages.value.push({
    type: 'user',
    content: inputMessage.value,
  })

  const userMessage = inputMessage.value
  inputMessage.value = ''

  try {
    // 调用AI接口
    const res = await chat(userMessage)
    chatMessages.value.push({
      type: 'ai',
      content: res.data,
    })

    // 滚动到底部
    await nextTick()
    if (chatMessagesRef.value) {
      chatMessagesRef.value.scrollTop = chatMessagesRef.value.scrollHeight
    }
  } catch (error) {
    message.error('发送失败,请重试')
  }
}

onMounted(() => {
  fetchProducts()
})
</script>

<style scoped>
.home {
  min-height: calc(100vh - 70px);
}

.banner {
  margin-bottom: 40px;
}

.banner-item {
  position: relative;
  height: 400px;
  overflow: hidden;
}

.banner-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.banner-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  color: #fff;
}

.banner-text h2 {
  font-size: 48px;
  margin-bottom: 10px;
}

.banner-text p {
  font-size: 20px;
}

.section-title {
  font-size: 28px;
  margin: 40px 0 30px;
  text-align: center;
  color: #333;
}

.category-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 20px;
}

.category-card {
  background: #fff;
  padding: 30px;
  border-radius: 8px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
}

.category-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.category-icon {
  font-size: 48px;
  margin-bottom: 10px;
}

.category-name {
  font-size: 16px;
  color: #333;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
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

.more-btn {
  text-align: center;
  margin-top: 40px;
}

.ai-assistant {
  position: fixed;
  right: 30px;
  bottom: 30px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  padding: 15px 20px;
  border-radius: 50px;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
  transition: all 0.3s;
  display: flex;
  align-items: center;
  gap: 8px;
}

.ai-assistant:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 16px rgba(102, 126, 234, 0.6);
}

.ai-text {
  font-size: 14px;
}

.chat-container {
  height: 400px;
  display: flex;
  flex-direction: column;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 15px;
  background: #f5f5f5;
  border-radius: 8px;
  margin-bottom: 15px;
}

.message {
  margin-bottom: 15px;
  padding: 10px 15px;
  border-radius: 8px;
  max-width: 70%;
  word-wrap: break-word;
}

.message.user {
  background: #667eea;
  color: #fff;
  margin-left: auto;
}

.message.ai {
  background: #fff;
  color: #333;
}

.chat-input {
  display: flex;
  gap: 10px;
}
</style>
