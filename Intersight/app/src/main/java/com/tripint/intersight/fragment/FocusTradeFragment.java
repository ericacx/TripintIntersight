package com.tripint.intersight.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.tripint.intersight.R;
import com.tripint.intersight.activity.MainActivity;
import com.tripint.intersight.adapter.FocusTradePageAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.IndustryListEntity;
import com.tripint.intersight.entity.user.ChooseEntity;
import com.tripint.intersight.fragment.base.BaseFragment;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.view.grid.GridSpacingItemDecoration;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订阅---关注的行业
 */
public class FocusTradeFragment extends BaseFragment {

    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.interestedNext)
    Button interestedNext;
    @Bind(R.id.industry_recycler_view)
    RecyclerView mRecyclerView;
    List<Industry> data = new ArrayList<>();
    Set<Integer> list = new LinkedHashSet<>();
    private ChooseEntity chooseEntity;
    private PageDataSubscriberOnNext<ChooseEntity> subscriber;
    private PageDataSubscriberOnNext<IndustryListEntity> industrySubscriber;
    private FocusTradePageAdapter mAdapter;

    public static FocusTradeFragment newInstance() {

        Bundle args = new Bundle();

        FocusTradeFragment fragment = new FocusTradeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_focus_trade, container, false);
        ButterKnife.bind(this, view);

        initView();


        return view;
    }

    private void initView() {
        subscriber = new PageDataSubscriberOnNext<ChooseEntity>() {
            @Override
            public void onNext(ChooseEntity entity) {
                chooseEntity = entity;
                Log.e("focus", String.valueOf(entity.getStatus()));
                int status = entity.getStatus();
                if (status == 102) {
                    goMainActivity();
                }
            }
        };

        industrySubscriber = new PageDataSubscriberOnNext<IndustryListEntity>() {
            @Override
            public void onNext(IndustryListEntity entity) {
                data = entity.getIndustry();
//                Log.e("focus", String.valueOf(entity.getStatus()));
                initAdapter();
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        int spanCount = 1; // 3 columns
        int spacing = 0; // 50px
        boolean includeEdge = true;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        BaseDataHttpRequest.getInstance(mActivity)
                .getIndustry(
                        new ProgressSubscriber<IndustryListEntity>(industrySubscriber, mActivity)
                );

    }

    private void goMainActivity() {
        Intent intent = new Intent();
        intent.setClass(mActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initAdapter() {
        mAdapter = new FocusTradePageAdapter(data);
        mAdapter.openLoadAnimation();
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                Industry entity = (Industry) adapter.getItem(position);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.toggle_select_industry);
                if (list.contains(entity.getId())) {
                    list.remove(entity.getId());
                    checkBox.setChecked(false);
                } else {
                    list.add(entity.getId());
                    checkBox.setChecked(true);
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.interestedNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.interestedNext:
                if (list != null && list.size() > 0) {
                    String s = new Gson().toJson(list);
                    Log.e("s", s);
                    BaseDataHttpRequest.getInstance(mActivity)
                            .postInsterestIndustry(
                                    new ProgressSubscriber<ChooseEntity>(subscriber, mActivity)
                                    , s);

                    goMainActivity();
                } else {
                    CommonUtils.showToast("请选择感兴趣的行业");
                }
                break;
        }
    }
}
