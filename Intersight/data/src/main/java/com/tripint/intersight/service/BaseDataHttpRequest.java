package com.tripint.intersight.service;

import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.SearchFilterEntity;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lirichen on 2016/9/21.
 */

public class BaseDataHttpRequest extends HttpRequest {

    private final String FLITER_TYPE_INTERVIEWER = "interviewer";
    private final String FLITER_TYPE_ARTICLES = "articles";

    private BaseDataService baseDataService;

    private BaseDataHttpRequest() {
        super();
        baseDataService = retrofit.create(BaseDataService.class);

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final BaseDataHttpRequest INSTANCE = new BaseDataHttpRequest();
    }

    public static BaseDataHttpRequest getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 用于取得行业数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param start      起始位置
     * @param count      获取长度
     */
    public void getIndustry(Subscriber<List<Industry>> subscriber, int start, int count) {

        Observable observable = baseDataService.getIndustry(start, count)
                .map(new HttpResultFunc<List<Industry>>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 用于获取搜索的过滤条件数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getSearchFilterInterviewer(Subscriber<SearchFilterEntity> subscriber) {

        Observable observable = baseDataService.getSearchFilter(FLITER_TYPE_INTERVIEWER)
                .map(new HttpResultFunc<SearchFilterEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 用于获取搜索的过滤条件数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getSearchFilterArticles(Subscriber<SearchFilterEntity> subscriber) {

        Observable observable = baseDataService.getSearchFilter(FLITER_TYPE_ARTICLES)
                .map(new HttpResultFunc<SearchFilterEntity>());

        toSubscribe(observable, subscriber);
    }
}
