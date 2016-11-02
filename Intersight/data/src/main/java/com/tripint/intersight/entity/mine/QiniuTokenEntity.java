package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/11/2.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QiniuTokenEntity implements Serializable {

    private String qiniuToken;

    public String getQiniuToken() {
        return qiniuToken;
    }

    public void setQiniuToken(String qiniuToken) {
        this.qiniuToken = qiniuToken;
    }
}
