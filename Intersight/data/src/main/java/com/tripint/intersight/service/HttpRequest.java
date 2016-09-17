package com.tripint.intersight.service;


import com.tripint.intersight.common.ApiException;
import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.executor.JobExecutor;
import com.tripint.intersight.executor.PostExecutionThread;
import com.tripint.intersight.executor.ThreadExecutor;
import com.tripint.intersight.executor.UIThread;
import com.tripint.intersight.interceptor.HeaderInterceptor;
import com.tripint.intersight.interceptor.LoggingInterceptor;
import com.tripint.intersight.interceptor.ParameterInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liukun on 16/3/9.
 */
public class HttpRequest {

    public static final String BASE_URL = "http://test.www.dongchajia.com/";

    private static final int DEFAULT_TIMEOUT = 300;

    ThreadExecutor threadExecutor = new JobExecutor(); //子线程
    PostExecutionThread postExecutionThread = new UIThread(); //主线程（UI线程）

    private Retrofit retrofit;
    private BaseDataService baseDataService;

    //构造方法私有
    private HttpRequest() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new LoggingInterceptor());
        builder.addInterceptor(new ParameterInterceptor());
        builder.addInterceptor(new HeaderInterceptor());
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        baseDataService = retrofit.create(BaseDataService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpRequest INSTANCE = new HttpRequest();
    }

    //获取单例
    public static HttpRequest getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     * @param subscriber  由调用者传过来的观察者对象
     * @param start 起始位置
     * @param count 获取长度
     */
    public void getTopMovie(Subscriber<List<Industry>> subscriber, int start, int count){

        Observable observable = baseDataService.getIndustry(start, count)
                .map(new HttpResultFunc<List<Industry>>());

        toSubscribe(observable, subscriber);
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
         o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<BaseResponse<T>, T> {

        @Override
        public T call(BaseResponse<T> response) {
            if (response.getCode() != 200) {
                throw new ApiException(response.getCode());
            }
            return response.getData();
        }
    }

}
