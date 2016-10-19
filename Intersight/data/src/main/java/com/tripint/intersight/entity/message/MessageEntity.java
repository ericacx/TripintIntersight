package com.tripint.intersight.entity.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/19.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageEntity implements Serializable {

    private List<MessageContentEntity> lists;
    private int interview;//0代表没有新消息,其他数字代表有新消息,在访谈消息显示红点
    private int discuss;
    private int comment;

    public List<MessageContentEntity> getLists() {
        return lists;
    }

    public void setLists(List<MessageContentEntity> lists) {
        this.lists = lists;
    }

    public int getInterview() {
        return interview;
    }

    public void setInterview(int interview) {
        this.interview = interview;
    }

    public int getDiscuss() {
        return discuss;
    }

    public void setDiscuss(int discuss) {
        this.discuss = discuss;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
