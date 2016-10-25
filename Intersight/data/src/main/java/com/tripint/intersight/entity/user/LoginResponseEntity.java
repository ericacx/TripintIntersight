package com.tripint.intersight.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/9/24.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseEntity implements Serializable {


    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOm51bGx9.uDN9jJP0fE2kRfMIRNFL_GxQvWqT8HiA9hM0rhL5bXI
     * status : 102
     */

    private String token;
    private int uid;
    private int status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
