package com.tripint.intersight.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;
import com.tripint.intersight.common.flowlayout.FlowLayout;
import com.tripint.intersight.common.flowlayout.TagAdapter;
import com.tripint.intersight.common.flowlayout.TagFlowLayout;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.entity.Ability;
import com.tripint.intersight.entity.Company;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.SearchRequestModel;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：wangdakuan
 * 主要功能:搜索页面
 * 创建时间：2016/3/24 15:22
 */
public class SearchPersonFragment extends BaseBackFragment {

    public static final String KEY_WORD = "关键字";
    public static final String PAGE = "SearchFragment";
    @Bind(R.id.toolbar_search_button)
    ImageView toolbarSearchButton;
    @Bind(R.id.toolbar_search_text)
    EditText toolbarSearchText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.keyword_flowlayout)
    TagFlowLayout keywordFlowlayout;
    @Bind(R.id.keyword_layout)
    LinearLayout keywordLayout;
    @Bind(R.id.hot_flowlayout)
    TagFlowLayout hotFlowlayout;
    @Bind(R.id.history_flowlayout)
    TagFlowLayout historyFlowlayout;
    @Bind(R.id.btn_empty)
    TextView btnEmpty;
    @Bind(R.id.history_layout)
    LinearLayout historyLayout;
    @Bind(R.id.company_flowlayout)
    TagFlowLayout companyFlowlayout;
    @Bind(R.id.company_layout)
    LinearLayout companyLayout;
    @Bind(R.id.hot_layout)
    LinearLayout hotLayout;


    private SearchFilterEntity searchFilterEntity; //热门关键字数据
    private ArrayList<String> historyList = new ArrayList<>(); //历史记录
    private ArrayList<String> keywordsList = new ArrayList<>(); //关键词
    private ArrayList<String> hotList = new ArrayList<>(); //历史记录
    private ArrayList<String> companyList = new ArrayList<>(); //关键词

    private TagAdapter<String> mKeywordAdapter;
    private TagAdapter<String> mHotAdapter;
    private TagAdapter<String> mHistoryAdapter;
    private TagAdapter<String> mCompanyAdapter;

    private String homeCityName;
    private String keyWord = "";
    //注册监听 定位数据

    private LayoutInflater mInflater;

    private PageDataSubscriberOnNext<SearchFilterEntity> subscriber;


    public static SearchPersonFragment newInstance() {

        Bundle args = new Bundle();

        SearchPersonFragment fragment = new SearchPersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInflater = LayoutInflater.from(mActivity);

        historyList = (ArrayList) ACache.get(mActivity).getAsObject(EnumKey.ACacheKey.HISTORY_CACHE_CITY);
        if (null == historyList) {
            historyList = new ArrayList<>();
        }
        searchFilterEntity = (SearchFilterEntity) ACache.get(mActivity).getAsObject(EnumKey.ACacheKey.KEYWORD_CACHE); //关键词获取缓存

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_person, null);
        ButterKnife.bind(this, view);
        httpRequestData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<SearchFilterEntity>() {
            @Override
            public void onNext(SearchFilterEntity entity) {
                //接口请求成功后处理
                searchFilterEntity = entity;
//                ToastUtil.showToast(mActivity, entity.getAbility().toString() +"");
                initView(null);
            }
        };

        BaseDataHttpRequest.getInstance().getSearchFilterInterviewer(new ProgressSubscriber(subscriber, mActivity));
    }


    private void setListener() {
        toolbarSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (StringUtils.isEmpty(v.getText().toString())) {
                    ToastUtil.showToast(mActivity, "请输入搜索内容");
                } else if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    show(toolbarSearchText.getText().toString());
                }
                return false;
            }

        });


        toolbarSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        keywordFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                show(keywordsList.get(position));
                return true;
            }
        });

        historyFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                show(historyList.get(position));
                return true;
            }
        });
        hotFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                show(hotList.get(position));
                return true;
            }
        });

        companyFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                show(companyList.get(position));
                return true;
            }
        });
    }

    private void show(String _key) {
        if (StringUtils.isEmpty(_key)) {
            ToastUtil.showToast(mActivity, "请输入搜索内容");
        } else {
            if (null == historyList) {
                historyList = new ArrayList<>();
            }
            if (historyList.contains(_key)) {
                historyList.remove(_key);
                historyList.add(0, _key);
            } else {
                historyList.add(0, _key);
                if (historyList.size() > 9) {
                    historyList.remove(9);
                }
            }
            if (historyList.size() > 0) {
                historyLayout.setVisibility(View.VISIBLE);
            } else {
                historyLayout.setVisibility(View.GONE);
            }
            if (null != mHistoryAdapter) {
                mHistoryAdapter.notifyDataChanged();
            }
            saveHistoryCache();
            showSearchListByKeyword(new SearchRequestModel(toolbarSearchText.getText().toString(), ""));

            toolbarSearchText.setText("");
        }
    }

    private void saveHistoryCache() {
        ACache.get(mActivity).remove(EnumKey.ACacheKey.HISTORY_CACHE_CITY);
        ACache.get(mActivity).put(EnumKey.ACacheKey.HISTORY_CACHE_CITY, historyList);
    }

    /**
     * 跳转搜索结果页面
     *
     * @param searchVO
     */
    public void showSearchListByKeyword(SearchRequestModel searchVO) {
        Bundle bundle = new Bundle();
        bundle.putInt(SearchResultFragment.ARG_SEARCH_TYPE, SearchResultFragment.ARG_SEARCH_TYPE_PERSON);
        bundle.putSerializable(SearchResultFragment.ARG_SEARCH_KEYWORD, searchVO);
        EventBus.getDefault().post(SearchResultFragment.newInstance(bundle));
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    public void initView(View view) {

        for (Industry industry : this.searchFilterEntity.getIndustry()) {
            keywordsList.add(industry.getName());
        }

        for (Ability ability : this.searchFilterEntity.getAbility()) {
            historyList.add(ability.getName());
        }

        for (Company company : this.searchFilterEntity.getKeywords()) {
            companyList.add(company.getName());
        }

        for (Ability ability : this.searchFilterEntity.getAbility()) {
            hotList.add(ability.getName());
        }


        historyFlowlayout.setAdapter(mHistoryAdapter = new TagAdapter<String>(historyList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.item_tv_search_tag,
                        historyFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        keywordFlowlayout.setAdapter(mKeywordAdapter = new TagAdapter<String>(keywordsList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.item_tv_search_tag,
                        keywordFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        hotFlowlayout.setAdapter(mHotAdapter = new TagAdapter<String>(hotList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.item_tv_search_tag,
                        hotFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        companyFlowlayout.setAdapter(mCompanyAdapter = new TagAdapter<String>(companyList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.item_tv_search_tag,
                        companyFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        if (historyList.size() > 0) {
            historyLayout.setVisibility(View.VISIBLE);
        } else {
            historyLayout.setVisibility(View.GONE);
        }

        setListener();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }
}
