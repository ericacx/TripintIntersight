package com.tripint.intersight.fragment.create;


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
public class CreateInterviewFragment extends BaseBackFragment {


    public static CreateInterviewFragment newInstance() {
        // Required empty public constructor
        CreateInterviewFragment fragment = new CreateInterviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_interview, container, false);
        return view;
    }

}
