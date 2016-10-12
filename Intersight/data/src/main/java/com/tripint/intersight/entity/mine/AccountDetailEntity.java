package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetailEntity implements Serializable {

    private int id;
    private String title;//标题
    private int amountTotal;//总数
    private String payLastTime;//时间
    private int type;//类型:0回答,1提问 2约访 3充值
    private int status;//状态:0等待对方回答 1:等待访谈完成 2 没有东西

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(int amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getPayLastTime() {
        return payLastTime;
    }

    public void setPayLastTime(String payLastTime) {
        this.payLastTime = payLastTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
