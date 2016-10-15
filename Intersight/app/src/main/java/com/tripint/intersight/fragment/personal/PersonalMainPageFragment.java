package com.tripint.intersight.fragment.personal;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
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
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.PersonalUserInfoEntity;
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

    private CodeDataEntity codeDataEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriberCode;

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

        subscriberCode = new PageDataSubscriberOnNext<CodeDataEntity>() {
            @Override
            public void onNext(CodeDataEntity entity) {
                codeDataEntity = entity;
            }
        };

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

                break;
            case R.id.personal_main_page_button_interview://约他访谈
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

                                EditText editText = ((EditText) dialog.findViewById(R.id.dialog_question_edit));
                                if (TextUtils.isEmpty(editText.getText().toString().trim())){
                                    ToastUtil.showToast(mActivity,"输入的内容不能为空");
                                } else {
                                    PersonalUserInfoEntity personalUserInfoEntity = new PersonalUserInfoEntity();
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
