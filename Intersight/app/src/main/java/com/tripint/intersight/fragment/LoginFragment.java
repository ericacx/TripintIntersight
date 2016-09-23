package com.tripint.intersight.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.ForgetPasswordActivity;
import com.tripint.intersight.activity.MainActivity;
import com.tripint.intersight.activity.ResigterActivity;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.fragment.base.BaseCloseFragment;
import com.tripint.intersight.helper.CommonUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends BaseCloseFragment {

    @Bind(R.id.login_et_username)
    EditText login_et_username;//用户名
    @Bind(R.id.login_et_password)
    EditText login_et_password;//密码
    @Bind(R.id.login_forget_pwd)
    TextView login_forget_pwd;//忘记密码
    @Bind(R.id.login_button_login)
    Button login_button_login;//登录按钮
    @Bind(R.id.login_button_register)
    Button login_button_register;//注册按钮
    @Bind(R.id.login_thirdLogin_linkedin)
    ImageView login_thirdLogin_linkedin;//领英登录
    @Bind(R.id.login_thirdLogin_wechat)
    ImageView login_thirdLogin_wechat;//微信登录
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    UMShareAPI mShareAPI;


    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        ButterKnife.bind(this, view);
        ACache.get(mActivity);
        initView();//初始化页面
        return view;
    }

    private void initView() {

        initToolbarNav(toolbar);

    }


    @OnClick({R.id.login_forget_pwd, R.id.login_button_login, R.id.login_button_register, R.id.login_thirdLogin_linkedin, R.id.login_thirdLogin_wechat})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.login_forget_pwd:
                intent.setClass(mActivity, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.login_button_login:
                intent.setClass(mActivity, MainActivity.class);
//                startActivity(intent);
                Bundle bundle = new Bundle();

                setFramgentResult(RESULT_OK, bundle);
                break;
            case R.id.login_button_register:
                intent.setClass(mActivity, ResigterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_thirdLogin_linkedin:
                break;
            case R.id.login_thirdLogin_wechat:
                SHARE_MEDIA platform = SHARE_MEDIA.WEIXIN;
                sharedLogin(platform);
                break;
        }
    }


    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            CommonUtils.showToast("Authorize succeed");
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            CommonUtils.showToast("Authorize failed");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            CommonUtils.showToast("Authorize cancel");
        }
    };

    protected void sharedLogin(SHARE_MEDIA platform) {
        mShareAPI = UMShareAPI.get(mActivity);

        mShareAPI.doOauthVerify(mActivity, platform, umAuthListener);




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
