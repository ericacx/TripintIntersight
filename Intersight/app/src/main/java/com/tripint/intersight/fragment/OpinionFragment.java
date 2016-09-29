package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.flipview.FlipView;
import com.tripint.intersight.entity.article.ArticleBannerEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.flipview.OpinionFlipViewAdapter;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 观点(文章)画面
 * A simple {@link Fragment} subclass.
 */
public class OpinionFragment extends BaseBackFragment {

    @Bind(R.id.opinionFlipView)
    FlipView opinionFlipView;
    private OpinionFlipViewAdapter mOpinionFlipViewAdapter2;
    private List<String> stringList;//图片url集合
    private ArticleBannerEntity articleBannerEntity;
    private PageDataSubscriberOnNext<ArticleBannerEntity> subscriber;

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


        subscriber = new PageDataSubscriberOnNext<ArticleBannerEntity>(){
            @Override
            public void onNext(ArticleBannerEntity entity) {
                articleBannerEntity = entity;
                stringList = new ArrayList<String>();
                for (int i = 0; i < entity.getBanner().size(); i++) {
                    stringList.add(entity.getBanner().get(i).getUrl());
                    Log.e("opinion",stringList.get(i));
                }
                mOpinionFlipViewAdapter2 = new OpinionFlipViewAdapter(mActivity,stringList);
                opinionFlipView.setAdapter(mOpinionFlipViewAdapter2);
            }
        };
        BaseDataHttpRequest.getInstance(mActivity).getArticleBanner(
                new ProgressSubscriber(subscriber, mActivity),2);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
