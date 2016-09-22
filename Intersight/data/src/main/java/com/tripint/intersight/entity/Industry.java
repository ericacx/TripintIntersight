package com.tripint.intersight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lirichen on 16/9/12.
 */
public class Industry implements Serializable{
    private int id;
    private int pid;
    private String name;
    private String icon;
    private int status;
    private int sort;

    private List<IndustryChild> industry_sub;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonIgnore
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<IndustryChild> getIndustry_sub() {
        return industry_sub;
    }

    public void setIndustry_sub(List<IndustryChild> industry_sub) {
        this.industry_sub = industry_sub;
    }
}
