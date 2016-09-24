package com.tripint.intersight.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 个人信息
 * Created by Eric on 16/9/24.
 */

public class UserInfoEntity implements Serializable {

    private String nickname;
    private String sex;
    @SerializedName("industry_id")
    private String industryId;
    @SerializedName("industry_id")
    private String jobId;
    private String title;
    private String company;
    private String uid;

    @SerializedName("create_at")
    private String createAt;
    private int id;


    public UserInfoEntity() {
    }

    public UserInfoEntity(String nickname, String sex, String industryId, String jobId, String title,
                          String company, String uid) {
        this.nickname = nickname;
        this.sex = sex;
        this.industryId = industryId;
        this.jobId = jobId;
        this.title = title;
        this.company = company;
        this.uid = uid;
    }

    public UserInfoEntity(String nickname, String sex, String industryId, String jobId, String title,
                          String company, String uid, String createAt, int id) {
        this.nickname = nickname;
        this.sex = sex;
        this.industryId = industryId;
        this.jobId = jobId;
        this.title = title;
        this.company = company;
        this.uid = uid;
        this.createAt = createAt;
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
