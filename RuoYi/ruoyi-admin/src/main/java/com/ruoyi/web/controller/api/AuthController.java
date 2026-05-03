package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * 系统登录接口
 * 
 * @author ruoyi
 */
@Tag(name = "系统登录", description = "用户登录、登出相关接口")
@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController
{
    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 登录结果
     */
    @Operation(
        summary = "用户登录",
        description = "用户通过用户名和密码进行登录，需要提供验证码"
    )
    @Parameters({
        @Parameter(name = "username", description = "用户名", required = true, in = ParameterIn.QUERY),
        @Parameter(name = "password", description = "密码", required = true, in = ParameterIn.QUERY),
        @Parameter(name = "code", description = "验证码", required = true, in = ParameterIn.QUERY),
        @Parameter(name = "uuid", description = "验证码UUID", required = true, in = ParameterIn.QUERY)
    })
    @PostMapping("/login")
    public AjaxResult login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String code,
            @RequestParam String uuid)
    {
        // TODO: 实现登录逻辑
        return AjaxResult.success("登录成功");
    }

    /**
     * 用户登出
     * 
     * @return 结果
     */
    @Operation(
        summary = "用户登出",
        description = "退出当前登录状态"
    )
    @PostMapping("/logout")
    public AjaxResult logout()
    {
        // TODO: 实现登出逻辑
        return AjaxResult.success("登出成功");
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @Operation(
        summary = "获取用户信息",
        description = "获取当前登录用户的详细信息"
    )
    @GetMapping("/info")
    public AjaxResult getUserInfo()
    {
        // TODO: 实现获取用户信息逻辑
        return AjaxResult.success();
    }

    /**
     * 获取验证码
     * 
     * @return 验证码信息
     */
    @Operation(
        summary = "获取验证码",
        description = "获取登录所需的验证码图片和UUID"
    )
    @GetMapping("/captcha")
    public AjaxResult getCaptcha()
    {
        // TODO: 实现获取验证码逻辑
        return AjaxResult.success();
    }

    /**
     * 刷新Token
     * 
     * @return 新的Token
     */
    @Operation(
        summary = "刷新Token",
        description = "使用旧的Token刷新获取新的Token"
    )
    @PostMapping("/refresh")
    public AjaxResult refreshToken()
    {
        // TODO: 实现刷新Token逻辑
        return AjaxResult.success();
    }
}
