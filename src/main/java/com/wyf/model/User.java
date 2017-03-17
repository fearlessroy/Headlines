package com.wyf.model;

/**
 * Created by w7397 on 2017/3/17.
 */
public class User {
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;

    public User(String name) {
        this.name = name;
    }
}
