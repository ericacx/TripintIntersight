package com.tripint.intersight.model;

/**
 * Created by lirichen on 16/9/14.
 */
public class QAModel {
    private String id;
    private String profileImage;
    private String title;
    private String name;
    private String company;
    private String jobTitle;
    private int likeCount;
    private int lisentetCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getLisentetCount() {
        return lisentetCount;
    }

    public void setLisentetCount(int lisentetCount) {
        this.lisentetCount = lisentetCount;
    }
}
