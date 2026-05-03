package com.shop.service.impl;

import com.shop.service.AiAssistantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * AI助手服务实现类
 * 使用Spring AI实现智能客服和商品推荐功能
 * 
 * @author Lingma
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiAssistantServiceImpl implements AiAssistantService {
    
    private final ChatClient chatClient;
    
    @Override
    public String chat(String message) {
        try {
            // 构建系统提示词
            String systemPrompt = "你是一个专业的商城客服助手,名叫'小优'。你的职责是:\n" +
                    "1. 友好、专业地回答用户关于商品、订单、物流等问题\n" +
                    "2. 如果用户询问商品信息,可以给出购买建议\n" +
                    "3. 如果用户有投诉,要耐心倾听并给出解决方案\n" +
                    "4. 保持礼貌、热情的服务态度\n" +
                    "5. 回答要简洁明了,不要过于冗长\n\n" +
                    "请用中文回答用户的问题。";
            
            // 调用AI模型
            String response = chatClient.prompt()
                    .system(systemPrompt)
                    .user(message)
                    .call()
                    .content();
            
            log.info("AI客服对话 - 用户: {}, 回复: {}", message, response);
            return response;
            
        } catch (Exception e) {
            log.error("AI客服对话失败", e);
            return "抱歉,我现在有点忙,请稍后再试或联系人工客服。";
        }
    }
    
    @Override
    public String recommendProducts(String requirement) {
        try {
            // 构建系统提示词
            String systemPrompt = "你是一个专业的购物顾问,擅长根据用户需求推荐合适的商品。\n" +
                    "请根据用户的描述,分析他们的需求,然后:\n" +
                    "1. 理解用户的使用场景和预算范围\n" +
                    "2. 推荐3-5款适合的商品类型\n" +
                    "3. 说明推荐理由\n" +
                    "4. 给出选购建议\n\n" +
                    "请用中文回答,语气要专业且友好。";
            
            // 调用AI模型
            String response = chatClient.prompt()
                    .system(systemPrompt)
                    .user("我想找这样的商品: " + requirement)
                    .call()
                    .content();
            
            log.info("商品推荐 - 需求: {}, 推荐: {}", requirement, response);
            return response;
            
        } catch (Exception e) {
            log.error("商品推荐失败", e);
            return "抱歉,推荐服务暂时不可用,您可以浏览我们的商品分类找到心仪的商品。";
        }
    }
    
    @Override
    public String optimizeProductDescription(String productName, String currentDescription) {
        try {
            // 构建系统提示词
            String systemPrompt = "你是一个专业的电商文案策划师,擅长优化商品描述。\n" +
                    "请根据商品名称和现有描述,重新撰写一个更有吸引力的商品描述:\n" +
                    "1. 突出商品的核心卖点和优势\n" +
                    "2. 使用生动、具体的语言\n" +
                    "3. 适当使用emoji增加可读性\n" +
                    "4. 结构清晰,分点描述\n" +
                    "5. 控制在200字以内\n\n" +
                    "请用中文回答。";
            
            // 调用AI模型
            String userMessage = String.format(
                    "商品名称: %s\n当前描述: %s\n请优化这个商品描述:",
                    productName,
                    currentDescription != null ? currentDescription : "无"
            );
            
            String response = chatClient.prompt()
                    .system(systemPrompt)
                    .user(userMessage)
                    .call()
                    .content();
            
            log.info("商品描述优化 - 商品: {}", productName);
            return response;
            
        } catch (Exception e) {
            log.error("商品描述优化失败", e);
            return currentDescription;
        }
    }
}
