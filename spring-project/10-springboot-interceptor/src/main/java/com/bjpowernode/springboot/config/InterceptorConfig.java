package com.bjpowernode.springboot.config;

import com.bjpowernode.springboot.interceptor.IsLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * ClassName:InterceptorConfig
 * Package:com.bjpowernode.springboot.config
 * Description:
 *
 * @date:2018/10/9 11:43
 * @author:bjpowernode.com
 */
@Configuration //用于标记当前类是一个配置类（类似Spring中的配置文件），并需要被SpringBoot扫描
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration= registry.addInterceptor(new IsLoginInterceptor());
        //定义拦截器可拦截的请求路径数组，** 表示任意目录下的任意请求
        String []addPathPatterns={"/private/**"};
        registration.addPathPatterns(addPathPatterns);
        //定义拦截器不需要拦截的请求路径数组，如果不需要被拦截的请求路径，与需要拦截的路径前缀不同，这个选项可写
        String []excludePathPatterns={"/public/**"};
        registration.excludePathPatterns();


        //添加更多拦截器
//        registration= registry.addInterceptor(new Xxx());
//        //定义拦截器可拦截的请求路径数组，** 表示任意目录下的任意请求
//        String []addPathPatterns={"/xxx/**"};
//        registration.addPathPatterns(addPathPatterns);
//        //定义拦截器不需要拦截的请求路径数组，如果不需要被拦截的请求路径，与需要拦截的路径前缀不同，这个选项可写
//        String []excludePathPatterns={"/xxx/**"};
//        registration.excludePathPatterns();

    }
}
