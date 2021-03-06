package com.tripint.intersight.fragment.mine.message;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.discuss.DiscussEntity;
import com.tripint.intersight.entity.message.MessageContentEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.home.AskAnswerDetailFragment;
import com.tripint.intersight.fragment.home.AskReplayDetailFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.service.HttpRequest;
import com.tripint.intersight.service.MessageDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskAnswerMessageFragment extends BaseBackFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ask_answer_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private final int PAGE_SIZE = 10;
    private int TOTAL_COUNTER = 0;
    private int mCurrentCounter = 0;

    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<BasePageableResponse<MessageContentEntity>> subscriber;
    private BasePageableResponse<MessageContentEntity> data = new BasePageableResponse<MessageContentEntity>();

    public static AskAnswerMessageFragment newInstance() {
        Bundle args = new Bundle();
        AskAnswerMessageFragment fragment = new AskAnswerMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_answer_message, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        initView(null);
        initAdapter();
        httpRequestData();
        return view;
    }

    /**
     * 网络请求数据源
     */
    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<MessageContentEntity>>() {
            @Override
            public void onNext(BasePageableResponse<MessageContentEntity> messageDataEntities) {
                data = messageDataEntities;
                List<MineMultipleItemModel> models = getMineMultipleItemModel(data.getLists());
                if (mCurrentCounter == 0) {
//                ToastUtil.showToast(mActivity, entity.getAbilityName().toString() +"");
                    mAdapter.setNewData(models);
                } else {
                    mAdapter.addData(models);
                }
                TOTAL_COUNTER = data.getTotal();
                mCurrentCounter = mAdapter.getData().size();
            }
        };
        MessageDataHttpRequest.getInstance(mActivity).getAskAnswerMessage(new ProgressSubscriber(subscriber, mActivity), 1);
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("问答消息");
    }

    /**
     * 适配数据
     */
    private void initAdapter() {

        mAdapter = new MineCommonMultipleAdapter(getMineMultipleItemModel(data.getLists()));
        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                MineMultipleItemModel model = (MineMultipleItemModel) adapter.getItem(position);
                DiscussEntity result = new DiscussEntity(model.getMessageContentEntity().getId());
                if (model.getMessageContentEntity().getMessageType() == 1) {
                    EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(result)));
                } else if (model.getMessageContentEntity().getMessageType() == 2) {
                    EventBus.getDefault().post(new StartFragmentEvent(AskReplayDetailFragment.newInstance(result)));
                }
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
        MessageDataHttpRequest.getInstance(mActivity).getAskAnswerMessage(new ProgressSubscriber(subscriber, mActivity), 1);
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
                    MessageDataHttpRequest.getInstance(mActivity).getAskAnswerMessage(new ProgressSubscriber(subscriber, mActivity), getCurrentPage());
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


    @NonNull
    private List<MineMultipleItemModel> getMineMultipleItemModel(List<MessageContentEntity> entiries) {
        List<MineMultipleItemModel> models = new ArrayList<>();
        int type = MineMultipleItemModel.MY_MESSAGE_ASK_ANSWER;
        if (entiries == null) return models;

        for (MessageContentEntity entiry : entiries) {
            models.add(new MineMultipleItemModel(type, entiry));
        }
        return models;
    }

//    @NonNull
//    private List<MineMultipleItemModel> getMineMultipleItemModel(List<MessageContentEntity> entities) {
//        int type = MineMultipleItemModel.MY_MESSAGE_ASK_ANSWER;
//        List<MineMultipleItemModel> models = new ArrayList<>();
//        if (models == null) return models;
//        for (MessageContentEntity entity : entities) {
//            models.add(new MineMultipleItemModel(type, entity));
//        }
//        return models;
//    }
}
