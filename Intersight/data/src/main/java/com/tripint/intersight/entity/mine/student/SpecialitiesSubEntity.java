package com.tripint.intersight.entity.mine.student;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/13.
 */

public class SpecialitiesSubEntity implements Serializable {

    private int id;
    private int pid;
    private String name;

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
}
