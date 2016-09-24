package com.tripint.intersight.entity.user;

/**
 * Created by Eric on 16/9/23.
 */

public class User {

    private String email;
    private String password;
    private int code;

    public User() {
    }

    public User(String email, String password, int code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
