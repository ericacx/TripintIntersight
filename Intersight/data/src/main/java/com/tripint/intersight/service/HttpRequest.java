package com.tripint.intersight.service;


import android.content.Context;

import com.tripint.intersight.common.ApiException;
import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.executor.JobExecutor;
import com.tripint.intersight.executor.PostExecutionThread;
import com.tripint.intersight.executor.ThreadExecutor;
import com.tripint.intersight.executor.UIThread;
import com.tripint.intersight.interceptor.HeaderInterceptor;
import com.tripint.intersight.interceptor.LoggingInterceptor;

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

    //    public static final String BASE_URL = "http://test.www.dongchajia.com/";
    public static final String BASE_URL = "http://m.dongchajia.com/";
    public static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_TIMEOUT = 300;
    protected Retrofit retrofit;
    protected Context mContext;
    ThreadExecutor threadExecutor = new JobExecutor(); //子线程
    PostExecutionThread postExecutionThread = new UIThread(); //主线程（UI线程）

    //构造方法私有
    protected HttpRequest(Context context) {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new LoggingInterceptor());
//        builder.addInterceptor(new ParameterInterceptor());
        builder.addInterceptor(new HeaderInterceptor(context));
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mContext = context;

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }


    protected <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
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
    protected class HttpResultFunc<T> implements Func1<BaseResponse<T>, T> {

        @Override
        public T call(BaseResponse<T> response) {

            if (response.getCode() == 200) {
                return response.getData();
            } else if (response.getCode() == 100){
                return response.getData();
            } else if (response.getCode() == 101){
                return response.getData();
            } else if (response.getCode() == 102){
                return response.getData();
            }
            throw new ApiException(response.getCode());
        }
    }

}
