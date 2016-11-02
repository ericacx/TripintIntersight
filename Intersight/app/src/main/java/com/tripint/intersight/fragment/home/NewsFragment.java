package com.tripint.intersight.fragment.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.widget.flipview.FlipView;
import com.tripint.intersight.entity.article.NewsEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.flipview.NewsFlipViewAdapter;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseBackFragment {


    @Bind(R.id.newsFlipView)
    FlipView mFlipView;
    private NewsFlipViewAdapter mNewsFlipViewAdapter;
    private BasePageableResponse<NewsEntity> data = new BasePageableResponse<NewsEntity>();
    private PageDataSubscriberOnNext<BasePageableResponse<NewsEntity>> subscriber;

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        httpRequestData();

        ButterKnife.bind(this, view);
        return view;
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<NewsEntity>>() {
            @Override
            public void onNext(BasePageableResponse<NewsEntity> entity) {
                data = entity;
                initAdapter();
            }
        };
        BaseDataHttpRequest.getInstance(mActivity).getNews(
                new ProgressSubscriber<BasePageableResponse<NewsEntity>>(subscriber, mActivity), 1
        );
    }

    private void initAdapter() {
        mNewsFlipViewAdapter = new NewsFlipViewAdapter(mActivity, data.getLists());
        mFlipView.setAdapter(mNewsFlipViewAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
