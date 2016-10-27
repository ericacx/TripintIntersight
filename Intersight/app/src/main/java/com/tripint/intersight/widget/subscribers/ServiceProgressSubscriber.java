package com.tripint.intersight.widget.subscribers;

import android.content.Context;
import android.util.Log;

import com.tripint.intersight.common.ApiException;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.widget.progress.ProgressCancelListener;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 在Service里用的Subscriber 不显示Dialog
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by liukun on 16/3/10.
 */
public class ServiceProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private final String TAG = "Intersight";
    private PageDataSubscriberOnNext mPageDataSubscriberOnNext;


    private Context context;

    public ServiceProgressSubscriber(PageDataSubscriberOnNext mPageDataSubscriberOnNext, Context context) {
        this.mPageDataSubscriberOnNext = mPageDataSubscriberOnNext;
        this.context = context;
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {

    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {

    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            CommonUtils.showToast("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            CommonUtils.showToast("网络中断，请检查您的网络状态");
        } else if (e instanceof ApiException) {
            CommonUtils.showToast(e.getMessage());
            Log.e(TAG, e.getMessage());
        } else {
            CommonUtils.showToast("未知错误,请查看日志异常");
            Log.e(TAG, e.getMessage());
        }


    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mPageDataSubscriberOnNext != null) {
            mPageDataSubscriberOnNext.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

}