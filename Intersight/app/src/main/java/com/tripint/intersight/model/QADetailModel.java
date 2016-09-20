package com.tripint.intersight.model;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/9/19.
 */
public class QADetailModel implements Serializable {
    private String personName;
    private String company;
    private String jobTitle;
    private String message;
    private String datetime;
    private String voiceMessageUrl;
    private String profileImgUrl;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getVoiceMessageUrl() {
        return voiceMessageUrl;
    }

    public void setVoiceMessageUrl(String voiceMessageUrl) {
        this.voiceMessageUrl = voiceMessageUrl;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }
}
