package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageAdapter;
import com.tripint.intersight.adapter.MyTabPagerAdapter;
import com.tripint.intersight.model.QAModel;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemChildClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskAnswerFragment extends Fragment {

    @Bind(R.id.ask_tabLayout)
    TabLayout mTabLayout;

    @Bind(R.id.recycler_view_ask_answer)
    RecyclerView mRecyclerView;

    private List<Fragment> fragmentList;
    private TabLayout.Tab tabOne,tabTwo,tabThree;//标签栏目

    private List<String> stringList;
    private MyTabPagerAdapter myTabPagerAdapter;

    private AskAnswerPageAdapter mAdapter;

    public AskAnswerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_answer, container, false);
        ButterKnife.bind(this, view);

        initView(view);
        initAdapter();
        return view;
    }

    private void initView(View view) {
//        viewPager = ((ViewPager) view.findViewById(R.id.ask_viewPager));

        stringList = new ArrayList<String>();
        stringList.add("行业领域");
        stringList.add("我的收藏");
        stringList.add("精选推荐");

        tabOne = mTabLayout.newTab();
        tabTwo = mTabLayout.newTab();
        tabThree = mTabLayout.newTab();

        tabOne.setText(stringList.get(0));
        tabOne.setText(stringList.get(1));
        tabOne.setText(stringList.get(2));

        mTabLayout.addTab(tabOne);
        mTabLayout.addTab(tabTwo);
        mTabLayout.addTab(tabThree);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        viewPager.setAdapter(myTabPagerAdapter);

//        tabLayout.setupWithViewPager(viewPager);


    }

    private void initAdapter() {
        mAdapter = new AskAnswerPageAdapter();
        mAdapter.openLoadAnimation();
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                QAModel status = (QAModel) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.image_ask_profile:
                        content = "img:" + status.getProfileImage();
                        break;
                    case R.id.textView_item_ask_title:
                        content = "name:" + status.getTitle();
                        break;
                }
                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

}
