package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.entity.stream.StreamResponseEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lirichen on 2016/9/21.
 */

public class PiliStreamDataHttpRequest extends HttpRequest {

    private final String FLITER_TYPE_INTERVIEWER = "interviewer";
    private final String FLITER_TYPE_ARTICLES = "articles";

    private PiliStreamDataService service;

    public final static int RESPONSE_STATUS_USER_EXIST = 1001;  //表示用户存在
    public final static int RESPONSE_STATUS_USER_NOT_EXIST = 1000;  //表示用户不存在
    public final static int RESPONSE_STATUS_SHARE_LOGIN_ERROR = 1003;  //用户绑定验证码不正确

    private PiliStreamDataHttpRequest(Context context) {
        super(context);
        service = retrofit.create(PiliStreamDataService.class);

    }

    private static PiliStreamDataHttpRequest instants;


    public static PiliStreamDataHttpRequest getInstance(Context context) {
        if (instants == null) {
            instants = new PiliStreamDataHttpRequest(context);
        }
        return instants;
    }


    /**
     * 举报用户 或 文章
     *
     * @param subscriber
     * @param
     */
    public void postPublishStreamUrl(Subscriber<StreamResponseEntity> subscriber)  //问答)
    {
        Observable observable = service.postPublishStreamUrl(0)
                .map(new HttpResultFunc<StreamResponseEntity>());
        toSubscribe(observable, subscriber);
    }
}
