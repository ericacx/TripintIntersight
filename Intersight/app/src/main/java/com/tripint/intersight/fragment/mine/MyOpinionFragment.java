package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.home.OpinionDetailFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.service.HttpRequest;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的观点页面
 * A simple {@link Fragment} subclass.
 */
public class MyOpinionFragment extends BaseBackFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


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
    private MineCommonMultipleAdapter mAdapter;
    private PageDataSubscriberOnNext<BasePageableResponse<MineFollowPointEntity>> subscriber;
    private BasePageableResponse<MineFollowPointEntity> data = new BasePageableResponse<MineFollowPointEntity>();
    private int mCurrentTab;

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
        initToolbar();
        initView(null);
        initAdapter();
        setTab(0);
        return view;
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("我的观点");
    }


    private void httpRequestData(int type) {
        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<MineFollowPointEntity>>() {
            @Override
            public void onNext(BasePageableResponse<MineFollowPointEntity> entity) {
                //接口请求成功后处理
                data = entity;
                List<MineMultipleItemModel> models = getMineMultipleItemModel(data.getLists());
                if (mCurrentCounter == 0) {
                    mAdapter.setNewData(models);
                } else {
                    mAdapter.addData(models);
                }
                TOTAL_COUNTER = data.getTotal();
                mCurrentCounter = mAdapter.getData().size();

            }
        };

        MineDataHttpRequest.getInstance(mActivity).getMyFollowPoint(new ProgressSubscriber(subscriber, mActivity), type, 1);
        Log.e("type",String.valueOf(type));
    }


    @NonNull
    private List<MineMultipleItemModel> getMineMultipleItemModel(List<MineFollowPointEntity> entiries) {
        List<MineMultipleItemModel> models = new ArrayList<>();
        int type = mCurrentTab == 0 ? MineMultipleItemModel.MY_OPTION : MineMultipleItemModel.MY_OPTION_FOLLOW;
        Log.e("currenttab",String.valueOf(mCurrentTab));
        if (entiries == null) return models;

        for (MineFollowPointEntity entiry : entiries) {
            models.add(new MineMultipleItemModel(type, entiry));
        }
        return models;
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
        btnMyCommonHeaderLeft.setSelected(tab == 0);
        btnMyCommonHeaderRight.setSelected(tab == 1);
        httpRequestData(tab);
        this.mCurrentTab = tab;
    }

    private void initAdapter() {
        mAdapter = new MineCommonMultipleAdapter(getMineMultipleItemModel(data.getLists()));
        mAdapter.openLoadAnimation();
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                int type = data.getLists().get(position).getType();
                int id = data.getLists().get(position).getId();
                EventBus.getDefault().post(new StartFragmentEvent(OpinionDetailFragment.newInstance(type,id)));
            }
        });

        mAdapter.setLoadingView(getLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);

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
        mCurrentCounter = 0;
        MineDataHttpRequest.getInstance(mActivity).getMyFollowPoint(new ProgressSubscriber(subscriber, mActivity), getCurrentPage(), 1);
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
                    MineDataHttpRequest.getInstance(mActivity).getMyFollowPoint(new ProgressSubscriber(subscriber, mActivity), mCurrentTab, getCurrentPage());
                }
            }
        }, 200);
    }

    public int getCurrentPage() {
        return mCurrentCounter / HttpRequest.DEFAULT_PAGE_SIZE + 1;
    }

    private View getLoadMoreView() {
        final View customLoading = LayoutInflater.from(mActivity).inflate(R.layout.common_loading, (ViewGroup) mRecyclerView.getParent(), false);
        return customLoading;
    }
}
