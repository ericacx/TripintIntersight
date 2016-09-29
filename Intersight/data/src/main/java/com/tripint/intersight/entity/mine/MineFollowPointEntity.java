package com.tripint.intersight.entity.mine;

import java.io.Serializable;

/**
 * Author: lirichen
 * Created by: ModelGenerator on 2016/9/28
 */
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
    private String title;//标题
    private String content;//内容
    private int comments;//评论数
    private int follows;//关注数
    private int praises;//点赞数
    private String nickname;//名字
    private String avatar;//头像
    private String jobName;//职位
    private String companyName;//公司名
    private String industryName;//行业

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPraises() {
        return praises;
    }

    public void setPraises(int praises) {
        this.praises = praises;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

}