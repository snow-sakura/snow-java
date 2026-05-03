package com.shop.controller;

import com.shop.common.Result;
import com.shop.service.AiAssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * AI助手控制器
 * 提供智能客服和商品推荐功能
 * 
 * @author Lingma
 */
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiAssistantController {
    
    private final AiAssistantService aiAssistantService;
    
    /**
     * 智能客服对话
     */
    @PostMapping("/chat")
    public Result<String> chat(@RequestBody String message) {
        String response = aiAssistantService.chat(message);
        return Result.success(response);
    }
    
    /**
     * 商品推荐
     */
    @PostMapping("/recommend")
    public Result<String> recommend(@RequestBody String requirement) {
        String recommendation = aiAssistantService.recommendProducts(requirement);
        return Result.success(recommendation);
    }
    
    /**
     * 优化商品描述
     */
    @PostMapping("/optimize-description")
    public Result<String> optimizeDescription(
            @RequestParam String productName,
            @RequestParam(required = false) String description) {
        
        String optimized = aiAssistantService.optimizeProductDescription(productName, description);
        return Result.success(optimized);
    }
}
