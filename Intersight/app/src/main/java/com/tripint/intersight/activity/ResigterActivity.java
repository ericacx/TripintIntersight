package com.tripint.intersight.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;

public class ResigterActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView register_back;//返回
    private EditText register_et_phone;//输入的手机号
    private Button register_verify_code;//发送验证码
    private EditText register_create_password;//创建密码
    private EditText register_input_password;//再次输入密码
    private EditText register_input_verify_code;//输入验证码
    private TextView register_tv_userProtocol;//用户协议
    private Button register_button_register;//注册按钮
    private Button register_button_reset;//重置按钮

    private String phone,create_password,input_password,verify_code;
    private Intent intent;
    private int time = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigter);

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        register_back = ((ImageView) findViewById(R.id.register_back));
        register_back.setOnClickListener(this);

        register_et_phone = ((EditText) findViewById(R.id.register_et_phone));//手机号
        register_create_password = ((EditText) findViewById(R.id.register_create_password));//创建密码
        register_input_password = ((EditText) findViewById(R.id.register_input_password));//再次输入密码
        register_input_verify_code = ((EditText) findViewById(R.id.register_input_verify_code));//输入验证码

        register_verify_code = ((Button) findViewById(R.id.register_verify_code));//发送验证码
        register_verify_code.setOnClickListener(this);

        register_tv_userProtocol = ((TextView) findViewById(R.id.register_tv_userProtocol));//用户协议
        register_tv_userProtocol.setOnClickListener(this);

        register_button_register = ((Button) findViewById(R.id.register_button_register));//注册按钮
        register_button_register.setOnClickListener(this);

        register_button_reset = ((Button) findViewById(R.id.register_button_reset));//重置按钮
        register_button_reset.setOnClickListener(this);

        //获取输入的内容
        phone = register_et_phone.getText().toString();//手机号
        create_password = register_create_password.getText().toString();//创建密码
        input_password = register_create_password.getText().toString();//再次输入密码
        verify_code = register_create_password.getText().toString();//输入的验证码

        intent = new Intent();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_back:
                ResigterActivity.this.finish();//返回退出此页面
                break;
            case R.id.register_verify_code://发送验证码
                register_verify_code.setText("重新获取("+time+")");
                register_verify_code.setClickable(false);
                handler.sendEmptyMessageDelayed(100, 1000);
                break;
            case R.id.register_tv_userProtocol:
                intent.setClass(ResigterActivity.this,UserProtocolActivity.class);//跳转到用户协议界面
                startActivity(intent);
                break;
            case R.id.register_button_register://注册按钮
                intent.setClass(ResigterActivity.this,InterestedActivity.class);
                startActivity(intent);
                break;
            case R.id.register_button_reset://重置按钮
                register_et_phone.setText("");
                register_create_password.setText("");
                register_input_password.setText("");
                register_input_verify_code.setText("");
                register_verify_code.setText("发送验证码");
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (time == 0) {
                        time = 60;
                        register_verify_code.setClickable(true);
                        register_verify_code.setText("获取验证码");
                    } else {
                        register_verify_code.setText("重新获取(" + (--time) + ")");
                        handler.sendEmptyMessageDelayed(100, 1000);
                    }
                    break;
            }
        }
    };
}
