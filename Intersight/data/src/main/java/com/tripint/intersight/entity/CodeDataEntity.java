package com.tripint.intersight.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/9/22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeDataEntity implements Serializable {

    /**
     * flg : send success
     */

    private String flg;

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }
}
