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
public class IntersightPlusFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> fragmentList;
    private TabLayout.Tab tabOne,tabTwo,tabThree;//标签栏目
    private AskAnswerFragment askAnswerFragment;
    private OpinionFragment opinionFragment;
    private NewsFragment newsFragment;

    private List<String> stringList;
    private MyTabPagerAdapter myTabPagerAdapter;

    public IntersightPlusFragment() {
        // Required empty public constructor
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
        tabLayout = ((TabLayout) view.findViewById(R.id.tabLayout));
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = ((ViewPager) view.findViewById(R.id.viewPager));

        stringList = new ArrayList<String>();
        stringList.add("问答");
        stringList.add("观点");
        stringList.add("资讯");

        fragmentList = new LinkedList<Fragment>();
        askAnswerFragment = new AskAnswerFragment();
        opinionFragment = new OpinionFragment();
        newsFragment = new NewsFragment();

        fragmentList.add(askAnswerFragment);
        fragmentList.add(opinionFragment);
        fragmentList.add(newsFragment);

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
