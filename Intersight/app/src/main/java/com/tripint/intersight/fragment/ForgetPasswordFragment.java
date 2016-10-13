package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.user.User;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.forgetpassword_et_hone)
    EditText forgetpasswordEtHone;//输入的手机号或者邮箱
    @Bind(R.id.forgetpassword_btn_code)
    Button forgetpasswordBtnCode;//发送验证码
    @Bind(R.id.forgetpassword_et_inputcode)
    EditText forgetpasswordEtInputcode;//输入验证码
    @Bind(R.id.forgetpassword_et_newpassword)
    EditText forgetpasswordEtNewpassword;//输入新密码
    @Bind(R.id.forgetpassword_et_twice_newpassword)
    EditText forgetpasswordEtTwiceNewpassword;//再次输入新密码
    @Bind(R.id.forgetpassword_btn_submit)
    Button forgetpasswordBtnSubmit;//提交按钮


    private int time = 60;

    private CodeDataEntity codeDataEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriberCode;

    private PageDataSubscriberOnNext<CodeDataEntity> subscriber;

    public static ForgetPasswordFragment newInstance() {

        Bundle args = new Bundle();

        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        ButterKnife.bind(this, view);
        initView();//初始化页面
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

        //重置密码
        subscriber = new PageDataSubscriberOnNext<CodeDataEntity>() {
            @Override
            public void onNext(CodeDataEntity entity) {
                //接口请求成功后处理
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
                        forgetpasswordBtnCode.setClickable(true);
                        forgetpasswordBtnCode.setText("获取验证码");
                    } else {
                        forgetpasswordBtnCode.setText("重新获取(" + (--time) + ")");
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

    @OnClick({R.id.forgetpassword_btn_code, R.id.forgetpassword_btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgetpassword_btn_code:
                if (forgetpasswordEtHone.getText().toString().length() != 11) {
                    ToastUtil.showToast(mActivity, "请输入正确的手机号");
                } else {
                    //发送验证码请求
                    BaseDataHttpRequest.getInstance(mActivity).getCode(
                            new ProgressSubscriber(subscriberCode, mActivity)
                            , forgetpasswordEtHone.getText().toString().trim());
                    forgetpasswordBtnCode.setText("重新获取(" + time + ")");
                    forgetpasswordBtnCode.setClickable(false);
                    handler.sendEmptyMessageDelayed(100, 1000);
                }
                break;
            case R.id.forgetpassword_btn_submit:

                if (forgetpasswordEtHone.getText().toString().trim().length() != 11) {
                    ToastUtil.showToast(mActivity, "请输入正确的手机号");
                } else if (TextUtils.isEmpty(forgetpasswordEtNewpassword.getText().toString().trim())) {
                    ToastUtil.showToast(mActivity, "创建的新密码不能为空");
                } else if (TextUtils.isEmpty(forgetpasswordEtTwiceNewpassword.getText().toString().trim())) {
                    ToastUtil.showToast(mActivity, "再次输入的新密码不能为空");
                } else if (!(forgetpasswordEtNewpassword.getText().toString().trim().
                        equals(forgetpasswordEtTwiceNewpassword.getText().toString().trim()))) {
                    ToastUtil.showToast(mActivity, "两次输入密码不一致,请重新输入");
                } else if (TextUtils.isEmpty(forgetpasswordEtInputcode.getText().toString().trim())) {
                    ToastUtil.showToast(mActivity, "输入的验证码不能为空");
                } else if (forgetpasswordEtNewpassword.getText().toString().trim().length() < 6 ||
                        forgetpasswordEtNewpassword.getText().toString().trim().length() > 16) {
                    ToastUtil.showToast(mActivity, "请输入6~16位的字符或者数字作为密码");
                } else {
                    User user = new User(forgetpasswordEtHone.getText().toString().trim(),
                            forgetpasswordEtNewpassword.getText().toString().trim(),
                            Integer.parseInt(forgetpasswordEtInputcode.getText().toString().trim()));
                    BaseDataHttpRequest.getInstance(mActivity).postResetpassword(
                            new ProgressSubscriber<CodeDataEntity>(subscriber,mActivity)
                            ,user
                    );
                }
                break;
        }
    }
}
