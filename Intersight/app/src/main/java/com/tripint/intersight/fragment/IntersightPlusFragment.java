package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.InterSightPagerFragmentAdapter;
import com.tripint.intersight.adapter.MyTabPagerAdapter;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;
import com.tripint.intersight.fragment.discuss.AskAnswerFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntersightPlusFragment extends BaseLazyMainFragment {

    private TabLayout mTab;
    private ViewPager mViewPager;

    private List<Fragment> fragmentList;
    private TabLayout.Tab tabOne, tabTwo, tabThree;//标签栏目
    private AskAnswerFragment askAnswerFragment;
    private OpinionFragment opinionFragment;
    private NewsFragment newsFragment;

    private List<String> stringList;
    private MyTabPagerAdapter myTabPagerAdapter;

    public static IntersightPlusFragment newInstance() {

        Bundle args = new Bundle();

        IntersightPlusFragment fragment = new IntersightPlusFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intersight_plus, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTab = ((TabLayout) view.findViewById(R.id.tab));
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mViewPager = ((ViewPager) view.findViewById(R.id.viewPager));

        mTab = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());


    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        mViewPager.setAdapter(new InterSightPagerFragmentAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }
}
