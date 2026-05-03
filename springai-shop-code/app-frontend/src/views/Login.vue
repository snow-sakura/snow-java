<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-header">
        <h1 @click="$router.push('/')">优品商城</h1>
        <p>品质生活优选</p>
      </div>

      <a-card class="login-card">
        <h2>用户登录</h2>
        <a-form
          :model="form"
          :rules="rules"
          ref="formRef"
          @finish="handleLogin"
        >
          <a-form-item name="username">
            <a-input
              v-model:value="form.username"
              placeholder="请输入用户名"
              size="large"
            >
              <template #prefix>👤</template>
            </a-input>
          </a-form-item>

          <a-form-item name="password">
            <a-input-password
              v-model:value="form.password"
              placeholder="请输入密码"
              size="large"
            >
              <template #prefix>🔒</template>
            </a-input-password>
          </a-form-item>

          <a-form-item>
            <div class="remember-me">
              <a-checkbox v-model:checked="rememberMe">记住我</a-checkbox>
            </div>
          </a-form-item>

          <a-form-item>
            <a-button
              type="primary"
              html-type="submit"
              size="large"
              block
              :loading="loading"
            >
              登录
            </a-button>
          </a-form-item>

          <div class="login-footer">
            <span>还没有账号?</span>
            <router-link to="/register">立即注册</router-link>
          </div>
        </a-form>
      </a-card>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/user'
import { useUserStore } from '@/stores/user'
import { useCartStore } from '@/stores/cart'
import { message } from 'ant-design-vue'

const router = useRouter()
const userStore = useUserStore()
const cartStore = useCartStore()

const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  username: '',
  password: '',
})

const rememberMe = ref(false)

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少6位', trigger: 'blur' },
  ],
}

// 登录处理
const handleLogin = async () => {
  loading.value = true
  
  try {
    const res = await login(form)
    
    // 保存token和用户信息
    userStore.setToken(res.data.token)
    userStore.setUserInfo({
      userId: res.data.userId,
      username: res.data.username,
      nickname: res.data.nickname,
      avatar: res.data.avatar,
    })
    
    // 如果勾选了"记住我",保存用户名
    if (rememberMe.value) {
      localStorage.setItem('rememberedUsername', form.username)
    } else {
      localStorage.removeItem('rememberedUsername')
    }
    
    message.success('登录成功')
    
    // 加载购物车
    await cartStore.fetchCartList()
    
    // 跳转到首页
    setTimeout(() => {
      router.push('/')
    }, 500)
  } catch (error) {
    console.error('登录失败:', error)
    // 错误信息由全局拦截器处理
  } finally {
    loading.value = false
  }
}

// 页面加载时恢复记住的用户名
const loadRememberedUsername = () => {
  const rememberedUsername = localStorage.getItem('rememberedUsername')
  if (rememberedUsername) {
    form.username = rememberedUsername
    rememberMe.value = true
  }
}

onMounted(() => {
  loadRememberedUsername()
})
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-container {
  width: 100%;
  max-width: 450px;
  padding: 20px;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
  color: #fff;
}

.login-header h1 {
  font-size: 36px;
  margin-bottom: 10px;
  cursor: pointer;
}

.login-header p {
  font-size: 16px;
  opacity: 0.9;
}

.login-card {
  padding: 30px;
}

.login-card h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}

.login-footer {
  text-align: center;
  margin-top: 20px;
  color: #666;
}

.login-footer a {
  color: #667eea;
  margin-left: 5px;
  text-decoration: none;
}

.login-footer a:hover {
  text-decoration: underline;
}

.remember-me {
  margin-bottom: 10px;
}
</style>
