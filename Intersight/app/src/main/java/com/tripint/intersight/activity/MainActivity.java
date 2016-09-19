package com.tripint.intersight.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.base.BaseActivity;
import com.tripint.intersight.common.fragmentation.SupportFragment;
import com.tripint.intersight.event.TabSelectedEvent;
import com.tripint.intersight.fragment.AskFragment;
import com.tripint.intersight.fragment.IntersightPlusFragment;
import com.tripint.intersight.fragment.MainContentFragment;
import com.tripint.intersight.fragment.MineFragment;
import com.tripint.intersight.widget.tabbar.BottomTabBar;
import com.tripint.intersight.widget.tabbar.BottomTabBarItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private Fragment fragment;//fragment控件
    private Toast toast;
    private TextView main_tvTitle;
    private boolean currentBackState;//返回键退出程序

    private SupportFragment[] mFragments = new SupportFragment[4];

    @Bind(R.id.bottomBar)
    BottomTabBar mBottomTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            mFragments[0] = MainContentFragment.newInstance();
            mFragments[1] = AskFragment.newInstance();
            mFragments[2] = MineFragment.newInstance();

            loadMultipleRootFragment(R.id.main_container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = findFragment(MainContentFragment.class);
            mFragments[1] = findFragment(AskFragment.class);
            mFragments[2] = findFragment(MineFragment.class);
        }

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        EventBus.getDefault().register(this);

        toast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);
        //盛放Fragment的容器
        FrameLayout main_container = ((FrameLayout) findViewById(R.id.main_container));

        mBottomTabBar
                .addItem(new BottomTabBarItem(this, R.mipmap.ic_launcher, "消息"))
                .addItem(new BottomTabBarItem(this, R.drawable.ic_expandable, "联系人"))
                .addItem(new BottomTabBarItem(this, R.drawable.ic_expandable, "发现"));

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
                SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof MainContentFragment) {
                        currentFragment.popToChild(IntersightPlusFragment.class, false);
                    } else if (currentFragment instanceof AskFragment) {
                        currentFragment.popToChild(AskFragment.class, false);
                    } else if (currentFragment instanceof MineFragment) {
                        currentFragment.popToChild(MineFragment.class, false);
                    }
                    return;
                }


                // 这里推荐使用EventBus来实现 -> 解耦
                if (count == 1) {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBus.getDefault().post(new TabSelectedEvent(position));
                }
            }
        });
    }

    @Subscribe
    public void onEvent(TabSelectedEvent event) {/* Do something */}

    ;


    /**
     * 提示按回退键退出程序
     */
    @Override
    public void onBackPressed() {
        if (currentBackState) {
            super.onBackPressed();
        }
        currentBackState = true;
        if (!isFinishing()) {
            toast.show();
        } else {
            toast.cancel();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    currentBackState = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
