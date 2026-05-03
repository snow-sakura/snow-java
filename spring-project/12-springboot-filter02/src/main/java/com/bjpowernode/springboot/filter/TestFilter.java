package com.bjpowernode.springboot.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * ClassName:TestFilter
 * Package:com.bjpowernode.springboot.filter
 * Description:
 *
 * @date:2018/10/9 12:12
 * @author:bjpowernode.com
 */

public class TestFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入了Filter02，执行请求之前");
        filterChain.doFilter(servletRequest,servletResponse);//继续执行请求
        System.out.println("进入了Filter02，执行请求之后");
    }

    @Override
    public void destroy() {

    }
}
