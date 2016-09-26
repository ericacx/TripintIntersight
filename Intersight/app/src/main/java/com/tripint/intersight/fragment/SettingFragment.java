package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tripint.intersight.R;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.search.SearchMainFragment;

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

    public SettingFragment() {
        // Required empty public constructor
    }


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
        return view;
    }

    @OnClick({R.id.layout_container_feedback})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.layout_container_feedback: //行业领域
                EventBus.getDefault().post(new StartFragmentEvent(AdviceFeedbackFragment.newInstance()));

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
