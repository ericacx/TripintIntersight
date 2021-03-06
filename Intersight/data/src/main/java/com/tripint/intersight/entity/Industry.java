package com.tripint.intersight.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lirichen on 16/9/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Industry implements Serializable{
    private int id;
    private int pid;
    private String name;
    private String icon;
    private int status;
    private int sort;

    private boolean isChecked;

    private List<IndustryChild> industrySub;

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

    public List<IndustryChild> getIndustrySub() {
        return industrySub;
    }

    public void setIndustrySub(List<IndustryChild> industrySub) {
        this.industrySub = industrySub;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
