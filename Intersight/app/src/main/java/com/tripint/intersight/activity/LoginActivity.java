package com.tripint.intersight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.MainActivity;
import com.tripint.intersight.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView login_back;//返回
    private EditText login_et_username;//用户名
    private EditText login_et_password;//密码
    private TextView login_forget_password;//忘记密码
    private Button login_button_login;//登录按钮
    private Button login_button_register;//注册按钮
    private TextView login_thirdLogin_linkedin,login_thirdLogin_wechat;//第三方登录

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();//初始化页面
    }

    private void initView() {
        login_back = ((ImageView) findViewById(R.id.login_back));
        login_back.setOnClickListener(this);

        login_et_username = ((EditText) findViewById(R.id.login_et_username));
        login_et_password = ((EditText) findViewById(R.id.login_et_password));

        login_forget_password = ((TextView) findViewById(R.id.login_forget_pwd));
        login_forget_password.setOnClickListener(this);

        login_button_login = ((Button) findViewById(R.id.login_button_login));
        login_button_login.setOnClickListener(this);
        login_button_register = ((Button) findViewById(R.id.login_button_register));
        login_button_register.setOnClickListener(this);

        login_thirdLogin_linkedin = ((TextView) findViewById(R.id.login_thirdLogin_linkedin));
        login_thirdLogin_linkedin.setOnClickListener(this);

        login_thirdLogin_wechat = ((TextView) findViewById(R.id.login_thirdLogin_wechat));
        login_thirdLogin_wechat.setOnClickListener(this);

        intent = new Intent();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_back:
                LoginActivity.this.finish();
                break;
            case R.id.login_forget_pwd:
                intent.setClass(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.login_button_login:
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.login_button_register:
                intent.setClass(LoginActivity.this,ResigterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
