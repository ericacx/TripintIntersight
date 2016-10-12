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

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseBackFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 他的访谈  --- 页面
 * A simple {@link Fragment} subclass.
 */
public class HisInterviewFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.his_interview_ask)
    Button hisInterviewAsk;
    @Bind(R.id.his_interview_interview)
    Button hisInterviewInterview;
    @Bind(R.id.his_interview_LL)
    LinearLayout hisInterviewLL;
    @Bind(R.id.his_interview_recyclerView)
    RecyclerView hisInterviewRecyclerView;

    public static HisInterviewFragment newInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();
        HisInterviewFragment fragment = new HisInterviewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_his_interview, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        return view;
    }

    private void initToolbar() {
        initToolbarNav(toolbar);
        toolbar.setTitle("他的访谈");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.his_interview_ask, R.id.his_interview_interview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.his_interview_ask:
                break;
            case R.id.his_interview_interview:
                break;
        }
    }
}
