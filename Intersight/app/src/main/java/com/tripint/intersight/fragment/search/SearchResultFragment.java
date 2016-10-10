package com.tripint.intersight.fragment.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.SearchDropMenuAdapter;
import com.tripint.intersight.adapter.SearchResultMultipleAdapter;
import com.tripint.intersight.common.widget.filter.DropDownMenu;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterDoneListener;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.discuss.DiscussPageEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.home.AskAnswerDetailFragment;
import com.tripint.intersight.model.MultipleSearchItemModel;
import com.tripint.intersight.model.search.FilterUrl;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lirichen on 2016/9/21.
 */

public class SearchResultFragment extends BaseBackFragment implements OnFilterDoneListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener  {

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
    @Bind(R.id.search_drop_down_menu)
    DropDownMenu searchDropDownMenu;

    @Bind(R.id.recycler_view_ask_answer)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SearchFilterEntity searchFilterEntity; //搜索过滤条件数据

    private PageDataSubscriberOnNext<SearchFilterEntity> subscriber;

    private PageDataSubscriberOnNext<DiscussPageEntity> searchSubscriber;

    private SearchResultMultipleAdapter mAdapter;

    private DiscussPageEntity data = new DiscussPageEntity();


    private final int PAGE_SIZE = 1;

    private int TOTAL_COUNTER = 4;

    private int mCurrentCounter = 0;


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
        initAdapter();

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
                httpRequestSearchData(0);
            }
        };

        BaseDataHttpRequest.getInstance(mActivity).getSearchFilterArticles(new ProgressSubscriber(subscriber, mActivity));
    }

    private void httpRequestSearchData(int type) {
        searchSubscriber = new PageDataSubscriberOnNext<DiscussPageEntity>() {
            @Override
            public void onNext(DiscussPageEntity entity) {
                //接口请求成功后处理
                data = entity;
                List<MultipleSearchItemModel> models = getMultipleSearchItemModels(entity.getDiscuss());
                mAdapter.addData(models);
            }
        };


        DiscussDataHttpRequest.getInstance(mActivity).getDiscusses(new ProgressSubscriber(searchSubscriber, mActivity), type, 1, PAGE_SIZE);
    }

    @NonNull
    private List<MultipleSearchItemModel> getMultipleSearchItemModels(List<DiscussEntiry> entiries) {
        List<MultipleSearchItemModel> models = new ArrayList<>();

        int i = 1;
        for (DiscussEntiry entiry : entiries){
            int type = i % 2;

            models.add(new MultipleSearchItemModel(type, entiry));
            i++;
        }
        return models;
    }

    private void initAdapter() {
        mAdapter = new SearchResultMultipleAdapter(getMultipleSearchItemModels(data.getDiscuss()));
        mAdapter.openLoadAnimation();

        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                DiscussEntiry entity = (DiscussEntiry) adapter.getItem(position);
                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(entity)));
            }
        });

        mAdapter.setLoadingView(getLoadMoreView());

        mRecyclerView.setAdapter(mAdapter);
    }

    private View getLoadMoreView() {
        final View customLoading = LayoutInflater.from(mActivity).inflate(R.layout.common_loading, (ViewGroup) mRecyclerView.getParent(), false);
        return customLoading;
    }


    @Override
    public void onRefresh() {
        mAdapter.setNewData(getMultipleSearchItemModels(data.getDiscuss()));
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.removeAllFooterView();
        mCurrentCounter = PAGE_SIZE;
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onLoadMoreRequested() {

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    mAdapter.loadComplete();

                } else {
                    mAdapter.addData(getMultipleSearchItemModels(data.getDiscuss()));
                    mCurrentCounter = mAdapter.getData().size();
                }
            }
        }, 200);


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
        httpRequestSearchData(2);
    }

    private void initView(View view) {

        initToolbarNav(toolbar);
        initToolbarMenu(toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
