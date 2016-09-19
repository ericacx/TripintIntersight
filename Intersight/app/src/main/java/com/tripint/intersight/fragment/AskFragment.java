package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.tripint.intersight.R;
import com.tripint.intersight.fragment.base.BaseFragment;
import com.tripint.intersight.widget.BannerViewHolder;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskFragment extends BaseFragment implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, OnItemClickListener {


    private ConvenientBanner convenientBanner;
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

//        entityList = new ArrayList<DataEntity>();
//
//        for (int i = 0; i < 30; i++) {
//            dataEntity = new DataEntity(R.mipmap.ic_launcher, "Susan", "8年+", "销售总监", "腾讯（科技）信息有限公司", "互联网 | 游戏 | 软件");
//            entityList.add(dataEntity);
//        }

        convenientBanner = ((ConvenientBanner) view.findViewById(R.id.convenientBanner));

        //网络加载
        networkImages= Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createHolder() {
                return new BannerViewHolder();
            }
        }, networkImages)
                //小圆点
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});

        return view;
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

    @OnClick({R.id.askIvSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.askIvSearch:
                break;

        }
    }
}
