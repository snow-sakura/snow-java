import request from '@/utils/request'

/**
 * 分页查询商品列表
 */
export function getProductPage(params) {
  return request({
    url: '/product/page',
    method: 'get',
    params,
  })
}

/**
 * 获取商品详情
 */
export function getProductDetail(productId) {
  return request({
    url: `/product/${productId}`,
    method: 'get',
  })
}
