package com.tripint.intersight.fragment.personal;


import android.os.Bundle;
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
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.PersonalUserInfoEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.mine.MyInterviewDetailFragment;
import com.tripint.intersight.helper.AliPayUtils;
import com.tripint.intersight.helper.PayUtils;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.service.ExpertDataHttpRequest;
import com.tripint.intersight.service.MineDataHttpRequest;
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

    private int uid = 0;
    List<MineMultipleItemModel> models = new ArrayList<>();
    private MineCommonMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<BasePageableResponse<InterviewEntity>> subscriber;
    private BasePageableResponse<InterviewEntity> data = new BasePageableResponse<InterviewEntity>();

    private CodeDataEntity codeDataEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriberCode;
    private PageDataSubscriberOnNext<WXPayResponseEntity> paymentSubscriber;
    public static HisInterviewFragment newInstance(int uid) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, uid);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_his_interview, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        httpRequestData();
        return view;
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("他的访谈");
    }

    private void httpRequestData() {

        paymentSubscriber = new PageDataSubscriberOnNext<WXPayResponseEntity>() {
            @Override
            public void onNext(WXPayResponseEntity entity) {
                //接口请求成功后处理,调起微信支付。
                PayUtils.getInstant().requestWXpay(entity);
//
            }
        };

        subscriberCode = new PageDataSubscriberOnNext<CodeDataEntity>() {
            @Override
            public void onNext(CodeDataEntity entity) {
                codeDataEntity = entity;
                initPaymentDialog();
                Log.e("interview",entity.getFlg());
            }
        };

        subscriber = new PageDataSubscriberOnNext<BasePageableResponse<InterviewEntity>>() {
            @Override
            public void onNext(BasePageableResponse<InterviewEntity> entity) {
                //接口请求成功后处理
                data = entity;
                initView(null);
                initAdapter();
            }
        };

        ExpertDataHttpRequest.getInstance(mActivity).getHisInterview(new ProgressSubscriber(subscriber, mActivity), 26,1);
    }


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
                MineMultipleItemModel entity = (MineMultipleItemModel) adapter.getItem(position);
                EventBus.getDefault().post(new StartFragmentEvent(MyInterviewDetailFragment.newInstance(entity.getInterviewEntity())));
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
        int type = MineMultipleItemModel.HIS_INTERVIEW;
        for (InterviewEntity entiry : data.getLists()) {
            models.add(new MineMultipleItemModel(type, entiry));
        }
    }


    @OnClick({R.id.his_interview_ask, R.id.his_interview_interview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.his_interview_ask:
                initAskDialog();
                break;
            case R.id.his_interview_interview:
                initInterviewDialog();
                break;
        }
    }

    //向他提问
    private void initAskDialog() {
        final DialogPlus dialogPlus = DialogPlusUtils.Builder(mActivity)
                .setHolder(DialogPlusUtils.VIEW, new ViewHolder(R.layout.question_layout))
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

                        EditText editText = ((EditText) dialog.findViewById(R.id.dialog_question_edit));
                        if (TextUtils.isEmpty(editText.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的内容不能为空");
                        } else {
                            MineDataHttpRequest.getInstance(mActivity).postOtherQuestion(
                                    new ProgressSubscriber(subscriberCode, mActivity)
                                    ,uid,editText.getText().toString().trim()
                            );
                            dialog.dismiss();
                        }
                    }
                })
                .setGravity(Gravity.BOTTOM)
                .showCompleteDialog();
    }

    //约他访谈
    private void initInterviewDialog() {
        final DialogPlus dialog = DialogPlusUtils.Builder(mActivity)
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

                        EditText contact = ((EditText) dialog.findViewById(R.id.dialog_interview_nickname));
                        EditText email = ((EditText) dialog.findViewById(R.id.dialog_interview_email));
                        EditText phone = ((EditText) dialog.findViewById(R.id.dialog_interview_phone));
                        EditText company = ((EditText) dialog.findViewById(R.id.dialog_interview_company));
                        EditText subject = ((EditText) dialog.findViewById(R.id.dialog_interview_theme));
                        EditText outline = ((EditText) dialog.findViewById(R.id.dialog_interview_edit));
                        if (TextUtils.isEmpty(contact.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的姓名不能为空");
                        } else if (TextUtils.isEmpty(email.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的邮箱不能为空");
                        }else if (phone.getText().toString().trim().length() != 11){
                            ToastUtil.showToast(mActivity,"输入的手机不正确");
                        }else if (TextUtils.isEmpty(company.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的公司不能为空");
                        }else if (TextUtils.isEmpty(subject.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的主题不能为空");
                        }else if (TextUtils.isEmpty(outline.getText().toString().trim())){
                            ToastUtil.showToast(mActivity,"输入的提纲不能为空");
                        }else {
                            PersonalUserInfoEntity personalUserInfoEntity = new PersonalUserInfoEntity(
                                    uid,contact.getText().toString().trim(),company.getText().toString().trim(),
                                    phone.getText().toString().trim(),email.getText().toString().trim(),
                                    subject.getText().toString().trim(),outline.getText().toString().trim()
                            );
                            MineDataHttpRequest.getInstance(mActivity).postOtherInterview(
                                    new ProgressSubscriber(subscriberCode, mActivity)
                                    ,personalUserInfoEntity
                            );
                            dialog.dismiss();
                        }
                    }
                })
                .setGravity(Gravity.BOTTOM)
                .showCompleteDialog();
    }

    //支付对话框
    private void initPaymentDialog() {
        List<PaymentEntity> paymentEntities = new ArrayList<>();

        paymentEntities.add(new PaymentEntity(1, "支付宝", PaymentDataHttpRequest.TYPE_ALIPAY));
        paymentEntities.add(new PaymentEntity(2, "微信支付", PaymentDataHttpRequest.TYPE_WXPAY));
        PaymentSelectAdapter paymentDialogAdapter = new PaymentSelectAdapter(mActivity, paymentEntities);
        final DialogPlus dialogPlus = DialogPlusUtils.Builder(mActivity)
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

                    PaymentDataHttpRequest.getInstance(mActivity).requestWxPay(new ProgressSubscriber(paymentSubscriber, mActivity));
                } else if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_ALIPAY)) {
                    AliPayUtils.getInstant(mActivity).payV2();
                }

            }

            @Override
            public void ItemOnClick(int position, Object data, boolean isSelect) {

            }
        });
    }
}
