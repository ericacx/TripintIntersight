package com.tripint.intersight.fragment.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.flipview.FlipView;
import com.tripint.intersight.common.widget.flipview.OverFlipMode;
import com.tripint.intersight.entity.article.ArticlesEntity;
import com.tripint.intersight.event.PersonalEvent;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.create.CreateOpinionFragment;
import com.tripint.intersight.fragment.flipview.OpinionFlipViewAdapter;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.service.HttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 观点(文章)画面
 * A simple {@link Fragment} subclass.
 */
public class OpinionFragment extends BaseBackFragment implements FlipView.OnFlipListener, FlipView.OnOverFlipListener {

    @Bind(R.id.opinionFlipView)
    FlipView opinionFlipView;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar_search_button)
    ImageView toolbarSearchButton;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private int TOTAL_COUNTER = 0;//总数
    private int mCurrentCounter = 0;//当前条目的位置

    private OpinionFlipViewAdapter mAdapter;
    private BasePageableResponse<ArticlesEntity> data = new BasePageableResponse<ArticlesEntity>();
    private List<ArticlesEntity> entityList = new ArrayList<ArticlesEntity>();
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
        EventBus.getDefault().register(this);
        initToolbar();
        initAdapter();
        httpRequestData();
        return view;
    }

    private void initToolbar() {
        toolbarTitle.setText("观点");
        toolbarSearchButton.setImageResource(R.mipmap.iconfont_wgz);
    }


    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<ArticlesEntity>>() {
            @Override
            public void onNext(BasePageableResponse<ArticlesEntity> entity) {
                data = entity;
                entityList = entity.getLists();
                mAdapter.addResList(entityList);
                TOTAL_COUNTER = entity.getTotal();
                mCurrentCounter = mAdapter.getResList().size();
                Log.e("mCurrentCounter1", String.valueOf(mCurrentCounter));
            }
        };
        BaseDataHttpRequest.getInstance(mActivity).getArticles(
                new ProgressSubscriber(subscriber, mActivity), 1);
    }

    private void initAdapter() {

        mAdapter = new OpinionFlipViewAdapter(mActivity, entityList);
        opinionFlipView.setOnFlipListener(this);
        opinionFlipView.setOnOverFlipListener(this);
        opinionFlipView.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(PersonalEvent event) {
        httpRequestData();
    }


    public int getCurrentPage() {
        return mCurrentCounter / HttpRequest.DEFAULT_PAGE_SIZE + 1;
    }

    @OnClick(R.id.toolbar_search_button)
    public void onClick() {
        EventBus.getDefault().post(new StartFragmentEvent(CreateOpinionFragment.newInstance()));
    }

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        if (position > opinionFlipView.getPageCount() - 3 && opinionFlipView.getPageCount() < TOTAL_COUNTER) {

            BaseDataHttpRequest.getInstance(mActivity).getArticles(
                    new ProgressSubscriber(subscriber, mActivity), getCurrentPage());
            mAdapter.addResList(data.getLists());
        }
    }

    @Override
    public void onOverFlip(FlipView v, OverFlipMode mode, boolean overFlippingPrevious, float overFlipDistance, float flipDistancePerPage) {

    }
}
