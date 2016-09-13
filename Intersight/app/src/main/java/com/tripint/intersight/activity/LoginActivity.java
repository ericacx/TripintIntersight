package com.tripint.intersight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.MainActivity;
import com.tripint.intersight.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.login_et_username)
    EditText loginEtUsername;//用户名
    @BindView(R.id.login_et_password)
    EditText loginEtPassword;//密码
    @BindView(R.id.login_forget_pwd)
    TextView loginForgetPwd;//忘记密码
    @BindView(R.id.login_button_login)
    Button loginButtonLogin;//登录按钮
    @BindView(R.id.login_button_register)
    Button loginButtonRegister;//注册按钮
    @BindView(R.id.login_thirdLogin_linkedin)
    TextView loginThirdLoginLinkedin;//领英登录
    @BindView(R.id.login_thirdLogin_wechat)
    TextView loginThirdLoginWechat;//微信登录

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();//初始化页面
    }

    private void initView() {

        intent = new Intent();
    }


    @OnClick({ R.id.login_forget_pwd, R.id.login_button_login, R.id.login_button_register, R.id.login_thirdLogin_linkedin, R.id.login_thirdLogin_wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_forget_pwd:
                intent.setClass(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.login_button_login:
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.login_button_register:
                intent.setClass(LoginActivity.this, ResigterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_thirdLogin_linkedin:
                break;
            case R.id.login_thirdLogin_wechat:
                break;
        }
    }
}
