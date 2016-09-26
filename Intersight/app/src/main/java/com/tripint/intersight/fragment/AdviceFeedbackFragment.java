package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.filter.util.CommonUtil;
import com.tripint.intersight.entity.discuss.DiscussPageEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.home.AskAnswerFragment;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 意见反馈---页面
 * A simple {@link Fragment} subclass.
 */
public class AdviceFeedbackFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.editor_feedback_content)
    EditText editTextFeedbackContent;
    @Bind(R.id.button_submit)
    Button buttonSubmit;

    private PageDataSubscriberOnNext<String> subscriber;


    public AdviceFeedbackFragment() {
        // Required empty public constructor
    }

    public static AdviceFeedbackFragment newInstance() {

        Bundle args = new Bundle();

        AdviceFeedbackFragment fragment = new AdviceFeedbackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advice_feedback, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initToolbarNav(toolbar);
        toolbar.setTitle("意见反馈");

    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<String>() {
            @Override
            public void onNext(String entity) {
                //接口请求成功后处理

                CommonUtils.showToast("提交成功");

            }
        };

        String content = editTextFeedbackContent.getText().toString();
        MineDataHttpRequest.getInstance(mActivity).postFeedback(new ProgressSubscriber(subscriber, mActivity), content);
    }

    @OnClick(R.id.button_submit)
    public void onClick(View view) {

        httpRequestData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
