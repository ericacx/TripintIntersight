package com.tripint.intersight.entity.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpinionDetailEntity implements Serializable {

    private int id;
    private String thumb;
    private String title;
    private String createAt;
    private int userId;
    private String userNickname;
    private String userCompany;
    private String userAbility;
    private int praisesCount;
    private int favoritesCount;
    private int commentsCount;
    private int isPraises;
    private int isFavorites;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserAbility() {
        return userAbility;
    }

    public void setUserAbility(String userAbility) {
        this.userAbility = userAbility;
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

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
