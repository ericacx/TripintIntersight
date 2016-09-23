package com.tripint.intersight.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.base.BaseActivity;
import com.tripint.intersight.common.fragmentation.anim.DefaultVerticalAnimator;
import com.tripint.intersight.common.fragmentation.anim.FragmentAnimator;
import com.tripint.intersight.fragment.LoginFragment;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_content);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.main_container, LoginFragment.newInstance());
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }
}
