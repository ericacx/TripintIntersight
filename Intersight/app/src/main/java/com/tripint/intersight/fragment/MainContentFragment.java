package com.tripint.intersight.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.common.fragmentation.SupportFragment;
import com.tripint.intersight.common.fragmentation.anim.FragmentAnimator;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.event.TabSelectedEvent;
import com.tripint.intersight.fragment.base.BaseFragment;
import com.tripint.intersight.widget.tabbar.BottomTabBar;
import com.tripint.intersight.widget.tabbar.BottomTabBarItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class MainContentFragment extends BaseFragment {


    private SupportFragment[] mFragments = new SupportFragment[3];

    @Bind(R.id.bottomBar)
    BottomTabBar mBottomTabBar;

    public static MainContentFragment newInstance() {

        Bundle args = new Bundle();

        MainContentFragment fragment = new MainContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            mFragments[0] = IntersightPlusFragment.newInstance();
            mFragments[1] = AskFragment.newInstance();
            mFragments[2] = MineFragment.newInstance();

            loadMultipleRootFragment(R.id.main_container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = findFragment(IntersightPlusFragment.class);
            mFragments[1] = findFragment(AskFragment.class);
            mFragments[2] = findFragment(MineFragment.class);
        }

        initView();
        return view;
    }


    /**
     * 初始化控件
     */
    private void initView() {
        EventBus.getDefault().register(this);

        //盛放Fragment的容器

        mBottomTabBar
                .addItem(new BottomTabBarItem(mActivity, R.drawable.selector_shouye, "洞察+"))
                .addItem(new BottomTabBarItem(mActivity, R.drawable.selector_tiwen, "提问"))
                .addItem(new BottomTabBarItem(mActivity, R.drawable.selector_geren, "个人"));
        mBottomTabBar.setBackgroundColor(getResources().getColor(R.color.colorToolbarPrimary));

        mBottomTabBar.setOnTabSelectedListener(new BottomTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

                // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBus.getDefault().post(new TabSelectedEvent(position));

            }
        });
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
    }

    @Subscribe
    public void onEvent(TabSelectedEvent event) {/* Do something */}


    @Subscribe
    public void startFragment(StartFragmentEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
