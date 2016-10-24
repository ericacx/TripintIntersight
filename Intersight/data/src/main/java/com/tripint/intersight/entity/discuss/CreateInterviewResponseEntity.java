package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 2016/10/20.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateInterviewResponseEntity implements Serializable {
    private String flg;

    private int interviewId;

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }
}
