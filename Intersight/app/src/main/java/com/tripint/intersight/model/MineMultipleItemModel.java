package com.tripint.intersight.model;

import com.tripint.intersight.common.widget.recyclerviewadapter.entity.MultiItemEntity;
import com.tripint.intersight.entity.discuss.DiscussEntity;
import com.tripint.intersight.entity.message.CommentPraiseEntity;
import com.tripint.intersight.entity.message.MessageContentEntity;
import com.tripint.intersight.entity.message.MessageEntity;
import com.tripint.intersight.entity.mine.AccountDetailEntity;
import com.tripint.intersight.entity.mine.AskAnswerEntity;
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

    public static final int HIS_OPTION = 100;//他的观点
    public static final int HIS_OPTION_FOLLOW = 101;//他关注的观点

    public static final int HIS_INTERVIEW = 300; //他的访谈

    public static final int HIS_DISCUSS = 400; //他的问答
    public static final int HIS_DISCUSS_FOLLOW = 401; //他关注的问答

    public static final int MY_MESSAGE_NEW = 50;//新消息
    public static final int MY_MESSAGE_INTERVIEW = 60;//访谈消息
    public static final int MY_MESSAGE_ASK_ANSWER = 70;//问答消息
    public static final int MY_MESSAGE_COMMENT_PRAISE = 80;// 评论/赞消息

    public static final int MY_ACCOUNT_DETAIL = 90;//我的账户明细
    private int itemType;

    private AskAnswerEntity askAnswerEntity;//问答(我的,他的)
    private MineFollowPointEntity mineFollowPointEntity;//观点（我的,他的）
    private InterviewEntity interviewEntity;//访谈（我的,他的）
    private FocusEntity focusEntity;//关注
    private MessageContentEntity messageContentEntity;//新消息,访谈消息,问答消息
    private CommentPraiseEntity commentPraiseEntity;//评论/赞消息
    private AccountDetailEntity accountDetailEntity;//账户明细
    private DiscussEntity discussEntity;

    public MineMultipleItemModel(int itemType,DiscussEntity discussEntity) {
        this.itemType = itemType;
        this.discussEntity = discussEntity;
    }

    public MineMultipleItemModel(int itemType, AskAnswerEntity model) {//问答
        this.itemType = itemType;
        this.askAnswerEntity = model;
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

    public MineMultipleItemModel(int itemType, MessageContentEntity model) {//新消息,访谈消息,问答消息
        this.itemType = itemType;
        this.messageContentEntity = model;
    }

    public MineMultipleItemModel(int itemType, CommentPraiseEntity model) {//评论/赞消息
        this.itemType = itemType;
        this.commentPraiseEntity = model;
    }

    public MineMultipleItemModel(int itemType, AccountDetailEntity model) {//账户明细
        this.itemType = itemType;
        this.accountDetailEntity = model;
    }

    public AccountDetailEntity getAccountDetailEntity() {
        return accountDetailEntity;
    }

    public void setAccountDetailEntity(AccountDetailEntity accountDetailEntity) {
        this.accountDetailEntity = accountDetailEntity;
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

    public AskAnswerEntity getAskAnswerEntity() {
        return askAnswerEntity;
    }

    public void setAskAnswerEntity(AskAnswerEntity askAnswerEntity) {
        this.askAnswerEntity = askAnswerEntity;
    }

    public MineFollowPointEntity getMineFollowPointEntity() {
        return mineFollowPointEntity;
    }

    public void setMineFollowPointEntity(MineFollowPointEntity mineFollowPointEntity) {
        this.mineFollowPointEntity = mineFollowPointEntity;
    }

    public CommentPraiseEntity getCommentPraiseEntity() {
        return commentPraiseEntity;
    }

    public void setCommentPraiseEntity(CommentPraiseEntity commentPraiseEntity) {
        this.commentPraiseEntity = commentPraiseEntity;
    }

    public MessageContentEntity getMessageContentEntity() {
        return messageContentEntity;
    }

    public void setMessageContentEntity(MessageContentEntity messageContentEntity) {
        this.messageContentEntity = messageContentEntity;
    }

    public DiscussEntity getDiscussEntity() {
        return discussEntity;
    }

    public void setDiscussEntity(DiscussEntity discussEntity) {
        this.discussEntity = discussEntity;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
