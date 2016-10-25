package com.tripint.intersight.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.FocusTradeActivity;
import com.tripint.intersight.activity.InterestedActivity;
import com.tripint.intersight.activity.MainActivity;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.entity.common.CommonResponEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.ShareLoginModel;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第三方便当---绑定手机
 * A simple {@link Fragment} subclass.
 */
public class LongBindPhoneFragment extends BaseBackFragment {

    public static final String TAG = "InterSight";
    public static final String SHARE_LOGIN_MODEL = "share_login_model";


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
    @Bind(R.id.bind_phone_et_input_password)
    EditText bindPhoneEtInputPassword;
    @Bind(R.id.bind_phone_et_input_re_password)
    EditText bindPhoneEtInputRePassword;
    @Bind(R.id.container_password_input)
    LinearLayout containerPasswordInput;

    private int time = 60;
    private CommonResponEntity codeDataEntity;
    private PageDataSubscriberOnNext<CommonResponEntity> subscriberCode;

    private ShareLoginModel shareLoginModel;

    private boolean isUserExist;

    public static LongBindPhoneFragment newInstance(ShareLoginModel model) {

        Bundle args = new Bundle();
        args.putSerializable(SHARE_LOGIN_MODEL, model);
        LongBindPhoneFragment fragment = new LongBindPhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.shareLoginModel = (ShareLoginModel) bundle.getSerializable(SHARE_LOGIN_MODEL);
        }
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
        subscriberCode = new PageDataSubscriberOnNext<CommonResponEntity>() {
            @Override
            public void onNext(CommonResponEntity entity) {
                //接口请求成功后处理
                codeDataEntity = entity;
                Log.d(TAG, entity.getFlg());
                //当前手机号已经是注册用户，隐藏密码设置内容，否则显示密码提示用户输入
                if (BaseDataHttpRequest.RESPONSE_STATUS_USER_EXIST == codeDataEntity.getStatus()) {
                    containerPasswordInput.setVisibility(View.GONE);
                    isUserExist = true;
                } else if (BaseDataHttpRequest.RESPONSE_STATUS_USER_NOT_EXIST == codeDataEntity.getStatus()) {
                    containerPasswordInput.setVisibility(View.VISIBLE);
                    isUserExist = false;
                } else {

                    ACache.get(mActivity).put(EnumKey.User.USER_TOKEN, entity.getToken());
                    Log.e("login", entity.getToken());
                    int status = entity.getStatus();
                    Intent intent = new Intent();
                    if (status == 100) {
                        intent.setClass(mActivity, InterestedActivity.class);
                        startActivity(intent);
                    } else if (status == 101) {
                        intent.setClass(mActivity, FocusTradeActivity.class);
                        startActivity(intent);
                    } else if (status == 102) {
                        intent.setClass(mActivity, MainActivity.class);
                        startActivity(intent);
                    } else {
                        intent.setClass(mActivity, MainActivity.class);
                        startActivity(intent);
                    }
                }
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
                    BaseDataHttpRequest.getInstance(mActivity).checkUserPhoneExist(
                            new ProgressSubscriber(subscriberCode, mActivity)
                            , bindPhoneEtPhoneNumber.getText().toString().trim());
                    bindPhoneBtnVerifyCode.setText("重新获取(" + time + ")");
                    bindPhoneBtnVerifyCode.setClickable(false);
                    handler.sendEmptyMessageDelayed(100, 1000);
                }
                break;
            case R.id.bind_phone_complete://完成
                if (checkUserInput(isUserExist)) {
                    if (isUserExist) {
                        //用户已经存在，输入短信验证码，并绑定用户
                        BaseDataHttpRequest.getInstance(mActivity).shareLogin(
                                new ProgressSubscriber(subscriberCode, mActivity)
                                , shareLoginModel.getShareLoginType(), shareLoginModel.getOpenId(),
                                shareLoginModel.getUnionId(), shareLoginModel.getImgUrl(), shareLoginModel.getNickName(),
                                bindPhoneEtPhoneNumber.getText().toString().trim(),
                                bindPhoneEtInputVerifyCode.getText().toString(), "", "mobileUnregister");
                    } else {
                        //用户已经存在，输入短信验证码，并绑定用户
                        BaseDataHttpRequest.getInstance(mActivity).shareLogin(
                                new ProgressSubscriber(subscriberCode, mActivity)
                                , shareLoginModel.getShareLoginType(), shareLoginModel.getOpenId(),
                                shareLoginModel.getUnionId(), shareLoginModel.getImgUrl(), shareLoginModel.getNickName(),
                                bindPhoneEtPhoneNumber.getText().toString().trim(),
                                bindPhoneEtInputVerifyCode.getText().toString().trim(),
                                bindPhoneEtInputPassword.getText().toString().trim(), "mobileRegister");
                    }
                }
                break;
        }
    }

    private boolean checkUserInput(boolean isUserExist) {
        if (bindPhoneEtPhoneNumber.getText().toString().length() != 11) {
            ToastUtil.showToast(mActivity, "请输入正确的手机号");
            return false;
        }
        if (bindPhoneEtInputVerifyCode.getText().toString().length() != 4) {
            ToastUtil.showToast(mActivity, "请输入正确的验证码");
            return false;
        }
        if (!isUserExist) {
            if (bindPhoneEtInputPassword.getText().toString().length() < 6 || bindPhoneEtInputPassword.getText().toString().length() > 16) {
                ToastUtil.showToast(mActivity, "密码必须大于6位");
                return false;
            }
            if (bindPhoneEtInputRePassword.getText().toString().length() < 6 || bindPhoneEtInputRePassword.getText().toString().length() > 16) {
                ToastUtil.showToast(mActivity, "重复密码必须大于6位");
                return false;
            }
            if (!bindPhoneEtInputRePassword.getText().toString().trim().equals(bindPhoneEtInputRePassword.getText().toString().trim())) {
                ToastUtil.showToast(mActivity, "两次密码不一致，请确认");
                return false;
            }
        }
        return true;
    }
}
