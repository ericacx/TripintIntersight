package com.tripint.intersight.fragment.mine.setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.mine.AdviceFeedbackFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseBackFragment {


    @Bind(R.id.layout_container_feedback)
    RelativeLayout layoutContainerFeedback;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.button_logout)
    Button buttonLogout;
    @Bind(R.id.setting_account_info)
    RelativeLayout settingAccountInfo;
    @Bind(R.id.setting_about_intersight)
    RelativeLayout settingAboutIntersight;
    @Bind(R.id.setting_tvCacheSize)
    TextView settingTvCacheSize;
    @Bind(R.id.setting_clear)
    RelativeLayout settingClear;
    @Bind(R.id.setting_update)
    RelativeLayout settingUpdate;
    @Bind(R.id.setting_service)
    TextView settingService;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        initToolbarNav(toolbar);
        toolbar.setTitle("设置");
        return view;
    }

    @OnClick({R.id.layout_container_feedback, R.id.button_logout,R.id.setting_account_info,
            R.id.setting_about_intersight, R.id.setting_clear, R.id.setting_update, R.id.setting_service})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.layout_container_feedback: //意见反馈
                EventBus.getDefault().post(new StartFragmentEvent(AdviceFeedbackFragment.newInstance()));
                break;
            case R.id.button_logout: //退出登录
                ACache.get(mActivity).put(EnumKey.User.USER_TOKEN, "");
                pop();
                break;
            case R.id.setting_account_info://帐号信息
                EventBus.getDefault().post(new StartFragmentEvent(AccountInfoFragment.newInstance()));
                break;
            case R.id.setting_about_intersight://关于洞察+
                EventBus.getDefault().post(new StartFragmentEvent(AboutIntersightFragment.newInstance()));
                break;
            case R.id.setting_clear://清除缓存

                break;
            case R.id.setting_update://检查更新
                break;
            case R.id.setting_service://洞察+平台服务协议
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
