package com.tripint.intersight.service;

import android.content.Context;

import com.tripint.intersight.entity.payment.WXPayResponseEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lirichen on 2016/9/21.
 */

public class PaymentDataHttpRequest extends HttpRequest {


    public static final String TYPE_WXPAY = "WXPAY";
    public static final String TYPE_ALIPAY = "ALIPAY";

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
    public void requestWxPay(Subscriber<WXPayResponseEntity> subscriber) {

        Observable observable = service.postWxPay()
                .map(new HttpResultFunc<WXPayResponseEntity>());

        toSubscribe(observable, subscriber);
    }

}
