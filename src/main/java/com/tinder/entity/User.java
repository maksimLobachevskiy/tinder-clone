package com.tinder.entity;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private Boolean choice;
    private int age;
    private Long groupId;
    private String login;
    private String password;
    private String email;
    private String url;
    private Integer count;


    public User(Long id, String name,Boolean choice){
        this.id = id;
        this.name = name;
        this.choice=choice;

    }
    public User(Long id, String name, int age, Long groupId, String login, String password,int count) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.groupId = groupId;
        this.login = login;
        this.password = password;
this.count=count;
    }



    public User(Long id,String name, int age, String email, String url, String password,int count,Boolean choice) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.url = url;
        this.password = password;
        this.count=count;
        this.choice = choice;
    }

    public Boolean getChoice() {
        return choice;
    }

    public void setChoice(Boolean choice) {
        this.choice = choice;
    }

    public Integer getCounter() {
        return count;
    }

    public void setCounter(Integer counter) {
        this.count = counter;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String pretty() {
        return String.format("%s %s", getName(), getUrl());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", choice='" + choice + '\'' +
                ", age=" + age +
                ", groupId=" + groupId +
                ", login=" + login +
                ", pass" + password +
                '}';
    }
}
