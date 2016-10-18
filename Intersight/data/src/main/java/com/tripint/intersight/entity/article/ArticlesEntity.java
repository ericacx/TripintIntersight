package com.tripint.intersight.entity.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tripint.intersight.entity.common.BannerEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticlesEntity implements Serializable {

    private int type;//0:banner 1:图文 2:文字
    private List<BannerEntity> banner;//banner
    private List<ArticleContentEntity> article;//带有banner的文章资源
    private List<TextDetailEntity> textDetail;//文字
    private List<ArticleContentEntity> imgTextDetail;//图文

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<BannerEntity> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerEntity> banner) {
        this.banner = banner;
    }

    public List<ArticleContentEntity> getArticle() {
        return article;
    }

    public void setArticle(List<ArticleContentEntity> article) {
        this.article = article;
    }

    public List<TextDetailEntity> getTextDetail() {
        return textDetail;
    }

    public void setTextDetail(List<TextDetailEntity> textDetail) {
        this.textDetail = textDetail;
    }

    public List<ArticleContentEntity> getImgTextDetail() {
        return imgTextDetail;
    }

    public void setImgTextDetail(List<ArticleContentEntity> imgTextDetail) {
        this.imgTextDetail = imgTextDetail;
    }
}
