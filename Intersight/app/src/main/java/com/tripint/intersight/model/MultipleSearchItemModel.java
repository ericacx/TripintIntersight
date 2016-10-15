package com.tripint.intersight.model;

import com.tripint.intersight.common.widget.recyclerviewadapter.entity.MultiItemEntity;
import com.tripint.intersight.entity.SearchArticleEntity;
import com.tripint.intersight.entity.discuss.InterviewEntity;

/**
 * Created by lirichen on 2016/9/20.
 */

public class MultipleSearchItemModel implements MultiItemEntity{

    public static final int INTERVIEW = 1;
    public static final int ARTICLE = 2;

    public static final int SPAN_SIZE = 1;


    private int itemType;
    private SearchArticleEntity content;

    private InterviewEntity interview;


    public MultipleSearchItemModel(int itemType, SearchArticleEntity model) {
        this.itemType = itemType;
        this.content = model;
    }

    public MultipleSearchItemModel(int itemType, InterviewEntity model) {
        this.itemType = itemType;
        this.interview = model;
    }

    public SearchArticleEntity getContent() {
        return content;
    }

    public void setContent(SearchArticleEntity content) {
        this.content = content;
    }

    public InterviewEntity getInterview() {
        return interview;
    }

    public void setInterview(InterviewEntity interview) {
        this.interview = interview;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
