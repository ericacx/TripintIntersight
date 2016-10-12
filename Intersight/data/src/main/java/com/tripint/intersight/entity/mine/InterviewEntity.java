package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/9/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterviewEntity implements Serializable {

    //列表返回数据
    private int id;
    private int status;//状态:0联系中,1访谈成功
    private int type;//0约访 1被约访
    private String subject;//标题名
    private String description;//内容
    private int createAt;//时间
    private String name;//行业

    //item = 列表数据+以下参数
    private int code;//会议邀请码
    private String nickname;//姓名
    private String avatar;//头像
    private String companyNmae;//公司名
    private String abilityName;//职位

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompanyNmae() {
        return companyNmae;
    }

    public void setCompanyNmae(String companyNmae) {
        this.companyNmae = companyNmae;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }
}
