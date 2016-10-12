package com.tripint.intersight.fragment.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MineCommonMultipleAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.service.ExpertDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 他的观点----页面
 * A simple {@link Fragment} subclass.
 */
public class HisOpinionFragment extends BaseBackFragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_my_common_header_left)
    TextView btnMyCommonHeaderLeft;
    @Bind(R.id.btn_my_common_header_right)
    TextView btnMyCommonHeaderRight;
    @Bind(R.id.hisAskAnswerVG)
    LinearLayout hisAskAnswerVG;
    @Bind(R.id.his_opinion_ask)
    Button hisOpinionAsk;
    @Bind(R.id.his_opinion_interview)
    Button hisOpinionInterview;
    @Bind(R.id.hisAskAnswerLL)
    LinearLayout hisAskAnswerLL;
    @Bind(R.id.his_opinion_recyclerView)
    RecyclerView mRecyclerView;

    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<List<MineFollowPointEntity>> subscriber;

    private List<MineFollowPointEntity> data;

    private int tab;

    public static HisOpinionFragment newInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();
        HisOpinionFragment fragment = new HisOpinionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_his_opinion, container, false);
        ButterKnife.bind(this, view);
        setTab(0);
        return view;
    }


    /**
     * 请求不同的关键字 精选自由行、省心国内游、品质出境游
     *
     * @param tab
     */
    private void setTab(int tab) {
        this.tab = tab;
        btnMyCommonHeaderLeft.setSelected(tab == 0);
        btnMyCommonHeaderRight.setSelected(tab == 1);
        httpRequestData(tab);
    }

    private void httpRequestData(int type) {
        subscriber = new PageDataSubscriberOnNext<List<MineFollowPointEntity>>() {
            @Override
            public void onNext(List<MineFollowPointEntity> entity) {
                //接口请求成功后处理
                data = entity;
                initView(null);
                initAdapter(tab);
            }
        };

        ExpertDataHttpRequest.getInstance(mActivity).getHisFollowPoint(new ProgressSubscriber(subscriber, mActivity), type, 1, 10);
    }

    protected void initView(View view) {
        initToolbarNav(toolbar);
        toolbar.setTitle("他的观点");
        mRecyclerView.setHasFixedSize(true);
    }

    private void initAdapter(int tab) {

        List<MineMultipleItemModel> models = new ArrayList<>();

        int type = tab == 0 ? MineMultipleItemModel.HIS_OPTION : MineMultipleItemModel.HIS_OPTION_FOLLOW;
        for (MineFollowPointEntity entiry : data) {

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

    @OnClick({R.id.his_opinion_ask, R.id.his_opinion_interview,R.id.btn_my_common_header_left, R.id.btn_my_common_header_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_my_common_header_left://他的观点
                if (!btnMyCommonHeaderLeft.isSelected()) {
                    setTab(0);
                }
                break;
            case R.id.btn_my_common_header_right://他关注的观点
                if (!btnMyCommonHeaderRight.isSelected()) {
                    setTab(1);
                }
                break;
            case R.id.his_opinion_ask:
                break;
            case R.id.his_opinion_interview:
                break;
        }
    }

}
