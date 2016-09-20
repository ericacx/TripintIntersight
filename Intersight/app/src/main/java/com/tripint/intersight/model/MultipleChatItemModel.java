package com.tripint.intersight.model;

import com.tripint.intersight.common.widget.recyclerviewadapter.entity.MultiItemEntity;

/**
 * Created by lirichen on 2016/9/20.
 */

public class MultipleChatItemModel implements MultiItemEntity{

    public static final int CHAT_LEFT = 1;
    public static final int CHAT_RIGHT = 2;

    public static final int SPAN_SIZE = 1;

    public MultipleChatItemModel(int itemType, QADetailModel model){
        this.itemType = itemType;
        this.content = model;
    }

    private int itemType;
    private QADetailModel content;



    public QADetailModel getContent() {
        return content;
    }

    public void setContent(QADetailModel content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
