package com.tripint.intersight.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageAdapter;
import com.tripint.intersight.adapter.AskRecyclerViewAdapter;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.entity.article.ArticleBannerEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.discuss.DiscussPageEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;
import com.tripint.intersight.fragment.search.SearchMainFragment;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.widget.BannerViewHolder;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 提问画面
 * A simple {@link Fragment} subclass.
 */
public class AskFragment extends BaseLazyMainFragment {


    @Bind(R.id.toolbar_search_text)
    TextView toolbarSearchText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.askRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar_search_button)
    ImageView toolbarSearchButton;

    private AskRecyclerViewAdapter mAdapter;


    private PageDataSubscriberOnNext<DiscussPageEntity> subscriber;
    private PageDataSubscriberOnNext<ArticleBannerEntity> bannerSubscriber;

    private DiscussPageEntity data;

    private LinearLayoutManager linearLayoutManager;

    private List<String> networkImages;

    //    private DataEntity dataEntity;
    public static AskFragment newInstance() {

        Bundle args = new Bundle();

        AskFragment fragment = new AskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        ButterKnife.bind(this, view);

        httpBannerRequestData();
        return view;
    }

    private void httpBannerRequestData() {
        bannerSubscriber = new PageDataSubscriberOnNext<ArticleBannerEntity>() {
            @Override
            public void onNext(ArticleBannerEntity entity) {
                //接口请求成功后处理
                networkImages = new ArrayList<String>();
                for (int i = 0; i <entity.getBanner().size(); i++) {
                    networkImages.add(entity.getBanner().get(i).getUrl());
                    Log.e("opinion",networkImages.get(i));
                }

                initView(null);
                httpRequestData();
            }
        };


        BaseDataHttpRequest.getInstance(mActivity).getBanner(new ProgressSubscriber(bannerSubscriber, mActivity), 2);
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<DiscussPageEntity>() {
            @Override
            public void onNext(DiscussPageEntity entity) {
                //接口请求成功后处理
                data = entity;
//                ToastUtil.showToast(mActivity, entity.getAbility().toString() +"");
                initAdapter();
            }
        };


        DiscussDataHttpRequest.getInstance(mActivity).getDiscusses(new ProgressSubscriber(subscriber, mActivity), 1, 10);
    }

    protected void initView(View view) {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initAdapter() {
        mAdapter = new AskRecyclerViewAdapter(data.getDiscuss());
        mAdapter.openLoadAnimation();

        mRecyclerView.addOnItemTouchListener(new com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                DiscussEntiry entity = (DiscussEntiry) adapter.getItem(position);
                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(entity)));
            }
        });

        mAdapter.addHeaderView(getHeaderView(getHeaderViewClickListener()));
        mRecyclerView.setAdapter(mAdapter);
    }


    private View getHeaderView(View.OnClickListener clickListener) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_ask_answer_banner_header, null);
        view.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ConvenientBanner mainBanner = (ConvenientBanner) view.findViewById(R.id.banner_qa_main);
        mainBanner.setPages(new CBViewHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createHolder() {
                return new BannerViewHolder();
            }
        }, networkImages)
                //小圆点
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        mainBanner.setOnClickListener(clickListener);
        return view;
    }


    @OnClick({R.id.toolbar_search_button, R.id.toolbar_search_text})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.toolbar_search_button: //行业领域
                EventBus.getDefault().post(new StartFragmentEvent(SearchMainFragment.newInstance()));
                break;
            case R.id.toolbar_search_text: //行业领域
                EventBus.getDefault().post(new StartFragmentEvent(SearchMainFragment.newInstance()));
                break;

        }
    }

    private View.OnClickListener getHeaderViewClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(getActivity(), "Header view Click");
            }
        };
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
