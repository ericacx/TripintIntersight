package com.tripint.intersight.fragment.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import butterknife.ButterKnife;

/**
 * 明星洞察家---页面
 * A simple {@link Fragment} subclass.
 */
public class StarIntersighterFragment extends BaseBackFragment {

    public static StarIntersighterFragment newInstance() {

        Bundle args = new Bundle();

        StarIntersighterFragment fragment = new StarIntersighterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_star_intersighter, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
