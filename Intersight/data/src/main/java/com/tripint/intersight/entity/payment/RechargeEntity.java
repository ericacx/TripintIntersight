package com.tripint.intersight.entity.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RechargeEntity implements Serializable {
    private int totalPcoin;

    public int getTotalPcoin() {
        return totalPcoin;
    }

    public void setTotalPcoin(int totalPcoin) {
        this.totalPcoin = totalPcoin;
    }
}
