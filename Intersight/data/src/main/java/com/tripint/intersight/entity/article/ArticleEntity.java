package com.tripint.intersight.entity.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.UserInfoEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleEntity implements Serializable {

    private List<ArticleContentEntity> articleContentEntityList;
}
