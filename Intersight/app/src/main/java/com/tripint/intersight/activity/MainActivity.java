package com.tripint.intersight.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.base.BaseActivity;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.fragmentation.anim.DefaultHorizontalAnimator;
import com.tripint.intersight.common.fragmentation.anim.FragmentAnimator;
import com.tripint.intersight.fragment.MainContentFragment;
import com.umeng.common.UmengMessageDeviceConfig;
import com.umeng.message.MsgConstant;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_content);
        if(savedInstanceState == null){
            loadRootFragment(R.id.main_container, MainContentFragment.newInstance());
        }
        if (InterSightApp.getApp().getPermissionsChecker().lacksPermissions(InterSightApp.getApp().PHONE)) {
            PermissionsActivity.startActivityForResult(MainActivity.this, InterSightApp.getApp().REQUEST_CODE, InterSightApp.getApp().PHONE);
        } else {
            mPushAgent.onAppStart();
            String info = String.format("DeviceToken:%s\n" +
                            "SdkVersion:%s\nAppVersionCode:%s\nAppVersionName:%s",
                    mPushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
                    UmengMessageDeviceConfig.getAppVersionCode(this), UmengMessageDeviceConfig.getAppVersionName(this));
            Log.d(TAG, info);
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }


}
