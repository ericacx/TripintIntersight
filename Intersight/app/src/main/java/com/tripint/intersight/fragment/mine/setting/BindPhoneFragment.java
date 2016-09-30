package com.tripint.intersight.fragment.mine.setting;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置----帐号信息---绑定手机
 * A simple {@link Fragment} subclass.
 */
public class BindPhoneFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bind_phone_image)
    ImageView bindPhoneImage;
    @Bind(R.id.bind_phone_textview)
    TextView bindPhoneTextview;
    @Bind(R.id.bind_phone_et_phoneNumber)
    EditText bindPhoneEtPhoneNumber;
    @Bind(R.id.bind_phone_btn_verifyCode)
    Button bindPhoneBtnVerifyCode;
    @Bind(R.id.bind_phone_et_input_verifyCode)
    EditText bindPhoneEtInputVerifyCode;
    @Bind(R.id.bind_phone_complete)
    Button bindPhoneComplete;

    private int time = 60;
    private CodeDataEntity codeDataEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriberCode;

    public static BindPhoneFragment newInstance() {

        Bundle args = new Bundle();

        BindPhoneFragment fragment = new BindPhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bind_phone, container, false);
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
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (time == 0) {
                        time = 10;
                        bindPhoneBtnVerifyCode.setClickable(true);
                        bindPhoneBtnVerifyCode.setText("获取验证码");
                    } else {
                        bindPhoneBtnVerifyCode.setText("重新获取(" + (--time) + ")");
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

    @OnClick({R.id.bind_phone_btn_verifyCode, R.id.bind_phone_complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_phone_btn_verifyCode://发送验证码
                if (bindPhoneEtPhoneNumber.getText().toString().length() != 11) {
                    ToastUtil.showToast(mActivity, "请输入正确的手机号");
                } else {
                    //发送验证码请求
                    BaseDataHttpRequest.getInstance(mActivity).getCode(
                            new ProgressSubscriber(subscriberCode, mActivity)
                            , bindPhoneEtPhoneNumber.getText().toString().trim());
                    bindPhoneBtnVerifyCode.setText("重新获取(" + time + ")");
                    bindPhoneBtnVerifyCode.setClickable(false);
                    handler.sendEmptyMessageDelayed(100, 1000);
                }
                break;
            case R.id.bind_phone_complete://完成
                break;
        }
    }
}
