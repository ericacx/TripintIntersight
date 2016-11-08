package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/28.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscussAskDetailEntity implements Serializable {
    private int id;
    private int actionCode;//1我的回答,2我的提问
    private int statusCode;//1待回答 2:已回答
    private int answerUserId;
    private String answerUserAvatar;
    private String answerUserNickname;
    private String answerUserCompany;
    private String answerUserAbility;
    private String answerCreateAt;
    private String authorCreateAt;
    private int authorUserId;
    private String authorUserAvatar;
    private String authorUserNickname;
    private String authorUserCompany;
    private String authorUserAbility;
    private String content;
    private String audioUrl;
    private int audioId;
    private int audioTime;
    private String payment;
    private int praisesCount;
    private int favoritesCount;
    private int isPraises;
    private int isFavorites;
    private int isPayment;
    private String listenPayment;

    public DiscussAskDetailEntity() {

    }

    public DiscussAskDetailEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getAnswerUserId() {
        return answerUserId;
    }

    public void setAnswerUserId(int answerUserId) {
        this.answerUserId = answerUserId;
    }

    public String getAnswerUserAvatar() {
        return answerUserAvatar;
    }

    public void setAnswerUserAvatar(String answerUserAvatar) {
        this.answerUserAvatar = answerUserAvatar;
    }

    public String getAnswerUserNickname() {
        return answerUserNickname;
    }

    public void setAnswerUserNickname(String answerUserNickname) {
        this.answerUserNickname = answerUserNickname;
    }

    public String getAnswerUserCompany() {
        return answerUserCompany;
    }

    public void setAnswerUserCompany(String answerUserCompany) {
        this.answerUserCompany = answerUserCompany;
    }

    public String getAnswerUserAbility() {
        return answerUserAbility;
    }

    public void setAnswerUserAbility(String answerUserAbility) {
        this.answerUserAbility = answerUserAbility;
    }

    public int getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(int authorUserId) {
        this.authorUserId = authorUserId;
    }

    public String getAuthorUserAvatar() {
        return authorUserAvatar;
    }

    public void setAuthorUserAvatar(String authorUserAvatar) {
        this.authorUserAvatar = authorUserAvatar;
    }

    public String getAuthorUserNickname() {
        return authorUserNickname;
    }

    public void setAuthorUserNickname(String authorUserNickname) {
        this.authorUserNickname = authorUserNickname;
    }

    public String getAuthorUserCompany() {
        return authorUserCompany;
    }

    public void setAuthorUserCompany(String authorUserCompany) {
        this.authorUserCompany = authorUserCompany;
    }

    public String getAuthorUserAbility() {
        return authorUserAbility;
    }

    public void setAuthorUserAbility(String authorUserAbility) {
        this.authorUserAbility = authorUserAbility;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    public int getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(int audioTime) {
        this.audioTime = audioTime;
    }

    public String getAnswerCreateAt() {
        return answerCreateAt;
    }

    public void setAnswerCreateAt(String answerCreateAt) {
        this.answerCreateAt = answerCreateAt;
    }

    public String getAuthorCreateAt() {
        return authorCreateAt;
    }

    public void setAuthorCreateAt(String authorCreateAt) {
        this.authorCreateAt = authorCreateAt;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getPraisesCount() {
        return praisesCount;
    }

    public void setPraisesCount(int praisesCount) {
        this.praisesCount = praisesCount;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public int getIsPraises() {
        return isPraises;
    }

    public void setIsPraises(int isPraises) {
        this.isPraises = isPraises;
    }

    public int getIsFavorites() {
        return isFavorites;
    }

    public void setIsFavorites(int isFavorites) {
        this.isFavorites = isFavorites;
    }

    public int getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(int isPayment) {
        this.isPayment = isPayment;
    }

    public String getListenPayment() {
        return listenPayment;
    }

    public void setListenPayment(String listenPayment) {
        this.listenPayment = listenPayment;
    }
}

