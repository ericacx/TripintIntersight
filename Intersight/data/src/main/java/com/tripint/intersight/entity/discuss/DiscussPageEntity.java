package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.common.BannerEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lirichen on 2016/9/22.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscussPageEntity implements Serializable {

    private List<BannerEntity> banners = new ArrayList<>();
    private List<DiscussEntiry> discuss =  new ArrayList<>();

    public List<BannerEntity> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerEntity> banners) {
        this.banners = banners;
    }

    public List<DiscussEntiry> getDiscuss() {
        return discuss;
    }

    public void setDiscuss(List<DiscussEntiry> discuss) {
        this.discuss = discuss;
    }
}
