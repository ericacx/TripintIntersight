package com.tripint.intersight.fragment.mine.setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.entity.mine.UserHomeEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置---帐号信息
 * A simple {@link Fragment} subclass.
 */
public class AccountInfoFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.accountInfoArrow)
    ImageView accountInfoArrow;
    @Bind(R.id.account_info_textview_phone)
    TextView accountInfoTextviewPhone;
    @Bind(R.id.account_info_phone)
    RelativeLayout accountInfoPhone;
    @Bind(R.id.accountInfoArrow2)
    ImageView accountInfoArrow2;
    @Bind(R.id.account_info_textview_email)
    TextView accountInfoTextviewEmail;
    @Bind(R.id.account_info_email)
    RelativeLayout accountInfoEmail;
    @Bind(R.id.account_info_password)
    RelativeLayout accountInfoPassword;

    private PageDataSubscriberOnNext<UserHomeEntity> subscriber;

    private UserHomeEntity data;

    public static AccountInfoFragment newInstance() {
        Bundle args = new Bundle();
        AccountInfoFragment fragment = new AccountInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_info, container, false);
        ButterKnife.bind(this, view);
        initToolbarNav(toolbar);
        toolbar.setTitle("帐号信息");
        httpRequestData();
        return view;
    }


    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<UserHomeEntity>() {
            @Override
            public void onNext(UserHomeEntity entity) {
                //接口请求成功后处理
                data = entity;
                initView(null);

            }
        };

        MineDataHttpRequest.getInstance(mActivity).getUserHome(new ProgressSubscriber(subscriber, mActivity));
    }

    private void initView(View view) {
        accountInfoTextviewPhone.setText(data.getMobile());
        accountInfoTextviewEmail.setText(data.getEmail());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.account_info_phone, R.id.account_info_email, R.id.account_info_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_info_phone://绑定手机
//                EventBus.getDefault().post(new StartFragmentEvent(BindPhoneFragment.newInstance()));
                break;
            case R.id.account_info_email://绑定邮箱
//                EventBus.getDefault().post(new StartFragmentEvent(BindEmailFragment.newInstance()));
                break;
            case R.id.account_info_password://修改密码
                EventBus.getDefault().post(new StartFragmentEvent(ModifyPasswordFragment.newInstance()));
                break;
//            case R.id.account_info_wechat_switch:
//                break;
//            case R.id.account_info_linkedin_switch:
//                break;
        }
    }


}
