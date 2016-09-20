package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.flipview.NewsFlipViewAdapter;

import se.emilsjolander.flipview.FlipView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseBackFragment {


    private FlipView mFlipView;
    private NewsFlipViewAdapter mNewsFlipViewAdapter;
    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mFlipView = (FlipView) view.findViewById(R.id.newsFlipView);
        mNewsFlipViewAdapter = new NewsFlipViewAdapter(getActivity());
        mFlipView.setAdapter(mNewsFlipViewAdapter);
        return view;
    }

}
