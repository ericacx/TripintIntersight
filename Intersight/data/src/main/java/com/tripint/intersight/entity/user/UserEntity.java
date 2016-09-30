package com.tripint.intersight.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.Ability;
import com.tripint.intersight.entity.Company;
import com.tripint.intersight.entity.Industry;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/9/22.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntity implements Serializable {
    private int uid;
    private String nickname;//名字
    private String avatar;//头像
    private Ability ability;//职位
    private Company company;//公司
    private Industry industry;//行业

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

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }
}
