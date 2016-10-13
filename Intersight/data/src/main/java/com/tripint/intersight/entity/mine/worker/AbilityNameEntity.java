package com.tripint.intersight.entity.mine.worker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbilityNameEntity implements Serializable {
    //职能
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
