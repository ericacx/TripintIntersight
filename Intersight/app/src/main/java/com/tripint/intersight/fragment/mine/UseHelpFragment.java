package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseBackFragment;

/**
 * 使用帮助---页面
 * A simple {@link Fragment} subclass.
 */
public class UseHelpFragment extends BaseBackFragment{


    public static UseHelpFragment newInstance(){
        Bundle args = new Bundle();

        UseHelpFragment fragment = new UseHelpFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_use_help, container, false);

        return view;
    }

}
