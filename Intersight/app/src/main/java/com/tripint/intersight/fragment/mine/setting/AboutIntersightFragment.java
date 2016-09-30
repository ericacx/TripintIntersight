package com.tripint.intersight.fragment.mine.setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseBackFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 设置---关于洞察+
 * A simple {@link Fragment} subclass.
 */
public class AboutIntersightFragment extends BaseBackFragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.about_intersight_imageview)
    ImageView aboutIntersightImageview;
    @Bind(R.id.editor_feedback_content)
    TextView editorFeedbackContent;
    @Bind(R.id.about_intersight_textview_time)
    TextView aboutIntersightTextviewTime;
    @Bind(R.id.about_intersight_textview_company)
    TextView aboutIntersightTextviewCompany;

    public static AboutIntersightFragment newInstance() {
        Bundle args = new Bundle();
        AboutIntersightFragment fragment = new AboutIntersightFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_intersight, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        initToolbarNav(toolbar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
