package com.tripint.intersight.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDeviceTokenResponseEntity implements Serializable {
    private String flg;
    private int status;

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
