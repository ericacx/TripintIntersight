package com.tripint.intersight.entity.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleUserInfoEntity implements Serializable {

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
    private int type;
    private int schoolId;
    private int specialitiesId;
    private int qualifications;
    private String job;
    private ArticlePositionEntity position;
    private ArticleIndustryEntity industry;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getSpecialitiesId() {
        return specialitiesId;
    }

    public void setSpecialitiesId(int specialitiesId) {
        this.specialitiesId = specialitiesId;
    }

    public int getQualifications() {
        return qualifications;
    }

    public void setQualifications(int qualifications) {
        this.qualifications = qualifications;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public ArticlePositionEntity getPosition() {
        return position;
    }

    public void setPosition(ArticlePositionEntity position) {
        this.position = position;
    }

    public ArticleIndustryEntity getIndustry() {
        return industry;
    }

    public void setIndustry(ArticleIndustryEntity industry) {
        this.industry = industry;
    }
}
