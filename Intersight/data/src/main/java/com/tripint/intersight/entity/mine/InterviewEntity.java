package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/9/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterviewEntity implements Serializable {

/*
    "id": 65,
            "status": 0,
            "code": 123,
            "custType": 1,
            "subject": "韩国消费",
            "description": null,
            "createAt": 1476954998,
            "nickname": "Maxkow",
            "avatar": "http://oc153j0jh.bkt.clouddn.com/wdor9LSlinmUV4roTi29.png",
            "companyName": "Tripint",
            "abilityName": "高级管理",
            "style": "电话访谈"
    */
    //列表返回数据
    private int id;
    private int fromUid;
    private int status;//状态:0联系中-红色,1访谈成功-白色
    private String subject;//标题名
    private String description;//内容
    private int createAt;//时间
    private String name;
    private String type;//我的约访,我被约访


    //访谈或约访页面数据
    private int code;
    private int custType;
    private String nickname;
    private String avatar;
    private String companyName;
    private String abilityName;
    private String style;//访谈形式

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCustType() {
        return custType;
    }

    public void setCustType(int custType) {
        this.custType = custType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getFromUid() {
        return fromUid;
    }

    public void setFromUid(int fromUid) {
        this.fromUid = fromUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }
}
