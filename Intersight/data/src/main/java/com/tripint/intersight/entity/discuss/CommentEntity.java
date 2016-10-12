package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.user.UserEntity;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/10.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentEntity implements Serializable {
    private int id;
    private int uid;
    private int pid;
    private int discussId;
    private String content;
    private String createAt;
    private String removeAt;
    private String toNickname;
    private UserEntity user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscussId() {
        return discussId;
    }

    public void setDiscussId(int discussId) {
        this.discussId = discussId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getRemoveAt() {
        return removeAt;
    }

    public void setRemoveAt(String removeAt) {
        this.removeAt = removeAt;
    }

    public String getToNickname() {
        return toNickname;
    }

    public void setToNickname(String toNickname) {
        this.toNickname = toNickname;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
