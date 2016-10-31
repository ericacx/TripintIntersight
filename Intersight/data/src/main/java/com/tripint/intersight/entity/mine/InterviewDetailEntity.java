package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.article.ArticleCommentEntity;
import com.tripint.intersight.entity.discuss.CommentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterviewDetailEntity implements Serializable {

    private InterviewEntity interview;
    private List<ArticleCommentEntity> comments;


    public InterviewEntity getInterview() {
        return interview;
    }

    public void setInterview(InterviewEntity interview) {
        this.interview = interview;
    }

    public List<ArticleCommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<ArticleCommentEntity> comments) {
        this.comments = comments;
    }
}
