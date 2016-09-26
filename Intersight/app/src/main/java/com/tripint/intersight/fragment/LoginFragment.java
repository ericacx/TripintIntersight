package com.tripint.intersight.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.tripint.intersight.R;
import com.tripint.intersight.activity.ForgetPasswordActivity;
import com.tripint.intersight.activity.LoginActivity;
import com.tripint.intersight.activity.MainActivity;
import com.tripint.intersight.activity.ResigterActivity;
import com.tripint.intersight.activity.base.BaseActivity;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;
import com.tripint.intersight.entity.user.LoginEntity;
import com.tripint.intersight.entity.user.User;
import com.tripint.intersight.fragment.base.BaseCloseFragment;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.helper.ProgressDialogUtils;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;


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


    private LoginActivity mContext;

    private Context mAppContext;

    private HashMap<String, String> params = new HashMap<String, String>();

    private LoginEntity.UserInfoBean loginEntity;
    private PageDataSubscriberOnNext<LoginEntity.UserInfoBean> subscriber;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof BaseActivity) {
            mContext = (LoginActivity) getActivity();
        }
        mAppContext = InterSightApp.getApp().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        ButterKnife.bind(this, view);
        ACache.get(mContext);
        initView();//初始化页面
        return view;
    }

    private void initView() {

        initToolbarNav(toolbar);

        //验证码
        subscriber = new PageDataSubscriberOnNext<LoginEntity.UserInfoBean>() {
            @Override
            public void onNext(LoginEntity.UserInfoBean entity) {
                //接口请求成功后处理
                loginEntity = entity;
                Log.e("aaa",entity.getToke());
                ACache.get(mActivity).put(EnumKey.User.USER_TOKEN, entity.getToke());
                Intent intent = new Intent();
                intent.setClass(mContext, MainActivity.class);
                startActivity(intent);
            }
        };
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

                User user = new User(login_et_username.getText().toString().trim(),login_et_password.getText().toString().trim());

                BaseDataHttpRequest.getInstance(mActivity).postLogin(
                        new ProgressSubscriber(subscriber, mContext), user);

                intent = new Intent();
                intent.setClass(mContext,MainActivity.class);
                startActivity(intent);
//                Bundle bundle = new Bundle();
//                setFramgentResult(RESULT_OK, bundle);

                break;
            case R.id.login_button_register:
                intent.setClass(mActivity, ResigterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_thirdLogin_linkedin:
                sharedLinkedInLogin();
                break;
            case R.id.login_thirdLogin_wechat:
                SHARE_MEDIA platform = SHARE_MEDIA.WEIXIN;
                sharedLogin(platform);
                break;
        }
      }


    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    private void sharedLinkedInLogin() {
        showProgressDialog();
        LISessionManager.getInstance(mActivity).init(mActivity, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                dismissProgressDialog();
                setUpdateState();
                Toast.makeText(mActivity, "success" + LISessionManager.getInstance(mActivity).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthError(LIAuthError error) {
                dismissProgressDialog();
                setUpdateState();
                Toast.makeText(InterSightApp.getApp().getApplicationContext(), "failed " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }, true);
    }

    private void setUpdateState() {
        LISessionManager sessionManager = LISessionManager.getInstance(mActivity);
        LISession session = sessionManager.getSession();
        boolean accessTokenValid = session.isValid();

        CommonUtils.showToast(
                accessTokenValid ? session.getAccessToken().toString() : "Sync with LinkedIn to enable these buttons");


    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    protected void sharedLogin(final SHARE_MEDIA platform) {

        showProgressDialog();
        mContext.mShareAPI.doOauthVerify(mContext, platform, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                mContext.showProgress("正在验证授权信息……");

                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if ("access_token".equals(entry.getKey())) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                    if ("uid".equals(entry.getKey())) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                    if ("screen_name".equals(entry.getKey())) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                    if ("version".equals(entry.getKey())) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                }
                params.put("version", "1");
//                MLog.d("授权第一步=" + params.toString());
                mContext.mShareAPI.getPlatformInfo(mContext, platform, new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        if (null != map) {
                            for (Map.Entry<String, String> entry : map.entrySet()) {
//                                params.put(entry.getKey(), entry.getValue());
                                if ("access_token".equals(entry.getKey())) {
                                    params.put(entry.getKey(), entry.getValue());
                                }
                                if ("screen_name".equals(entry.getKey())) {
                                    params.put(entry.getKey(), entry.getValue());
                                }
                                if ("version".equals(entry.getKey())) {
                                    params.put(entry.getKey(), entry.getValue());
                                }
                                if (share_media == SHARE_MEDIA.WEIXIN) {
                                    if (entry.getKey().equals("openid")) {
                                        params.put("uid", entry.getValue());
                                    }
                                } else {
                                    if ("uid".equals(entry.getKey())) {
                                        params.put(entry.getKey(), entry.getValue());
                                    }
                                }
                            }
//                            MLog.d("授权第二步=" + params.toString());
//                            submitLoginInfo(url, params, flag, media);
                        } else {
                            CommonUtils.showToast("授权失败");
//                            mContext.dismissProgressDialog();
                        }
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable e) {
//                        mContext.dismissProgressDialog();
                        CommonUtils.showToast("授权失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
//                        mContext.dismissProgressDialog();
                        CommonUtils.showToast("授权失败");
                    }
                });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable e) {
//                mContext.dismissProgressDialog();
//                mContext.httpError(e);
                dismissProgressDialog();

                CommonUtils.showToast("授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
//                mContext.dismissProgressDialog();
                dismissProgressDialog();

                CommonUtils.showToast("授权失败");
            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mContext.mShareAPI.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
