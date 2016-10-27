package com.tripint.intersight.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lirichen on 2016/9/21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndustryListEntity implements Serializable {

    private List<Industry> industry;

    public List<Industry> getIndustry() {
        return industry;
    }

    public void setIndustry(List<Industry> industry) {
        this.industry = industry;
    }
}
