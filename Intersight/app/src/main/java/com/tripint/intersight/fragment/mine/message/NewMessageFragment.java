package com.tripint.intersight.fragment.mine.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新消息提醒页面
 * A simple {@link Fragment} subclass.
 */
public class NewMessageFragment extends BaseBackFragment {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.new_message_recyclerview)
    RecyclerView newMessageRecyclerview;
    @Bind(R.id.new_message_text_view_interview)
    TextView newMessageTextViewInterview;
    @Bind(R.id.new_message_text_view_ask_answer)
    TextView newMessageTextViewAskAnswer;
    @Bind(R.id.new_message_text_view_comment_agree)
    TextView newMessageTextViewCommentAgree;
    @Bind(R.id.new_message_text_view_system_message)
    TextView newMessageTextViewSystemMessage;

    public static NewMessageFragment newInstance() {
        Bundle args = new Bundle();
        NewMessageFragment fragment = new NewMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_message, container, false);
        ButterKnife.bind(this, view);
        initToolbarNav(toolbar);
        toolbar.setTitle("新消息提醒");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.new_message_text_view_interview, R.id.new_message_text_view_ask_answer, R.id.new_message_text_view_comment_agree, R.id.new_message_text_view_system_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_message_text_view_interview://访谈消息
                EventBus.getDefault().post(new StartFragmentEvent(InterviewMessageFragment.newInstance()));
                break;
            case R.id.new_message_text_view_ask_answer://问答消息
                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerMessageFragment.newInstance()));
                break;
            case R.id.new_message_text_view_comment_agree://评论或赞
                EventBus.getDefault().post(new StartFragmentEvent(CommentAgreeFragment.newInstance()));
                break;
            case R.id.new_message_text_view_system_message://系统消息
                EventBus.getDefault().post(new StartFragmentEvent(SystemMessageFragment.newInstance()));
                break;
        }
    }
}
