package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageAdapter;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemChildClickListener;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.QAModel;
import com.tripint.intersight.widget.BannerViewHolder;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskAnswerFragment extends BaseBackFragment {

    @Bind(R.id.recycler_view_ask_answer)
    RecyclerView mRecyclerView;

    @Bind(R.id.btn_qa_profession)
    TextView mQAPrefessionButton;

    @Bind(R.id.btn_qa_interest)
    TextView mQAInterestrButton;

    @Bind(R.id.btn_qa_recommend)
    TextView mQARecommendButton;


    private AskAnswerPageAdapter mAdapter;

    private List<String> networkImages;

    private String[] images = {
            "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg"};

    public static AskAnswerFragment newInstance() {

        Bundle args = new Bundle();

        AskAnswerFragment fragment = new AskAnswerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_answer, container, false);
        ButterKnife.bind(this, view);

        initView(view);
        initAdapter();
        setTab(0);//默认选中第一个TAB
        return view;
    }

    private void initView(View view) {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //网络加载
        networkImages= Arrays.asList(images);



    }

    private View getHeaderView(View.OnClickListener clickListener){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_ask_answer_banner_header, null);
        view.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        ConvenientBanner mainBanner = (ConvenientBanner)view.findViewById(R.id.banner_qa_main);
        mainBanner.setPages(new CBViewHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createHolder() {
                return new BannerViewHolder();
            }
        }, networkImages)
                //小圆点
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        mainBanner.setOnClickListener(clickListener);
        return view;
    }

    @OnClick({R.id.btn_qa_recommend, R.id.btn_qa_interest, R.id.btn_qa_profession})
    public void onTabBarClick(View view){
        switch (view.getId()) {

            case R.id.btn_qa_profession: //行业领域
                if (!mQAPrefessionButton.isSelected()) {
                    setTab(0);
                }
                break;
            case R.id.btn_qa_interest: //我的关注
                if (!mQAInterestrButton.isSelected()) {
                    setTab(1);
                }
                break;
            case R.id.btn_qa_recommend: //精选推荐
                if (!mQARecommendButton.isSelected()) {
                    setTab(2);
                }
                break;
        }
    }

    /**
     * 请求不同的关键字 精选自由行、省心国内游、品质出境游
     *
     * @param tab
     */
    private void setTab(int tab) {
        mQAPrefessionButton.setSelected(tab == 0);
        mQAInterestrButton.setSelected(tab == 1);
        mQARecommendButton.setSelected(tab == 2);
//        httpLocationRelationShips(RequestParameters.homePssociate[tab]);
    }


    private void initAdapter() {
        mAdapter = new AskAnswerPageAdapter();
        mAdapter.openLoadAnimation();
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                QAModel status = (QAModel) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.image_ask_profile:
                        content = "img:" + status.getProfileImage();
                        break;
                    case R.id.textView_item_ask_title:
                        content = "name:" + status.getTitle();
                        break;
                }
                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
            }
        });

        mAdapter.addHeaderView(getHeaderView(getHeaderViewClickListener()));
        mRecyclerView.setAdapter(mAdapter);
    }

    private View.OnClickListener getHeaderViewClickListener(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(getActivity(), "Header view Click");
            }
        };
    }

}
