package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.entity.message.CommentPraiseEntity;
import com.tripint.intersight.entity.message.MessageContentEntity;
import com.tripint.intersight.entity.message.MessageEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Eric on 16/10/10.
 */

public class MessageDataHttpRequest extends HttpRequest {

    private static MessageDataHttpRequest instants;

    private MessageDataService service;

    private MessageDataHttpRequest(Context context) {
        super(context);
        service = retrofit.create(MessageDataService.class);
    }

    public static MessageDataHttpRequest getInstance(Context context){
        if (instants == null){
            instants = new MessageDataHttpRequest(context);
        }
        return instants;
    }

    /**
     * 消息---新消息
     *
     * @param subscriber
     */
    public void getNewMessage(Subscriber<MessageEntity> subscriber, int page) {
        Observable observable = service.getNewMessage(page, DEFAULT_PAGE_SIZE)
                .map(new HttpResultFunc<MessageEntity>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---访谈消息
     *
     * @param subscriber
     */
    public void getInterviewMessage(Subscriber<BasePageableResponse<MessageContentEntity>> subscriber, int page) {
        Observable observable = service.getInterviewMessage(page, DEFAULT_PAGE_SIZE)
                .map(new HttpResultFunc<BasePageableResponse<MessageContentEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---问答消息
     *
     * @param subscriber
     */
    public void getAskAnswerMessage(Subscriber<BasePageableResponse<MessageContentEntity>> subscriber, int page) {
        Observable observable = service.getAskAnswerMessage(page, DEFAULT_PAGE_SIZE)
                .map(new HttpResultFunc<BasePageableResponse<MessageContentEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---评论或赞消息
     *
     * @param subscriber
     */
    public void getCommentPraiseMessage(Subscriber<BasePageableResponse<CommentPraiseEntity>> subscriber, int page) {
        Observable observable = service.getCommentPraiseMessage(page, DEFAULT_PAGE_SIZE)
                .map(new HttpResultFunc<BasePageableResponse<CommentPraiseEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---系统消息
     *
     * @param subscriber
     */
//    public void getSystemMessage(Subscriber<BasePageableResponse<MessageContentEntity>> subscriber, int page) {
//        Observable observable = service.getSystemMessage(page, DEFAULT_PAGE_SIZE)
//                .map(new HttpResultFunc<BasePageableResponse<MessageContentEntity>>());
//        toSubscribe(observable, subscriber);
//    }
}
