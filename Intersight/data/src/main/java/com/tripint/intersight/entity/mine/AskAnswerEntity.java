package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.user.UserEntity;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AskAnswerEntity implements Serializable {

    private int id;
    private String action;//我的问答,我的提问
    private String content;//标题
    private String createAt;//时间
    private String industry;//行业
    private String status;//待回答,已回答

    private UserEntity userEntity;//用户信息（头像,名字,职位,公司）

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
