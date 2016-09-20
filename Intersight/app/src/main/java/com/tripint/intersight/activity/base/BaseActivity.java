package com.tripint.intersight.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tripint.intersight.common.fragmentation.SupportActivity;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;

/**
 * 作者：lirichen
 * 主要功能:页面公用管理
 * 创建时间：2016/09/11 12:46
 */
public class BaseActivity extends SupportActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
