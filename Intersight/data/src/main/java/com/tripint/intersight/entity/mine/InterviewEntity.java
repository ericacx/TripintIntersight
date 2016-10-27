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
    private int status;//约访,被约访
    private int custType;//状态:0联系中-红色,1访谈成功-白色
    private String title;//标题名
    private String description;//内容
    private String createAt;//时间
    private String industryName;


    //访谈或约访页面数据
    private int invitationCode;//邀请码
    private int userId;
    private String userNickname;//姓名
    private String userAvatar;//头像
    private String userCompany;//公司
    private String content;//内容
    private String userAbility;//职能
    private String interviewTime;//详情-时间
    private int customType;//访谈形式

    public InterviewEntity() {
    }

    public InterviewEntity(int id) {
        this.id = id;
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

    public int getCustType() {
        return custType;
    }

    public void setCustType(int custType) {
        this.custType = custType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public int getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(int invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserAbility() {
        return userAbility;
    }

    public void setUserAbility(String userAbility) {
        this.userAbility = userAbility;
    }

    public String getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(String interviewTime) {
        this.interviewTime = interviewTime;
    }

    public int getCustomType() {
        return customType;
    }

    public void setCustomType(int customType) {
        this.customType = customType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
