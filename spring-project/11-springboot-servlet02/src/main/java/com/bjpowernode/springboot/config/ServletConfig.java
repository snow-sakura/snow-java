package com.bjpowernode.springboot.config;

import com.bjpowernode.springboot.servlet.TestServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:ServletConfig
 * Package:com.bjpowernode.springboot.config
 * Description:
 *
 * @date:2018/10/9 12:05
 * @author:bjpowernode.com
 */
@Configuration
public class ServletConfig {
    //指定让当前方法为Spring的一个Bean的配置
    //方法名相当于bean标签的 id  方法返回值相当于与bean标签的class
    @Bean
    public ServletRegistrationBean testServletRegistgration(){
        ServletRegistrationBean servletRegistrationBean=new ServletRegistrationBean(new TestServlet(),"/testServlet02");
       return servletRegistrationBean;
    }
}
