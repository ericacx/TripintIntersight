
package com.tripint.intersight.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tripint.intersight.R;
import com.tripint.intersight.common.Constants;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.event.PayEvent;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    public static final String WX_MESSAGE = "weixin_message";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.WX_PAY_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == 0) { //支付成功
            EventBus.getDefault().post(new PayEvent(true));
//            Intent intent = AppBroadCast.getAppBroadCastIntent(WX_MESSAGE);
//            sendOrderedBroadcast(intent, null);
        } else {
            EventBus.getDefault().post(new PayEvent(false));
            ToastUtil.showToast(this, "支付失败");
        }
        finish();
    }
}
