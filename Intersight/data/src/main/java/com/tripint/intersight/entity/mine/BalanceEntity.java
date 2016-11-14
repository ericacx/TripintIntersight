package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/11/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceEntity implements Serializable {
    private int balance;
    private int payment1;
    private int payment2;
    private int payment3;
    private int payment4;
    private int payment5;
    private int payment6;


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPayment1() {
        return payment1;
    }

    public void setPayment1(int payment1) {
        this.payment1 = payment1;
    }

    public int getPayment2() {
        return payment2;
    }

    public void setPayment2(int payment2) {
        this.payment2 = payment2;
    }

    public int getPayment3() {
        return payment3;
    }

    public void setPayment3(int payment3) {
        this.payment3 = payment3;
    }

    public int getPayment4() {
        return payment4;
    }

    public void setPayment4(int payment4) {
        this.payment4 = payment4;
    }

    public int getPayment5() {
        return payment5;
    }

    public void setPayment5(int payment5) {
        this.payment5 = payment5;
    }

    public int getPayment6() {
        return payment6;
    }

    public void setPayment6(int payment6) {
        this.payment6 = payment6;
    }
}
