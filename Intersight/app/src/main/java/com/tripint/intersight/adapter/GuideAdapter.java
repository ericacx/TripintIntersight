package com.tripint.intersight.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Eric on 16/8/23.
 */
public class GuideAdapter extends FragmentPagerAdapter {
    private List<Fragment> guideFragment;
    public GuideAdapter(FragmentManager fm, List<Fragment> guideFragment) {
        super(fm);
        this.guideFragment = guideFragment;
    }

    @Override
    //根据参数指定的索引值，返回数据源中的数据
    public Fragment getItem(int i) {
        return guideFragment.get(i);
    }

    @Override
    //返回数据源中的数据的个数
    public int getCount() {
        int ret = 0;
        if (guideFragment != null) {
            ret = guideFragment.size();
        }

        return ret;
    }
}
