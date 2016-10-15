package com.tripint.intersight.fragment.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.entity.mine.PersonalUserHomeEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.PersonalInfoFragment;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人主页   ----  页面
 * A simple {@link Fragment} subclass.
 */
public class PersonalMainPageFragment extends BaseBackFragment {

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
    @Bind(R.id.scrollview)
    NestedScrollView scrollview;

    private PersonalUserHomeEntity data;
    private PageDataSubscriberOnNext<PersonalUserHomeEntity> subscriber;

    private int uid = 25;

    public static PersonalMainPageFragment newInstance() {
        Bundle args = new Bundle();
        PersonalMainPageFragment fragment = new PersonalMainPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_main_page, container, false);
        ButterKnife.bind(this, view);
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

            }
        };

        MineDataHttpRequest.getInstance(mActivity).getPersonalUserHome(new ProgressSubscriber(subscriber, mActivity),uid);
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
                .placeholder(R.drawable.loading_normal_icon)
                .transform(new GlideCircleTransform(mActivity))
                .into(personalMainPagePersonalInfo);

        initToolbar();
    }
    private void initToolbar() {
        initToolbarNav(toolbar);
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
                break;
            case R.id.personal_main_page_button_interview://约他访谈
                break;
            case R.id.personal_main_page_personalInfo://他的个人信息
                break;
            case R.id.personal_main_page_askAnswer://他的问答
                EventBus.getDefault().post(new StartFragmentEvent(HisAskAnswerFragment.newInstance()));
                break;
            case R.id.personal_main_page_interview://他的访谈
                EventBus.getDefault().post(new StartFragmentEvent(HisInterviewFragment.newInstance()));
                break;
            case R.id.personal_main_page_opinion://他的观点
                EventBus.getDefault().post(new StartFragmentEvent(HisOpinionFragment.newInstance()));
                break;
        }
    }

}
