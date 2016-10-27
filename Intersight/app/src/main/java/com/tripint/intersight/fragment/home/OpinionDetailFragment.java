package com.tripint.intersight.fragment.home;


import android.os.Bundle;
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


    public static OpinionDetailFragment newInstance() {
        // Required empty public constructor
        OpinionDetailFragment fragment = new OpinionDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opinion_detail, container, false);
        return view;
    }

}
