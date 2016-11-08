package com.tripint.intersight.entity.mine.worker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditUserEntity implements Serializable {

    public static final int USER_TYPE_STUFF = 1;//职员
    public static final int USER_TYPE_STUDENT = 2;//学生

    private int type;//1,职员 2,学生
    private String nickname;//昵称
    private String mobile;//手机
    private String email;//邮箱
    private String avatar;//头像
    private String logo;//（公司,学校）logo
    private String desc;//个人简介

    private String organization;//公司、学校
    private String job;//职位
    private int industryId;//行业
    private int abilityId;//职能
    private String experience;//工作年限

    private int specialities;//专业
    private int qualifications;//学历


    public EditUserEntity(int type) {
        this.type = type;
    }

    public EditUserEntity(int type, String nickname, String mobile, String email, String avatar,
                          String logo, String desc, String organization, String job, int industryId, int abilityId,
                          String experience) {
        this.type = type;
        this.nickname = nickname;
        this.mobile = mobile;
        this.email = email;
        this.avatar = avatar;
        this.logo = logo;
        this.desc = desc;
        this.organization = organization;
        this.job = job;
        this.industryId = industryId;
        this.abilityId = abilityId;
        this.experience = experience;
    }

    public EditUserEntity(int type, String nickname, String mobile, String email, String avatar, String logo,
                          String desc, String organization, int specialities, int qualifications) {
        this.type = type;
        this.nickname = nickname;
        this.mobile = mobile;
        this.email = email;
        this.avatar = avatar;
        this.logo = logo;
        this.desc = desc;
        this.organization = organization;
        this.specialities = specialities;
        this.qualifications = qualifications;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }


    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getSpecialities() {
        return specialities;
    }

    public void setSpecialities(int specialities) {
        this.specialities = specialities;
    }

    public int getQualifications() {
        return qualifications;
    }

    public void setQualifications(int qualifications) {
        this.qualifications = qualifications;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(int abilityId) {
        this.abilityId = abilityId;
    }
}
