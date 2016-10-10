package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.entity.discuss.DiscussPageEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;

import java.util.List;

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
    public void getNewMessage(Subscriber<List<DiscussPageEntity>> subscriber, int page, int size) {
        Observable observable = service.getNewMessage(page, size)
                .map(new HttpResultFunc<List<DiscussPageEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---访谈消息
     *
     * @param subscriber
     */
    public void getInterviewMessage(Subscriber<List<DiscussPageEntity>> subscriber, int page, int size) {
        Observable observable = service.getInterviewMessage(page, size)
                .map(new HttpResultFunc<List<DiscussPageEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---问答消息
     *
     * @param subscriber
     */
    public void getAskAnswerMessage(Subscriber<List<DiscussPageEntity>> subscriber, int page, int size) {
        Observable observable = service.getAskAnswerMessage(page, size)
                .map(new HttpResultFunc<List<DiscussPageEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---评论或赞消息
     *
     * @param subscriber
     */
    public void getCommentAgreeMessage(Subscriber<List<DiscussPageEntity>> subscriber, int page, int size) {
        Observable observable = service.getCommentAgreeMessage(page, size)
                .map(new HttpResultFunc<List<DiscussPageEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息---系统消息
     *
     * @param subscriber
     */
    public void getSystemMessage(Subscriber<List<DiscussPageEntity>> subscriber, int page, int size) {
        Observable observable = service.getSystemMessage(page, size)
                .map(new HttpResultFunc<List<DiscussPageEntity>>());
        toSubscribe(observable, subscriber);
    }
}
