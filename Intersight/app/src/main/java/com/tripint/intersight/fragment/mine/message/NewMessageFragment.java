package com.tripint.intersight.fragment.mine.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MineCommonMultipleAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.discuss.DiscussEntity;
import com.tripint.intersight.entity.message.CommentPraiseEntity;
import com.tripint.intersight.entity.message.MessageContentEntity;
import com.tripint.intersight.entity.message.MessageEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.home.AskAnswerDetailFragment;
import com.tripint.intersight.fragment.home.AskReplayDetailFragment;
import com.tripint.intersight.fragment.mine.MyInterviewDetailFragment;
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
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private final int PAGE_SIZE = 10;

    private int TOTAL_COUNTER = 0;

    private int mCurrentCounter = 0;

    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<MessageEntity> subscriber;
    private MessageEntity data = new MessageEntity();
    private CommentPraiseEntity commentPraiseEntity = new CommentPraiseEntity();
    List<MineMultipleItemModel> models = new ArrayList<>();

    private int interview;
    private int discuss;
    private int comment;

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
        initLayout();
        return view;
    }

    /**
     * 是否显示小红点
     */
    private void initLayout() {
        if (interview != 0){

        } else {

        }

        if (discuss != 0){

        } else {

        }

        if (comment != 0){

        } else {

        }
    }

    /**
     * 请求数据
     */
    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<MessageEntity>() {
            @Override
            public void onNext(MessageEntity entity) {
                data = entity;
                initView(null);
                interview = data.getInterview();
                discuss = data.getDiscuss();
                comment = data.getComment();
//                models = data.getLists();
                //适配数据
                initData();
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
                MineMultipleItemModel model = (MineMultipleItemModel) adapter.getItem(position);
                DiscussEntity result = new DiscussEntity(model.getMessageContentEntity().getId());
                InterviewEntity interviewEntity = new InterviewEntity(model.getMessageContentEntity().getId());
                if (model.getMessageContentEntity().getType() == 1){
                    EventBus.getDefault().post(new StartFragmentEvent(MyInterviewDetailFragment.newInstance(interviewEntity)));
                }else if (model.getMessageContentEntity().getType() == 2){
                    if (model.getMessageContentEntity().getMessageType() == 1) {
                        EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(result)));
                    } else if (model.getMessageContentEntity().getMessageType() == 2) {
                        EventBus.getDefault().post(new StartFragmentEvent(AskReplayDetailFragment.newInstance(result)));
                    }
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

    @OnClick({R.id.new_message_text_view_interview, R.id.new_message_text_view_ask_answer, R.id.new_message_text_view_comment_agree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_message_text_view_interview://访谈消息
                EventBus.getDefault().post(new StartFragmentEvent(InterviewMessageFragment.newInstance()));
                break;
            case R.id.new_message_text_view_ask_answer://问答消息
                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerMessageFragment.newInstance()));
                break;
            case R.id.new_message_text_view_comment_agree://评论或赞
                EventBus.getDefault().post(new StartFragmentEvent(CommentPhraiseFragment.newInstance(commentPraiseEntity)));
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

        for (MessageContentEntity entity : data.getLists()) {
            models.add(new MineMultipleItemModel(type, entity));
        }
    }
}
