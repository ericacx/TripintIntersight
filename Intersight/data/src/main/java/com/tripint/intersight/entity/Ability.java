package com.tripint.intersight.entity;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/9/21.
 */

public class Ability implements Serializable {
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
