package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.tripint.intersight.R;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;
import com.tripint.intersight.fragment.search.SearchMainFragment;
import com.tripint.intersight.widget.BannerViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskFragment extends BaseLazyMainFragment implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, OnItemClickListener {


    @Bind(R.id.toolbar_search_text)
    TextView toolbarSearchText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @Bind(R.id.askRecyclerView)
    RecyclerView askRecyclerView;
    @Bind(R.id.toolbar_search_button)
    ImageView toolbarSearchButton;
    private List<String> networkImaAskFragmentges;

    private String[] images = {
            "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
    };
    private LinearLayoutManager linearLayoutManager;

    private List<String> networkImages;

    //    private DataEntity dataEntity;
    public static AskFragment newInstance() {

        Bundle args = new Bundle();

        AskFragment fragment = new AskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        //网络加载
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createHolder() {
                return new BannerViewHolder();
            }
        }, networkImages)
                //小圆点
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});

    }

    @OnClick({R.id.toolbar_search_button, R.id.toolbar_search_text})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.toolbar_search_button: //行业领域
                EventBus.getDefault().post(new StartFragmentEvent(SearchMainFragment.newInstance()));
                break;
            case R.id.toolbar_search_text: //行业领域
                EventBus.getDefault().post(new StartFragmentEvent(SearchMainFragment.newInstance()));
                break;

        }
    }

    //开始自动翻页
    @Override
    public void onStart() {
        super.onStart();
        convenientBanner.startTurning(2000);
    }

    // 停止自动翻页
    @Override
    public void onStop() {
        super.onStop();
        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
