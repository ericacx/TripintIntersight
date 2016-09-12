package com.tripint.intersight.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tripint.intersight.R;

public class VerifyPhoneActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_back;//返回退出页面
    private EditText et_phoneNumber;//手机号
    private EditText et_input_verifyCode;//输入验证码
    private Button btn_verifyCode;//发送验证码
    private Button btn_next;//下一步

    private int time = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        initView();

    }

    /**
     * 初始化控件
     */
    private void initView() {

        iv_back = ((ImageView) findViewById(R.id.iv_back));//返回
        iv_back.setOnClickListener(this);

        et_phoneNumber = ((EditText) findViewById(R.id.et_phoneNumber));//手机号
        et_input_verifyCode = ((EditText) findViewById(R.id.et_input_verifyCode));//验证码

        btn_verifyCode = ((Button) findViewById(R.id.btn_verifyCode));
        btn_verifyCode.setOnClickListener(this);
        btn_next = ((Button) findViewById(R.id.btn_next));
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                VerifyPhoneActivity.this.finish();
                break;
            case R.id.btn_verifyCode:
                btn_verifyCode.setText("重新获取("+time+")");
                btn_verifyCode.setClickable(false);
                handler.sendEmptyMessageDelayed(100, 1000);
                break;
            case R.id.btn_next:

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
                        btn_verifyCode.setClickable(true);
                        btn_verifyCode.setText("获取验证码");
                    } else {
                        btn_verifyCode.setText("重新获取(" + (--time) + ")");
                        handler.sendEmptyMessageDelayed(100, 1000);
                    }
                    break;
            }
        }
    };
}
