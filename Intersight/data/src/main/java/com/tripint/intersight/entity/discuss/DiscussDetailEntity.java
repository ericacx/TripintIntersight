package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Author: lirichen
 * Created by: ModelGenerator on 2016/9/22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscussDetailEntity implements Serializable {
    private DiscussEntiry detail;
    private VoiceEntity voices;
    private AuthorEntity author;
    private List<CommentEntity> comments;

    public DiscussEntiry getDetail() {
        return detail;
    }

    public void setDetail(DiscussEntiry detail) {
        this.detail = detail;
    }

    public VoiceEntity getVoices() {
        return voices;
    }

    public void setVoices(VoiceEntity voices) {
        this.voices = voices;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
}