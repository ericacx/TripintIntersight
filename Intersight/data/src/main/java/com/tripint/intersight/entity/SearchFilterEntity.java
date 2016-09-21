package com.tripint.intersight.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lirichen on 2016/9/21.
 */

public class SearchFilterEntity implements Serializable {
    private List<Industry> industry;
    private List<Ability> function;
    private List<Ability> keywords;

    public List<Ability> getFunction() {
        return function;
    }

    public void setFunction(List<Ability> function) {
        this.function = function;
    }

    public List<Ability> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Ability> keywords) {
        this.keywords = keywords;
    }

    public List<Industry> getIndustry() {
        return industry;
    }

    public void setIndustry(List<Industry> industry) {
        this.industry = industry;
    }
}
