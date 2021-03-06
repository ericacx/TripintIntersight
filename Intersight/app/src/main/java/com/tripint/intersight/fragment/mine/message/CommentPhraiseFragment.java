package com.tripint.intersight.fragment.mine.message;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import com.tripint.intersight.entity.message.CommentPraiseEntity;
import com.tripint.intersight.entity.message.MessageContentEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.service.HttpRequest;
import com.tripint.intersight.service.MessageDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 评论或赞过
 * A simple {@link Fragment} subclass.
 */
public class CommentPhraiseFragment extends BaseBackFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    public static final String ARG_COMMENT_ID = "arg_comment_id";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.comment_agree_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private final int PAGE_SIZE = 10;
    private int TOTAL_COUNTER = 0;
    private int mCurrentCounter = 0;
    private MineCommonMultipleAdapter mAdapter;

    private BasePageableResponse<CommentPraiseEntity> data = new BasePageableResponse<CommentPraiseEntity>();
    private PageDataSubscriberOnNext<BasePageableResponse<CommentPraiseEntity>> subscriber;

    public static CommentPhraiseFragment newInstance(CommentPraiseEntity entity) {
        Bundle args = new Bundle();
        args.putInt(CommentPhraiseFragment.ARG_COMMENT_ID, entity.getId());
        CommentPhraiseFragment fragment = new CommentPhraiseFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_agree, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        initView(null);
        initAdapter();
        httpResquestData();
        return view;
    }

    private void httpResquestData() {
        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<CommentPraiseEntity>>() {
            @Override
            public void onNext(BasePageableResponse<CommentPraiseEntity> entity) {
                data = entity;
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
        MessageDataHttpRequest.getInstance(mActivity).getCommentPraiseMessage(new ProgressSubscriber(subscriber, mActivity),1);
    }

    private void initAdapter() {

        mAdapter = new MineCommonMultipleAdapter(getMineMultipleItemModel(data.getLists()));
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


    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("评论/赞消息");
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
        mCurrentCounter = 0 ;
        MessageDataHttpRequest.getInstance(mActivity).getCommentPraiseMessage(new ProgressSubscriber(subscriber, mActivity),1);
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
                    MessageDataHttpRequest.getInstance(mActivity).getCommentPraiseMessage(new ProgressSubscriber(subscriber, mActivity),getCurrentPage());
                }
            }
        }, 200);
    }

    private View getLoadMoreView() {
        final View customLoading = LayoutInflater.from(mActivity).inflate(R.layout.common_loading, (ViewGroup) mRecyclerView.getParent(), false);
        return customLoading;
    }
    public int getCurrentPage() {
        return mCurrentCounter / HttpRequest.DEFAULT_PAGE_SIZE + 1;
    }


//    @NonNull
//    private List<MineMultipleItemModel> getMineMultipleItemModel(List<CommentPraiseEntity> entities) {
//        int type = MineMultipleItemModel.MY_MESSAGE_COMMENT_PRAISE;
//        List<MineMultipleItemModel> models = new ArrayList<>();
//        if (models == null) return models;
//        for (CommentPraiseEntity entity : entities) {
//            models.add(new MineMultipleItemModel(type, entity));
//        }
//        return models;
//    }


    @NonNull
    private List<MineMultipleItemModel> getMineMultipleItemModel(List<CommentPraiseEntity> entiries) {
        List<MineMultipleItemModel> models = new ArrayList<>();
        int type = MineMultipleItemModel.MY_MESSAGE_COMMENT_PRAISE;
        if (entiries == null) return models;

        for (CommentPraiseEntity entiry : entiries) {
            models.add(new MineMultipleItemModel(type, entiry));
        }
        return models;
    }
}
