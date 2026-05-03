package com.bjpowernode.activemq.model;

import java.io.Serializable;

/**
 * ClassName:User
 * Package:com.bjpowernode.activemq.model
 * Description:
 *
 * @date:2018/10/16 9:25
 * @author:bjpowernode.com
 */
public class User implements Serializable {
    private Long id;
    private String name;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
