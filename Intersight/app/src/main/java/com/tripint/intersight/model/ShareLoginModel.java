package com.tripint.intersight.model;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/12.
 */

public class ShareLoginModel implements Serializable {

    private String shareLoginType;
    private String openId;
    private String unionId;
    private String imgUrl;
    private String nickName;

    public ShareLoginModel(String shareLoginType) {
        this.shareLoginType = shareLoginType;
    }

    public ShareLoginModel(String shareLoginType, String openId, String unionId, String imgUrl, String nickName) {
        this.shareLoginType = shareLoginType;
        this.openId = openId;
        this.unionId = unionId;
        this.imgUrl = imgUrl;
        this.nickName = nickName;
    }

    public String getShareLoginType() {
        return shareLoginType;
    }

    public void setShareLoginType(String shareLoginType) {
        this.shareLoginType = shareLoginType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
