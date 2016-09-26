package com.tripint.intersight.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.SearchDropMenuAdapter;
import com.tripint.intersight.common.widget.filter.DropDownMenu;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterDoneListener;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.search.FilterUrl;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lirichen on 2016/9/21.
 */

public class SearchResultFragment extends BaseBackFragment implements OnFilterDoneListener {

    public static final String ARG_SEARCH_TYPE = "arg_search_type";
    public static final String ARG_SEARCH_KEYWORD = "arg_search_keyword";


    public static final int ARG_SEARCH_TYPE_PERSON = 1;
    public static final int ARG_SEARCH_TYPE_RESOURCE = 2;


    @Bind(R.id.toolbar_search_text)
    TextView toolbarSearchText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_search_button)
    ImageView toolbarSearchButton;
    @Bind(R.id.mFilterContentView)
    TextView mFilterContentView;
    @Bind(R.id.search_drop_down_menu)
    DropDownMenu searchDropDownMenu;

    private SearchFilterEntity searchFilterEntity; //搜索过滤条件数据

    private PageDataSubscriberOnNext<SearchFilterEntity> subscriber;


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
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        ButterKnife.bind(this, view);
        httpRequestData();

        return view;
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<SearchFilterEntity>() {
            @Override
            public void onNext(SearchFilterEntity entity) {
                //接口请求成功后处理
                searchFilterEntity = entity;
//                ToastUtil.showToast(mActivity, entity.getAbility().toString() +"");
                initView(null);
                initFilterDropDownView();
            }
        };

        BaseDataHttpRequest.getInstance(mActivity).getSearchFilterArticles(new ProgressSubscriber(subscriber, mActivity));
    }

    private void initFilterDropDownView() {
        String[] titleList = new String[]{"行业", "职能", "排序"};
        searchDropDownMenu.setMenuAdapter(new SearchDropMenuAdapter(mActivity, titleList, searchFilterEntity, this));
    }

    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        if (position != 3) {
            searchDropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }

        searchDropDownMenu.close();
        mFilterContentView.setText(FilterUrl.instance().toString());
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
