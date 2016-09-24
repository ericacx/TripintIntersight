package com.tripint.intersight.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lirichen on 2016/9/21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchFilterEntity implements Serializable {

    private List<Industry> industry;
    private List<Ability> ability;
    private List<Company> keywords;

    public List<Ability> getAbility() {
        return ability;
    }

    public void setAbility(List<Ability> ability) {
        this.ability = ability;
    }

    public List<Company> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Company> keywords) {
        this.keywords = keywords;
    }

    public List<Industry> getIndustry() {
        return industry;
    }

    public void setIndustry(List<Industry> industry) {
        this.industry = industry;
    }
}
