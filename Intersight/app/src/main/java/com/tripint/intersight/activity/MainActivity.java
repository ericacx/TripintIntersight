package com.tripint.intersight.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.bugly.crashreport.CrashReport;
import com.tripint.intersight.R;
import com.tripint.intersight.common.fragmentation.SupportActivity;
import com.tripint.intersight.common.fragmentation.anim.DefaultHorizontalAnimator;
import com.tripint.intersight.common.fragmentation.anim.FragmentAnimator;
import com.tripint.intersight.fragment.MainContentFragment;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends SupportActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_content);
        if(savedInstanceState == null){
            loadRootFragment(R.id.main_container, MainContentFragment.newInstance());
        }
        CrashReport.testJavaCrash();

    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
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
