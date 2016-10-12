package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.entity.discuss.DiscussEntiry;

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
    public void getNewMessage(Subscriber<BasePageableResponse<DiscussEntiry>> subscriber, int page, int size) {
        Observable observable = service.getNewMessage(page, size)
                .map(new HttpResultFunc<BasePageableResponse<DiscussEntiry>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---访谈消息
     *
     * @param subscriber
     */
    public void getInterviewMessage(Subscriber<BasePageableResponse<DiscussEntiry>> subscriber, int page, int size) {
        Observable observable = service.getInterviewMessage(page, size)
                .map(new HttpResultFunc<BasePageableResponse<DiscussEntiry>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---问答消息
     *
     * @param subscriber
     */
    public void getAskAnswerMessage(Subscriber<BasePageableResponse<DiscussEntiry>> subscriber, int page, int size) {
        Observable observable = service.getAskAnswerMessage(page, size)
                .map(new HttpResultFunc<BasePageableResponse<DiscussEntiry>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---评论或赞消息
     *
     * @param subscriber
     */
    public void getCommentAgreeMessage(Subscriber<BasePageableResponse<DiscussEntiry>> subscriber, int page, int size) {
        Observable observable = service.getCommentAgreeMessage(page, size)
                .map(new HttpResultFunc<BasePageableResponse<DiscussEntiry>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---系统消息
     *
     * @param subscriber
     */
    public void getSystemMessage(Subscriber<BasePageableResponse<DiscussEntiry>> subscriber, int page, int size) {
        Observable observable = service.getSystemMessage(page, size)
                .map(new HttpResultFunc<BasePageableResponse<DiscussEntiry>>());
        toSubscribe(observable, subscriber);
    }
}
