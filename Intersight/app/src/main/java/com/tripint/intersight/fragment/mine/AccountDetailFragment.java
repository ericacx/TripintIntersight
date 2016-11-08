package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MineCommonMultipleAdapter;
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.discuss.InterviewEntity;
import com.tripint.intersight.entity.mine.AccountDetailEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.model.MultipleSearchItemModel;
import com.tripint.intersight.service.HttpRequest;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 账户明细
 * A simple {@link Fragment} subclass.
 */
public class AccountDetailFragment extends BaseBackFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.account_detail_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private final int PAGE_SIZE = 10;

    private int TOTAL_COUNTER = 0;
    private int mCurrentCounter = 0;

    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<BasePageableResponse<AccountDetailEntity>> subscriber;

    private BasePageableResponse<AccountDetailEntity> data = new BasePageableResponse<AccountDetailEntity>();


    public static AccountDetailFragment newInstance() {

        Bundle args = new Bundle();

        AccountDetailFragment fragment = new AccountDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_detail, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        httpRequestData();//请求数据
        return view;
    }

    /**
     * 网络请求数据
     */
    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<AccountDetailEntity>>() {
            @Override
            public void onNext(BasePageableResponse<AccountDetailEntity> entity) {
                //接口请求成功后处理
                data = entity;
                List<MineMultipleItemModel> models = getMultipleItemModels(data.getLists());
                initView(null);
                initAdapter();
                if (mCurrentCounter == 0) {
                    mAdapter.setNewData(models);
                } else {
                    mAdapter.addData(models);
                    Log.e("models", String.valueOf(models.size()));
                }
                TOTAL_COUNTER = data.getTotal();
                Log.e("total",String.valueOf(TOTAL_COUNTER));
                mCurrentCounter = mAdapter.getData().size();
                Log.e("counter",String.valueOf(mCurrentCounter));
            }
        };

        MineDataHttpRequest.getInstance(mActivity).getAccountDetail(new ProgressSubscriber(subscriber, mActivity), 1);
    }

    /**
     * 适配数据
     */
    private void initAdapter() {

        mAdapter = new MineCommonMultipleAdapter(getMultipleItemModels(data.getLists()));
        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
            }
        });
        mAdapter.setLoadingView(getLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);
    }

    //toolbar
    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("账户明细");
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
        mCurrentCounter = 0 ;
        MineDataHttpRequest.getInstance(mActivity).getAccountDetail(new ProgressSubscriber(subscriber, mActivity), 1);
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
                    MineDataHttpRequest.getInstance(mActivity).getAccountDetail(new ProgressSubscriber(subscriber, mActivity), getCurrentPage());
                }
            }
        }, 200);
    }

    private View getLoadMoreView() {
        final View customLoading = LayoutInflater.from(mActivity).inflate(R.layout.common_loading, (ViewGroup) mRecyclerView.getParent(), false);
        return customLoading;
    }


    public int getCurrentPage() {
        return mCurrentCounter / HttpRequest.DEFAULT_PAGE_SIZE + 1;
    }

    @NonNull
    private List<MineMultipleItemModel> getMultipleItemModels(List<AccountDetailEntity> entiries) {
        List<MineMultipleItemModel> models = new ArrayList<>();
        int type = MineMultipleItemModel.MY_ACCOUNT_DETAIL;
        if (entiries == null) return models;

        for (AccountDetailEntity entiry : entiries) {
            models.add(new MineMultipleItemModel(type, entiry));
        }
        Log.e("models",String.valueOf(models.size()));

        return models;

    }
}
