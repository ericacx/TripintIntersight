package com.tripint.intersight.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageAdapter;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.pulltorefresh.PtrClassicFrameLayout;
import com.tripint.intersight.common.widget.pulltorefresh.PtrDefaultHandler;
import com.tripint.intersight.common.widget.pulltorefresh.PtrFrameLayout;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.article.ArticleBannerEntity;
import com.tripint.intersight.entity.common.BannerEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.discuss.DiscussPageEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseFragment;
import com.tripint.intersight.fragment.flipview.OpinionFlipViewAdapter;
import com.tripint.intersight.model.PaginationModel;
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
 * A simple {@link Fragment} subclass.
 */
public class AskAnswerFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler_view_ask_answer)
    RecyclerView mRecyclerView;

    @Bind(R.id.btn_qa_profession)
    TextView mQAPrefessionButton;

    @Bind(R.id.btn_qa_interest)
    TextView mQAInterestrButton;

    @Bind(R.id.btn_qa_recommend)
    TextView mQARecommendButton;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private final int PAGE_SIZE = 1;

    private int TOTAL_COUNTER = 4;

    private int mCurrentCounter = 0;

    private AskAnswerPageAdapter mAdapter;

    private PageDataSubscriberOnNext<DiscussPageEntity> subscriber;

    private PageDataSubscriberOnNext<ArticleBannerEntity> bannerSubscriber;

    private DiscussPageEntity data = new DiscussPageEntity();

    private PaginationModel pageModel = new PaginationModel();


    private List<String> networkImages;


    public static AskAnswerFragment newInstance() {

        Bundle args = new Bundle();

        AskAnswerFragment fragment = new AskAnswerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_answer, container, false);
        ButterKnife.bind(this, view);

        initView(null);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setTab(0);//默认选中第一个TAB
        httpRequestBannerData();
    }

    private void httpRequestBannerData(){


        bannerSubscriber = new PageDataSubscriberOnNext<ArticleBannerEntity>(){
            @Override
            public void onNext(ArticleBannerEntity entity) {

                networkImages = new ArrayList<String>();
                for (int i = 0; i <entity.getBanner().size(); i++) {
                    networkImages.add(entity.getBanner().get(i).getUrl());
                    Log.e("opinion",networkImages.get(i));
                }
                initAdapter();
                setTab(0);

            }
        };
        BaseDataHttpRequest.getInstance(mActivity).getBanner(
                new ProgressSubscriber(bannerSubscriber, mActivity),2);
    }

    private void httpRequestData(int type) {
        subscriber = new PageDataSubscriberOnNext<DiscussPageEntity>() {
            @Override
            public void onNext(DiscussPageEntity entity) {
                //接口请求成功后处理
                data = entity;
                mAdapter.addData(data.getDiscuss());
            }
        };


        DiscussDataHttpRequest.getInstance(mActivity).getDiscusses(new ProgressSubscriber(subscriber, mActivity), type, 1, PAGE_SIZE);
    }

    protected void initView(View view) {

        swipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



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

    private View getLoadMoreView() {
        final View customLoading = LayoutInflater.from(mActivity).inflate(R.layout.common_loading, (ViewGroup) mRecyclerView.getParent(), false);
        return customLoading;
    }

    @OnClick({R.id.btn_qa_recommend, R.id.btn_qa_interest, R.id.btn_qa_profession})
    public void onTabBarClick(View view) {
        switch (view.getId()) {

            case R.id.btn_qa_profession: //行业领域
                if (!mQAPrefessionButton.isSelected()) {
                    setTab(0);
                }
                break;
            case R.id.btn_qa_interest: //我的关注
                if (!mQAInterestrButton.isSelected()) {
                    setTab(1);
                }
                break;
            case R.id.btn_qa_recommend: //精选推荐
                if (!mQARecommendButton.isSelected()) {
                    setTab(2);
                }
                break;
        }
    }

    /**
     * 请求不同的
     *
     * @param tab
     */
    private void setTab(int tab) {
        mQAPrefessionButton.setSelected(tab == 0);
        mQAInterestrButton.setSelected(tab == 1);
        mQARecommendButton.setSelected(tab == 2);
        httpRequestData(tab);
    }


    private void initAdapter() {
        mAdapter = new AskAnswerPageAdapter(data.getDiscuss());
        mAdapter.openLoadAnimation();

        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                DiscussEntiry entity = (DiscussEntiry) adapter.getItem(position);
                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(entity)));
            }
        });

        mAdapter.addHeaderView(getHeaderView(getHeaderViewClickListener()));
        mAdapter.setLoadingView(getLoadMoreView());

        mRecyclerView.setAdapter(mAdapter);
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
    public void onRefresh() {
        mAdapter.setNewData(data.getDiscuss());
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
                    mAdapter.addData(data.getDiscuss());
                    mCurrentCounter = mAdapter.getData().size();
                }
            }
        }, 200);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
