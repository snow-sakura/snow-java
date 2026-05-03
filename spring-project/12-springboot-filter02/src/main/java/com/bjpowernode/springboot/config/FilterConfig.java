package com.bjpowernode.springboot.config;

import com.bjpowernode.springboot.filter.TestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:FilterConfig
 * Package:com.bjpowernode.springboot.config
 * Description:
 *
 * @date:2018/10/9 12:19
 * @author:bjpowernode.com
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean testFilterRegistration(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean(new TestFilter());

        List<String> list=new ArrayList<String>();
        list.add("/*");
        filterRegistrationBean.setUrlPatterns(list);
        return filterRegistrationBean;
    }
}
