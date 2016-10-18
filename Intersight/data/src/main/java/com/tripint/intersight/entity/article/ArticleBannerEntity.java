package com.tripint.intersight.entity.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.common.BannerEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleBannerEntity implements Serializable {
    private List<BannerEntity> banner;

    public List<BannerEntity> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerEntity> banner) {
        this.banner = banner;
    }
}
