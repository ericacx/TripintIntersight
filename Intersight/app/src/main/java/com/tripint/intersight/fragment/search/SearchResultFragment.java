package com.tripint.intersight.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseBackFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lirichen on 2016/9/21.
 */

public class SearchResultFragment extends BaseBackFragment {

    public static final String ARG_SEARCH_TYPE = "arg_search_type";
    public static final String ARG_SEARCH_KEYWORD = "arg_search_keyword";


    public static final int ARG_SEARCH_TYPE_PERSON = 1;
    public static final int ARG_SEARCH_TYPE_RESOURCE = 2;


    @Bind(R.id.toolbar_search_text)
    TextView toolbarSearchText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private int searchType; //1:人 ;  2:内容

    public static SearchResultFragment newInstance(int searchType) {

        Bundle args = new Bundle();

        SearchResultFragment fragment = new SearchResultFragment();
        args.putInt(ARG_SEARCH_TYPE, searchType);
        fragment.setArguments(args);
        return fragment;
    }

    public static SearchResultFragment newInstance(Bundle args) {

        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.searchType = bundle.getInt(ARG_SEARCH_TYPE);
        }
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
        initToolbarMenu(toolbar);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
