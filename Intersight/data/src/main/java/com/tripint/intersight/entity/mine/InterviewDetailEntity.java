package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.discuss.CommentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterviewDetailEntity implements Serializable {

    private InterviewEntity interviewEntity;
    private List<CommentEntity> commentEntityList;

    public InterviewEntity getInterviewEntity() {
        return interviewEntity;
    }

    public void setInterviewEntity(InterviewEntity interviewEntity) {
        this.interviewEntity = interviewEntity;
    }

    public List<CommentEntity> getCommentEntityList() {
        return commentEntityList;
    }

    public void setCommentEntityList(List<CommentEntity> commentEntityList) {
        this.commentEntityList = commentEntityList;
    }
}
