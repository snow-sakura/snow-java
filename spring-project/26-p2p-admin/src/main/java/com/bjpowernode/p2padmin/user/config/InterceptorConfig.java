package com.bjpowernode.p2padmin.user.config;

import com.bjpowernode.p2padmin.user.interceptor.PermissionCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName:InterceptorConfig
 * Package:com.bjpowernode.p2padmin.user.config
 * Description:
 *
 * @date:2018/10/29 9:18
 * @author:bjpowernode.com
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration= registry.addInterceptor(new PermissionCheckInterceptor());
//
//        registration.addPathPatterns("/admin/**");
    }
}
