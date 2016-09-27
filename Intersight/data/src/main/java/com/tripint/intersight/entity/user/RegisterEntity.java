package com.tripint.intersight.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/9/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterEntity implements Serializable {


    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOm51bGx9.uDN9jJP0fE2kRfMIRNFL_GxQvWqT8HiA9hM0rhL5bXI
     */

    private String token;
    private int status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
