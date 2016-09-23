package com.tripint.intersight.entity.discuss;

import com.tripint.intersight.entity.common.BannerEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lirichen on 2016/9/22.
 */

public class DiscussPageEntity implements Serializable {
    private List<BannerEntity> banners;
    private List<DiscussEntiry> discusses;

    public List<BannerEntity> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerEntity> banners) {
        this.banners = banners;
    }

    public List<DiscussEntiry> getDiscusses() {
        return discusses;
    }

    public void setDiscusses(List<DiscussEntiry> discusses) {
        this.discusses = discusses;
    }
}
