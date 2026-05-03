<template>
  <div class="register-page">
    <div class="register-container">
      <div class="register-header">
        <h1 @click="$router.push('/')">优品商城</h1>
        <p>品质生活优选</p>
      </div>

      <a-card class="register-card">
        <h2>用户注册</h2>
        <a-form
          :model="form"
          :rules="rules"
          ref="formRef"
          layout="vertical"
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

          <a-form-item name="confirmPassword">
            <a-input-password
              v-model:value="form.confirmPassword"
              placeholder="请确认密码"
              size="large"
            >
              <template #prefix>🔒</template>
            </a-input-password>
          </a-form-item>

          <a-form-item name="nickname">
            <a-input
              v-model:value="form.nickname"
              placeholder="请输入昵称(选填)"
              size="large"
            >
              <template #prefix>😊</template>
            </a-input>
          </a-form-item>

          <a-form-item name="phone">
            <a-input
              v-model:value="form.phone"
              placeholder="请输入手机号(选填)"
              size="large"
            >
              <template #prefix>📱</template>
            </a-input>
          </a-form-item>

          <a-form-item name="email">
            <a-input
              v-model:value="form.email"
              placeholder="请输入邮箱(选填)"
              size="large"
            >
              <template #prefix>✉️</template>
            </a-input>
          </a-form-item>

          <a-form-item>
            <a-button
              type="primary"
              size="large"
              block
              :loading="loading"
              @click.prevent="handleRegister"
            >
              注册
            </a-button>
          </a-form-item>

          <div class="register-footer">
            <span>已有账号?</span>
            <router-link to="/login">立即登录</router-link>
          </div>
        </a-form>
      </a-card>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api/user'
import { message } from 'ant-design-vue'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  nickname: '',
  phone: '',
  email: '',
})

// 验证密码一致性
const validateConfirmPassword = ({ getFieldValue }) => ({
  validator(_, value) {
    if (!value || getFieldValue('password') === value) {
      return Promise.resolve()
    }
    return Promise.reject(new Error('两次输入的密码不一致'))
  },
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度为3-20个字符', trigger: 'blur' },
    {
      pattern: /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
      message: '用户名只能包含字母、数字、下划线和中文',
      trigger: 'blur',
    },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: ['blur', 'change'] },
  ],
  nickname: [
    { max: 50, message: '昵称长度不能超过50个字符', trigger: 'blur' },
  ],
  phone: [
    {
      pattern: /^1[3-9]\d{9}$/,
      message: '请输入正确的11位手机号',
      trigger: 'blur',
    },
  ],
  email: [
    {
      type: 'email',
      message: '请输入正确的邮箱地址',
      trigger: 'blur',
    },
  ],
}

// 注册处理
const handleRegister = async () => {
  alert('按钮点击了!')
  console.log('===== 注册按钮被点击 =====')
  
  try {
    // 手动验证表单
    await formRef.value.validate()
    
    loading.value = true
    console.log('开始注册,表单数据:', form)
    
    // 去除空字符串字段
    const registerData = {}
    Object.keys(form).forEach(key => {
      if (key !== 'confirmPassword') {
        const value = form[key]
        if (value !== '' && value !== null && value !== undefined) {
          registerData[key] = value
        }
      }
    })
    
    console.log('发送到后端的数据:', registerData)
    await register(registerData)
    
    message.success('注册成功,请登录')
    
    // 延迟跳转,让用户看到成功提示
    setTimeout(() => {
      router.push('/login')
    }, 1000)
  } catch (error) {
    if (error.errorFields) {
      // 表单验证失败 - 显示第一个错误
      const firstError = error.errorFields[0]
      message.error(firstError.errors[0] || '请检查表单填写是否正确')
      console.error('表单验证失败:', error.errorFields)
    } else {
      // 后端错误,由全局拦截器处理
      message.error('注册失败: ' + (error.message || '未知错误'))
      console.error('注册失败:', error)
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.register-container {
  width: 100%;
  max-width: 450px;
  padding: 20px;
}

.register-header {
  text-align: center;
  margin-bottom: 30px;
  color: #fff;
}

.register-header h1 {
  font-size: 36px;
  margin-bottom: 10px;
  cursor: pointer;
}

.register-header p {
  font-size: 16px;
  opacity: 0.9;
}

.register-card {
  padding: 30px;
}

.register-card h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}

.register-footer {
  text-align: center;
  margin-top: 20px;
  color: #666;
}

.register-footer a {
  color: #667eea;
  margin-left: 5px;
  text-decoration: none;
}

.register-footer a:hover {
  text-decoration: underline;
}
</style>
