package com.tripint.intersight.fragment.mine.setting;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置----帐号信息---绑定邮箱
 * A simple {@link Fragment} subclass.
 */
public class BindEmailFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bind_email_image)
    ImageView bindEmailImage;
    @Bind(R.id.bind_email_textview)
    TextView bindEmailTextview;
    @Bind(R.id.bind_email_et_phoneNumber)
    EditText bindEmailEtPhoneNumber;
    @Bind(R.id.bind_email_btn_verifyCode)
    Button bindEmailBtnVerifyCode;
    @Bind(R.id.bind_email_et_input_verifyCode)
    EditText bindEmailEtInputVerifyCode;
    @Bind(R.id.bind_email_complete)
    Button bindEmailComplete;

    private int time = 60;
    private CodeDataEntity codeDataEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriberCode;

    private PageDataSubscriberOnNext<CodeDataEntity> subscriber;

    public static BindEmailFragment newInstance() {

        Bundle args = new Bundle();

        BindEmailFragment fragment = new BindEmailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bind_email, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        initToolbarNav(toolbar);

        //验证码
        subscriberCode = new PageDataSubscriberOnNext<CodeDataEntity>() {
            @Override
            public void onNext(CodeDataEntity entity) {
                //接口请求成功后处理
                codeDataEntity = entity;
                Log.e("aaa", entity.getFlg());
            }
        };

        //完成按钮
        subscriber = new PageDataSubscriberOnNext<CodeDataEntity>() {
            @Override
            public void onNext(CodeDataEntity entity) {
                codeDataEntity = entity;
                pop();
            }
        };
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (time == 0) {
                        bindEmailBtnVerifyCode.setClickable(true);
                        bindEmailBtnVerifyCode.setText("获取验证码");
                    } else {
                        bindEmailBtnVerifyCode.setText("重新获取(" + (--time) + ")");
                        handler.sendEmptyMessageDelayed(100, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.bind_email_btn_verifyCode, R.id.bind_email_complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_email_btn_verifyCode:
                if (bindEmailEtPhoneNumber.getText().toString().length() == 0) {
                    ToastUtil.showToast(mActivity, "请输入正确的邮箱");
                } else {
                    //发送验证码请求
                    MineDataHttpRequest.getInstance(mActivity).getEmailCode(
                            new ProgressSubscriber(subscriberCode, mActivity)
                            , bindEmailEtPhoneNumber.getText().toString().trim());
                    bindEmailBtnVerifyCode.setText("重新获取(" + time + ")");
                    bindEmailBtnVerifyCode.setClickable(false);
                    handler.sendEmptyMessageDelayed(100, 1000);
                }
                break;
            case R.id.bind_email_complete://发送完成请求
                if (bindEmailEtPhoneNumber.getText().toString().trim().length() == 0) {
                    ToastUtil.showToast(mActivity, "请输入正确的邮箱");
                } else if (TextUtils.isEmpty(bindEmailEtInputVerifyCode.getText().toString().trim())) {
                    ToastUtil.showToast(mActivity, "输入的验证码不能为空");
                } else {
                    MineDataHttpRequest.getInstance(mActivity).postChangeEmail(
                            new ProgressSubscriber<CodeDataEntity>(subscriber, mActivity)
                            , bindEmailEtPhoneNumber.getText().toString().trim(),
                            Integer.parseInt(bindEmailEtInputVerifyCode.getText().toString().trim())
                    );
                }
                break;
        }
    }
}
