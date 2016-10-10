package com.tripint.intersight.model;

import com.tripint.intersight.common.widget.recyclerviewadapter.entity.MultiItemEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;

/**
 * Created by lirichen on 2016/9/20.
 */

public class MultipleSearchItemModel implements MultiItemEntity{

    public static final int INTERVIEW = 1;
    public static final int ARTICLE = 2;

    public static final int SPAN_SIZE = 1;


    private int itemType;
    private DiscussEntiry content;


    public MultipleSearchItemModel(int itemType, DiscussEntiry model){
        this.itemType = itemType;
        this.content = model;
    }

    public DiscussEntiry getContent() {
        return content;
    }

    public void setContent(DiscussEntiry content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
