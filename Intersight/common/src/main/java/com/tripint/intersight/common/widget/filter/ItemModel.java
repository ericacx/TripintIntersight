package com.tripint.intersight.common.widget.filter;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/15.
 */

public class ItemModel implements Serializable {


    public ItemModel() {
    }

    public ItemModel(int key, String name) {
        this.key = key;
        this.name = name;
    }

    private int key;
    private String name;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
