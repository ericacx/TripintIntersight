package com.tripint.intersight.entity.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/20.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliPayResponseEntity implements Serializable {
    private String orderString;

    public String getOrderString() {
        return orderString;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }
}
