package com.tripint.intersight.model;

import com.tripint.intersight.common.widget.recyclerviewadapter.entity.MultiItemEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;

/**
 * Created by lirichen on 2016/9/20.
 */

public class MineMultipleItemModel implements MultiItemEntity {

    public static final int MY_OPTION = 10; //我的观点
    public static final int MY_OPTION_FOLLOW = 11; //我关注的观点

    public static final int MY_FOCUSE = 20; //
    public static final int MY_FOCUSE_FOLLOW = 21; //

    public static final int MY_INTERVIEW = 30; //
    public static final int MY_INTERVIEW_FOLLOW = 31; //

    public static final int MY_DISCUSS = 40; //
    public static final int MY_DISCUSS_FOLLOW = 41; //


    private int itemType;
    private DiscussEntiry discussEntiry;
    private MineFollowPointEntity mineFollowPointEntity;


    public MineMultipleItemModel(int itemType, DiscussEntiry model) {
        this.itemType = itemType;
        this.discussEntiry = model;
    }

    public MineMultipleItemModel(int itemType, MineFollowPointEntity model) {
        this.itemType = itemType;
        this.mineFollowPointEntity = model;
    }

    public DiscussEntiry getContent() {
        return discussEntiry;
    }

    public void setContent(DiscussEntiry content) {
        this.discussEntiry = content;
    }


    public MineFollowPointEntity getMineFollowPointEntity() {
        return mineFollowPointEntity;
    }

    public void setMineFollowPointEntity(MineFollowPointEntity mineFollowPointEntity) {
        this.mineFollowPointEntity = mineFollowPointEntity;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
