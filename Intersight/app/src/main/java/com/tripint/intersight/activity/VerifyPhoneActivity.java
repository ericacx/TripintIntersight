package com.tripint.intersight.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tripint.intersight.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;//返回退出页面
    @BindView(R.id.et_phoneNumber)
    EditText etPhoneNumber;//手机号
    @BindView(R.id.btn_verifyCode)
    Button btnVerifyCode;//输入验证码
    @BindView(R.id.et_input_verifyCode)
    EditText etInputVerifyCode;//发送验证码
    @BindView(R.id.btn_next)
    Button btnNext;//下一步

    private int time = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        ButterKnife.bind(this);

        initView();

    }

    /**
     * 初始化控件
     */
    private void initView() {


    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (time == 0) {
                        time = 60;
                        btnVerifyCode.setClickable(true);
                        btnVerifyCode.setText("获取验证码");
                    } else {
                        btnVerifyCode.setText("重新获取(" + (--time) + ")");
                        handler.sendEmptyMessageDelayed(100, 1000);
                    }
                    break;
            }
        }
    };

    @OnClick({R.id.iv_back, R.id.btn_verifyCode, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                VerifyPhoneActivity.this.finish();
                break;
            case R.id.btn_verifyCode:
                btnVerifyCode.setText("重新获取(" + time + ")");
                btnVerifyCode.setClickable(false);
                handler.sendEmptyMessageDelayed(100, 1000);
                break;
            case R.id.btn_next:

                break;
        }
    }
}
