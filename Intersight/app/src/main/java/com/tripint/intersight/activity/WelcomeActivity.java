package com.tripint.intersight.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tripint.intersight.R;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;
import com.tripint.intersight.common.utils.PackageUtils;
import com.umeng.analytics.MobclickAgent;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences sp = getSharedPreferences("version",MODE_PRIVATE);

        //相当于旧版本
        final String version = ACache.get(this).getAsString(EnumKey.ACacheKey.APP_VERSION_CODE);

        //相当于新版本
        final String newVersion = PackageUtils.getPackageVersion(this);
        final Object isLaunched = ACache.get(this).getAsString(EnumKey.ACacheKey.KEYWORD_APP_LAUNCHED);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //说明登录过，不需要进入导航页，直接进入主界面
                //当新版本与旧版本一致时直接跳转进入主界面
                if (newVersion.equals(version) && isLaunched != null) {

                    if (InterSightApp.getApp().isUserLogin()) {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);

                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);

                        startActivity(intent);
                    }

                    finish();

                }else {//需要进入导航页
                    Intent intent = new Intent(WelcomeActivity.this,GuideActivity.class);

                    startActivity(intent);

                    finish();
                }
            }
        });
        thread.start();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
