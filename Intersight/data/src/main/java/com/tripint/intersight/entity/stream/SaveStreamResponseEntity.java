package com.tripint.intersight.entity.stream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveStreamResponseEntity implements Serializable {
    private String audioUrl;
    private String flg;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }
}
