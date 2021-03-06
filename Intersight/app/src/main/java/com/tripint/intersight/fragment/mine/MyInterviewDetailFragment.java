package com.tripint.intersight.fragment.mine;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageDetailCommentAdapter;
import com.tripint.intersight.adapter.PaymentSelectAdapter;
import com.tripint.intersight.adapter.listener.RecyclerViewItemOnClick;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.KeyboardUtils;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ListHolder;
import com.tripint.intersight.common.widget.dialogplus.OnClickListener;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemChildClickListener;
import com.tripint.intersight.entity.article.ArticleCommentEntity;
import com.tripint.intersight.entity.discuss.CommentEntity;
import com.tripint.intersight.entity.discuss.CommentResultEntity;
import com.tripint.intersight.entity.discuss.CreateDiscussResponseEntity;
import com.tripint.intersight.entity.discuss.CreateInterviewResponseEntity;
import com.tripint.intersight.entity.mine.InterviewDetailEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.payment.AliPayResponseEntity;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.create.CreateDiscussFragment;
import com.tripint.intersight.fragment.create.CreateInterviewFragment;
import com.tripint.intersight.helper.AliPayUtils;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.helper.PayUtils;
import com.tripint.intersight.service.CommonDataHttpRequest;
import com.tripint.intersight.service.DiscussDataHttpRequest;
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
 * \
 * 我的约访 我被约访 详情页面
 * A simple {@link Fragment} subclass.
 */
public class MyInterviewDetailFragment extends BaseBackFragment {

    public static final String ARG_INTERVIEW_Detail_ID = "arg_interview_detail_id";
    public static final String ARG_INTERVIEW_PAY_ID = "arg_interview_pay_id";
    public static final String ARG_DISCUSS_PAY_ID = "arg_discuss_pay_id";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.my_interview_status)
    TextView myInterviewStatus;
    @Bind(R.id.my_interview_type)
    TextView myInterviewType;
    @Bind(R.id.my_interview_code)
    TextView myInterviewCode;
    @Bind(R.id.my_interview_time)
    TextView myInterviewTime;//时间
    @Bind(R.id.my_interview_head)
    TextView myInterviewHead;//标题
    @Bind(R.id.my_interview_content)
    TextView myInterviewContent;//内容
    @Bind(R.id.my_interview_people)
    TextView myInterviewPeople;//约访人
    @Bind(R.id.my_interview_name)
    TextView myInterviewName;//名字
    @Bind(R.id.my_interview_company)
    TextView myInterviewCompany;//公司
    @Bind(R.id.my_interview_title)
    TextView myInterviewTitle;//职位

    @Bind(R.id.my_interview_detail_kefu)
    Button myInterviewDetailKefu;
    @Bind(R.id.my_interview_detail_twiceInterview)
    Button myInterviewDetailTwiceInterview;
    @Bind(R.id.my_interview_detail_ask)
    Button myInterviewDetailAsk;
    @Bind(R.id.my_interview_detail_recyclerView)
    RecyclerView recyclerViewAskAnswerComment;
    @Bind(R.id.text_view_comment_submit)
    TextView textViewCommentSubmit;
    @Bind(R.id.edit_user_comment_replay)
    EditText editUserCommentReplay;
    @Bind(R.id.user_replay_container)
    RelativeLayout userReplayContainer;

    private int mInterviewId;
    private int toUid;
    private int pid = 0;
    private int userId = 0;
    private PageDataSubscriberOnNext<InterviewDetailEntity> subscriber;
    private InterviewDetailEntity detailEntity;

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

    private String currentAction = "";
    private String discucssPay;
    private String interviewPay;
    private TextView email;
    private EditText input;
    private ArticleCommentEntity currentSubCommentEntity; //创建子摩评论

    private PageDataSubscriberOnNext<CommentResultEntity> putSubscriber;

    private AskAnswerPageDetailCommentAdapter mAdapter;

    public static MyInterviewDetailFragment newInstance(InterviewEntity entity) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putInt(MyInterviewDetailFragment.ARG_INTERVIEW_Detail_ID, entity.getId());
