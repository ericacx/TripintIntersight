package com.tripint.intersight.entity.mine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/9/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FocusEntity implements Serializable {


    /**
     * uid : 26
     * nickname : 刘进
     * avatar : http://oc153j0jh.bkt.clouddn.com/1472448319003R52335-17.jpg
     * companyName : 推聘信息科技（上海）有限公司
     * abilityName : 销售管理
     * industryName : 互联网·游戏·软件
     */

    private int uid;
    private String nickname;//名字
    private String avatar;//头像
    private String worktime;//工作年限
    private String companyName;//公司名称
    private String abilityName;//职位名称
    private String industryName;//行业名称

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
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

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

}
