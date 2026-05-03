import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getCartList, addToCart as apiAddToCart, removeFromCart as apiRemoveFromCart } from '@/api/cart'

export const useCartStore = defineStore('cart', () => {
  // 购物车列表
  const cartList = ref([])
  
  /**
   * 获取购物车列表
   */
  async function fetchCartList() {
    try {
      const res = await getCartList()
      cartList.value = res.data
    } catch (error) {
      console.error('获取购物车失败:', error)
    }
  }
  
  /**
   * 添加到购物车
   */
  async function addToCart(productId, quantity = 1) {
    try {
      await apiAddToCart(productId, quantity)
      await fetchCartList()
    } catch (error) {
      throw error
    }
  }
  
  /**
   * 从购物车移除
   */
  async function removeFromCart(productId) {
    try {
      await apiRemoveFromCart(productId)
      await fetchCartList()
    } catch (error) {
      throw error
    }
  }
  
  /**
   * 清空购物车
   */
  async function clearCart() {
    cartList.value = []
  }
  
  /**
   * 购物车商品数量
   */
  const cartCount = computed(() => {
    return cartList.value.reduce((sum, item) => sum + item.quantity, 0)
  })
  
  /**
   * 购物车总价
   */
  const cartTotal = computed(() => {
    return cartList.value.reduce((sum, item) => {
      return sum + (parseFloat(item.totalPrice) || 0)
    }, 0).toFixed(2)
  })
  
  return {
    cartList,
    cartCount,
    cartTotal,
    fetchCartList,
    addToCart,
    removeFromCart,
    clearCart,
  }
})
