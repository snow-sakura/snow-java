import request from '@/utils/request'

/**
 * 创建订单
 */
export function createOrder(data) {
  return request({
    url: '/order/create',
    method: 'post',
    data,
  })
}

/**
 * 获取订单详情
 */
export function getOrderDetail(orderNo) {
  return request({
    url: '/order/detail',
    method: 'get',
    params: { orderNo },
  })
}

/**
 * 分页查询用户订单列表
 */
export function getUserOrders(params) {
  return request({
    url: '/order/list',
    method: 'get',
    params,
  })
}

/**
 * 取消订单
 */
export function cancelOrder(orderNo) {
  return request({
    url: '/order/cancel',
    method: 'put',
    params: { orderNo },
  })
}

/**
 * 确认收货
 */
export function confirmReceipt(orderNo) {
  return request({
    url: '/order/confirm',
    method: 'put',
    params: { orderNo },
  })
}
