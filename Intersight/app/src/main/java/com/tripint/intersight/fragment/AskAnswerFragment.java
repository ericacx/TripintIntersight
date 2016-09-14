package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MyTabPagerAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskAnswerFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> fragmentList;
    private TabLayout.Tab tabOne,tabTwo,tabThree;//标签栏目
    private TradeFragment tradeFragment;//行业洞察
    private MyIntersightFragment myIntersightFragment;//我的洞察
    private FeaturedFragment featuredFragment;//精选洞察

    private List<String> stringList;
    private MyTabPagerAdapter myTabPagerAdapter;

    public AskAnswerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_answer, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tabLayout = ((TabLayout) view.findViewById(R.id.ask_tabLayout));
        viewPager = ((ViewPager) view.findViewById(R.id.ask_viewPager));

        stringList = new ArrayList<String>();
        stringList.add("行业领域");
        stringList.add("我的收藏");
        stringList.add("精选推荐");

        fragmentList = new LinkedList<Fragment>();
        tradeFragment = new TradeFragment();
        myIntersightFragment = new MyIntersightFragment();
        featuredFragment = new FeaturedFragment();

        fragmentList.add(tradeFragment);
        fragmentList.add(myIntersightFragment);
        fragmentList.add(featuredFragment);

        tabOne = tabLayout.newTab();
        tabTwo = tabLayout.newTab();
        tabThree = tabLayout.newTab();

        tabOne.setText(stringList.get(0));
        tabOne.setText(stringList.get(1));
        tabOne.setText(stringList.get(2));

        tabLayout.addTab(tabOne);
        tabLayout.addTab(tabTwo);
        tabLayout.addTab(tabThree);

        myTabPagerAdapter = new MyTabPagerAdapter(getChildFragmentManager(),stringList,fragmentList);
        viewPager.setAdapter(myTabPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(myTabPagerAdapter);
    }
}
