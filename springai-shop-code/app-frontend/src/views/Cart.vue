<template>
  <MainLayout>
    <div class="cart container">
      <h2 class="page-title">购物车</h2>

      <a-card v-if="cartStore.cartList.length > 0" class="cart-card">
        <a-table
          :data-source="cartStore.cartList"
          :pagination="false"
          row-key="id"
        >
          <a-table-column title="商品信息" key="product">
            <template #default="{ record }">
              <div class="product-info">
                <img :src="record.productImage" :alt="record.productName" @error="handleImageError" />
                <span>{{ record.productName }}</span>
              </div>
            </template>
          </a-table-column>

          <a-table-column title="单价" key="price" width="120">
            <template #default="{ record }">
              <span class="price-small">¥{{ record.productPrice }}</span>
            </template>
          </a-table-column>

          <a-table-column title="数量" key="quantity" width="150">
            <template #default="{ record }">
              <a-input-number
                v-model:value="record.quantity"
                :min="1"
                size="small"
                @change="(value) => handleQuantityChange(record, value)"
              />
            </template>
          </a-table-column>

          <a-table-column title="小计" key="total" width="120">
            <template #default="{ record }">
              <span class="price">¥{{ record.totalPrice }}</span>
            </template>
          </a-table-column>

          <a-table-column title="操作" key="action" width="100">
            <template #default="{ record }">
              <a-button
                type="link"
                danger
                @click="handleRemove(record.productId)"
              >
                删除
              </a-button>
            </template>
          </a-table-column>
        </a-table>

        <div class="cart-footer">
          <div class="cart-total">
            <span>合计:</span>
            <span class="price-large">¥{{ cartStore.cartTotal }}</span>
          </div>
          <a-space>
            <a-button @click="handleClearCart">清空购物车</a-button>
            <a-button type="primary" size="large" @click="handleCheckout">
              结算
            </a-button>
          </a-space>
        </div>
      </a-card>

      <a-empty v-else description="购物车是空的" />
    </div>

    <!-- 结算对话框 -->
    <a-modal
      v-model:open="checkoutVisible"
      title="确认订单"
      @ok="handleCreateOrder"
      :confirm-loading="orderLoading"
    >
      <a-form :model="orderForm" layout="vertical">
        <a-form-item label="收货人姓名" required>
          <a-input v-model:value="orderForm.receiverName" />
        </a-form-item>
        <a-form-item label="收货人电话" required>
          <a-input v-model:value="orderForm.receiverPhone" />
        </a-form-item>
        <a-form-item label="收货地址" required>
          <a-textarea v-model:value="orderForm.receiverAddress" :rows="3" />
        </a-form-item>
        <a-form-item label="备注">
          <a-textarea v-model:value="orderForm.remark" :rows="2" />
        </a-form-item>
      </a-form>
    </a-modal>
  </MainLayout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import MainLayout from '@/components/MainLayout.vue'
import { useCartStore } from '@/stores/cart'
import { createOrder } from '@/api/order'
import { message } from 'ant-design-vue'

const router = useRouter()
const cartStore = useCartStore()

const checkoutVisible = ref(false)
const orderLoading = ref(false)

const orderForm = reactive({
  receiverName: '',
  receiverPhone: '',
  receiverAddress: '',
  remark: '',
})

// 更新数量
const handleQuantityChange = async (record, value) => {
  try {
    await cartStore.updateCartQuantity(record.productId, value)
  } catch (error) {
    // 失败时恢复原值
    await cartStore.fetchCartList()
  }
}

// 删除商品
const handleRemove = async (productId) => {
  try {
    await cartStore.removeFromCart(productId)
    message.success('已删除')
  } catch (error) {
    message.error('删除失败')
  }
}

// 清空购物车
const handleClearCart = async () => {
  try {
    await cartStore.clearCart()
    message.success('已清空购物车')
  } catch (error) {
    message.error('操作失败')
  }
}

// 图片加载失败处理
const handleImageError = (e) => {
  e.target.src = 'https://picsum.photos/seed/default/400/400'
}

// 结算
const handleCheckout = () => {
  if (cartStore.cartList.length === 0) {
    message.warning('购物车为空')
    return
  }
  checkoutVisible.value = true
}

// 创建订单
const handleCreateOrder = async () => {
  if (!orderForm.receiverName || !orderForm.receiverPhone || !orderForm.receiverAddress) {
    message.warning('请填写完整的收货信息')
    return
  }

  orderLoading.value = true
  
  try {
    const cartIds = cartStore.cartList.map(item => item.id)
    
    const res = await createOrder({
      cartIds,
      receiverName: orderForm.receiverName,
      receiverPhone: orderForm.receiverPhone,
      receiverAddress: orderForm.receiverAddress,
      remark: orderForm.remark,
    })
    
    message.success('订单创建成功')
    checkoutVisible.value = false
    
    // 跳转到订单列表
    router.push('/orders')
  } catch (error) {
    console.error('创建订单失败:', error)
  } finally {
    orderLoading.value = false
  }
}

onMounted(() => {
  cartStore.fetchCartList()
})
</script>

<style scoped>
.cart {
  min-height: calc(100vh - 70px);
}

.page-title {
  font-size: 24px;
  margin: 20px 0;
}

.cart-card {
  padding: 20px;
}

.product-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.product-info img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 4px;
}

.cart-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.cart-total {
  font-size: 16px;
}

.price-large {
  color: #ff6b35;
  font-size: 28px;
  font-weight: bold;
  margin-left: 10px;
}
</style>
