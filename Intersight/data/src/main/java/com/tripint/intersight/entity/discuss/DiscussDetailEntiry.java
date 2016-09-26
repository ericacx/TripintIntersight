package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.user.UserEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Author: lirichen
 * Created by: ModelGenerator on 2016/9/22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscussDetailEntiry implements Serializable {
    private List<DiscussEntiry> discussDetail;

    public List<DiscussEntiry> getDiscussDetail() {
        return discussDetail;
    }

    public void setDiscussDetail(List<DiscussEntiry> discussDetail) {
        this.discussDetail = discussDetail;
    }
}