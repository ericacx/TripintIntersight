package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.Ability;
import com.tripint.intersight.entity.Industry;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/10/10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorEntity implements Serializable {
    private int uid;
    private String nickname;
    private String avatar; //": "http://oc153j0jh.bkt.clouddn.com/1472448319003R52335-17.jpg",
    private int industryId;//": 1,
    private int abilityId; //": 1,
    private Ability ability;//": {

    private Industry industry; //": {

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

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(int abilityId) {
        this.abilityId = abilityId;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }
}
