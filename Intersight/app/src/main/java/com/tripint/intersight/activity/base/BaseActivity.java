package com.tripint.intersight.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tripint.intersight.common.fragmentation.SupportActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.common.UmengMessageDeviceConfig;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;

/**
 * 作者：lirichen
 * 主要功能:页面公用管理
 * 创建时间：2016/09/11 12:46
 */
public class BaseActivity extends SupportActivity {

    public final String TAG = "INTERSIGHT";

    protected PushAgent mPushAgent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.onAppStart();
        String info = String.format("DeviceToken:%s\n" +
                        "SdkVersion:%s\nAppVersionCode:%s\nAppVersionName:%s",
                mPushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
                UmengMessageDeviceConfig.getAppVersionCode(this), UmengMessageDeviceConfig.getAppVersionName(this));
        Log.d(TAG, info);

    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
