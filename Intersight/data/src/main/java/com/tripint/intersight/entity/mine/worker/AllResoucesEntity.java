package com.tripint.intersight.entity.mine.worker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/13.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllResoucesEntity implements Serializable {

    private List<CompanyNameEntity> companyNameEntityList;//公司信息,名字和logo
    private List<IndustryNameEntity> industryNameEntityList;//行业
    private List<AbilityNameEntity> abilityNameEntityList;//职能
    private List<JobNameEntity> jobNameEntityList;//职位

    public List<CompanyNameEntity> getCompanyNameEntityList() {
        return companyNameEntityList;
    }

    public void setCompanyNameEntityList(List<CompanyNameEntity> companyNameEntityList) {
        this.companyNameEntityList = companyNameEntityList;
    }

    public List<IndustryNameEntity> getIndustryNameEntityList() {
        return industryNameEntityList;
    }

    public void setIndustryNameEntityList(List<IndustryNameEntity> industryNameEntityList) {
        this.industryNameEntityList = industryNameEntityList;
    }

    public List<AbilityNameEntity> getAbilityNameEntityList() {
        return abilityNameEntityList;
    }

    public void setAbilityNameEntityList(List<AbilityNameEntity> abilityNameEntityList) {
        this.abilityNameEntityList = abilityNameEntityList;
    }

    public List<JobNameEntity> getJobNameEntityList() {
        return jobNameEntityList;
    }

    public void setJobNameEntityList(List<JobNameEntity> jobNameEntityList) {
        this.jobNameEntityList = jobNameEntityList;
    }
}
