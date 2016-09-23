package com.tripint.intersight.service;

import com.tripint.intersight.entity.discuss.DiscussPageEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lirichen on 2016/9/21.
 */

public class DiscussDataHttpRequest extends HttpRequest {

    private final String FLITER_TYPE_INTERVIEWER = "interviewer";
    private final String FLITER_TYPE_ARTICLES = "articles";

    private DiscussDataService service;

    private DiscussDataHttpRequest() {
        super();
        service = retrofit.create(DiscussDataService.class);

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final DiscussDataHttpRequest INSTANCE = new DiscussDataHttpRequest();
    }

    public static DiscussDataHttpRequest getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 用于取得行业数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param tab        0 问答首页接口  1,我关注的问答  2,精选
     * @param start      起始位置
     * @param count      获取长度
     */
    public void getDiscusses(Subscriber<DiscussPageEntity> subscriber, int tab, int start, int count) {

        Observable observable = null;
        if (tab == 0) {
            observable = service.getDiscuss(start, count)
                    .map(new HttpResultFunc<DiscussPageEntity>());
        } else if (tab == 1) {
            observable = service.getDiscuss(start, count)
                    .map(new HttpResultFunc<DiscussPageEntity>());
        } else if (tab == 2) {
            observable = service.getDiscuss(start, count)
                    .map(new HttpResultFunc<DiscussPageEntity>());
        }

        toSubscribe(observable, subscriber);
    }


}
