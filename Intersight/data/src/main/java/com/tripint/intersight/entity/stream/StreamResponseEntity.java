package com.tripint.intersight.entity.stream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamResponseEntity implements Serializable {
    private String publishUrl;

    public String getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }
}
