package com.tripint.intersight.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.base.BaseActivity;
import com.tripint.intersight.common.fragmentation.SupportActivity;
import com.tripint.intersight.common.fragmentation.SupportFragment;
import com.tripint.intersight.common.fragmentation.anim.DefaultHorizontalAnimator;
import com.tripint.intersight.common.fragmentation.anim.DefaultVerticalAnimator;
import com.tripint.intersight.common.fragmentation.anim.FragmentAnimator;
import com.tripint.intersight.event.StartBrotherEvent;
import com.tripint.intersight.event.TabSelectedEvent;
import com.tripint.intersight.fragment.AskFragment;
import com.tripint.intersight.fragment.IntersightPlusFragment;
import com.tripint.intersight.fragment.MainContentFragment;
import com.tripint.intersight.fragment.MineFragment;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;
import com.tripint.intersight.widget.tabbar.BottomTabBar;
import com.tripint.intersight.widget.tabbar.BottomTabBarItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends SupportActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_content);
        if(savedInstanceState == null){
            loadRootFragment(R.id.main_container, MainContentFragment.newInstance());
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
