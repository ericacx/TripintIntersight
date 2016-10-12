package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MineCommonMultipleAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的观点页面
 * A simple {@link Fragment} subclass.
 */
public class MyOpinionFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_my_common_header_left)
    TextView btnMyCommonHeaderLeft;
    @Bind(R.id.btn_my_common_header_right)
    TextView btnMyCommonHeaderRight;
    @Bind(R.id.recycler_view_main)
    RecyclerView mRecyclerView;


    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<List<MineFollowPointEntity>> subscriber;

    private List<MineFollowPointEntity> data;

    private int tab;

    public static MyOpinionFragment newInstance() {

        Bundle args = new Bundle();

        MyOpinionFragment fragment = new MyOpinionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_opinion, container, false);
        ButterKnife.bind(this, view);
        setTab(0);
        return view;
    }


    private void httpRequestData(int type) {
        subscriber = new PageDataSubscriberOnNext<List<MineFollowPointEntity>>() {
            @Override
            public void onNext(List<MineFollowPointEntity> entity) {
                //接口请求成功后处理
                data = entity;
                initView(null);
                initAdapter(tab);
            }
        };

        MineDataHttpRequest.getInstance(mActivity).getMyFollowPoint(new ProgressSubscriber(subscriber, mActivity), type, 1, 10);
    }


    @OnClick({R.id.btn_my_common_header_left, R.id.btn_my_common_header_right})
    public void onTabBarClick(View view) {
        switch (view.getId()) {

            case R.id.btn_my_common_header_left: //我的观点
                if (!btnMyCommonHeaderLeft.isSelected()) {
                    setTab(0);
                }
                break;
            case R.id.btn_my_common_header_right: //我关注的观点
                if (!btnMyCommonHeaderRight.isSelected()) {
                    setTab(1);
                }
                break;
        }
    }

    /**
     * 请求不同的关键字 精选自由行、省心国内游、品质出境游
     *
     * @param tab
     */
    private void setTab(int tab) {
        this.tab = tab;
        btnMyCommonHeaderLeft.setSelected(tab == 0);
        btnMyCommonHeaderRight.setSelected(tab == 1);
        httpRequestData(tab);
    }


    protected void initView(View view) {
        initToolbarNav(toolbar);
        toolbar.setTitle("我的观点");
        mRecyclerView.setHasFixedSize(true);
    }

    private void initAdapter(int tab) {

        List<MineMultipleItemModel> models = new ArrayList<>();

        int type = tab == 0 ? MineMultipleItemModel.MY_OPTION : MineMultipleItemModel.MY_OPTION_FOLLOW;
        for (MineFollowPointEntity entiry : data) {

            models.add(new MineMultipleItemModel(type, entiry));
        }

        final GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 1);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MineCommonMultipleAdapter(models);
        mAdapter.openLoadAnimation();

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
//                DiscussEntiry entity = (DiscussEntiry) adapter.getItem(position);
//                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(entity)));
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
