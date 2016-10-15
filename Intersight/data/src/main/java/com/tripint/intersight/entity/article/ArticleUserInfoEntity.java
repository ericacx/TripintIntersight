package com.tripint.intersight.entity.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleUserInfoEntity implements Serializable {

    private int uid;
    private String nickname;
    private String avatar;
    private String sex;
    private String birthday;
    private int industryId;
    private int abilityId;
    private int companyId;
    private int jobId;
    private String balance;
    private String experience;
    private int createAt;
    private int updateAt;
    private String desc;
    private int type;
    private int schoolId;
    private int specialitiesId;
    private int qualifications;
    private ArticlePositionEntity articlePositionEntity;
    private ArticleIndustryEntity articleIndustryEntity;
}
