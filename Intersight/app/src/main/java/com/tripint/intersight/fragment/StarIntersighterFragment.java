package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;

/**
 * 明星洞察家---页面
 * A simple {@link Fragment} subclass.
 */
public class StarIntersighterFragment extends Fragment {


    public StarIntersighterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_star_intersighter, container, false);
    }

}