//        args.putString(MyInterviewDetailFragment.ARG_DISCUSS_PAY_ID,discucssPay);
//        args.putString(MyInterviewDetailFragment.ARG_INTERVIEW_PAY_ID,interviewPay);
        MyInterviewDetailFragment fragment = new MyInterviewDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mInterviewId = bundle.getInt(ARG_INTERVIEW_Detail_ID);
//            discucssPay = bundle.getString(ARG_DISCUSS_PAY_ID);
//            interviewPay = bundle.getString(ARG_INTERVIEW_PAY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_interview_detail, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        httpRequestData();
        return view;
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
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

        subscriber = new PageDataSubscriberOnNext<InterviewDetailEntity>() {
            @Override
            public void onNext(InterviewDetailEntity entity) {
                //接口请求成功后处理
                detailEntity = entity;
                initView(null);
                initCommentAdapter();
            }
        };

        MineDataHttpRequest.getInstance(mActivity).getInterviewDetail(new ProgressSubscriber(subscriber, mActivity), mInterviewId);
    }

    private void initView(View view) {
        if (detailEntity.getInterview() != null) {

            toUid = detailEntity.getInterview().getUserId();
            discucssPay = detailEntity.getInterview().getDiscussPay();
            interviewPay = detailEntity.getInterview().getInterviewPay();
            if (detailEntity.getInterview().getStatus() == 1) {
                toolbar.setTitle("我的约访");
                myInterviewPeople.setText("受访者:");
                myInterviewDetailTwiceInterview.setVisibility(View.VISIBLE);
            } else if (detailEntity.getInterview().getStatus() == 2) {
                toolbar.setTitle("我被约访");
                myInterviewPeople.setText("约访人:");
                myInterviewDetailTwiceInterview.setVisibility(View.GONE);
            }

            if (detailEntity.getInterview().getCustType() == 1) {
                myInterviewStatus.setText("联系中");
                myInterviewStatus.setTextColor(Color.RED);
            } else if (detailEntity.getInterview().getCustType() == 2) {
                myInterviewStatus.setText("访谈成功");
                myInterviewStatus.setTextColor(Color.WHITE);
            }

            if (detailEntity.getInterview().getCustomType() == 1) {
                myInterviewType.setText("电话访谈");
            } else if (detailEntity.getInterview().getCustomType() == 2) {
                myInterviewType.setText("面谈");
            }
            myInterviewCode.setText(detailEntity.getInterview().getInvitationCode() + "");//会议邀请码
            myInterviewTime.setText(detailEntity.getInterview().getInterviewTime());//访谈时间

            myInterviewHead.setText(detailEntity.getInterview().getTitle());//标题
            myInterviewContent.setText(detailEntity.getInterview().getContent());//内容
            myInterviewName.setText(detailEntity.getInterview().getUserNickname());//姓名
            myInterviewCompany.setText(detailEntity.getInterview().getUserCompany());//公司名
            myInterviewTitle.setText(detailEntity.getInterview().getUserAbility());//职位

        }

        inithttpPutRequestData();
    }

    private void initCommentAdapter() {

        mAdapter = new AskAnswerPageDetailCommentAdapter(detailEntity.getComments());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mAdapter.openLoadAnimation();
        recyclerViewAskAnswerComment.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                ArticleCommentEntity status = (ArticleCommentEntity) adapter.getItem(position);
                pid = ((ArticleCommentEntity) adapter.getItem(position)).getId();
                userId = ((ArticleCommentEntity) adapter.getItem(position)).getUserId();
                switch (view.getId()) {
                    case R.id.image_ask_profile:
                        content = "img:" + status.getContent();
                        break;
                    case R.id.textView_item_ask_action:
                        currentSubCommentEntity = status;
                        content = "name:" + status.getCreateAt();
                        KeyboardUtils.showSoftInput(mActivity, editUserCommentReplay);
                        break;
                }
            }
        });

        recyclerViewAskAnswerComment.setLayoutManager(layoutManager);
        recyclerViewAskAnswerComment.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({ R.id.my_interview_detail_kefu, R.id.my_interview_detail_twiceInterview, R.id.my_interview_detail_ask})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_interview_detail_kefu://客服
                initKefuDialog();
                break;
            case R.id.my_interview_detail_twiceInterview://再次约访
