package com.tripint.intersight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResigterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.register_back)
    ImageView registerBack;//返回
    @BindView(R.id.register_et_phone)
    EditText registerEtPhone;//输入的手机号
    @BindView(R.id.register_verify_code)
    Button registerVerifyCode;//发送验证码
    @BindView(R.id.register_create_password)
    EditText registerCreatePassword;//创建密码
    @BindView(R.id.register_input_password)
    EditText registerInputPassword;//再次输入密码
    @BindView(R.id.register_input_verify_code)
    EditText registerInputVerifyCode;//输入验证码
    @BindView(R.id.register_tv_userProtocol)
    TextView registerTvUserProtocol;//用户协议
    @BindView(R.id.register_button_register)
    Button registerButtonRegister;//注册按钮
    @BindView(R.id.register_button_reset)
    Button registerButtonReset;//重置按钮

    private String phone, create_password, input_password, verify_code;
    private Intent intent;
    private int time = 60;

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

        //获取输入的内容
        phone = registerEtPhone.getText().toString();//手机号
        create_password = registerCreatePassword.getText().toString();//创建密码
        input_password = registerInputPassword.getText().toString();//再次输入密码
        verify_code = registerInputVerifyCode.getText().toString();//输入的验证码

        intent = new Intent();
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
        switch (view.getId()) {
            case R.id.register_back:
                ResigterActivity.this.finish();//返回退出此页面
                break;
            case R.id.register_verify_code://发送验证码
                registerVerifyCode.setText("重新获取(" + time + ")");
                registerVerifyCode.setClickable(false);
                handler.sendEmptyMessageDelayed(100, 1000);
                break;
            case R.id.register_tv_userProtocol:
                intent.setClass(ResigterActivity.this, UserProtocolActivity.class);//跳转到用户协议界面
                startActivity(intent);
                break;
            case R.id.register_button_register://注册按钮
                intent.setClass(ResigterActivity.this, InterestedActivity.class);
                startActivity(intent);
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
}
