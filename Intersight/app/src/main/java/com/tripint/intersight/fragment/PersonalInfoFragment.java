package com.tripint.intersight.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.TakePhotoUtil;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.mine.worker.AllResoucesEntity;
import com.tripint.intersight.entity.mine.worker.EditUserEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑个人资料
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfoFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personal_info_submit)
    Button personalInfoSubmit;//提交

    @Bind(R.id.personal_info_next_one)
    ImageView personalInfoNextOne;
    @Bind(R.id.personal_info_avatar)
    CircleImageView personalInfoAvatar;
    @Bind(R.id.personal_info_rl_avatar)
    RelativeLayout personalInfoRlAvatar;//头像

    @Bind(R.id.personal_info_text_view_phone)
    TextView personalInfoTextViewPhone;
    @Bind(R.id.personal_info_phone)
    EditText personalInfoPhone;//手机

    @Bind(R.id.personal_info_textview_email)
    TextView personalInfoTextviewEmail;
    @Bind(R.id.personal_info_email)
    EditText personalInfoEmail;//邮箱

    @Bind(R.id.personal_info_textview_nickname)
    TextView personalInfoTextviewNickname;
    @Bind(R.id.personal_info_nickname)
    EditText personalInfoNickname;//昵称

    @Bind(R.id.personal_info_textview_companyname)
    TextView personalInfoTextviewCompanyname;
    @Bind(R.id.personal_info_company)
    EditText personalInfoCompany;//公司名称

    @Bind(R.id.personal_info_next)
    ImageView personalInfoNext;
    @Bind(R.id.personal_info_company_logo)
    CircleImageView personalInfoCompanyLogo;//公司logo

    @Bind(R.id.personal_info_trade_down)
    ImageView personalInfoTradeDown;
    @Bind(R.id.personal_info_trade)
    TextView personalInfoTrade;//行业

    @Bind(R.id.personal_info_title_down)
    ImageView personalInfoTitleDown;
    @Bind(R.id.personal_info_position)
    TextView personalInfoPosition;
    @Bind(R.id.personal_info_rl_position)
    RelativeLayout personalInfoRlPosition;//职能

    @Bind(R.id.personal_info_textview_title)
    TextView personalInfoTextviewTitle;
    @Bind(R.id.personal_info_title)
    EditText personalInfoTitle;//职位

    @Bind(R.id.personal_info_experience_down)
    ImageView personalInfoExperienceDown;
    @Bind(R.id.personal_info_experience)
    TextView personalInfoExperience;
    @Bind(R.id.personal_info_rl_experience)
    RelativeLayout personalInfoRlExperience;//工作年限

    @Bind(R.id.personal_info_personalInfo)
    EditText personalInfoPersonalInfo;//个人简介

    private CodeDataEntity codeDataEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriber;

    private PageDataSubscriberOnNext<AllResoucesEntity> subscriberAllResource;

    public static PersonalInfoFragment newInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();

        PersonalInfoFragment fragment = new PersonalInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        initToolbarNav(toolbar);
        toolbar.setTitle("个人资料");

        //完成按钮
        subscriber = new PageDataSubscriberOnNext<CodeDataEntity>() {
            @Override
            public void onNext(CodeDataEntity entity) {
                codeDataEntity = entity;
                pop();
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.personal_info_submit, R.id.personal_info_rl_avatar, R.id.personal_info_rl_position, R.id.personal_info_rl_experience})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_info_submit://提交
//                if (){
//
//                }else if (){
//
//                } else {
//                    int type = 0;
//                    EditUserEntity editUserEntity = new EditUserEntity();
//                    MineDataHttpRequest
//                            .getInstance(mActivity)
//                            .postEditUser(new ProgressSubscriber<CodeDataEntity>(subscriber,mActivity),editUserEntity);
//                }
                break;
            case R.id.personal_info_rl_avatar://头像
                TakePhotoUtil.showDialog(this);
                break;
            case R.id.personal_info_rl_position://职能
                break;
            case R.id.personal_info_rl_experience://工作年限
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = TakePhotoUtil.dealActivityResult(this, requestCode, resultCode, data, true);
        personalInfoAvatar.setImageBitmap(bitmap);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
