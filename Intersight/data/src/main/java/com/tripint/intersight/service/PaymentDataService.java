package com.tripint.intersight.service;


import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lirichen on 16/9/12.
 */
public interface PaymentDataService {

    //调起微信支付
    @POST("wxpay")
    Observable<BaseResponse<WXPayResponseEntity>> postWxPay();

}
