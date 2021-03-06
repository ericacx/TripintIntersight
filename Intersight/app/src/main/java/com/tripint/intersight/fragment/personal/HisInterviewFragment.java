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
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.payment.AliPayResponseEntity;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.create.CreateDiscussFragment;
import com.tripint.intersight.fragment.create.CreateInterviewFragment;
import com.tripint.intersight.fragment.mine.MyInterviewDetailFragment;
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

/**
 * 他的访谈  --- 页面
 * A simple {@link Fragment} subclass.
 */
public class HisInterviewFragment extends BaseBackFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    public static final String ARG_USER_ID = "arg_user_id";
    public static final String ARG_USER_NAME = "arg_user_name";
    public static final String ARG_INTERVIEW_PAY_ID = "arg_interview_pay_id";
    public static final String ARG_DISCUSS_PAY_ID = "arg_discuss_pay_id";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.his_interview_ask)
    Button hisInterviewAsk;
    @Bind(R.id.his_interview_interview)
    Button hisInterviewInterview;
    @Bind(R.id.his_interview_LL)
    LinearLayout hisInterviewLL;
    @Bind(R.id.his_interview_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private final int PAGE_SIZE = 10;
    private int TOTAL_COUNTER = 0;
    private int mCurrentCounter = 0;
    private String discussPay;
    private String interviewPay;
    private int uid = 0;
    private String name = null;

    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<BasePageableResponse<InterviewEntity>> subscriber;
    private BasePageableResponse<InterviewEntity> data = new BasePageableResponse<InterviewEntity>();

    private CreateDiscussResponseEntity createDiscussResponseEntity;
    private CreateInterviewResponseEntity createInterviewResponseEntity;

    private PageDataSubscriberOnNext<CreateDiscussResponseEntity> subscriberDiscussCode;
    private PageDataSubscriberOnNext<CreateInterviewResponseEntity> subscriberInterviewCode;

    private PageDataSubscriberOnNext<WXPayResponseEntity> wxPaySubscriber;
    private PageDataSubscriberOnNext<AliPayResponseEntity> aliPaySubscriber;

    private String discussContent;
    private String interviewContent = null;
    private DialogPlus dialogPlus;
    protected static final int MSG_START_STREAMING_DISCUSS = 0;
    protected static final int MSG_START_STREAMING_INTERVIEW = 1;

    public static HisInterviewFragment newInstance(int uid, String nickname,String discussPay,String interviewPay) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, uid);
        args.putString(ARG_USER_NAME,nickname);
        args.putString(ARG_DISCUSS_PAY_ID,discussPay);
        args.putString(ARG_INTERVIEW_PAY_ID,interviewPay);
        HisInterviewFragment fragment = new HisInterviewFragment();
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
        View view = inflater.inflate(R.layout.fragment_his_interview, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        initView(null);
        initAdapter();
        httpRequestData();
        return view;
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle(name+"的访谈");
    }

    private void httpRequestData() {

        wxPaySubscriber = new PageDataSubscriberOnNext<WXPayResponseEntity>() {
            @Override
            public void onNext(WXPayResponseEntity entity) {
                //接口请求成功后处理,调起微信支付。
                PayUtils.getInstant().requestWXpay(entity);
//
            }
        };

        aliPaySubscriber = new PageDataSubscriberOnNext<AliPayResponseEntity>() {
            @Override
            public void onNext(AliPayResponseEntity entity) {
                //接口请求成功后处理,调起微信支付。
                AliPayUtils.getInstant(mActivity).pay(entity);
//
            }
        };

        subscriberDiscussCode = new PageDataSubscriberOnNext<CreateDiscussResponseEntity>() {
            @Override
            public void onNext(CreateDiscussResponseEntity entity) {
                Log.d(TAG, entity.getFlg());
                createDiscussResponseEntity = entity;
                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_START_STREAMING_DISCUSS), 500);
            }
        };

        subscriberInterviewCode = new PageDataSubscriberOnNext<CreateInterviewResponseEntity>() {
            @Override
            public void onNext(CreateInterviewResponseEntity entity) {
                Log.d(TAG, entity.getFlg());
                createInterviewResponseEntity = entity;
                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_START_STREAMING_INTERVIEW), 500);
            }
        };

        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<InterviewEntity>>() {
            @Override
            public void onNext(BasePageableResponse<InterviewEntity> entity) {
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

        ExpertDataHttpRequest.getInstance(mActivity).getHisInterview(new ProgressSubscriber(subscriber, mActivity),uid,1);
    }


    private void initAdapter() {

        mAdapter = new MineCommonMultipleAdapter(getMineMultipleItemModel(data.getLists()));
        mAdapter.openLoadAnimation();
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                MineMultipleItemModel entity = (MineMultipleItemModel) adapter.getItem(position);
                EventBus.getDefault().post(new StartFragmentEvent(MyInterviewDetailFragment.newInstance(entity.getInterviewEntity())));
            }
        });
        mAdapter.setLoadingView(getLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);
    }


    @NonNull
    private List<MineMultipleItemModel> getMineMultipleItemModel(List<InterviewEntity> entiries) {
        List<MineMultipleItemModel> models = new ArrayList<>();
        int type = MineMultipleItemModel.HIS_INTERVIEW;
        if (entiries == null) return models;

        for (InterviewEntity entiry : entiries) {
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
        hisInterviewAsk.setText("￥"+discussPay+" 向他提问");
    }

    @Override
    public void onRefresh() {
        mCurrentCounter = 0;
        ExpertDataHttpRequest.getInstance(mActivity).getHisInterview(new ProgressSubscriber(subscriber, mActivity),uid,1);
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
                    ExpertDataHttpRequest.getInstance(mActivity).getHisInterview(new ProgressSubscriber(subscriber, mActivity),uid,getCurrentPage());
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

    @OnClick({R.id.his_interview_ask, R.id.his_interview_interview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.his_interview_ask:
//                initAskDialog();
                EventBus.getDefault().post(new StartFragmentEvent(CreateDiscussFragment.newInstance(uid,discussPay)));
                break;
            case R.id.his_interview_interview:
                EventBus.getDefault().post(new StartFragmentEvent(CreateInterviewFragment.newInstance(uid,interviewPay)));
//                initInterviewDialog();
                break;
        }
    }

    /**
     * 向他提问
     */
    private void initAskDialog(){
        dialogPlus = DialogPlusUtils.Builder(mActivity)
                .setHolder(DialogPlusUtils.VIEW, new ViewHolder(R.layout.question_layout))
                .setTitleName("请输入您的问题")
                .setIsHeader(true)
                .setIsFooter(true)
                .setIsExpanded(false)
                .setCloseName("取消")
                .setOnCloseListener(new DialogPlusUtils.OnCloseListener() {
                    @Override
                    public void closeListener(DialogPlus dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .setConfirmName("确认")
                .setOnConfirmListener(new DialogPlusUtils.OnConfirmListener() {
                    @Override
                    public void confirmListener(DialogPlus dialog, View view) {

                        EditText editText = ((EditText) dialog.findViewById(R.id.dialog_question_edit));
                        discussContent = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(editText.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的内容不能为空");
                        } else {
                            DiscussDataHttpRequest.getInstance(mActivity).createDiscusses(
                                    new ProgressSubscriber(subscriberDiscussCode, mActivity)
                                    , discussContent, uid, uid
                            );
                            dialog.dismiss();
                        }
                    }
                })
                .setGravity(Gravity.BOTTOM)
                .showCompleteDialog();
    }
    /**
     * 约他访谈
     */
    private void initInterviewDialog() {
        dialogPlus = DialogPlusUtils.Builder(mActivity)
                .setHolder(DialogPlusUtils.VIEW, new ViewHolder(R.layout.interview_layout))
                .setIsHeader(false)
                .setIsFooter(true)
                .setIsExpanded(false)
                .setCloseName("取消")
                .setOnCloseListener(new DialogPlusUtils.OnCloseListener() {
                    @Override
                    public void closeListener(DialogPlus dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .setConfirmName("确认")
                .setOnConfirmListener(new DialogPlusUtils.OnConfirmListener() {
                    @Override
                    public void confirmListener(DialogPlus dialog, View view) {

                        EditText nickname = ((EditText) dialog.findViewById(R.id.dialog_interview_nickname));
                        EditText email = ((EditText) dialog.findViewById(R.id.dialog_interview_email));
                        EditText phone = ((EditText) dialog.findViewById(R.id.dialog_interview_phone));
                        EditText company = ((EditText) dialog.findViewById(R.id.dialog_interview_company));
                        EditText theme = ((EditText) dialog.findViewById(R.id.dialog_interview_theme));
                        EditText editor = ((EditText) dialog.findViewById(R.id.dialog_interview_edit));
                        if (TextUtils.isEmpty(nickname.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的姓名不能为空");
                        } else if (TextUtils.isEmpty(email.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的邮箱不能为空");
                        }else if (phone.getText().toString().trim().length() != 11){
                            ToastUtil.showToast(mActivity,"输入的手机不正确");
                        }else if (TextUtils.isEmpty(company.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的公司不能为空");
                        }else if (TextUtils.isEmpty(theme.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的主题不能为空");
                        }else if (TextUtils.isEmpty(editor.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的提纲不能为空");
                        }else {
                        }
                    }
                })
                .setGravity(Gravity.BOTTOM)
                .showCompleteDialog();
    }

    /***
     * 提问支付对话框
     */
    private void requestPaymentDiscussDialog() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Begin Payment Dialog");
                List<PaymentEntity> paymentEntities = new ArrayList<>();

                paymentEntities.add(new PaymentEntity(1, "支付宝", PaymentDataHttpRequest.TYPE_ALIPAY));
                paymentEntities.add(new PaymentEntity(2, "微信支付", PaymentDataHttpRequest.TYPE_WXPAY));
                PaymentSelectAdapter paymentDialogAdapter = new PaymentSelectAdapter(mActivity, paymentEntities);
                dialogPlus = DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.LIST, new ListHolder())
                        .setAdapter(paymentDialogAdapter)
                        .setTitleName("请选择支付方式")
                        .setIsHeader(true)
                        .setIsFooter(false)
                        .setIsExpanded(false)
                        .setGravity(Gravity.CENTER)
                        .showCompleteDialog();
                paymentDialogAdapter.setOnRecyclerViewItemOnClick(new RecyclerViewItemOnClick() {
                    @Override
                    public void ItemOnClick(int position, Object data) {
                        PaymentEntity select = (PaymentEntity) data;

                        if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_WXPAY)) {

                            PaymentDataHttpRequest.getInstance(mActivity).requestWxPayForDiscuss(new ProgressSubscriber(wxPaySubscriber, mActivity), createDiscussResponseEntity.getDiscussId(), uid, discussContent);
                        } else if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_ALIPAY)) {
                            PaymentDataHttpRequest.getInstance(mActivity).requestAliPayForDiscuss(new ProgressSubscriber(aliPaySubscriber, mActivity), createDiscussResponseEntity.getDiscussId(), uid, discussContent);

                        }

                    }

                    @Override
                    public void ItemOnClick(int position, Object data, boolean isSelect) {

                    }
                });
                dialogPlus.show();
            }
        });
    }

    /***
     * 访谈支付对话框
     */
    private void requestPaymentInterviewDialog() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Begin Payment Dialog");
                List<PaymentEntity> paymentEntities = new ArrayList<>();

                paymentEntities.add(new PaymentEntity(1, "支付宝", PaymentDataHttpRequest.TYPE_ALIPAY));
                paymentEntities.add(new PaymentEntity(2, "微信支付", PaymentDataHttpRequest.TYPE_WXPAY));
                PaymentSelectAdapter paymentDialogAdapter = new PaymentSelectAdapter(mActivity, paymentEntities);
                dialogPlus = DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.LIST, new ListHolder())
                        .setAdapter(paymentDialogAdapter)
                        .setTitleName("请选择支付方式")
                        .setIsHeader(true)
                        .setIsFooter(false)
                        .setIsExpanded(false)
                        .setGravity(Gravity.CENTER)
                        .showCompleteDialog();
                paymentDialogAdapter.setOnRecyclerViewItemOnClick(new RecyclerViewItemOnClick() {
                    @Override
                    public void ItemOnClick(int position, Object data) {
                        PaymentEntity select = (PaymentEntity) data;

                        if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_WXPAY)) {

                            PaymentDataHttpRequest.getInstance(mActivity).requestWxPayForDiscuss(new ProgressSubscriber(wxPaySubscriber, mActivity), createInterviewResponseEntity.getInterviewId(), uid, interviewContent);
                        } else if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_ALIPAY)) {
                            PaymentDataHttpRequest.getInstance(mActivity).requestAliPayForDiscuss(new ProgressSubscriber(aliPaySubscriber, mActivity), createInterviewResponseEntity.getInterviewId(), uid, interviewContent);

                        }

                    }

                    @Override
                    public void ItemOnClick(int position, Object data, boolean isSelect) {

                    }
                });
                dialogPlus.show();
            }
        });
    }

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_STREAMING_DISCUSS:
                    requestPaymentDiscussDialog();
                    break;
                case MSG_START_STREAMING_INTERVIEW:
                    requestPaymentInterviewDialog();
                default:
                    Log.e(TAG, "Invalid message");
                    break;
            }
        }
    };
}
