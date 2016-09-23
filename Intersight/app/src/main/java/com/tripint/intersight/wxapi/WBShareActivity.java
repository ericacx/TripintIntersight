package com.tripint.intersight.wxapi;

import android.os.Bundle;

import com.tencent.bugly.crashreport.BuglyLog;
import com.umeng.socialize.media.WBShareCallBackActivity;

/**
 * 作者：wangdakuan
 * 主要功能:新浪微博分享
 * 创建时间：2016/2/18 17:36
 */
public class WBShareActivity extends WBShareCallBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
        } catch (Exception e) {
            finish();
            BuglyLog.e("WXEntryActivity", e.getMessage());
        }
    }
}

