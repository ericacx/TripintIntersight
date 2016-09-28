package com.tripint.intersight.helper;

import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.Constants;
import com.tripint.intersight.model.PayRequestModel;

/**
 * Created by lirichen on 2016/9/28.
 */

public class PayUtils {

    private IWXAPI api;

    static PayUtils instant;

    private PayUtils() {
        initIWXAPI();
    }

    public static PayUtils getInstant() {

        if (instant == null) {
            instant = new PayUtils();
        }
        return instant;

    }

    public void initIWXAPI() {
        api = WXAPIFactory.createWXAPI(InterSightApp.getApp(), Constants.WX_PAY_ID, false);
        api.registerApp(Constants.WX_PAY_ID);
    }


    public void sendWXpay(PayRequestModel requestModel) {
        try {
            PayReq req = new PayReq();
            req.appId = Constants.WX_PAY_ID;
            req.partnerId = Constants.WX_PARTNER_ID;
            req.prepayId = requestModel.getPrepayId();
            req.nonceStr = requestModel.getNoncestr();
            req.timeStamp = requestModel.getTimeStamp();
            req.packageValue = requestModel.getPackageValue();
            req.sign = requestModel.getSign();
            ProgressDialogUtils.getInstants(InterSightApp.getApp()).show();
            api.sendReq(req);
        } catch (Exception e) {
            Log.e("INTERSIGHT", "Exception :" + e.getMessage());

        }
    }

}
