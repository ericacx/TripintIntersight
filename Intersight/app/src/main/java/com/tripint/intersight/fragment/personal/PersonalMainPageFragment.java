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
import com.tripint.intersight.fragment.create.CreateDiscussFragment;
import com.tripint.intersight.fragment.create.CreateInterviewFragment;
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
    private PersonalUserHomeEntity data;
    private PageDataSubscriberOnNext<PersonalUserHomeEntity> subscriber;

    private int uid = 0;
    private String nickname = null;

    private String discucssPay = null;
    private String interviewPay = null;
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

        discucssPay = data.getDiscussPay();
        interviewPay = data.getInterviewPay();
        personalMainPageName.setText(data.getNickname());//名字
        personalMainPageTitle.setText(data.getAbilityName());//职位
        personalMainPageCompany.setText(data.getCompanyName());//公司
        personalMainPageTrade.setText(data.getIndustryName());//行业
        personalMainPageExperience.setText(data.getExperience());//工作年限
        personalMainPageIntroduction.setText(data.getDesc());
        Glide.with(mActivity).load(data.getAvatar())//头像
                .crossFade()
                .fitCenter()
                .placeholder(R.mipmap.ic_avatar)
                .transform(new GlideCircleTransform(mActivity))
                .into(personalMainPagePersonalInfo);
        personalMainPageButtonAsk.setText("￥"+discucssPay+" 向他提问");
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
//                initAskDialog();

                EventBus.getDefault().post(new StartFragmentEvent(CreateDiscussFragment.newInstance(uid,discucssPay)));
                break;
            case R.id.personal_main_page_button_interview://约他访谈
//                initInterviewDialog();
                EventBus.getDefault().post(new StartFragmentEvent(CreateInterviewFragment.newInstance(uid,interviewPay)));
                break;
            case R.id.personal_main_page_personalInfo://他的个人信息
                break;
            case R.id.personal_main_page_askAnswer://他的问答
                nickname = data.getNickname();
                EventBus.getDefault().post(new StartFragmentEvent(HisAskAnswerFragment.newInstance(uid,nickname,discucssPay,interviewPay)));
                break;
            case R.id.personal_main_page_interview://他的访谈
                nickname = data.getNickname();
                EventBus.getDefault().post(new StartFragmentEvent(HisInterviewFragment.newInstance(uid,nickname,discucssPay,interviewPay)));
                break;
            case R.id.personal_main_page_opinion://他的观点
                nickname = data.getNickname();
                EventBus.getDefault().post(new StartFragmentEvent(HisOpinionFragment.newInstance(uid,nickname,discucssPay,interviewPay)));
                break;
        }
    }

}
