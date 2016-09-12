package com.tripint.intersight.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Eric on 16/5/3.
 */
public class MyTabPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> stringLists;
    private List<Fragment> fragmentLists;
    public MyTabPagerAdapter(FragmentManager fragmentManager,
                             List<String> stringList, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.stringLists = stringList;
        this.fragmentLists = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return fragmentLists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringLists.get(position);
    }
}
