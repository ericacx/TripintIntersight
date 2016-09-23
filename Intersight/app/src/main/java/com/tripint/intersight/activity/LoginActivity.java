package com.tripint.intersight.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.base.BaseActivity;
import com.tripint.intersight.common.fragmentation.anim.DefaultVerticalAnimator;
import com.tripint.intersight.common.fragmentation.anim.FragmentAnimator;
import com.tripint.intersight.event.ShareLoginEvent;
import com.tripint.intersight.fragment.LoginFragment;
import com.tripint.intersight.helper.CommonUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_content);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.main_container, LoginFragment.newInstance());
        }
        EventBus.getDefault().register(this);
//        mShareAPI.deleteOauth(this, SHARE_MEDIA.WEIXIN , new UMAuthListener() {
//            @Override
//            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//                CommonUtils.showToast("Authorize succeed");
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//                CommonUtils.showToast("Authorize failed");
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA platform, int action) {
//                CommonUtils.showToast("Authorize cancel");
//            }
//
//        });

    }


    @Subscribe
    public void sharedLogin(ShareLoginEvent event) {

        sharedLogin(event.platform);
    }


    protected void sharedLogin(SHARE_MEDIA platform) {

        mShareAPI.doOauthVerify(this, platform, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                CommonUtils.showToast("Authorize succeed");
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                CommonUtils.showToast("Authorize failed");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                CommonUtils.showToast("Authorize cancel");
            }

        });

    }

//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mShareAPI.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
