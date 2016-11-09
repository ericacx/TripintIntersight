package com.tripint.intersight.fragment.personal;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.MineCommonMultipleAdapter;
import com.tripint.intersight.adapter.PaymentSelectAdapter;
import com.tripint.intersight.adapter.listener.RecyclerViewItemOnClick;
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ListHolder;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.discuss.CreateDiscussResponseEntity;
import com.tripint.intersight.entity.discuss.CreateInterviewResponseEntity;
import com.tripint.intersight.entity.discuss.DiscussEntity;
import com.tripint.intersight.entity.mine.AskAnswerEntity;
import com.tripint.intersight.entity.payment.AliPayResponseEntity;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.create.CreateDiscussFragment;
import com.tripint.intersight.fragment.create.CreateInterviewFragment;
import com.tripint.intersight.fragment.home.AskAnswerDetailFragment;
import com.tripint.intersight.fragment.home.AskReplayDetailFragment;
import com.tripint.intersight.helper.AliPayUtils;
import com.tripint.intersight.helper.PayUtils;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.service.ExpertDataHttpRequest;
import com.tripint.intersight.service.HttpRequest;
import com.tripint.intersight.service.PaymentDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tripint.intersight.model.MineMultipleItemModel.MY_DISCUSS;

/**
 * 他的主页----他的问答--
 * A simple {@link Fragment} subclass.
 */
public class HisAskAnswerFragment extends BaseBackFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_USER_ID = "arg_user_id";
    public static final String ARG_USER_NAME = "arg_user_name";
    public static final String ARG_INTERVIEW_PAY_ID = "arg_interview_pay_id";
    public static final String ARG_DISCUSS_PAY_ID = "arg_discuss_pay_id";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_my_common_header_left)
    TextView btnMyCommonHeaderLeft;
    @Bind(R.id.btn_my_common_header_right)
    TextView btnMyCommonHeaderRight;
    @Bind(R.id.hisAskAnswerVG)
    LinearLayout hisAskAnswerVG;
    @Bind(R.id.his_ask_answer_ask)
    Button hisAskAnswerAsk;
    @Bind(R.id.his_ask_answer_interview)
    Button hisAskAnswerInterview;
    @Bind(R.id.hisAskAnswerLL)
    LinearLayout hisAskAnswerLL;
    @Bind(R.id.his_ask_answer_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private final int PAGE_SIZE = 10;
    private int TOTAL_COUNTER = 0;
    private int mCurrentCounter = 0;
    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<BasePageableResponse<AskAnswerEntity>> subscriber;

    private BasePageableResponse<AskAnswerEntity> data = new BasePageableResponse<AskAnswerEntity>();
    private String discussPay;
    private String interviewPay;
    private int tab = 0;
    private int uid = 0;
    private String name = null;

    public static HisAskAnswerFragment newInstance(int uid, String nickname,String discussPay,String interviewPay) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, uid);
        args.putString(ARG_USER_NAME,nickname);
        args.putString(ARG_DISCUSS_PAY_ID,discussPay);
        args.putString(ARG_INTERVIEW_PAY_ID,interviewPay);
        HisAskAnswerFragment fragment = new HisAskAnswerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getInt(ARG_USER_ID);
            name = bundle.getString(ARG_USER_NAME);
            discussPay = bundle.getString(ARG_DISCUSS_PAY_ID);
            interviewPay = bundle.getString(ARG_INTERVIEW_PAY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_his_ask_answer, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        initView(null);
        initAdapter();
        setTab(0);
        return view;
    }

    private void httpRequestData(int type) {

        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<AskAnswerEntity>>() {
            @Override
            public void onNext(BasePageableResponse<AskAnswerEntity> entity) {
                //接口请求成功后处理
                data = entity;
                List<MineMultipleItemModel> models = getMineMultipleItemModel(data.getLists());
                if (mCurrentCounter == 0) {
                    mAdapter.setNewData(models);
                } else {
                    mAdapter.addData(models);
                }
                TOTAL_COUNTER = data.getTotal();
                mCurrentCounter = mAdapter.getData().size();
            }
        };
        ExpertDataHttpRequest.getInstance(mActivity).getHisAskAnswer(new ProgressSubscriber(subscriber, mActivity), type, uid, 1);
    }


    @OnClick({R.id.btn_my_common_header_left, R.id.btn_my_common_header_right})
    public void onTabBarClick(View view) {
        switch (view.getId()) {

            case R.id.btn_my_common_header_left: //行业领域
                if (!btnMyCommonHeaderLeft.isSelected()) {
                    setTab(0);
                }
                break;
            case R.id.btn_my_common_header_right: //我的关注
                if (!btnMyCommonHeaderRight.isSelected()) {
                    setTab(1);
                }
                break;
        }
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

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle(name+"的问答");
    }

    private void initAdapter() {

        mAdapter = new MineCommonMultipleAdapter(getMineMultipleItemModel(data.getLists()));
        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                MineMultipleItemModel model = (MineMultipleItemModel) adapter.getItem(position);
                DiscussEntity result = new DiscussEntity(model.getAskAnswerEntity().getId());
                if (model.getAskAnswerEntity().getStatus().equals("已回答")){
                    EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(result)));
                } else if (model.getAskAnswerEntity().getStatus().equals("待回答")){
                    EventBus.getDefault().post(new StartFragmentEvent(AskReplayDetailFragment.newInstance(result)));
                }
            }
        });
        mAdapter.setLoadingView(getLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);
    }


    @NonNull
    private List<MineMultipleItemModel> getMineMultipleItemModel(List<AskAnswerEntity> entiries) {
        List<MineMultipleItemModel> models = new ArrayList<>();
        int type = tab == 0 ? MineMultipleItemModel.HIS_DISCUSS : MineMultipleItemModel.HIS_DISCUSS_FOLLOW;
        if (entiries == null) return models;

        for (AskAnswerEntity entiry : entiries) {
            models.add(new MineMultipleItemModel(type, entiry));
        }
        return models;
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
        hisAskAnswerAsk.setText("￥"+discussPay+" 向他提问");
    }

    @Override
    public void onRefresh() {
        mCurrentCounter = 0;
        ExpertDataHttpRequest.getInstance(mActivity).getHisAskAnswer(new ProgressSubscriber(subscriber, mActivity), tab, uid,1);
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
                    ExpertDataHttpRequest.getInstance(mActivity).getHisAskAnswer(new ProgressSubscriber(subscriber, mActivity), tab, uid, getCurrentPage());
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

    @OnClick({R.id.his_ask_answer_ask, R.id.his_ask_answer_interview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.his_ask_answer_ask:
//                initAskDialog();

                EventBus.getDefault().post(new StartFragmentEvent(CreateDiscussFragment.newInstance(uid,discussPay)));
                break;
            case R.id.his_ask_answer_interview:
//                initInterviewDialog();
                EventBus.getDefault().post(new StartFragmentEvent(CreateInterviewFragment.newInstance(uid,interviewPay)));
                break;
        }
    }

}
