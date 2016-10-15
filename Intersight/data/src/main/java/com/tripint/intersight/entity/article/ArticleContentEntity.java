package com.tripint.intersight.entity.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleContentEntity implements Serializable {

    /*
     * 第二种类型  图文   第三种类型  :纯text
     */

    //图文
    private int id;
    private int uid;
    private String title;
    private int type;
    private String subTitle;
    private String content;
    private String thumb;//图片
    private int industryId;
    private int status;
    private int views;
    private int comments;
    private int follows;
    private int favorites;
    private int praises;
    private int unpraises;
    private int createAt;
    private int updateAt;
    private int removeAt;
    private ArticleUserInfoEntity articleUserInfoEntity;
    private ArticleIndustryEntity articleIndustryEntity;

    private int id2;
    private String content2;
    private int uid2;
    private int industryId2;
    private int createAt2;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getPraises() {
        return praises;
    }

    public void setPraises(int praises) {
        this.praises = praises;
    }

    public int getUnpraises() {
        return unpraises;
    }

    public void setUnpraises(int unpraises) {
        this.unpraises = unpraises;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public int getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(int updateAt) {
        this.updateAt = updateAt;
    }

    public int getRemoveAt() {
        return removeAt;
    }

    public void setRemoveAt(int removeAt) {
        this.removeAt = removeAt;
    }

    public ArticleUserInfoEntity getArticleUserInfoEntity() {
        return articleUserInfoEntity;
    }

    public void setArticleUserInfoEntity(ArticleUserInfoEntity articleUserInfoEntity) {
        this.articleUserInfoEntity = articleUserInfoEntity;
    }

    public ArticleIndustryEntity getArticleIndustryEntity() {
        return articleIndustryEntity;
    }

    public void setArticleIndustryEntity(ArticleIndustryEntity articleIndustryEntity) {
        this.articleIndustryEntity = articleIndustryEntity;
    }
}
