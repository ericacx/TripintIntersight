package com.tripint.intersight.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 个人信息
 * Created by Eric on 16/9/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalUserInfoEntity implements Serializable {

    private int toUid;
    private String contact;//联系人
    private String company;
    private String mobile;
    private String email;
    private String subject;//主题
    private int industryId;
    private String outline;


    public PersonalUserInfoEntity(int toUid, String contact, String company, String mobile, String email, String subject,int industryId, String outline) {
        this.toUid = toUid;
        this.contact = contact;
        this.company = company;
        this.mobile = mobile;
        this.email = email;
        this.subject = subject;
        this.industryId = industryId;
        this.outline = outline;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getToUid() {
        return toUid;
    }

    public void setToUid(int toUid) {
        this.toUid = toUid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }
}
