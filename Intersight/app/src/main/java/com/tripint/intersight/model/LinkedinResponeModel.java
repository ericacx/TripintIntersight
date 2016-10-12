package com.tripint.intersight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedinResponeModel implements Serializable {

    private String firstName;
    private String headline;
    private String id;
    private SiteStandardProfileRequest siteStandardProfileRequest;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SiteStandardProfileRequest getSiteStandardProfileRequest() {
        return siteStandardProfileRequest;
    }

    public void setSiteStandardProfileRequest(SiteStandardProfileRequest siteStandardProfileRequest) {
        this.siteStandardProfileRequest = siteStandardProfileRequest;
    }

    public class SiteStandardProfileRequest {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}


