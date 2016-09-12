package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.flipview.FlipAdapter;

import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    private FlipView mFlipView;
    private FlipAdapter mAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mFlipView = (FlipView) view.findViewById(R.id.flip_view);
        mAdapter = new FlipAdapter(getActivity());
        mFlipView.setAdapter(mAdapter);
        return view;
    }

}
