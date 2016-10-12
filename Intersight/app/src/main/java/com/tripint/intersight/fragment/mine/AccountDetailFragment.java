package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MineCommonMultipleAdapter;
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.mine.AccountDetailEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
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
public class AccountDetailFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.account_detail_recyclerView)
    RecyclerView mRecyclerView;

    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<BasePageableResponse<AccountDetailEntity>> subscriber;

    private BasePageableResponse<AccountDetailEntity> data;


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
                initView(null);
                initAdapter();
            }
        };

        MineDataHttpRequest.getInstance(mActivity).getAccountDetail(new ProgressSubscriber(subscriber, mActivity), 1, 10);
    }

    /**
     * 适配数据
     */
    private void initAdapter() {

        List<MineMultipleItemModel> models = new ArrayList<>();

        int type = MineMultipleItemModel.MY_ACCOUNT_DETAIL;

        for (AccountDetailEntity entity : data.getLists()) {
            models.add(new MineMultipleItemModel(type, entity));
        }

        final GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 1);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MineCommonMultipleAdapter(models);
        mAdapter.openLoadAnimation();

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    //toolbar
    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("账户明细");
    }
    protected void initView(View view) {
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
