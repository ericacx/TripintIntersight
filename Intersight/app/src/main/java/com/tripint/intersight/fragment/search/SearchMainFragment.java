package com.tripint.intersight.fragment.search;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lirichen on 2016/9/21.
 */

public class SearchMainFragment extends BaseBackFragment {

    @Bind(R.id.toolbar_search_text)
    TextView toolbarSearchText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.text_find_people)
    TextView textFindPeople;
    @Bind(R.id.layout_find_people)
    RelativeLayout layoutFindPeople;
    @Bind(R.id.text_find_resource)
    TextView textFindResource;
    @Bind(R.id.layout_find_resource)
    RelativeLayout layoutFindResource;

    public static SearchMainFragment newInstance() {

        Bundle args = new Bundle();

        SearchMainFragment fragment = new SearchMainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_main, container, false);
        ButterKnife.bind(this, view);

        initView(view);
        return view;
    }

    private void initView(View view) {

        initToolbarNav(toolbar);


    }

    @OnClick({R.id.layout_find_people, R.id.layout_find_resource})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.layout_find_people: //行业领域
                EventBus.getDefault().post(new StartFragmentEvent(SearchPersonFragment.newInstance()));

                break;
            case R.id.layout_find_resource: //我的关注
                EventBus.getDefault().post(new StartFragmentEvent(SearchResultFragment.newInstance(SearchResultFragment.ARG_SEARCH_TYPE_RESOURCE)));
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
