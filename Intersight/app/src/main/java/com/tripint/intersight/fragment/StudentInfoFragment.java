package com.tripint.intersight.fragment;


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
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentInfoFragment extends BaseBackFragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personal_info_student_submit)
    Button personalInfoStudentSubmit;
    @Bind(R.id.personal_info_next_one)
    ImageView personalInfoNextOne;
    @Bind(R.id.personal_info_student_avatar)
    CircleImageView personalInfoStudentAvatar;
    @Bind(R.id.personal_info_rl_avatar)
    RelativeLayout personalInfoRlAvatar;
    @Bind(R.id.personal_info_text_view_phone)
    TextView personalInfoTextViewPhone;
    @Bind(R.id.personal_info_phone)
    EditText personalInfoPhone;
    @Bind(R.id.personal_info_textview_email)
    TextView personalInfoTextviewEmail;
    @Bind(R.id.personal_info_email)
    EditText personalInfoEmail;
    @Bind(R.id.personal_info_textview_nickname)
    TextView personalInfoTextviewNickname;
    @Bind(R.id.personal_info_nickname)
    EditText personalInfoNickname;
    @Bind(R.id.personal_info_textview_university_name)
    TextView personalInfoTextviewUniversityName;
    @Bind(R.id.personal_info_company)
    EditText personalInfoCompany;
    @Bind(R.id.personal_info_next)
    ImageView personalInfoNext;
    @Bind(R.id.personal_info_university_logo)
    CircleImageView personalInfoUniversityLogo;
    @Bind(R.id.personal_info_trade_down)
    ImageView personalInfoTradeDown;
    @Bind(R.id.personal_info_specialty)
    TextView personalInfoSpecialty;
    @Bind(R.id.personal_info_textview_title)
    ImageView personalInfoTextviewTitle;
    @Bind(R.id.personal_info_diplomas)
    TextView personalInfoDiplomas;
    @Bind(R.id.personal_info_personalInfo)
    EditText personalInfoPersonalInfo;

    private CodeDataEntity codeDataEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriber;

    public static StudentInfoFragment newInstance() {
        Bundle args = new Bundle();

        StudentInfoFragment fragment = new StudentInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);
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

    @OnClick({R.id.personal_info_student_submit, R.id.personal_info_rl_avatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_info_student_submit:
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
            case R.id.personal_info_rl_avatar:
                break;
        }
    }
}
