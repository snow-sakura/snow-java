package com.bjpowernode.mysqlmasterslave.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:SlaveConfig
 * Package:com.bjpowernode.mysqlmasterslave.config
 * Description:
 *
 * @date:2018/10/20 10:39
 * @author:bjpowernode.com
 */
@Configuration("slaveConfig")
@MapperScan(basePackages={"com.bjpowernode.mysqlmasterslave.slave.mapper"},sqlSessionFactoryRef="slaveSqlSessionFactoryBean")
public class SlaveConfig {



    @Bean
    public DruidDataSource slaveDruidDataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.31.128:3309/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    @Bean
    public SqlSessionFactory slaveSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(slaveDruidDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate slaveSqlSessionTemplate(){
        SqlSessionTemplate template= null;
        try {
            template = new SqlSessionTemplate(slaveSqlSessionFactoryBean());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return template;
    }
}
