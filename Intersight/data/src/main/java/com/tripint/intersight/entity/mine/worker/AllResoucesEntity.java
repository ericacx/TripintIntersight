package com.tripint.intersight.entity.mine.worker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.mine.student.QualificationsNameEntity;
import com.tripint.intersight.entity.mine.student.SchoolNameEntity;
import com.tripint.intersight.entity.mine.student.SpecialitiesNameEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/13.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllResoucesEntity implements Serializable {

    private List<CompanyNameEntity> companyName;//公司信息,名字和logo
    private List<IndustryNameEntity> industryName;//行业
    private List<AbilityNameEntity> abilityName;//职能
    private List<JobNameEntity> jobName;//职位

    private List<SchoolNameEntity> schoolName;//学校
    private List<SpecialitiesNameEntity> specialitiesName;//专业
    private List<QualificationsNameEntity> qualificationsName;//学历；

    public List<CompanyNameEntity> getCompanyName() {
        return companyName;
    }

    public void setCompanyName(List<CompanyNameEntity> companyName) {
        this.companyName = companyName;
    }

    public List<IndustryNameEntity> getIndustryName() {
        return industryName;
    }

    public void setIndustryName(List<IndustryNameEntity> industryName) {
        this.industryName = industryName;
    }

    public List<AbilityNameEntity> getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(List<AbilityNameEntity> abilityName) {
        this.abilityName = abilityName;
    }

    public List<JobNameEntity> getJobName() {
        return jobName;
    }

    public void setJobName(List<JobNameEntity> jobName) {
        this.jobName = jobName;
    }

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
