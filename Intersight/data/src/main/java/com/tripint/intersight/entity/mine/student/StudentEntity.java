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

    private List<SchoolNameEntity> schoolName;//学校
    private List<SpecialitiesNameEntity> specialitiesName;//专业
    private List<QualificationsNameEntity> qualificationsName;//学历；

    public List<SchoolNameEntity> getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(List<SchoolNameEntity> schoolName) {
        this.schoolName = schoolName;
    }

    public List<SpecialitiesNameEntity> getSpecialitiesName() {
        return specialitiesName;
    }

    public void setSpecialitiesName(List<SpecialitiesNameEntity> specialitiesName) {
        this.specialitiesName = specialitiesName;
    }

    public List<QualificationsNameEntity> getQualificationsName() {
        return qualificationsName;
    }

    public void setQualificationsName(List<QualificationsNameEntity> qualificationsName) {
        this.qualificationsName = qualificationsName;
    }
}
