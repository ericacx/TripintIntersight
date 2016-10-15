package com.tripint.intersight.fragment.mine.message;


import android.os.Bundle;
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
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MineCommonMultipleAdapter;
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.message.MessageDataEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.service.MessageDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新消息提醒页面
 * A simple {@link Fragment} subclass.
 */
public class NewMessageFragment extends BaseBackFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.new_message_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.new_message_text_view_interview)
    TextView newMessageTextViewInterview;
    @Bind(R.id.new_message_text_view_ask_answer)
    TextView newMessageTextViewAskAnswer;
    @Bind(R.id.new_message_text_view_comment_agree)
    TextView newMessageTextViewCommentAgree;
    @Bind(R.id.new_message_text_view_system_message)
    TextView newMessageTextViewSystemMessage;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private final int PAGE_SIZE = 10;

    private int TOTAL_COUNTER = 0;

    private int mCurrentCounter = 0;

    List<MineMultipleItemModel> models = new ArrayList<>();

    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<BasePageableResponse<MessageDataEntity>> subscriber;
    private BasePageableResponse<MessageDataEntity> data = new BasePageableResponse<MessageDataEntity>();

    public static NewMessageFragment newInstance() {
        Bundle args = new Bundle();
        NewMessageFragment fragment = new NewMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_message, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        httpRequestData();
        return view;
    }

    /**
     * 请求数据
     */
    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<MessageDataEntity>>() {
            @Override
            public void onNext(BasePageableResponse<MessageDataEntity> entity) {
                data = entity;
                Log.e("newmessage", String.valueOf(entity.getTotal()));
                initView(null);
                //适配数据
                initAdapter();
            }
        };
        MessageDataHttpRequest.getInstance(mActivity).getNewMessage(new ProgressSubscriber(subscriber, mActivity),1);
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("新消息提醒");
    }

    /**
     * 适配数据
     */
    private void initAdapter() {

        initData();
        mAdapter = new MineCommonMultipleAdapter(models);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
//                DiscussEntiry entity = (DiscussEntiry) adapter.getItem(position);
//                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(entity)));
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

    @OnClick({R.id.new_message_text_view_interview, R.id.new_message_text_view_ask_answer, R.id.new_message_text_view_comment_agree, R.id.new_message_text_view_system_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_message_text_view_interview://访谈消息
                EventBus.getDefault().post(new StartFragmentEvent(InterviewMessageFragment.newInstance()));
                break;
            case R.id.new_message_text_view_ask_answer://问答消息
                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerMessageFragment.newInstance()));
                break;
            case R.id.new_message_text_view_comment_agree://评论或赞
                EventBus.getDefault().post(new StartFragmentEvent(CommentPhraiseFragment.newInstance()));
                break;
            case R.id.new_message_text_view_system_message://系统消息
                EventBus.getDefault().post(new StartFragmentEvent(SystemMessageFragment.newInstance()));
                break;
        }
    }


    protected void initView(View view) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onRefresh() {
        initData();
        mAdapter.setNewData(models);
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
                    initData();
                    mAdapter.addData(models);
                    mCurrentCounter = mAdapter.getData().size();
                }
            }
        }, 200);
    }

    private View getLoadMoreView() {
        final View customLoading = LayoutInflater.from(mActivity).inflate(R.layout.common_loading, (ViewGroup) mRecyclerView.getParent(), false);
        return customLoading;
    }

    private void initData(){
        int type = MineMultipleItemModel.MY_MESSAGE_NEW;

        for (MessageDataEntity entity : data.getLists()) {
            models.add(new MineMultipleItemModel(type, entity));
        }
    }
}
