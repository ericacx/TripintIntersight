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
import android.widget.EditText;
import android.widget.ImageView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.SearchDropMenuAdapter;
import com.tripint.intersight.adapter.SearchResultMultipleAdapter;
import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.utils.KeyboardUtils;
import com.tripint.intersight.common.widget.filter.DropDownMenu;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterDoneListener;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemClickListener;
import com.tripint.intersight.entity.SearchArticleEntity;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.entity.discuss.InterviewEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.personal.PersonalMainPageFragment;
import com.tripint.intersight.model.MultipleSearchItemModel;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.service.HttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lirichen on 2016/9/21.
 */

public class SearchResultFragment extends BaseBackFragment implements OnFilterDoneListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_SEARCH_TYPE = "arg_search_type";
    public static final String ARG_SEARCH_KEYWORD = "arg_search_keyword";


    public static final int ARG_SEARCH_TYPE_PERSON = 1;
    public static final int ARG_SEARCH_TYPE_RESOURCE = 2;


    @Bind(R.id.toolbar_search_text)
    EditText toolbarSearchText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_search_button)
    ImageView toolbarSearchButton;
    @Bind(R.id.search_drop_down_menu)
    DropDownMenu searchDropDownMenu;


    @Bind(R.id.recycler_view_ask_answer)
    RecyclerView mRecyclerView;

    @Bind(R.id.mFilterContentView)
    SwipeRefreshLayout mSwipeRefreshLayout;
//    @Bind(R.id.mFilterContentView)
//    FrameLayout mFilterContentView;

    private SearchFilterEntity searchFilterEntity; //搜索过滤条件数据

    private PageDataSubscriberOnNext<SearchFilterEntity> subscriber;

    private PageDataSubscriberOnNext<BasePageableResponse<InterviewEntity>> searchInterviewSubscriber;

    private PageDataSubscriberOnNext<BasePageableResponse<SearchArticleEntity>> searchArticleSubscriber;

    private SearchResultMultipleAdapter mAdapter;

    private BasePageableResponse<InterviewEntity> interviews = new BasePageableResponse<>();

    private BasePageableResponse<SearchArticleEntity> articles = new BasePageableResponse<>();


    private int TOTAL_COUNTER = 0;

    private int mCurrentCounter = 0;

    private String keyword = "";
    private String searchIndustry = "";
    private String searchAbility = "";

    private String sort = "";

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
        initView(null);
        initAdapter();
        return view;
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<SearchFilterEntity>() {
            @Override
            public void onNext(SearchFilterEntity entity) {
                //接口请求成功后处理
                searchFilterEntity = entity;
//                ToastUtil.showToast(mActivity, entity.getAbilityName().toString() +"");

                initFilterDropDownView();

                httpRequestSearchData(0);
            }
        };

        BaseDataHttpRequest.getInstance(mActivity).getSearchFilterArticles(new ProgressSubscriber(subscriber, mActivity));
    }

    private void httpRequestSearchData(int type) {
        searchInterviewSubscriber = new PageDataSubscriberOnNext<BasePageableResponse<InterviewEntity>>() {
            @Override
            public void onNext(BasePageableResponse<InterviewEntity> entity) {
                //接口请求成功后处理
                interviews = entity;
                List<MultipleSearchItemModel> models = getMultipleSearchInterViewItemModels(entity.getLists());
                if (mCurrentCounter == 0) {
                    mAdapter.setNewData(models);
                } else {
                    mAdapter.addData(models);
                }
                mCurrentCounter = mAdapter.getData().size();
                TOTAL_COUNTER = entity.getTotal();
            }
        };

        searchArticleSubscriber = new PageDataSubscriberOnNext<BasePageableResponse<SearchArticleEntity>>() {
            @Override
            public void onNext(BasePageableResponse<SearchArticleEntity> entity) {
                //接口请求成功后处理
                articles = entity;
                List<MultipleSearchItemModel> models = getMultipleSearchArticleItemModels(entity.getLists());
                if (mCurrentCounter == 0) {
                    mAdapter.setNewData(models);
                } else {
                    mAdapter.addData(models);
                }
                mCurrentCounter = mAdapter.getData().size();
                TOTAL_COUNTER = entity.getTotal();
            }
        };

        requestSearchData(-1, 0);

    }

    @NonNull
    private List<MultipleSearchItemModel> getMultipleSearchInterViewItemModels(List<InterviewEntity> entiries) {
        List<MultipleSearchItemModel> models = new ArrayList<>();

        if (entiries == null) return models;

        for (InterviewEntity entiry : entiries) {
            models.add(new MultipleSearchItemModel(searchType, entiry));
        }
        return models;
    }

    @NonNull
    private List<MultipleSearchItemModel> getMultipleSearchArticleItemModels(List<SearchArticleEntity> entiries) {
        List<MultipleSearchItemModel> models = new ArrayList<>();

        if (entiries == null) return models;

        for (SearchArticleEntity entiry : entiries) {
            models.add(new MultipleSearchItemModel(searchType, entiry));
        }
        return models;
    }

    private void initAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        if (ARG_SEARCH_TYPE_PERSON == searchType) {
            mAdapter = new SearchResultMultipleAdapter(getMultipleSearchInterViewItemModels(interviews.getLists()));
        } else {
            mAdapter = new SearchResultMultipleAdapter(getMultipleSearchArticleItemModels(articles.getLists()));
        }
        mAdapter.openLoadAnimation();

        mAdapter.openLoadMore(HttpRequest.DEFAULT_PAGE_SIZE);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                MultipleSearchItemModel entity = (MultipleSearchItemModel) adapter.getItem(position);
                //TODO
                if (entity.getItemType() == MultipleSearchItemModel.ARTICLE) {

                } else if (entity.getItemType() == MultipleSearchItemModel.INTERVIEW) {

                    EventBus.getDefault().post(new StartFragmentEvent(PersonalMainPageFragment.newInstance(entity.getInterview().getUid())));

                }
