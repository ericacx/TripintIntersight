package com.tripint.intersight.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tripint.intersight.fragment.home.NewsFragment;
import com.tripint.intersight.fragment.home.OpinionFragment;
import com.tripint.intersight.fragment.home.AskAnswerFragment;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class InterSightPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private String[] mTab = new String[]{"问答", "观点", "资讯"};

    public InterSightPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return AskAnswerFragment.newInstance();
        } else if (position == 1) {
            return OpinionFragment.newInstance();
        } else {
            return NewsFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTab[position];
    }
}
