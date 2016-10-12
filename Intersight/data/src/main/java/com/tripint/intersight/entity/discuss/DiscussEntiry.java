package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.user.UserEntity;

import java.io.Serializable;

/**
 * Author: lirichen
 * Created by: ModelGenerator on 2016/9/22DiscussEntiry
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscussEntiry implements Serializable {

    private int id;
    private int uid;
    private String content;//标题
    private int industryId;
    private int fromUid;
    private String createAt;
    private int praises;
    private int isPraises;
    private int follows;
    private int isFollows;
    private int listens;
    private UserEntity userInfo;
    private VoiceEntity voices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getListens() {
        return listens;
    }

    public void setListens(int listens) {
        this.listens = listens;
    }

    public int getFromUid() {
        return fromUid;
    }

    public void setFromUid(int fromUid) {
        this.fromUid = fromUid;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getPraises() {
        return praises;
    }

    public void setPraises(int praises) {
        this.praises = praises;
    }

    public int getIsPraises() {
        return isPraises;
    }

    public void setIsPraises(int isPraises) {
        this.isPraises = isPraises;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getIsFollows() {
        return isFollows;
    }

    public void setIsFollows(int isFollows) {
        this.isFollows = isFollows;
    }

    public UserEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserEntity userInfo) {
        this.userInfo = userInfo;
    }

    public VoiceEntity getVoices() {
        return voices;
    }

    public void setVoices(VoiceEntity voices) {
        this.voices = voices;
    }
}