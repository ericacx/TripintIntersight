package com.tripint.intersight.model;

import com.tripint.intersight.common.widget.recyclerviewadapter.entity.MultiItemEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.message.MessageDataEntity;
import com.tripint.intersight.entity.mine.FocusEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;

/**
 * Created by lirichen on 2016/9/20.
 */

public class MineMultipleItemModel implements MultiItemEntity {

    public static final int MY_OPTION = 10; //我的观点
    public static final int MY_OPTION_FOLLOW = 11; //我关注的观点

    public static final int MY_FOCUSE = 20; //我的关注
    public static final int MY_FOCUSE_FOLLOW = 21; //被关注

    public static final int MY_INTERVIEW = 30; //我的访谈

    public static final int MY_DISCUSS = 40; //我的问答
    public static final int MY_DISCUSS_FOLLOW = 41; //我关注的问答

    public static final int MY_MESSAGE_NEW = 50;//新消息
    public static final int MY_MESSAGE_INTERVIEW = 60;//访谈消息
    public static final int MY_MESSAGE_ASK_ANSWER = 70;//问答消息

    private int itemType;
    private DiscussEntiry discussEntiry;//问答
    private MineFollowPointEntity mineFollowPointEntity;//观点
    private FocusEntity focusEntity;//关注
    private InterviewEntity interviewEntity;//我的访谈
    private MessageDataEntity messageDataEntity;//新消息,访谈消息,问答消息

    public MineMultipleItemModel(int itemType, DiscussEntiry model) {//问答
        this.itemType = itemType;
        this.discussEntiry = model;
    }

    public MineMultipleItemModel(int itemType, FocusEntity model) {//关注
        this.itemType = itemType;
        this.focusEntity = model;
    }

    public MineMultipleItemModel(int itemType, MineFollowPointEntity model) {//观点
        this.itemType = itemType;
        this.mineFollowPointEntity = model;
    }

    public MineMultipleItemModel(int itemType, InterviewEntity model) {//访谈
        this.itemType = itemType;
        this.interviewEntity = model;
    }

    public MineMultipleItemModel(int itemType, MessageDataEntity model) {//新消息,访谈消息,问答消息
        this.itemType = itemType;
        this.messageDataEntity = model;
    }

    public InterviewEntity getInterviewEntity() {
        return interviewEntity;
    }

    public void setInterviewEntity(InterviewEntity interviewEntity) {
        this.interviewEntity = interviewEntity;
    }

    public FocusEntity getFocusEntity() {
        return focusEntity;
    }

    public void setFocusEntity(FocusEntity focusEntity) {
        this.focusEntity = focusEntity;
    }

    public DiscussEntiry getDiscussEntiry() {
        return discussEntiry;
    }

    public void setDiscussEntiry(DiscussEntiry discussEntiry) {
        this.discussEntiry = discussEntiry;
    }

    public MineFollowPointEntity getMineFollowPointEntity() {
        return mineFollowPointEntity;
    }

    public void setMineFollowPointEntity(MineFollowPointEntity mineFollowPointEntity) {
        this.mineFollowPointEntity = mineFollowPointEntity;
    }

    public MessageDataEntity getMessageDataEntity() {
        return messageDataEntity;
    }

    public void setMessageDataEntity(MessageDataEntity messageDataEntity) {
        this.messageDataEntity = messageDataEntity;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
