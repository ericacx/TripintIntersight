package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.entity.payment.AliPayResponseEntity;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lirichen on 2016/9/21.
 */

public class PaymentDataHttpRequest extends HttpRequest {


    public static final String TYPE_WXPAY = "WXPAY";
    public static final String TYPE_ALIPAY = "ALIPAY";

    public static final String PAY_CONTENT_TYPE_DISCUSS = "discuss";
    public static final String PAY_CONTENT_TYPE_INTERVIEW = "interview";

    private PaymentDataService service;

    private PaymentDataHttpRequest(Context context) {
        super(context);
        service = retrofit.create(PaymentDataService.class);

    }

    private static PaymentDataHttpRequest instants;


    public static PaymentDataHttpRequest getInstance(Context context) {
        if (instants == null) {
            instants = new PaymentDataHttpRequest(context);
        }
        return instants;
    }


    /**
     * 用于取得行业数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void requestWxPayForDiscuss(Subscriber<WXPayResponseEntity> subscriber, int discussId, int toUid, String discussContent) {

        Observable observable = service.postWxPay(PAY_CONTENT_TYPE_DISCUSS, discussId, toUid, discussContent)
                .map(new HttpResultFunc<WXPayResponseEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 访谈
     *
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void requestWxPayForInterview(Subscriber<WXPayResponseEntity> subscriber, int interviewId, int toUid, String interviewContent) {

        Observable observable = service.postWxPay(PAY_CONTENT_TYPE_DISCUSS, interviewId, toUid, interviewContent)
                .map(new HttpResultFunc<WXPayResponseEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 用于取得行业数据
     *
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void requestAliPayForDiscuss(Subscriber<AliPayResponseEntity> subscriber, int discussId, int toUid, String discussContent) {

        Observable observable = service.postAliPay(PAY_CONTENT_TYPE_DISCUSS, discussId, toUid, discussContent)
                .map(new HttpResultFunc<AliPayResponseEntity>());

        toSubscribe(observable, subscriber);
    }

}
