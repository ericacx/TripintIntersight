package com.tripint.intersight.fragment.create;


import android.os.Bundle;
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

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.PaymentSelectAdapter;
import com.tripint.intersight.adapter.listener.RecyclerViewItemOnClick;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ListHolder;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.common.widget.filter.adapter.SimpleTextAdapter;
import com.tripint.intersight.common.widget.filter.typeview.DoubleListView;
import com.tripint.intersight.common.widget.filter.util.CommonUtil;
import com.tripint.intersight.common.widget.filter.util.UIUtil;
import com.tripint.intersight.common.widget.filter.view.FilterCheckedTextView;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.IndustryChild;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.entity.discuss.CreateDiscussResponseEntity;
import com.tripint.intersight.entity.payment.AliPayResponseEntity;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.helper.AliPayUtils;
import com.tripint.intersight.helper.PayUtils;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.service.PaymentDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateDiscussFragment extends BaseBackFragment {

    public static final String ARG_USER_ID = "arg_user_id";
    //    protected static final int MSG_START_STREAMING_DISCUSS = 0;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.dialog_question_edit)
    EditText dialogQuestionEdit;
    @Bind(R.id.choose_industry)
    TextView chooseIndustry;
    @Bind(R.id.create_discuss_ll_choose)
    LinearLayout createDiscussLlChoose;
    @Bind(R.id.create_discuss_send)
    Button createDiscussSend;

    private String discussContent;
    private List<Industry> industies = new ArrayList<>(); //行业数据
    private int currentIndestry;//行业
    private DialogPlus dialogPlus;
    private int uid;
    private SearchFilterEntity searchFilterEntity; //搜索过滤条件数据
    private PageDataSubscriberOnNext<SearchFilterEntity> subscriber;
    private CreateDiscussResponseEntity createDiscussResponseEntity;
    private PageDataSubscriberOnNext<CreateDiscussResponseEntity> subscriberDiscussCode;
    private PageDataSubscriberOnNext<WXPayResponseEntity> wxPaySubscriber;
    private PageDataSubscriberOnNext<AliPayResponseEntity> aliPaySubscriber;

    //    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MSG_START_STREAMING_DISCUSS:
//                    requestPaymentDiscussDialog();
//                    break;
//                default:
//                    Log.e(TAG, "Invalid message");
//                    break;
//            }
//        }
//    };
    public static CreateDiscussFragment newInstance(int uid) {
        // Required empty public constructor
        CreateDiscussFragment fragment = new CreateDiscussFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_create_discuss, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
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
                createDiscussResponseEntity = entity;
//                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_START_STREAMING_DISCUSS), 500);
                requestPaymentDiscussDialog();
            }
        };

        subscriber = new PageDataSubscriberOnNext<SearchFilterEntity>() {
            @Override
            public void onNext(SearchFilterEntity entity) {
                //接口请求成功后处理
                searchFilterEntity = entity;
                industies = searchFilterEntity.getIndustry();
            }
        };

        BaseDataHttpRequest.getInstance(mActivity).getSearchFilterArticles(new ProgressSubscriber(subscriber, mActivity));
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("向他提问");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.create_discuss_ll_choose, R.id.create_discuss_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_discuss_ll_choose:
                dialogPlus = DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createDoubleListView()))
                        .setTitleName("请选择行业")
                        .setIsHeader(true)
                        .setIsFooter(false)
                        .setIsExpanded(false)
                        .setGravity(Gravity.TOP)
                        .showCompleteDialog();
                break;
            case R.id.create_discuss_send:
                discussContent = dialogQuestionEdit.getText().toString().trim();
                if (TextUtils.isEmpty(dialogQuestionEdit.getText().toString().trim())) {
                    ToastUtil.showToast(mActivity, "输入的内容不能为空");
                } else if (currentIndestry == 0) {
                    ToastUtil.showToast(mActivity, "请选择行业");
                } else {
                    DiscussDataHttpRequest.getInstance(mActivity).createDiscusses(
                            new ProgressSubscriber(subscriberDiscussCode, mActivity)
                            , discussContent, currentIndestry, uid
                    );
                }
                break;
        }

    }

    //行业
    private View createDoubleListView() {
        DoubleListView<Industry, IndustryChild> comTypeDoubleListView = new DoubleListView<Industry, IndustryChild>(mActivity)
                .leftAdapter(new SimpleTextAdapter<Industry>(null, mActivity) {
                    @Override
                    public String provideText(Industry Industry) {
                        return Industry.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<IndustryChild>(null, mActivity) {
                    @Override
                    public String provideText(IndustryChild s) {
                        return s.getName();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<Industry, IndustryChild>() {
                    @Override
                    public List<IndustryChild> provideRightList(Industry item, int position) {
                        List<IndustryChild> child = item.getIndustrySub();
                        if (CommonUtil.isEmpty(child)) {

//                            onFilterDone(0, item.getId(), item.getName());
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<Industry, IndustryChild>() {
                    @Override
                    public void onRightItemClick(Industry item, IndustryChild childItem) {
                        currentIndestry = childItem.getId();
                        chooseIndustry.setText(childItem.getName());
                        dialogPlus.dismiss();
//                        onFilterDone(0, childItem.getId(), childItem.getName());
                    }
                });


        //初始化选中.
        comTypeDoubleListView.setLeftList(industies, 1);
        comTypeDoubleListView.setRightList(industies.get(1).getIndustrySub(), -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mActivity.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
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

//                            PaymentDataHttpRequest.getInstance(mActivity).requestWxPayForDiscuss(new ProgressSubscriber(wxPaySubscriber, mActivity), createDiscussResponseEntity.getDiscussId(), discussContent);
                        } else if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_ALIPAY)) {
//                            PaymentDataHttpRequest.getInstance(mActivity).requestAliPayForDiscuss(new ProgressSubscriber(aliPaySubscriber, mActivity), createDiscussResponseEntity.getDiscussId(), discussContent);

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
}
