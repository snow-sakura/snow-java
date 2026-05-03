package com.bjpowernode.springboot.model;

/**
 * ClassName:User
 * Package:com.bjpowernode.springboot.model
 * Description:
 *
 * @date:2018/10/10 10:54
 * @author:bjpowernode.com
 */
public class User {
    private String name;
    private Integer age;
    private Integer sex;
    private Integer id;
    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User(Integer id, String name, Integer age, Integer sex) {
        this.id=id;
        this.name = name;
        this.age = age;
        this.sex = sex;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
