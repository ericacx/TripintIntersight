package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.Ability;
import com.tripint.intersight.entity.Company;
import com.tripint.intersight.entity.Industry;

/**
 * Author: lirichen
 * Created by: ModelGenerator on 2016/10/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterviewEntity {
    private int uid;
    private String nickname;
    private String avatar;
    private String sex;
    private String brithday;
    private int industryId;
    private int abilityId;
    private int companyId;
    private int jobId;
    private String balance;
    private String experience;
    private String createAt;
    private int updateAt;
    private String desc;
    private Ability position;
    private Industry company;
    private Company industry;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(int updateAt) {
        this.updateAt = updateAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Ability getPosition() {
        return position;
    }

    public void setPosition(Ability position) {
        this.position = position;
    }

    public Industry getCompany() {
        return company;
    }

    public void setCompany(Industry company) {
        this.company = company;
    }

    public Company getIndustry() {
        return industry;
    }

    public void setIndustry(Company industry) {
        this.industry = industry;
    }
}