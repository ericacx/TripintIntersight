package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MineCommonMultipleAdapter;
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.mine.FocusEntity;
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
 * 我的关注
 * A simple {@link Fragment} subclass.
 */
public class MyFocusedFragment extends BaseBackFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_my_common_header_left)
    TextView btnMyCommonHeaderLeft;
    @Bind(R.id.btn_my_common_header_right)
    TextView btnMyCommonHeaderRight;
    @Bind(R.id.recycler_view_main)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private final int PAGE_SIZE = 10;

    private int TOTAL_COUNTER = 0;

    private int mCurrentCounter = 0;

    List<MineMultipleItemModel> models = new ArrayList<>();

    private MineCommonMultipleAdapter mAdapter;
    private PageDataSubscriberOnNext<BasePageableResponse<FocusEntity>> subscriber;
    private BasePageableResponse<FocusEntity> data = new BasePageableResponse<FocusEntity>();

    private int tab;

    public static MyFocusedFragment newInstance() {

        Bundle args = new Bundle();

        MyFocusedFragment fragment = new MyFocusedFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_focused, container, false);
        ButterKnife.bind(this, view);

        initToolbarNav(toolbar);
        toolbar.setTitle("我的关注");
        setTab(0);
        return view;
    }

    private void httpRequestData(int type) {
        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<FocusEntity>>() {
            @Override
            public void onNext(BasePageableResponse<FocusEntity> entity) {
                //接口请求成功后处理
                data = entity;
                Log.e("myfocus", String.valueOf(entity.getTotal()));
                initView(null);
                initAdapter(tab);
            }
        };
        MineDataHttpRequest.getInstance(mActivity).getMyFocus(new ProgressSubscriber(subscriber, mActivity), type, 1);
    }


    @OnClick({R.id.btn_my_common_header_left, R.id.btn_my_common_header_right})
    public void onTabBarClick(View view) {
        switch (view.getId()) {

            case R.id.btn_my_common_header_left: //关注
                if (!btnMyCommonHeaderLeft.isSelected()) {
                    setTab(0);
                }
                break;
            case R.id.btn_my_common_header_right: //被关注
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


    private void initAdapter(int tab) {

        initData();

        mAdapter = new MineCommonMultipleAdapter(models);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
//                DiscussEntiry entity = (DiscussEntiry) adapter.getItem(position);
//                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(entity)));
            }
        });
        mAdapter.setLoadingView(getLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        int type = tab == 0 ? MineMultipleItemModel.MY_FOCUSE : MineMultipleItemModel.MY_FOCUSE_FOLLOW;
        for (FocusEntity entity : data.getLists()) {
            models.add(new MineMultipleItemModel(type, entity));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected void initView(View view) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onRefresh() {
        initData();
        mAdapter.setNewData(models);
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.removeAllFooterView();
        mCurrentCounter = PAGE_SIZE;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    mAdapter.loadComplete();

                } else {
                    initData();
                    mAdapter.addData(models);
                    mCurrentCounter = mAdapter.getData().size();
                }
            }
        }, 200);
    }

    private View getLoadMoreView() {
        final View customLoading = LayoutInflater.from(mActivity).inflate(R.layout.common_loading, (ViewGroup) mRecyclerView.getParent(), false);
        return customLoading;
    }
}
