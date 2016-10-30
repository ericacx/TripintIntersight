package com.tripint.intersight.entity.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.discuss.CommentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDetailEntity implements Serializable {

    private OpinionDetailEntity detail;
    private List<ArticleCommentEntity> comments;

    public OpinionDetailEntity getDetail() {
        return detail;
    }

    public void setDetail(OpinionDetailEntity detail) {
        this.detail = detail;
    }

    public List<ArticleCommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<ArticleCommentEntity> comments) {
        this.comments = comments;
    }
}
