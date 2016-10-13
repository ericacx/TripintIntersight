package com.tripint.intersight.entity.mine.worker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditUserEntity implements Serializable {

    private int type;//0,职员 1,学生
    private String nickname;//昵称
    private String mobile;//手机
    private String email;//邮箱
    private String avatar;//头像
    private String logo;//（公司,学校）logo
    private String desc;//个人简介

    private String company;//公司
    private int job;//职位
    private int industry_id;//行业
    private int ability_id;//职能
    private String experience;//工作年限

    private String school;//学校
    private int specialities;//专业
    private int qualifications;//学历


    public EditUserEntity() {
    }

    public EditUserEntity(int type, String nickname, String mobile, String email, String avatar,
                          String logo, String desc, String company, int job, int industry_id, int ability_id,
                          String experience) {
        this.type = type;
        this.nickname = nickname;
        this.mobile = mobile;
        this.email = email;
        this.avatar = avatar;
        this.logo = logo;
        this.desc = desc;
        this.company = company;
        this.job = job;
        this.industry_id = industry_id;
        this.ability_id = ability_id;
        this.experience = experience;
    }

    public EditUserEntity(int type, String nickname, String mobile, String email, String avatar, String logo,
                          String desc, String school, int specialities, int qualifications) {
        this.type = type;
        this.nickname = nickname;
        this.mobile = mobile;
        this.email = email;
        this.avatar = avatar;
        this.logo = logo;
        this.desc = desc;
        this.school = school;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getIndustry_id() {
        return industry_id;
    }

    public void setIndustry_id(int industry_id) {
        this.industry_id = industry_id;
    }

    public int getAbility_id() {
        return ability_id;
    }

    public void setAbility_id(int ability_id) {
        this.ability_id = ability_id;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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
}
