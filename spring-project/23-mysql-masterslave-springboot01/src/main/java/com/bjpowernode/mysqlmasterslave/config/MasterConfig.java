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
 * ClassName:MasterConfig
 * Package:com.bjpowernode.mysqlmasterslave.config
 * Description:
 *
 * @date:2018/10/20 10:28
 * @author:bjpowernode.com
 */
@Configuration("masterConfig")
//    <bean id="masterMapperScannerConfigurer"  class="org.mybatis.spring.mapper.MapperScannerConfigurer">
//        <property name="basePackage" value="com.bjpowernode.masterslave.master.mapper"/>
//        <property name="sqlSessionFactoryBeanName" value="masterSqlSessionFactoryBean"/>
//    </bean>
@MapperScan(basePackages={"com.bjpowernode.mysqlmasterslave.master.mapper"},sqlSessionFactoryRef="masterSqlSessionFactoryBean")
public class MasterConfig {

//
//    <bean id="masterDruidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
//        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
//        <property name="url" value="jdbc:mysql://192.168.31.128:3307/test"/>
//        <property name="username" value="root"/>
//        <property name="password" value="123456"/>
//    </bean>
    @Bean
    public DruidDataSource masterDruidDataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.31.128:3307/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }
//    <bean id="masterSqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
//        <property name="dataSource" ref="masterDruidDataSource"/>
//    </bean>
        @Bean
        public SqlSessionFactory masterSqlSessionFactoryBean() throws Exception {
            SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(masterDruidDataSource());
            return sqlSessionFactoryBean.getObject();
        }

//    <bean id="masterMapperScannerConfigurer"  class="org.mybatis.spring.mapper.MapperScannerConfigurer">
//        <property name="basePackage" value="com.bjpowernode.masterslave.master.mapper"/>
//        <property name="sqlSessionFactoryBeanName" value="masterSqlSessionFactoryBean"/>
//    </bean>
//    @Bean
//    public MapperScannerConfigurer masterMapperScannerConfigurer(){
//        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
//
//        mapperScannerConfigurer.setBasePackage("com.bjpowernode.mysqlmasterslave.master.mapper");
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("masterSqlSessionFactoryBean");
//        return mapperScannerConfigurer;
//    }
    @Bean
    public SqlSessionTemplate masterSqlSessionTemplate(){
        SqlSessionTemplate template= null;
        try {
            template = new SqlSessionTemplate(masterSqlSessionFactoryBean());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return template;
    }
}
