import request from '@/utils/request'

/**
 * 添加商品到购物车
 */
export function addToCart(productId, quantity = 1) {
  return request({
    url: '/cart/add',
    method: 'post',
    params: { productId, quantity },
  })
}

/**
 * 更新购物车商品数量
 */
export function updateCartQuantity(productId, quantity) {
  return request({
    url: '/cart/update',
    method: 'put',
    params: { productId, quantity },
  })
}

/**
 * 删除购物车商品
 */
export function removeFromCart(productId) {
  return request({
    url: '/cart/remove',
    method: 'delete',
    params: { productId },
  })
}

/**
 * 获取购物车列表
 */
export function getCartList() {
  return request({
    url: '/cart/list',
    method: 'get',
  })
}

/**
 * 清空购物车
 */
export function clearCart() {
  return request({
    url: '/cart/clear',
    method: 'delete',
  })
}
