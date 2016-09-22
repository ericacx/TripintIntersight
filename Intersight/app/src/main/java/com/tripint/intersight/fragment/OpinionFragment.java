package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.flipview.OpinionFlipViewAdapter2;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.flipview.FlipView;

/**
 * 观点(文章)画面
 * A simple {@link Fragment} subclass.
 */
public class OpinionFragment extends Fragment {

    @Bind(R.id.opinionFlipView)
    FlipView opinionFlipView;
    private OpinionFlipViewAdapter2 mOpinionFlipViewAdapter2;

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

        ButterKnife.bind(this, view);

        mOpinionFlipViewAdapter2 = new OpinionFlipViewAdapter2(getContext());
        opinionFlipView.setAdapter(mOpinionFlipViewAdapter2);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
