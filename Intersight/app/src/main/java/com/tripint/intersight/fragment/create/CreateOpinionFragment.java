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
public class CreateOpinionFragment extends BaseBackFragment {


    public static CreateOpinionFragment newInstance() {
        // Required empty public constructor
        CreateOpinionFragment fragment = new CreateOpinionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_opinion, container, false);
    }

}
