package com.tripint.intersight.entity.mine.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QualificationsNameEntity implements Serializable {

    private int id;
    private int pid;
    private String name;
    private int sort;
    private List<QualificationsSubEntity> qualificationsSubEntityList;

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<QualificationsSubEntity> getQualificationsSubEntityList() {
        return qualificationsSubEntityList;
    }

    public void setQualificationsSubEntityList(List<QualificationsSubEntity> qualificationsSubEntityList) {
        this.qualificationsSubEntityList = qualificationsSubEntityList;
    }
}
