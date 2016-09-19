package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpinionFragment extends Fragment{


    public static OpinionFragment newInstance() {

        Bundle args = new Bundle();

        OpinionFragment fragment = new OpinionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opinion, container, false);

        return view;
    }

}
