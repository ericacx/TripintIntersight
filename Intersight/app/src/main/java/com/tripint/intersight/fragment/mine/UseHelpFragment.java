package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.entity.mine.HelpAndProtocolEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 使用帮助---页面
 * A simple {@link Fragment} subclass.
 */
public class UseHelpFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.use_help_text)
    TextView useHelpText;

    private PageDataSubscriberOnNext<HelpAndProtocolEntity> subscriber;
    private HelpAndProtocolEntity data = new HelpAndProtocolEntity();
    public static UseHelpFragment newInstance() {
        Bundle args = new Bundle();

        UseHelpFragment fragment = new UseHelpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_use_help, container, false);
        ButterKnife.bind(this, view);
        initToolbarNav(toolbar);
        toolbar.setTitle("使用帮助");
        httpRequestData();
        return view;
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<HelpAndProtocolEntity>() {
            @Override
            public void onNext(HelpAndProtocolEntity helpAndProtocolEntity) {
                data = helpAndProtocolEntity;
                useHelpText.setText(data.getUseApp().getValue());
            }
        };
        MineDataHttpRequest.getInstance(mActivity).getHelpAndProtocol(new ProgressSubscriber<HelpAndProtocolEntity>(subscriber,mActivity));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
