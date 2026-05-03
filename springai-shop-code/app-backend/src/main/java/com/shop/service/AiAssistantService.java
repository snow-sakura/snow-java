package com.shop.service;

/**
 * AI助手服务接口
 * 提供智能客服和商品推荐功能
 * 
 * @author Lingma
 */
public interface AiAssistantService {
    
    /**
     * 智能客服对话
     * 
     * @param message 用户消息
     * @return AI回复
     */
    String chat(String message);
    
    /**
     * 基于用户需求推荐商品
     * 
     * @param requirement 用户需求描述
     * @return 推荐建议
     */
    String recommendProducts(String requirement);
    
    /**
     * 生成商品描述优化建议
     * 
     * @param productName 商品名称
     * @param currentDescription 当前描述
     * @return 优化后的描述
     */
    String optimizeProductDescription(String productName, String currentDescription);
}
