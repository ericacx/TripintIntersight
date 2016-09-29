package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.entity.discuss.DiscussPageEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.type;

/**
 * 账户明细
 * A simple {@link Fragment} subclass.
 */
public class AccountDetailFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.accountDetailRecyclerView)
    RecyclerView accountDetailRecyclerView;

    private PageDataSubscriberOnNext<DiscussPageEntity> subscriber;

    private DiscussPageEntity data;


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

//        httpRequestData();//请求数据
        return view;
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<DiscussPageEntity>() {
            @Override
            public void onNext(DiscussPageEntity entity) {
                //接口请求成功后处理
                data = entity;
                initView(null);
                initAdapter();
            }
        };


        DiscussDataHttpRequest.getInstance(mActivity).getDiscusses(new ProgressSubscriber(subscriber, mActivity), type, 1, 10);
    }

    private void initAdapter() {

    }

    //toolbar
    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("账户明细");
    }
    protected void initView(View view) {
        accountDetailRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
