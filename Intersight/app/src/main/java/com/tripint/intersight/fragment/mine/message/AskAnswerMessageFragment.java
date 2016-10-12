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
import com.tripint.intersight.entity.message.MessageDataEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskAnswerMessageFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ask_answer_recyclerview)
    RecyclerView mRecyclerView;

    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<List<MessageDataEntity>> subscriber;
    private List<MessageDataEntity> data;

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
        httpRequestData();
        return view;
    }

    /**
     * 网络请求数据源
     */
    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<List<MessageDataEntity>>() {
            @Override
            public void onNext(List<MessageDataEntity> messageDataEntities) {
                data = messageDataEntities;
                initView(null);
                initAdapter();
            }
        };
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("问答消息");
    }

    protected void initView(View view) {
        mRecyclerView.setHasFixedSize(true);
    }

    /**
     * 适配数据
     */
    private void initAdapter() {

        List<MineMultipleItemModel> models = new ArrayList<>();

        int type = MineMultipleItemModel.MY_MESSAGE_NEW;

        for (MessageDataEntity entity : data) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
