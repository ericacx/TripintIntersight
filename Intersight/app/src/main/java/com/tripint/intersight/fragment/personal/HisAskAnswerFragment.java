package com.tripint.intersight.fragment.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseBackFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 他的主页----他的问答--
 * A simple {@link Fragment} subclass.
 */
public class HisAskAnswerFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_my_common_header_left)
    TextView btnMyCommonHeaderLeft;
    @Bind(R.id.btn_my_common_header_right)
    TextView btnMyCommonHeaderRight;
    @Bind(R.id.hisAskAnswerVG)
    LinearLayout hisAskAnswerVG;
    @Bind(R.id.his_ask_answer_ask)
    Button hisAskAnswerAsk;
    @Bind(R.id.his_ask_answer_interview)
    Button hisAskAnswerInterview;
    @Bind(R.id.hisAskAnswerLL)
    LinearLayout hisAskAnswerLL;
    @Bind(R.id.his_ask_answer_recyclerview)
    RecyclerView hisAskAnswerRecyclerview;

    public static HisAskAnswerFragment newInstance() {
        Bundle args = new Bundle();
        HisAskAnswerFragment fragment = new HisAskAnswerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_his_ask_answer, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        return view;
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("他的观点");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.his_ask_answer_ask, R.id.his_ask_answer_interview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.his_ask_answer_ask:
                break;
            case R.id.his_ask_answer_interview:
                break;
        }
    }
}
