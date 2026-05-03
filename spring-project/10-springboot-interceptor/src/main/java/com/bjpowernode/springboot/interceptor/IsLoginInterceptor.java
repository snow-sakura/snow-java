package com.bjpowernode.springboot.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:IsLoginInterceptor
 * Package:com.bjpowernode.springboot.interceptor
 * Description:
 *
 * @date:2018/10/9 11:40
 * @author:bjpowernode.com
 */
public class IsLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user=request.getSession().getAttribute("userSession");
        System.out.println("进入了拦截器");
        if(user==null){
            System.out.println("需要登录");
            return false;
        }
        System.out.println("已经登录");
        return true;
    }
}