//                initInterviewDialog();
                EventBus.getDefault().post(new StartFragmentEvent(CreateInterviewFragment.newInstance(toUid, interviewPay)));
                break;
            case R.id.my_interview_detail_ask://向他提问
                EventBus.getDefault().post(new StartFragmentEvent(CreateDiscussFragment.newInstance(toUid, discucssPay)));
                break;
        }
    }

    /**
     * 联系客服
     */
    private void initKefuDialog() {
        dialogPlus = DialogPlusUtils.Builder(mActivity)
                .setHolder(DialogPlusUtils.VIEW, new ViewHolder(R.layout.kefu_layout))
                .setTitleName("联系客服")
                .setIsHeader(true)
                .setIsFooter(true)
                .setCloseName("取消")
                .setOnCloseListener(new DialogPlusUtils.OnCloseListener() {
                    @Override
                    public void closeListener(DialogPlus dialog, View view) {
                        dialogPlus.dismiss();
                    }
                })
                .setConfirmName("确定")
                .setOnConfirmListener(new DialogPlusUtils.OnConfirmListener() {
                    @Override
                    public void confirmListener(DialogPlus dialog, View view) {

//                        email = ((TextView) view.findViewById(R.id.kefu_email));
//                        input = ((EditText) view.findViewById(R.id.kefu_question_edit));
//
//                        Uri uri = Uri.parse("332045512@qq.com");
//                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
//                        startActivity(Intent.createChooser(intent,"选择邮箱客户端"));

                        dialogPlus.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .showCompleteDialog();
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
                                    , discussContent, mInterviewId, mInterviewId
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

                            PaymentDataHttpRequest.getInstance(mActivity).requestWxPayForDiscuss(new ProgressSubscriber(wxPaySubscriber, mActivity), createDiscussResponseEntity.getDiscussId(), detailEntity.getInterview().getUserId(), discussContent);
                        } else if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_ALIPAY)) {
                            PaymentDataHttpRequest.getInstance(mActivity).requestAliPayForDiscuss(new ProgressSubscriber(aliPaySubscriber, mActivity), createDiscussResponseEntity.getDiscussId(), detailEntity.getInterview().getUserId(), discussContent);

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

                            PaymentDataHttpRequest.getInstance(mActivity).requestWxPayForDiscuss(new ProgressSubscriber(wxPaySubscriber, mActivity), createInterviewResponseEntity.getInterviewId(), detailEntity.getInterview().getUserId(), interviewContent);
                        } else if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_ALIPAY)) {
                            PaymentDataHttpRequest.getInstance(mActivity).requestAliPayForDiscuss(new ProgressSubscriber(aliPaySubscriber, mActivity), createInterviewResponseEntity.getInterviewId(), detailEntity.getInterview().getUserId(), interviewContent);

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


    private void inithttpPutRequestData() {
        putSubscriber = new PageDataSubscriberOnNext<CommentResultEntity>() {
            @Override
            public void onNext(CommentResultEntity entity) {
                switch (currentAction) {

                    case DiscussDataHttpRequest.TYPE_COMMENT: //行业领域
                        CommonUtils.showToast("提交成功");
                        editUserCommentReplay.setText("");
                        httpRequestData();
                        break;
                    case DiscussDataHttpRequest.TYPE_SUB_COMMENT: //行业领域
                        CommonUtils.showToast("提交成功");
                        editUserCommentReplay.setText("");
                        httpRequestData();
                        break;
                }
            }
        };


    }

    @OnClick(R.id.text_view_comment_submit)
    public void onClick() {
        String content = editUserCommentReplay.getText().toString();
        if (StringUtils.isEmpty(content)) {
            CommonUtils.showToast("点评内容不能为空");
        } else {
            if (currentSubCommentEntity == null) {
                currentAction = DiscussDataHttpRequest.TYPE_COMMENT;
                CommonDataHttpRequest.getInstance(mActivity).createInterviewComment(new ProgressSubscriber(putSubscriber, mActivity), mInterviewId, toUid, content);
            } else {
                currentAction = DiscussDataHttpRequest.TYPE_SUB_COMMENT;
                CommonDataHttpRequest.getInstance(mActivity).createInterviewSubComment(new ProgressSubscriber(putSubscriber, mActivity),
                        mInterviewId, userId, content, pid);
            }
        }
    }
}
