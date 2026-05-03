<template>
  <MainLayout>
    <div class="orders container">
      <h2 class="page-title">我的订单</h2>

      <div class="filter-bar">
        <a-radio-group v-model:value="statusFilter" @change="fetchOrders">
          <a-radio-button :value="undefined">全部</a-radio-button>
          <a-radio-button :value="0">待付款</a-radio-button>
          <a-radio-button :value="1">已付款</a-radio-button>
          <a-radio-button :value="2">已发货</a-radio-button>
          <a-radio-button :value="3">已完成</a-radio-button>
          <a-radio-button :value="4">已取消</a-radio-button>
        </a-radio-group>
      </div>

      <div class="order-list">
        <a-card v-for="order in orders" :key="order.id" class="order-card">
          <div class="order-header">
            <div class="order-info">
              <span>订单号: {{ order.orderNo }}</span>
              <span class="order-time">{{ formatTime(order.createTime) }}</span>
            </div>
            <a-tag :color="getStatusColor(order.status)">
              {{ getStatusText(order.status) }}
            </a-tag>
          </div>

          <div class="order-content">
            <div class="order-amount">
              <span>订单金额:</span>
              <span class="price">¥{{ order.payAmount }}</span>
            </div>
            
            <div class="order-address">
              <span>收货地址:</span>
              <span>{{ order.receiverAddress }}</span>
            </div>
          </div>

          <div class="order-actions">
            <a-button v-if="order.status === 0" @click="handleCancel(order.orderNo)">
              取消订单
            </a-button>
            <a-button
              v-if="order.status === 2"
              type="primary"
              @click="handleConfirm(order.orderNo)"
            >
              确认收货
            </a-button>
          </div>
        </a-card>
      </div>

      <div class="pagination">
        <a-pagination
          v-model:current="current"
          v-model:page-size="pageSize"
          :total="total"
          @change="fetchOrders"
        />
      </div>
    </div>
  </MainLayout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import MainLayout from '@/components/MainLayout.vue'
import { getUserOrders, cancelOrder, confirmReceipt } from '@/api/order'
import { message } from 'ant-design-vue'

const orders = ref([])
const current = ref(1)
const pageSize = ref(10)
const total = ref(0)
const statusFilter = ref(undefined)

// 获取订单列表
const fetchOrders = async () => {
  try {
    const params = {
      current: current.value,
      size: pageSize.value,
      status: statusFilter.value,
    }
    
    const res = await getUserOrders(params)
    orders.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    message.error('获取订单列表失败')
  }
}

// 取消订单
const handleCancel = async (orderNo) => {
  try {
    await cancelOrder(orderNo)
    message.success('订单已取消')
    fetchOrders()
  } catch (error) {
    message.error('取消失败')
  }
}

// 确认收货
const handleConfirm = async (orderNo) => {
  try {
    await confirmReceipt(orderNo)
    message.success('确认收货成功')
    fetchOrders()
  } catch (error) {
    message.error('操作失败')
  }
}

// 格式化时间
const formatTime = (time) => {
  if (!time) return ''
  return new Date(time).toLocaleString('zh-CN')
}

// 获取状态颜色
const getStatusColor = (status) => {
  const colors = {
    0: 'orange',
    1: 'blue',
    2: 'cyan',
    3: 'green',
    4: 'red',
  }
  return colors[status] || 'default'
}

// 获取状态文本
const getStatusText = (status) => {
  const texts = {
    0: '待付款',
    1: '已付款',
    2: '已发货',
    3: '已完成',
    4: '已取消',
  }
  return texts[status] || '未知'
}

onMounted(() => {
  fetchOrders()
})
</script>

<style scoped>
.orders {
  min-height: calc(100vh - 70px);
}

.page-title {
  font-size: 24px;
  margin: 20px 0;
}

.filter-bar {
  background: #fff;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.order-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.order-card {
  padding: 20px;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
  margin-bottom: 15px;
}

.order-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.order-time {
  font-size: 12px;
  color: #999;
}

.order-content {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 15px;
}

.order-amount {
  font-size: 16px;
}

.order-address {
  font-size: 14px;
  color: #666;
}

.order-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding-top: 15px;
  border-top: 1px solid #eee;
}

.pagination {
  text-align: center;
  margin-top: 30px;
}
</style>
