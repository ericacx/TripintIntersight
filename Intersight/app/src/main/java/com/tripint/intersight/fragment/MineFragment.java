package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseLazyMainFragment {


    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine1, container, false);
        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }
}
