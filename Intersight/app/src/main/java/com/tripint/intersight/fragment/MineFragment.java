package com.tripint.intersight.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.LoginActivity;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.event.StartFragmentForResultEvent;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;
import com.tripint.intersight.widget.image.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseLazyMainFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.mineCIVPersonalInfo)
    CircleImageView mineCIVPersonalInfo;
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
    @Bind(R.id.mineIvMoneyNum)
    TextView mineIvMoneyNum;
    @Bind(R.id.layout_container_account_retain)
    RelativeLayout layoutContainerAccountRetain;
    @Bind(R.id.layout_container_account_detail)
    RelativeLayout layoutContainerAccountDetail;
    @Bind(R.id.layout_container_my_focus)
    RelativeLayout layoutContainerMyFocus;
    @Bind(R.id.layout_container_star)
    RelativeLayout layoutContainerStar;
    @Bind(R.id.text_view_help)
    TextView layoutContainerHelp;
    @Bind(R.id.text_view_setting)
    TextView textViewSettings;

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
        initView(view);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView(View view) {

    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (!InterSightApp.getApp().isUserLogin()) {
            Intent intent = new Intent();
            intent.setClass(mActivity, LoginActivity.class);
            startActivityForResult(intent, StartFragmentForResultEvent.REQ_LOGIN_FRAGMENT);
        }

    }

    @OnClick({R.id.text_view_setting})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.text_view_setting: //设置
                EventBus.getDefault().post(new StartFragmentEvent(SettingFragment.newInstance()));

                break;
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
}
