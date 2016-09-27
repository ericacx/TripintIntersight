package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;

/**
 * 我被约访  详情页面
 * A simple {@link Fragment} subclass.
 */
public class MyInterviewedDetailFragment extends Fragment {


    public MyInterviewedDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_interviewed_detail, container, false);
    }

}
