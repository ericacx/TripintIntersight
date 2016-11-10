package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Author: lirichen
 * Created by: ModelGenerator on 2016/9/28
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MineFollowPointEntity implements Serializable{


    /**
     * id : 2
     * title : 沃尔玛埃及棉床垫下架
     * content :
     * comments : 0
     * follows : 0
     * praises : 0
     * nickname : 刘进
     * avatar : http://oc153j0jh.bkt.clouddn.com/1472448319003R52335-17.jpg
     * jobName : PHP开发工程师
     * companyName : 推聘信息科技（上海）有限公司
     * industryName : 互联网·游戏·软件
     */


    private int id;
    private int userId;
    private String title;//标题
    private String content;//内容
    private int commentsCount;//评论数
    private int favoritesCount;//关注数
    private int praisesCount;//点赞数
    private String userNickname;//名字
    private String userAvatar;//头像
    private String userAbility;//职位
    private String userOrganization;//公司名
    private String industryName;//行业
    /**
     * id : 2
     * title : 沃尔玛埃及棉床垫下架
     * content :
     * status : 0
     * createAt : 1472529300
     * name : 电子·通信·硬件
     */


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public int getPraisesCount() {
        return praisesCount;
    }

    public void setPraisesCount(int praisesCount) {
        this.praisesCount = praisesCount;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserAbility() {
        return userAbility;
    }

    public void setUserAbility(String userAbility) {
        this.userAbility = userAbility;
    }

    public String getUserOrganization() {
        return userOrganization;
    }

    public void setUserOrganization(String userOrganization) {
        this.userOrganization = userOrganization;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}