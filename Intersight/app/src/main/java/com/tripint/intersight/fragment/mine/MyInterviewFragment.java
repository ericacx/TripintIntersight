package com.tripint.intersight.fragment.mine;

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
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的访谈
 * A simple {@link Fragment} subclass.
 */
public class MyInterviewFragment extends BaseBackFragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.myInterviewRecyclerView)
    RecyclerView mRecyclerView;

    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<BasePageableResponse<InterviewEntity>> subscriber;
    private BasePageableResponse<InterviewEntity> data;

    public static MyInterviewFragment newInstance() {

        Bundle args = new Bundle();

        MyInterviewFragment fragment = new MyInterviewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_interview, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        httpRequestData();
        return view;
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<InterviewEntity>>() {
            @Override
            public void onNext(BasePageableResponse<InterviewEntity> entity) {
                //接口请求成功后处理
                data = entity;
                initView(null);
                initAdapter();
            }
        };

        MineDataHttpRequest.getInstance(mActivity).getMyInterview(new ProgressSubscriber(subscriber, mActivity), 1, 10);
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("我的访谈");
    }

    protected void initView(View view) {
        mRecyclerView.setHasFixedSize(true);
    }

    private void initAdapter() {

        List<MineMultipleItemModel> models = new ArrayList<>();

        int type = MineMultipleItemModel.MY_INTERVIEW;

        for (InterviewEntity entiry : data.getLists()) {
            models.add(new MineMultipleItemModel(type, entiry));
        }

        final GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 1);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MineCommonMultipleAdapter(models);
        mAdapter.openLoadAnimation();

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                InterviewEntity entity = (InterviewEntity) adapter.getItem(position);
                EventBus.getDefault().post(new StartFragmentEvent(MyInterviewDetailFragment.newInstance(entity)));
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
