package com.tripint.intersight.fragment.mine.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseBackFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskAnswerMessageFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ask_answer_recyclerview)
    RecyclerView askAnswerRecyclerview;

    public static AskAnswerMessageFragment newInstance() {
        Bundle args = new Bundle();
        AskAnswerMessageFragment fragment = new AskAnswerMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_answer_message, container, false);
        ButterKnife.bind(this, view);
        initToolbarNav(toolbar);
        toolbar.setTitle("问答消息");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
