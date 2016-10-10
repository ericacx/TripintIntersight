package com.tripint.intersight.fragment.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.activity.LoginActivity;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.entity.mine.UserHomeEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.event.StartFragmentForResultEvent;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;
import com.tripint.intersight.fragment.mine.setting.SettingFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseLazyMainFragment {

    @Bind(R.id.mineCIVPersonalInfo)
    ImageView mineCIVPersonalInfo;
    @Bind(R.id.mine_text_view_name)
    TextView mineTextViewName;
    @Bind(R.id.mineIvRewriteInfo)
    ImageView mineIvRewriteInfo;
    @Bind(R.id.text_view_mine_ask_answer)
    TextView textViewMineAskAnswer;
    @Bind(R.id.text_view_mine_interview)
    TextView textViewMineInterview;
    @Bind(R.id.text_view_my_option)
    TextView textViewMyOption;
    @Bind(R.id.text_view_my_money)
    TextView textViewMyMoney;
    @Bind(R.id.text_view_my_account_detail)
    TextView textViewMyAccountDetail;
    @Bind(R.id.textView_my_focus)
    TextView textViewMyFocus;
    @Bind(R.id.text_view_my_star)
    TextView textViewMyStar;
    @Bind(R.id.text_view_help)
    TextView textViewHelp;
    @Bind(R.id.text_view_setting)
    TextView textViewSetting;

    private PageDataSubscriberOnNext<UserHomeEntity> subscriber;

    private UserHomeEntity data;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine1, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    private void initView(View view) {
        mineTextViewName.setText(data.getNickname());
        textViewMyMoney.setText(data.getBalance());
        Glide.with(mActivity).load(data.getAvatar())
                .crossFade()
                .fitCenter()
                .placeholder(R.drawable.loading_normal_icon)
                .transform(new GlideCircleTransform(mActivity))
                .into(mineCIVPersonalInfo);
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

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (!InterSightApp.getApp().isUserLogin()) {
            Intent intent = new Intent();
            intent.setClass(mActivity, LoginActivity.class);
            startActivityForResult(intent, StartFragmentForResultEvent.REQ_LOGIN_FRAGMENT);
        } else {
            httpRequestData();
        }

    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

        refeashContent();
    }

    private void refeashContent() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.mineIvRewriteInfo, R.id.text_view_mine_ask_answer, R.id.text_view_mine_interview,
            R.id.text_view_my_option, R.id.text_view_my_money, R.id.text_view_my_account_detail,
            R.id.textView_my_focus, R.id.text_view_my_star, R.id.text_view_help, R.id.text_view_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mineIvRewriteInfo:
                break;
            case R.id.text_view_mine_ask_answer://我的问答
                EventBus.getDefault().post(new StartFragmentEvent(MyAskAnswerFragment.newInstance()));
                break;
            case R.id.text_view_mine_interview://我的访谈
                EventBus.getDefault().post(new StartFragmentEvent(MyInterviewFragment.newInstance()));
                break;
            case R.id.text_view_my_option://我的观点
                EventBus.getDefault().post(new StartFragmentEvent(MyOpinionFragment.newInstance()));
                break;
            case R.id.text_view_my_money:
                break;
            case R.id.text_view_my_account_detail://账户明细
                EventBus.getDefault().post(new StartFragmentEvent(AccountDetailFragment.newInstance()));
                break;
            case R.id.textView_my_focus://我的关注
                EventBus.getDefault().post(new StartFragmentEvent(MyFocusedFragment.newInstance()));
                break;
            case R.id.text_view_my_star://明星洞察家
                EventBus.getDefault().post(new StartFragmentEvent(StarIntersighterFragment.newInstance()));
                break;
            case R.id.text_view_help://使用帮助
                EventBus.getDefault().post(new StartFragmentEvent(UseHelpFragment.newInstance()));
                break;
            case R.id.text_view_setting://设置
                EventBus.getDefault().post(new StartFragmentEvent(SettingFragment.newInstance()));
                break;
        }
    }
}
