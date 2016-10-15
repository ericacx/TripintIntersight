package com.tripint.intersight.entity.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticlesEntity implements Serializable {

    private int type;//0:banner 1:图文 2:文字
    private List<ArticleBannerEntity> banner;//banner
    private List<ArticleContentEntity> article;//带有banner的文章资源
    private List<DetailEntity> detail;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ArticleBannerEntity> getBanner() {
        return banner;
    }

    public void setBanner(List<ArticleBannerEntity> banner) {
        this.banner = banner;
    }

    public List<ArticleContentEntity> getArticle() {
        return article;
    }

    public void setArticle(List<ArticleContentEntity> article) {
        this.article = article;
    }

    public List<DetailEntity> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailEntity> detail) {
        this.detail = detail;
    }
}
