<template>
  <div class="layout">
    <!-- 头部导航 -->
    <header class="header">
      <div class="container header-content">
        <div class="logo" @click="$router.push('/')">
          <h1>优品商城</h1>
        </div>
        
        <nav class="nav">
          <router-link to="/" class="nav-item">首页</router-link>
          <router-link to="/products" class="nav-item">全部商品</router-link>
        </nav>
        
        <div class="header-actions">
          <a-input-search
            v-model:value="searchKeyword"
            placeholder="搜索商品"
            style="width: 250px; margin-right: 20px"
            @search="handleSearch"
          />
          
          <router-link to="/cart" class="cart-icon">
            <a-badge :count="cartStore.cartCount" :offset="[5, 0]">
              <span style="font-size: 20px">🛒</span>
            </a-badge>
          </router-link>
          
          <template v-if="userStore.token">
            <a-dropdown>
              <a class="ant-dropdown-link" @click.prevent>
                {{ userStore.userInfo?.nickname || '用户' }}
              </a>
              <template #overlay>
                <a-menu>
                  <a-menu-item @click="$router.push('/orders')">
                    <span>我的订单</span>
                  </a-menu-item>
                  <a-menu-divider />
                  <a-menu-item @click="handleLogout">
                    <span>退出登录</span>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </template>
          <template v-else>
            <a-button type="link" @click="$router.push('/login')">登录</a-button>
            <a-button type="primary" @click="$router.push('/register')">注册</a-button>
          </template>
        </div>
      </div>
    </header>
    
    <!-- 主要内容 -->
    <main class="main-content">
      <slot />
    </main>
    
    <!-- 底部 -->
    <footer class="footer">
      <div class="container">
        <div class="footer-content">
          <div class="footer-section">
            <h3>关于我们</h3>
            <p>优品商城致力于为用户提供高品质的商品和优质的购物体验</p>
          </div>
          <div class="footer-section">
            <h3>客户服务</h3>
            <p>帮助中心</p>
            <p>售后服务</p>
            <p>联系我们</p>
          </div>
          <div class="footer-section">
            <h3>关注我们</h3>
            <p>微信公众号</p>
            <p>新浪微博</p>
          </div>
        </div>
        <div class="footer-bottom">
          <p>&copy; 2024 优品商城. All rights reserved.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useCartStore } from '@/stores/cart'
import { message } from 'ant-design-vue'

const router = useRouter()
const userStore = useUserStore()
const cartStore = useCartStore()

const searchKeyword = ref('')

// 搜索商品
const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({
      path: '/products',
      query: { keyword: searchKeyword.value },
    })
  }
}

// 退出登录
const handleLogout = () => {
  userStore.logout()
  cartStore.clearCart()
  message.success('已退出登录')
  router.push('/')
}
</script>

<style scoped>
.layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 70px;
}

.logo {
  cursor: pointer;
}

.logo h1 {
  font-size: 24px;
  color: #ff6b35;
  margin: 0;
}

.nav {
  display: flex;
  gap: 30px;
}

.nav-item {
  color: #333;
  text-decoration: none;
  font-size: 16px;
  transition: color 0.3s;
}

.nav-item:hover,
.nav-item.router-link-active {
  color: #ff6b35;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

.cart-icon {
  color: #333;
  text-decoration: none;
}

.main-content {
  flex: 1;
  padding: 20px 0;
}

.footer {
  background: #333;
  color: #fff;
  padding: 40px 0 20px;
  margin-top: 40px;
}

.footer-content {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 40px;
  margin-bottom: 30px;
}

.footer-section h3 {
  font-size: 16px;
  margin-bottom: 15px;
}

.footer-section p {
  font-size: 14px;
  color: #999;
  margin: 8px 0;
  cursor: pointer;
  transition: color 0.3s;
}

.footer-section p:hover {
  color: #fff;
}

.footer-bottom {
  text-align: center;
  padding-top: 20px;
  border-top: 1px solid #444;
}

.footer-bottom p {
  color: #999;
  font-size: 14px;
}
</style>
