package com.tripint.intersight.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/9/22.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BannerEntity implements Serializable {
    private int link;
    private String url;
    private int type;
    private int id;

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
