package com.bjpowernode.springboot.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName:User
 * Package:com.bjpowernode.springboot.model
 * Description:
 *
 * @date:2018/10/8 11:05
 * @author:bjpowernode.com
 */
@Component
@ConfigurationProperties(prefix ="bjpowernode" )
public class User {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
