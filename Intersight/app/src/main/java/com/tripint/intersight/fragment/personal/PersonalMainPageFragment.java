package com.tripint.intersight.fragment.personal;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.adapter.PaymentSelectAdapter;
import com.tripint.intersight.adapter.listener.RecyclerViewItemOnClick;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ListHolder;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.entity.PersonalUserInfoEntity;
import com.tripint.intersight.entity.discuss.CreateDiscussResponseEntity;
import com.tripint.intersight.entity.discuss.CreateInterviewResponseEntity;
import com.tripint.intersight.entity.mine.PersonalUserHomeEntity;
import com.tripint.intersight.entity.payment.AliPayResponseEntity;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.helper.AliPayUtils;
import com.tripint.intersight.helper.PayUtils;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.service.ExpertDataHttpRequest;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.service.PaymentDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人主页   ----  页面
 * A simple {@link Fragment} subclass.
 */
public class PersonalMainPageFragment extends BaseBackFragment {

    public static final String ARG_USER_ID = "arg_user_id";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personal_main_page_button_ask)
    Button personalMainPageButtonAsk;
    @Bind(R.id.personal_main_page_button_interview)
    Button personalMainPageButtonInterview;

    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.personal_main_page_personalInfo)
    CircleImageView personalMainPagePersonalInfo;
    @Bind(R.id.personal_main_page_name)
    TextView personalMainPageName;
    @Bind(R.id.personal_main_page_title)
    TextView personalMainPageTitle;//职位
    @Bind(R.id.personal_main_page_company)
    TextView personalMainPageCompany;//公司
    @Bind(R.id.personal_main_page_trade)
    TextView personalMainPageTrade;//行业
    @Bind(R.id.personal_main_page_experience)
    TextView personalMainPageExperience;//工作年限
    @Bind(R.id.personal_main_page_askAnswer)
    TextView personalMainPageAskAnswer;//问答
    @Bind(R.id.personal_main_page_interview)
    TextView personalMainPageInterview;//访谈
    @Bind(R.id.personal_main_page_opinion)
    TextView personalMainPageOpinion;//观点
    @Bind(R.id.personal_main_page_introduction)
    TextView personalMainPageIntroduction;//个人简介

    protected static final int MSG_START_STREAMING_DISCUSS = 0;
    protected static final int MSG_START_STREAMING_INTERVIEW = 1;

    private PersonalUserHomeEntity data;
    private PageDataSubscriberOnNext<PersonalUserHomeEntity> subscriber;

    private CreateDiscussResponseEntity createDiscussResponseEntity;
    private CreateInterviewResponseEntity createInterviewResponseEntity;

    private PageDataSubscriberOnNext<CreateDiscussResponseEntity> subscriberDiscussCode;
    private PageDataSubscriberOnNext<CreateInterviewResponseEntity> subscriberInterviewCode;
    private PageDataSubscriberOnNext<WXPayResponseEntity> wxPaySubscriber;
    private PageDataSubscriberOnNext<AliPayResponseEntity> aliPaySubscriber;

    private int uid = 0;

    private String discussContent;
    private String interviewContent = null;

    private DialogPlus dialogPlus;

    public static PersonalMainPageFragment newInstance(int uid) {
        Bundle args = new Bundle();
        PersonalMainPageFragment fragment = new PersonalMainPageFragment();
        args.putInt(ARG_USER_ID, uid);
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
        View view = inflater.inflate(R.layout.fragment_personal_main_page, container, false);
        ButterKnife.bind(this, view);

        initToolbarNav(toolbar);
        httpRequestData();
        return view;
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

        subscriber = new PageDataSubscriberOnNext<PersonalUserHomeEntity>() {
            @Override
            public void onNext(PersonalUserHomeEntity entity) {
                //接口请求成功后处理
                data = entity;
                initView(null);
                initToolbar();
            }
        };

        ExpertDataHttpRequest.getInstance(mActivity).getPersonalUserHome(new ProgressSubscriber(subscriber, mActivity),uid);
    }

    private void initView(View view) {
        personalMainPageName.setText(data.getNickname());//名字
        personalMainPageTitle.setText(data.getAbilityName());//职位
        personalMainPageCompany.setText(data.getCompanyName());//公司
        personalMainPageTrade.setText(data.getIndustryName());//行业
        personalMainPageExperience.setText(data.getExperience());//工作年限
        Glide.with(mActivity).load(data.getAvatar())//头像
                .crossFade()
                .fitCenter()
                .placeholder(R.mipmap.ic_avatar)
                .transform(new GlideCircleTransform(mActivity))
                .into(personalMainPagePersonalInfo);

    }
    private void initToolbar() {

        toolbar.setTitle(data.getNickname()+"的主页");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.personal_main_page_button_ask, R.id.personal_main_page_button_interview, R.id.personal_main_page_personalInfo
            ,R.id.personal_main_page_askAnswer, R.id.personal_main_page_interview, R.id.personal_main_page_opinion})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_main_page_button_ask://向他提问
                initAskDialog();
                break;
            case R.id.personal_main_page_button_interview://约他访谈
                initInterviewDialog();
                break;
            case R.id.personal_main_page_personalInfo://他的个人信息
                break;
            case R.id.personal_main_page_askAnswer://他的问答
                EventBus.getDefault().post(new StartFragmentEvent(HisAskAnswerFragment.newInstance(uid)));
                break;
            case R.id.personal_main_page_interview://他的访谈
                EventBus.getDefault().post(new StartFragmentEvent(HisInterviewFragment.newInstance(uid)));
                break;
            case R.id.personal_main_page_opinion://他的观点
                EventBus.getDefault().post(new StartFragmentEvent(HisOpinionFragment.newInstance(uid)));
                break;
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (dialogPlus != null) {
            if (dialogPlus.isShowing()) {
                dialogPlus.dismiss();
                return false;
            }
        }
        return super.onBackPressedSupport();

    }

    /**
     * 向他提问
     */
    private void initAskDialog() {
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
                        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                            ToastUtil.showToast(mActivity, "输入的内容不能为空");
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
                        if (TextUtils.isEmpty(nickname.getText().toString().trim())) {
                            ToastUtil.showToast(mActivity, "输入的姓名不能为空");
                        } else if (TextUtils.isEmpty(email.getText().toString().trim())) {
                            ToastUtil.showToast(mActivity, "输入的邮箱不能为空");
                        } else if (phone.getText().toString().trim().length() != 11) {
                            ToastUtil.showToast(mActivity, "输入的手机不正确");
                        } else if (TextUtils.isEmpty(company.getText().toString().trim())) {
                            ToastUtil.showToast(mActivity, "输入的公司不能为空");
                        } else if (TextUtils.isEmpty(theme.getText().toString().trim())) {
                            ToastUtil.showToast(mActivity, "输入的主题不能为空");
                        } else if (TextUtils.isEmpty(editor.getText().toString().trim())) {
                            ToastUtil.showToast(mActivity, "输入的提纲不能为空");
                        } else {
                            PersonalUserInfoEntity personalUserInfoEntity = new PersonalUserInfoEntity(
                                    uid, nickname.getText().toString().trim(), company.getText().toString().trim(),
                                    phone.getText().toString().trim(), email.getText().toString().trim(),
                                    theme.getText().toString().trim(), editor.getText().toString().trim()
                            );
                            MineDataHttpRequest.getInstance(mActivity).postOtherInterview(
                                    new ProgressSubscriber(subscriberInterviewCode, mActivity)
                                    , personalUserInfoEntity
                            );
                            dialog.dismiss();
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

                            PaymentDataHttpRequest.getInstance(mActivity).requestWxPayForDiscuss(new ProgressSubscriber(wxPaySubscriber, mActivity), createDiscussResponseEntity.getDiscussId(), discussContent);
                        } else if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_ALIPAY)) {
                            PaymentDataHttpRequest.getInstance(mActivity).requestAliPayForDiscuss(new ProgressSubscriber(aliPaySubscriber, mActivity), createDiscussResponseEntity.getDiscussId(), discussContent);

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

                            PaymentDataHttpRequest.getInstance(mActivity).requestWxPayForDiscuss(new ProgressSubscriber(wxPaySubscriber, mActivity), createInterviewResponseEntity.getInterviewId(), interviewContent);
                        } else if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_ALIPAY)) {
                            PaymentDataHttpRequest.getInstance(mActivity).requestAliPayForDiscuss(new ProgressSubscriber(aliPaySubscriber, mActivity), createInterviewResponseEntity.getInterviewId(), interviewContent);

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