//                EventBus.getDefault().post(new StartFragmentEvent(AskAnswerDetailFragment.newInstance(entity.getContent())));
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
        mCurrentCounter = 0;
        if (searchType == ARG_SEARCH_TYPE_PERSON) {

            DiscussDataHttpRequest.getInstance(mActivity).searchSpecialLists(new ProgressSubscriber(searchInterviewSubscriber, mActivity), getCurrentPage(), searchIndustry, searchAbility, "");
        } else {
            DiscussDataHttpRequest.getInstance(mActivity).searchArticles(new ProgressSubscriber(searchArticleSubscriber, mActivity), getCurrentPage(), keyword, searchIndustry, searchAbility, sort);
        }
        mAdapter.openLoadMore(HttpRequest.DEFAULT_PAGE_SIZE);
        mAdapter.removeAllFooterView();

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
                    if (searchType == ARG_SEARCH_TYPE_PERSON) {

                        DiscussDataHttpRequest.getInstance(mActivity).searchSpecialLists(new ProgressSubscriber(searchInterviewSubscriber, mActivity), getCurrentPage(), searchIndustry, searchAbility, "");
                    } else {
                        DiscussDataHttpRequest.getInstance(mActivity).searchArticles(new ProgressSubscriber(searchArticleSubscriber, mActivity), getCurrentPage(), keyword, searchIndustry, searchAbility, sort);
                    }
                }
            }
        }, 200);


    }

    private void initFilterDropDownView() {
        String[] titleList = new String[]{"行业", "职能", "排序"};
        searchDropDownMenu.setMenuAdapter(new SearchDropMenuAdapter(mActivity, titleList, searchFilterEntity, this));
    }

    @Override
    public void onFilterDone(int position, int positionTitle, String urlValue) {
        if (position != 3) {
            searchDropDownMenu.setPositionIndicatorText(position, urlValue);

        }

        searchDropDownMenu.close();
        mCurrentCounter = 0; //重新加载列表内容，

        requestSearchData(position, positionTitle);
    }

    private void requestSearchData(int position, int positionTitle) {

        keyword = toolbarSearchText.getText().toString();

        if (position == 0) {
            searchIndustry = String.valueOf(positionTitle);
        } else if (position == 1) {
            sort = String.valueOf(positionTitle);
        } else if (position == 2) {
            searchAbility = String.valueOf(positionTitle);
        }

        if (searchType == ARG_SEARCH_TYPE_PERSON) {

            DiscussDataHttpRequest.getInstance(mActivity).searchSpecialLists(new ProgressSubscriber(searchInterviewSubscriber, mActivity), getCurrentPage(), searchIndustry, searchAbility, "");
        } else {
            DiscussDataHttpRequest.getInstance(mActivity).searchArticles(new ProgressSubscriber(searchArticleSubscriber, mActivity), getCurrentPage(), keyword, searchIndustry, searchAbility, sort);
        }
    }

    private void initView(View view) {

        initToolbarNav(toolbar);
        initToolbarMenu(toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(this);


    }

    public int getCurrentPage() {
        return mCurrentCounter / HttpRequest.DEFAULT_PAGE_SIZE + 1;
    }

    @OnClick({R.id.toolbar_search_button})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.toolbar_search_button: //行业领域
                KeyboardUtils.hideSoftInput(mActivity, toolbarSearchText);
                requestSearchData(-1, 0);

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
