package com.tripint.intersight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.user.RegisterEntity;
import com.tripint.intersight.entity.user.User;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResigterActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.register_back)
    ImageView registerBack;//返回
    @Bind(R.id.register_et_phone)
    EditText registerEtPhone;//输入的手机号
    @Bind(R.id.register_verify_code)
    Button registerVerifyCode;//发送验证码
    @Bind(R.id.register_create_password)
    EditText registerCreatePassword;//创建密码
    @Bind(R.id.register_input_password)
    EditText registerInputPassword;//再次输入密码
    @Bind(R.id.register_input_verify_code)
    EditText registerInputVerifyCode;//输入验证码
    @Bind(R.id.register_tv_userProtocol)
    TextView registerTvUserProtocol;//用户协议
    @Bind(R.id.register_button_register)
    Button registerButtonRegister;//注册按钮
    @Bind(R.id.register_button_reset)
    Button registerButtonReset;//重置按钮

    private int time = 60;

    private CodeDataEntity codeDataEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriberCode;

    private RegisterEntity registerEntity;
    private PageDataSubscriberOnNext<RegisterEntity> subscriber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigter);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        //验证码
        subscriberCode = new PageDataSubscriberOnNext<CodeDataEntity>() {
            @Override
            public void onNext(CodeDataEntity entity) {
                //接口请求成功后处理
                codeDataEntity = entity;
                Log.e("aaa", entity.getFlg());
            }
        };

        //注册
        subscriber = new PageDataSubscriberOnNext<RegisterEntity>() {
            @Override
            public void onNext(RegisterEntity entity) {
                //接口请求成功后处理
                registerEntity = entity;
                ACache.get(ResigterActivity.this).put(EnumKey.User.USER_TOKEN, entity.getToken());
                Log.e("register",entity.getToken());
                Log.e("register", String.valueOf(entity.getStatus()));
                int status = entity.getStatus();
                if (status == 100){
                    Intent intent = new Intent();
                    intent.setClass(ResigterActivity.this, InterestedActivity.class);
                    startActivity(intent);
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
                        time = 60;
                        registerVerifyCode.setClickable(true);
                        registerVerifyCode.setText("获取验证码");
                    } else {
                        registerVerifyCode.setText("重新获取(" + (--time) + ")");
                        handler.sendEmptyMessageDelayed(100, 1000);
                    }
                    break;
            }
        }
    };

    @OnClick({R.id.register_back, R.id.register_verify_code, R.id.register_tv_userProtocol, R.id.register_button_register, R.id.register_button_reset})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.register_back:
                ResigterActivity.this.finish();//返回退出此页面
                break;
            case R.id.register_verify_code://发送验证码

                if (TextUtils.isEmpty(registerEtPhone.getText().toString())) {
                    ToastUtil.showToast(ResigterActivity.this, "手机号不能为空");
                    registerVerifyCode.setClickable(false);
                } else if (registerEtPhone.getText().toString().length() != 11) {
                    ToastUtil.showToast(ResigterActivity.this, "请输入正确的手机号");
                } else {
                    //发送验证码请求
                    BaseDataHttpRequest.getInstance(ResigterActivity.this).getCode(
                            new ProgressSubscriber(subscriberCode, ResigterActivity.this)
                            , registerEtPhone.getText().toString().trim());
                }
                registerVerifyCode.setText("重新获取(" + time + ")");
                registerVerifyCode.setClickable(false);
                handler.sendEmptyMessageDelayed(100, 1000);
                break;

            case R.id.register_tv_userProtocol:
                intent.setClass(ResigterActivity.this, UserProtocolActivity.class);//跳转到用户协议界面
                startActivity(intent);
                break;

            case R.id.register_button_register://注册按钮

                if (TextUtils.isEmpty(registerEtPhone.getText().toString())) {
                    ToastUtil.showToast(ResigterActivity.this, "手机号不能为空");

                } else if (registerEtPhone.getText().toString().length() != 11) {
                    ToastUtil.showToast(ResigterActivity.this, "请输入正确的手机号");
                } else if (TextUtils.isEmpty(registerCreatePassword.getText().toString())) {
                    ToastUtil.showToast(ResigterActivity.this, "创建的密码不能为空");
                } else if (TextUtils.isEmpty(registerInputPassword.getText().toString())) {
                    ToastUtil.showToast(ResigterActivity.this, "再次输入的密码不能为空");
                } else if (!(registerCreatePassword.getText().toString().trim().equals(registerInputPassword.getText().toString().trim()))) {
                    Log.e("et1", registerCreatePassword.getText().toString());
                    Log.e("et2", registerInputPassword.getText().toString());
                    ToastUtil.showToast(ResigterActivity.this, "两次输入密码不一致,请重新输入");
                } else if (TextUtils.isEmpty(registerInputVerifyCode.getText().toString())) {
                    ToastUtil.showToast(ResigterActivity.this, "验证码不能为空");
                } else if (registerInputPassword.getText().toString().length() < 6 || registerInputPassword.getText().toString().length() > 16) {
                    ToastUtil.showToast(ResigterActivity.this, "请输入6~16位的字符或者数字作为密码");
                } else {
                    User user = new User(registerEtPhone.getText().toString(), registerCreatePassword.getText().toString(), Integer.parseInt(registerInputVerifyCode.getText().toString()));
                    //发送注册请求
                    BaseDataHttpRequest.getInstance(ResigterActivity.this).postRegister(
                            new ProgressSubscriber(subscriber, ResigterActivity.this)
                            , user);
                }
                break;

            case R.id.register_button_reset://重置按钮
                registerEtPhone.setText("");
                registerCreatePassword.setText("");
                registerInputPassword.setText("");
                registerInputVerifyCode.setText("");
                registerVerifyCode.setText("发送验证码");
                break;
        }
    }

    /**
     * 发送手机短信验证码
     */
    public void httpRequestCodeData() {


    }

    /**
     * 注册按钮
     */

    public void httpRequestRegisterData() {


    }
}
