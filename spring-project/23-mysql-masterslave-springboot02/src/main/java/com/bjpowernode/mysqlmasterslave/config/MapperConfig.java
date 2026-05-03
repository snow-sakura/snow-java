package com.bjpowernode.mysqlmasterslave.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:MapperConfig
 * Package:com.bjpowernode.mysqlmasterslave.config
 * Description:
 *
 * @date:2018/10/20 10:59
 * @author:bjpowernode.com
 */
@Configuration("mapperConfig")
@MapperScan(basePackages = {"com.bjpowernode.mysqlmasterslave.mapper"},sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class MapperConfig {

    @Bean
    public DruidDataSource masterDruidDataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.31.128:3307/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

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
    public DynamicDataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource=new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDruidDataSource());
        Map map=new HashMap();
        map.put(DynamicDataSource.MASTER,masterDruidDataSource());
        map.put(DynamicDataSource.SLAVE,slaveDruidDataSource());
        dynamicDataSource.setTargetDataSources(map);
        return dynamicDataSource;
    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(){
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer(){
//        MapperScannerConfigurer mapperScannerConfigurer =new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
//        mapperScannerConfigurer.setBasePackage("com.bjpowernode.mysqlmasterslave.mapper");
//        return  mapperScannerConfigurer;
//    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(){
        SqlSessionTemplate sqlSessionTemplate=null;
        try {
            sqlSessionTemplate=new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  sqlSessionTemplate;
    }
}
