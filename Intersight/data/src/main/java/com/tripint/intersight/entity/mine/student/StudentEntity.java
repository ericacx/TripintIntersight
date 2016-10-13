package com.tripint.intersight.entity.mine.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentEntity implements Serializable {

    private List<SchoolNameEntity> schoolNameEntityList;
    private List<SpecialitiesNameEntity> specialitiesNameEntityList;
    private List<QualificationsNameEntity> qualificationsNameEntityList;

    public List<SchoolNameEntity> getSchoolNameEntityList() {
        return schoolNameEntityList;
    }

    public void setSchoolNameEntityList(List<SchoolNameEntity> schoolNameEntityList) {
        this.schoolNameEntityList = schoolNameEntityList;
    }

    public List<SpecialitiesNameEntity> getSpecialitiesNameEntityList() {
        return specialitiesNameEntityList;
    }

    public void setSpecialitiesNameEntityList(List<SpecialitiesNameEntity> specialitiesNameEntityList) {
        this.specialitiesNameEntityList = specialitiesNameEntityList;
    }

    public List<QualificationsNameEntity> getQualificationsNameEntityList() {
        return qualificationsNameEntityList;
    }

    public void setQualificationsNameEntityList(List<QualificationsNameEntity> qualificationsNameEntityList) {
        this.qualificationsNameEntityList = qualificationsNameEntityList;
    }
}
