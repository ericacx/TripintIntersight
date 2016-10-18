package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.widget.flipview.FlipView;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.entity.article.ArticlesEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.flipview.OpinionFlipViewAdapter;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 观点(文章)画面
 * A simple {@link Fragment} subclass.
 */
public class OpinionFragment extends BaseBackFragment{

    @Bind(R.id.opinionFlipView)
    FlipView opinionFlipView;
//    @Bind(R.id.swipe_refresh_layout)
//    SwipeRefreshLayout swipeRefreshLayout;

    private OpinionFlipViewAdapter mAdapter;

    private BasePageableResponse<ArticlesEntity> data = new BasePageableResponse<ArticlesEntity>();
    private PageDataSubscriberOnNext<BasePageableResponse<ArticlesEntity>> subscriber;

    public static OpinionFragment newInstance() {

        Bundle args = new Bundle();

        OpinionFragment fragment = new OpinionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opinion, container, false);
        ButterKnife.bind(this, view);
        httpRequestData();
        return view;
    }


    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<ArticlesEntity>>() {
            @Override
            public void onNext(BasePageableResponse<ArticlesEntity> entity) {
                data = entity;
                Log.e("sss", String.valueOf(entity.getLists()));
                initAdapter();
            }
        };
        BaseDataHttpRequest.getInstance(mActivity).getArticles(
                new ProgressSubscriber(subscriber, mActivity), 1);
    }

    private void initAdapter() {
        mAdapter = new OpinionFlipViewAdapter(mActivity, data.getLists());
        opinionFlipView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
