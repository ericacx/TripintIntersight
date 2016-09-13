package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskRecyclerViewAdapter;
import com.tripint.intersight.bean.DataEntity;
import com.tripint.intersight.view.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskFragment extends Fragment implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, OnItemClickListener {


    @Bind(R.id.asdEtInput)
    EditText asdEtInput;
    @Bind(R.id.askIvSearch)
    ImageView askIvSearch;

    ConvenientBanner convenientBanner;
    RecyclerView askRecyclerView;

    private List<DataEntity> entityList;
    private AskRecyclerViewAdapter askRecyclerViewAdapter;
    private List<String> networkImages;

    private String[] images = {
            "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
    };
    private LinearLayoutManager linearLayoutManager;
    private DataEntity dataEntity;
    public AskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask, container, false);

        entityList = new ArrayList<DataEntity>();

        for (int i = 0; i < 30; i++) {
            dataEntity = new DataEntity(R.mipmap.ic_launcher, "Susan", "8年+", "销售总监", "腾讯（科技）信息有限公司", "互联网 | 游戏 | 软件");
            entityList.add(dataEntity);
        }

        convenientBanner = ((ConvenientBanner) view.findViewById(R.id.convenientBanner));
        askRecyclerView = ((RecyclerView) view.findViewById(R.id.askRecyclerView));
        linearLayoutManager = new LinearLayoutManager(getContext());
        askRecyclerViewAdapter = new AskRecyclerViewAdapter(getActivity(), entityList);
        askRecyclerView.setLayoutManager(linearLayoutManager);
        askRecyclerView.setAdapter(askRecyclerViewAdapter);


        initImageLoader();

        //网络加载
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //小圆点
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});

        ButterKnife.bind(this, view);
        return view;
    }


    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.ic_launcher)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
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
