package com.tripint.intersight.entity.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentPraiseEntity implements Serializable{

    private String nickname;//名字
    private String avatar;//头像
    private String companyName;//公司
    private String abilityName;//职位
    private String title;//标题
    private String content;//别人的评论
    private String pidComment;//我的评论
    private int createAt;//创建时间
    private int state;//状态

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
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

    public String getPidComment() {
        return pidComment;
    }

    public void setPidComment(String pidComment) {
        this.pidComment = pidComment;
    }
}
