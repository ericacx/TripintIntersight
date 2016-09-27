package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.entity.discuss.DiscussDetailEntiry;
import com.tripint.intersight.entity.discuss.DiscussPageEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * 个人中心接口
 * Created by lirichen on 2016/9/21.
 */

public class MineDataHttpRequest extends HttpRequest {

    private final String FLITER_TYPE_INTERVIEWER = "interviewer";
    private final String FLITER_TYPE_ARTICLES = "articles";

    private MineDataService service;

    private MineDataHttpRequest(Context context) {
        super(context);
        service = retrofit.create(MineDataService.class);

    }

    private static MineDataHttpRequest instants;


    public static MineDataHttpRequest getInstance(Context context) {
        if (instants == null) {
            instants = new MineDataHttpRequest(context);
        }
        return instants;
    }


    /**
     * 提交用户反馈信息
     */
    public void postFeedback(Subscriber<String> subscriber, String content) {

        Observable observable = service.postFeedback(content)
                .map(new HttpResultFunc<String>());


        toSubscribe(observable, subscriber);
    }


}
