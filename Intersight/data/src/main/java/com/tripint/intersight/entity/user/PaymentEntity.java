package com.tripint.intersight.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentEntity implements Serializable {

    public PaymentEntity(){

    }

    public PaymentEntity(int id, String channelName, String channelPartentId){
        this.id = id;
        this.channelPartentId = channelPartentId;
        this.channelName = channelName;

    }

    private int id;
    private String channelName;
    private String channelPartentId;
    private String channelLogo;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelPartentId() {
        return channelPartentId;
    }

    public void setChannelPartentId(String channelPartentId) {
        this.channelPartentId = channelPartentId;
    }

    public String getChannelLogo() {
        return channelLogo;
    }

    public void setChannelLogo(String channelLogo) {
        this.channelLogo = channelLogo;
    }
}
