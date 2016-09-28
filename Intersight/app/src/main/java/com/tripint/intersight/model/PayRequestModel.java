package com.tripint.intersight.model;

import java.io.Serializable;

/**
 * Created by lenovo on 2014/5/16.
 */
public class PayRequestModel implements Serializable {

    private String ChannelID;
    private String PrepayId;
    private String Sign;
    private String Noncestr;
    private String TimeStamp;
    private String PackageValue;
    private String RequestURL;


    public String getChannelID() {
        return ChannelID;
    }

    public void setChannelID(String channelID) {
        ChannelID = channelID;
    }

    public String getNoncestr() {
        return Noncestr;
    }

    public void setNoncestr(String noncestr) {
        Noncestr = noncestr;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getPackageValue() {
        return PackageValue;
    }

    public void setPackageValue(String packageValue) {
        PackageValue = packageValue;
    }

    public String getPrepayId() {
        return PrepayId;
    }

    public void setPrepayId(String prepayId) {
        PrepayId = prepayId;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public String getRequestURL() {
        return RequestURL;
    }

    public void setRequestURL(String requestURL) {
        RequestURL = requestURL;
    }
}
