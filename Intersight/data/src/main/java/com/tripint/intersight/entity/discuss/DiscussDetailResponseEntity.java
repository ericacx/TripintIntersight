package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Author: lirichen
 * Created by: ModelGenerator on 2016/9/22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscussDetailResponseEntity implements Serializable {
    private DiscussAskDetailEntity detail;
    private List<CommentEntity> comments;

    public DiscussAskDetailEntity getDetail() {
        return detail;
    }

    public void setDetail(DiscussAskDetailEntity detail) {
        this.detail = detail;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
}