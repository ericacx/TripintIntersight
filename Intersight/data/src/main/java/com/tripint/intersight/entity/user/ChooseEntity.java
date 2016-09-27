package com.tripint.intersight.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/9/26.
 */
@JsonIgnoreProperties
public class ChooseEntity implements Serializable {


    /**
     * flg : setRole success
     * status : 101
     */

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
