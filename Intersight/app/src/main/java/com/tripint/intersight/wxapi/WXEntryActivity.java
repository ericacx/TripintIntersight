
package com.tripint.intersight.wxapi;

import android.os.Bundle;

import com.tencent.bugly.crashreport.BuglyLog;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {

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
