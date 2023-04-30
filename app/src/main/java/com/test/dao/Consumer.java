package com.test.dao;

import java.io.Serializable;

/**
 * 用户信息实体类
 */
public class Consumer implements Serializable {
    private String account;
    private String password;
    private String uname;
    private String sex;
    private Integer age ;
    private Integer weight;
    private String phone;
    private Float measure;//最新一次血糖测量结果

    public Consumer(String account, String password, String uname, String phone) {
        this.account = account;
        this.password = password;
        this.uname = uname;
        this.phone = phone;
    }

    public Consumer() {
    }

    public Consumer(String password, String uname) {
        this.password = password;
        this.uname = uname;
    }

    public Consumer(String account, String password, String uname, String sex, int age, int weight, String phone, float measure) {
        this.account = account;
        this.password = password;
        this.uname = uname;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.phone = phone;
        this.measure = measure;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getMeasure() {
        return measure;
    }

    public void setMeasure(float measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        return "consumer{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", uname='" + uname + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", phone='" + phone + '\'' +
                ", measure=" + measure +
                '}';
    }
}
