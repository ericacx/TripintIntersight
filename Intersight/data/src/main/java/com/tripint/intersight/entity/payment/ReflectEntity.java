package com.tripint.intersight.entity.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReflectEntity implements Serializable {

    private String flg;
    private int totalPcoin;
    private int totalMoney;

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public int getTotalPcoin() {
        return totalPcoin;
    }

    public void setTotalPcoin(int totalPcoin) {
        this.totalPcoin = totalPcoin;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
}
