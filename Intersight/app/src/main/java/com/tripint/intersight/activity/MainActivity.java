package com.tripint.intersight.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.base.BaseActivity;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.fragmentation.anim.DefaultHorizontalAnimator;
import com.tripint.intersight.common.fragmentation.anim.FragmentAnimator;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.fragment.MainContentFragment;
import com.tripint.intersight.fragment.home.AskAnswerDetailFragment;
import com.umeng.common.UmengMessageDeviceConfig;
import com.umeng.message.MsgConstant;

public class MainActivity extends BaseActivity {

    public final static String CONTENT_FRAGMENT_NAME = "content_fragment_name";

    public final static String CONTENT_FRAGMENT_NAME_DISCUSS = "discuss";

    public final static String CONTENT_FRAGMENT_PARAM_ID = "content_fragment_param_id";

    public final static String CONTENT_FRAGMENT_PARAM_EXTRA_ID = "content_fragment_param_extra_id";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_content);

        //处理推送消息跳转画面
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fragmentName = extras.getString(CONTENT_FRAGMENT_NAME);
            String paramId = extras.getString(CONTENT_FRAGMENT_PARAM_ID);
            if (StringUtils.equals(fragmentName, CONTENT_FRAGMENT_NAME_DISCUSS) && !StringUtils.isEmpty(paramId)) {
                int paramIdInt = Integer.parseInt(paramId);
                replaceLoadRootFragment(R.id.main_container, AskAnswerDetailFragment.newInstance(new DiscussEntiry(paramIdInt)), true);
            }

        } else {
            if (savedInstanceState == null) {
                loadRootFragment(R.id.main_container, MainContentFragment.newInstance());
            }
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
