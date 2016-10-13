package com.tripint.intersight.entity.mine.worker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndustryNameEntity implements Serializable {

    private int id;
    private int pid;
    private String name;
    private String icon;
    private List<IndustrySubEntity> industrySubEntityList;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<IndustrySubEntity> getIndustrySubEntityList() {
        return industrySubEntityList;
    }

    public void setIndustrySubEntityList(List<IndustrySubEntity> industrySubEntityList) {
        this.industrySubEntityList = industrySubEntityList;
    }
}
