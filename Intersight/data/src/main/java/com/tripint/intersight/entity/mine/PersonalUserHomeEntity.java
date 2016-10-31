package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalUserHomeEntity implements Serializable {
    private String nickname;//名字
    private String avatar;//头像
    private String companyName;//公司
    private String abilityName;//职位
    private String industryName;//行业
    private int industryId;//行业
    private String experience;//工作年限
    private String desc;//个人简介
    private int role;//返回类型:1 职员 2 学生
    private String discussPay;
    private String interviewPay;
    private String userAccountCount;
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

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getDiscussPay() {
        return discussPay;
    }

    public void setDiscussPay(String discussPay) {
        this.discussPay = discussPay;
    }

    public String getInterviewPay() {
        return interviewPay;
    }

    public void setInterviewPay(String interviewPay) {
        this.interviewPay = interviewPay;
    }

    public String getUserAccountCount() {
        return userAccountCount;
    }

    public void setUserAccountCount(String userAccountCount) {
        this.userAccountCount = userAccountCount;
    }
}
