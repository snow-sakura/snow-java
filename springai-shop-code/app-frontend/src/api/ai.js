import request from '@/utils/request'

/**
 * 智能客服对话
 */
export function chat(message) {
  return request({
    url: '/ai/chat',
    method: 'post',
    data: message,
  })
}

/**
 * 商品推荐
 */
export function recommendProducts(requirement) {
  return request({
    url: '/ai/recommend',
    method: 'post',
    data: requirement,
  })
}

/**
 * 优化商品描述
 */
export function optimizeDescription(productName, description) {
  return request({
    url: '/ai/optimize-description',
    method: 'post',
    params: { productName, description },
  })
}
