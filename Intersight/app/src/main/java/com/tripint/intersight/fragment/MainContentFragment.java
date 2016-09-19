package com.tripint.intersight.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class MainContentFragment extends BaseLazyMainFragment {

    public static MainContentFragment newInstance() {

        Bundle args = new Bundle();

        MainContentFragment fragment = new MainContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_content, container, false);
        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadRootFragment(R.id.main_container, IntersightPlusFragment.newInstance());
        }
    }
}
