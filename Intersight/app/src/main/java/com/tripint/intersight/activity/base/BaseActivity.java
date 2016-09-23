package com.tripint.intersight.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tripint.intersight.common.fragmentation.SupportActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

/**
 * 作者：lirichen
 * 主要功能:页面公用管理
 * 创建时间：2016/09/11 12:46
 */
public class BaseActivity extends SupportActivity {

    public UMShareAPI mShareAPI;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareAPI = UMShareAPI.get(this);
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
