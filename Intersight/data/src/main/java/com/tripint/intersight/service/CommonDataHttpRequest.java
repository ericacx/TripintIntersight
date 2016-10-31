package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.entity.discuss.CommentResultEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Eric on 16/10/29.
 */

public class CommonDataHttpRequest extends HttpRequest {

    public static final int ACTION_CREATE = 1;//创建
    public static final int ACTION_DELETE = 2;//取消

    public static final int TYPE_ARTICLE = 1;//文章（观点）
    public static final int TYPE_DISCUSS = 2;//问答
    public static final int TYPE_INTERVIEW = 3;//访谈
    public static final int TYPE_ARTICLE_COMMENT = 4;//文章（观点）的评论
    public static final int TYPE_DISCUSS_COMMENT = 5;//问答的评论
    public static final int TYPE_INTERVIEW_COMMENT = 6;//访谈的评论
    public static final int FLG_COMMENT = 1;//评论
    public static final int FLG_PRAISE = 2;//点赞
    public static final int FLG_FOCUS = 3;//关注

    public static final int PID = 0;//默认为0 ,只有二级评论才用,其他取0
    public static final String CONTENT = null;//内容为空

    private CommonDataService service;
    private static CommonDataHttpRequest instants;

    private CommonDataHttpRequest(Context context) {
        super(context);
        service = retrofit.create(CommonDataService.class);

    }

    public static CommonDataHttpRequest getInstance(Context context) {
        if (instants == null) {
            instants = new CommonDataHttpRequest(context);
        }
        return instants;
    }


    /**
     * 创建问答点赞
     * @param subscriber
     * @param itemId
     * @param toUid
     */
    public void createDiscussPraise(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid) {
        Observable observable = service.postCommon(itemId,TYPE_DISCUSS,ACTION_CREATE,toUid,CONTENT,FLG_PRAISE,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 取消问答点赞
     */
    public void deleteDiscussPraise(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid) {
        Observable observable = service.postCommon(itemId,TYPE_DISCUSS,ACTION_DELETE,toUid,CONTENT,FLG_PRAISE,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 创建问答关注
     * @param subscriber
     * @param itemId
     * @param toUid
     */
    public void createDiscussFocus(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid) {
        Observable observable = service.postCommon(itemId,TYPE_DISCUSS,ACTION_CREATE,toUid,CONTENT,FLG_FOCUS,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 取消问答关注
     */
    public void deleteDiscussFocus(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid) {
        Observable observable = service.postCommon(itemId,TYPE_DISCUSS,ACTION_DELETE,toUid,CONTENT,FLG_FOCUS,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 创建问答评论
     * @param subscriber
     * @param itemId
     * @param toUid
     */
    public void createDiscussComment(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid,String content) {
        Observable observable = service.postCommon(itemId,TYPE_DISCUSS,ACTION_CREATE,toUid,content,FLG_COMMENT,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 创建问答子评论
     * @param subscriber
     * @param itemId
     * @param toUid
     */
    public void createDiscussSubComment(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid,String content,int pid) {
        Observable observable = service.postCommon(itemId,TYPE_DISCUSS_COMMENT,ACTION_CREATE,toUid,content,FLG_COMMENT,pid)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }



    /**
     * 创建观点点赞
     * @param subscriber
     * @param itemId
     * @param toUid
     */
    public void createArticlePraise(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid) {
        Observable observable = service.postCommon(itemId,TYPE_ARTICLE,ACTION_CREATE,toUid,CONTENT,FLG_PRAISE,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 取消观点点赞
     */
    public void deleteArticlePraise(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid) {
        Observable observable = service.postCommon(itemId,TYPE_ARTICLE,ACTION_DELETE,toUid,CONTENT,FLG_PRAISE,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 创建观点关注
     * @param subscriber
     * @param itemId
     * @param toUid
     */
    public void createArticleFocus(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid) {
        Observable observable = service.postCommon(itemId,TYPE_ARTICLE,ACTION_CREATE,toUid,CONTENT,FLG_FOCUS,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 取消观点关注
     */
    public void deleteArticleFocus(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid) {
        Observable observable = service.postCommon(itemId,TYPE_ARTICLE,ACTION_DELETE,toUid,CONTENT,FLG_FOCUS,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 创建观点评论
     * @param subscriber
     * @param itemId
     * @param toUid
     */
    public void createArticleComment(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid,String content) {
        Observable observable = service.postCommon(itemId,TYPE_ARTICLE,ACTION_CREATE,toUid,content,FLG_COMMENT,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 创建观点子评论
     * @param subscriber
     * @param itemId
     * @param toUid
     */
    public void createArticleSubComment(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid,String content,int pid) {
        Observable observable = service.postCommon(itemId,TYPE_ARTICLE_COMMENT,ACTION_CREATE,toUid,content,FLG_COMMENT,pid)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }


    /**
     * 创建观点评论
     * @param subscriber
     * @param itemId
     * @param toUid
     */
    public void createInterviewComment(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid,String content) {
        Observable observable = service.postCommon(itemId,TYPE_INTERVIEW,ACTION_CREATE,toUid,content,FLG_COMMENT,PID)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 创建观点子评论
     * @param subscriber
     * @param itemId
     * @param toUid
     */
    public void createInterviewSubComment(Subscriber<CommentResultEntity> subscriber, int itemId, int toUid,String content,int pid) {
        Observable observable = service.postCommon(itemId,TYPE_INTERVIEW_COMMENT,ACTION_CREATE,toUid,content,FLG_COMMENT,pid)
                .map(new HttpResultFunc<CommentResultEntity>());
        toSubscribe(observable, subscriber);
    }
}
