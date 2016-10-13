package com.tripint.intersight.entity.mine.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchoolNameEntity implements Serializable {

    private int schoolId;
    private String schoolName;
    private String logo;

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
