package com.tripint.intersight.fragment;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.tripint.intersight.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base,container);
        final ImageView fabIconNew = new ImageView(getContext());
        fabIconNew.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(getActivity())
                .setContentView(fabIconNew)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(getActivity());
        ImageView rlIcon1 = new ImageView(getActivity());
        ImageView rlIcon2 = new ImageView(getActivity());
        ImageView rlIcon3 = new ImageView(getActivity());

        rlIcon1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .attachTo(rightLowerButton)
                .build();

        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                fabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }
        });
        return view;
    }

}
