package com.tripint.intersight.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseBackFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpinionDetailFragment extends BaseBackFragment {

    public static final String ARG_ARTICLE_ID = "arg_article_id";
    public static final String ARG_TYPE = "arg_type";

    private int type;
    private int articleId;

    public static OpinionDetailFragment newInstance(int type , int articleId) {
        // Required empty public constructor
        OpinionDetailFragment fragment = new OpinionDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ARTICLE_ID,articleId);
        args.putInt(ARG_TYPE,type);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(ARG_TYPE);
            articleId = bundle.getInt(ARG_ARTICLE_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opinion_detail, container, false);
        return view;
    }

}
