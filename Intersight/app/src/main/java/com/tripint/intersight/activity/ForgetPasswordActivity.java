package com.tripint.intersight.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.user.RegisterEntity;
import com.tripint.intersight.helper.PhoneUtils;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Bind(R.id.register_back)
    ImageView registerBack;
    @Bind(R.id.forgetPasswordEtPhone)
    EditText forgetPasswordEtPhone;//输入的手机号或者邮箱
    @Bind(R.id.forgetPasswordBtnCode)
    Button forgetPasswordBtnCode;//发送验证码
    @Bind(R.id.forgetPasswordEtInputCode)
    EditText forgetPasswordEtInputCode;//输入验证码
    @Bind(R.id.forgetPasswordEtNewPwd)
    EditText forgetPasswordEtNewPwd;//输入新密码
    @Bind(R.id.forgetPasswordEtTwiceNewPwd)
    EditText forgetPasswordEtTwiceNewPwd;//再次输入新密码
    @Bind(R.id.forgetPasswordBtnSubmit)
    Button forgetPasswordBtnSubmit;//提交按钮

    private int time = 60;

    private CodeDataEntity codeDataEntity;
    private PageDataSubscriberOnNext<CodeDataEntity> subscriberCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
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
                Log.e("aaa",entity.getFlg());
            }
        };

    }





    @OnClick({R.id.forgetPasswordBtnCode, R.id.forgetPasswordBtnSubmit})
    public void onClick(View view) {

        String phone = forgetPasswordEtPhone.getText().toString().trim();
        String inputCode = forgetPasswordEtInputCode.getText().toString().trim();
        String newPassword = forgetPasswordEtNewPwd.getText().toString().trim();
        String twicenewPassword = forgetPasswordEtTwiceNewPwd.getText().toString().trim();

        switch (view.getId()) {
            case R.id.forgetPasswordBtnCode:
                if (TextUtils.isEmpty(phone)) {
                    forgetPasswordBtnCode.setClickable(false);
                    ToastUtil.showToast(ForgetPasswordActivity.this, "手机号不能为空");
                    return;
                }
                else if (PhoneUtils.isPhone(phone)) {
                    ToastUtil.showToast(ForgetPasswordActivity.this, "请输入正确的手机号");
                    return;
                }else {
                    forgetPasswordBtnCode.setClickable(true);
                    forgetPasswordBtnCode.setText("重新获取(" + time + ")");
                    httpRequestCodeData();
                }
                forgetPasswordBtnCode.setClickable(false);
                handler.sendEmptyMessageDelayed(100, 1000);
                break;
            case R.id.forgetPasswordBtnSubmit:
                break;
        }
    }

    //发送验证码请求
    private void httpRequestCodeData() {

        BaseDataHttpRequest.getInstance().getCode(
                new ProgressSubscriber(subscriberCode, ForgetPasswordActivity.this)
                , forgetPasswordEtPhone.getText().toString());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (time == 0) {
                        time = 60;
                        forgetPasswordBtnCode.setClickable(true);
                        forgetPasswordBtnCode.setText("获取验证码");
                    } else {
                        forgetPasswordBtnCode.setText("重新获取(" + (--time) + ")");
                        handler.sendEmptyMessageDelayed(100, 1000);
                    }
                    break;
            }
        }
    };
}
