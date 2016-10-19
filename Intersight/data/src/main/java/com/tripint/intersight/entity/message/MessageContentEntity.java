package com.tripint.intersight.entity.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageContentEntity implements Serializable {

    private int id;
    private String subject;//标题
    private int time;
    private int code;
    private String nickname;
    private int createAt;
    private int state;//3:发起访谈,

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
