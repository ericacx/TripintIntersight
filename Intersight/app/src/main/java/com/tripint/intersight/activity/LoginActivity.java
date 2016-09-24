package com.tripint.intersight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.base.BaseActivity;
import com.tripint.intersight.common.fragmentation.anim.DefaultVerticalAnimator;
import com.tripint.intersight.common.fragmentation.anim.FragmentAnimator;
import com.tripint.intersight.fragment.LoginFragment;
import com.umeng.socialize.UMShareAPI;

public class LoginActivity extends BaseActivity {

    public UMShareAPI mShareAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_content);
        mShareAPI = UMShareAPI.get(this);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.main_container, LoginFragment.newInstance());
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

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
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
