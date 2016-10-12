package com.tripint.intersight.fragment.mine.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MineCommonMultipleAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.message.CommentPraiseEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 评论或赞过
 * A simple {@link Fragment} subclass.
 */
public class CommentPhraiseFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.comment_agree_recyclerview)
    RecyclerView mRecyclerView;

    private MineCommonMultipleAdapter mAdapter;

    private List<CommentPraiseEntity> data;
    private PageDataSubscriberOnNext<List<CommentPraiseEntity>> subscriber;

    public static CommentPhraiseFragment newInstance() {
        Bundle args = new Bundle();
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
        httpResquestData();
        return view;
    }

    private void httpResquestData() {
        subscriber = new PageDataSubscriberOnNext<List<CommentPraiseEntity>>() {
            @Override
            public void onNext(List<CommentPraiseEntity> commentPraiseEntities) {
                data = commentPraiseEntities;
                initView(null);
                initAdapter();
            }
        };
    }

    private void initAdapter() {
        List<MineMultipleItemModel> models = new ArrayList<>();

        int type = MineMultipleItemModel.MY_MESSAGE_COMMENT_PRAISE;

        for (CommentPraiseEntity entity : data) {
            models.add(new MineMultipleItemModel(type, entity));
        }

        final GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 1);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MineCommonMultipleAdapter(models);
        mAdapter.openLoadAnimation();

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
//                DiscussEntiry entity = (DiscussEntiry) adapter.getItem(position);
//                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(entity)));
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initView(View view) {
        mRecyclerView.setHasFixedSize(true);
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
}
