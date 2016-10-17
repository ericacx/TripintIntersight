package com.tripint.intersight.model;

import java.io.Serializable;

/**
 * Created by lenovo on 2014/5/3.
 */
public class Img implements Serializable {

    private String Url;
    private String Description;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
