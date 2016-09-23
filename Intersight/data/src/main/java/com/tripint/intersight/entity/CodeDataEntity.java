package com.tripint.intersight.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/9/22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeDataEntity implements Serializable {

    @SerializedName("msg")
    private List<String> msgCode;

    public List<String> getToken() {
        return msgCode;
    }

    public void setToken(List<String> token) {
        this.msgCode = token;
    }
}
