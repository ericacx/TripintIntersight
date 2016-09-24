package com.tripint.intersight.fragment.discuss;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageDetailMultipleAdapter;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemChildClickListener;
import com.tripint.intersight.entity.discuss.DiscussDetailEntiry;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.discuss.DiscussPageEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MultipleChatItemModel;
import com.tripint.intersight.model.QADetailModel;
import com.tripint.intersight.model.QAModel;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;
import com.tripint.intersight.widget.tabbar.BottomTabBar;
import com.tripint.intersight.widget.tabbar.BottomTabBarItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskAnswerDetailFragment extends BaseBackFragment {

    public static final String ARG_DISCUSS_ID = "arg_discuss_id";

    @Bind(R.id.recycler_view_ask_answer_detail)
    RecyclerView recyclerViewAskAnswerDetail;
    @Bind(R.id.text_qa_info)
    TextView textQaInfo;
    @Bind(R.id.btn_qa_record_voice)
    Button btnQaRecordVoice;
    @Bind(R.id.speaker_container)
    LinearLayout layoutSpeakerContainer;
    @Bind(R.id.user_comment_bar)
    BottomTabBar userCommentBar;
    @Bind(R.id.recycler_view_ask_answer_comment)
    RecyclerView recyclerViewAskAnswerComment;
    @Bind(R.id.user_comment_container)
    LinearLayout layoutUserCommentContainer;

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    private AskAnswerPageDetailMultipleAdapter mAdapter;

    private PageDataSubscriberOnNext<DiscussDetailEntiry> subscriber;

    private DiscussDetailEntiry data;

    private int mDiscussId;

    public static AskAnswerDetailFragment newInstance(DiscussEntiry entiry) {

        Bundle args = new Bundle();
        args.putInt(AskAnswerDetailFragment.ARG_DISCUSS_ID, entiry.getId());
        AskAnswerDetailFragment fragment = new AskAnswerDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            mDiscussId = bundle.getInt(ARG_DISCUSS_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_answer_detail, container, false);
        ButterKnife.bind(this, view);

        httpRequestData();
        return view;
    }

    private void initView(View view) {

        toolbar.setTitle("问答详情");
        initToolbarNav(toolbar);
        initToolbarMenu(toolbar);
        //网络加载
        userCommentBar
                .addItem(new BottomTabBarItem(mActivity, R.mipmap.iconfont_zan02, "赞157"))
                .addItem(new BottomTabBarItem(mActivity, R.mipmap.iconfont_heartbig01, "关注"))
                .addItem(new BottomTabBarItem(mActivity, R.mipmap.iconfont_jubao, "举报"));
        userCommentBar.setBackgroundColor(getResources().getColor(R.color.colorBottomBarPrimary));
        userCommentBar.setOnTabSelectedListener(new BottomTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                //showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });


    }


    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<DiscussDetailEntiry>() {
            @Override
            public void onNext(DiscussDetailEntiry entity) {
                //接口请求成功后处理
                data = entity;
//                ToastUtil.showToast(mActivity, entity.getAbility().toString() +"");
                initView(null);
                initAdapter();
            }
        };


        DiscussDataHttpRequest.getInstance().getDiscussDetail(new ProgressSubscriber(subscriber, mActivity), mDiscussId);
    }


    private void initAdapter() {
        List<MultipleChatItemModel> models = new ArrayList<>();

        int i = 1;
        for (DiscussEntiry entiry : data.getDiscussDetail()){
            int type = i % 2;

            models.add(new MultipleChatItemModel(type, entiry));
            i++;
        }

        mAdapter = new AskAnswerPageDetailMultipleAdapter(models);
        final GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 1);
        mAdapter.openLoadAnimation();
        recyclerViewAskAnswerDetail.addOnItemTouchListener(new OnItemChildClickListener() {
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

//        mAdapter.addHeaderView(getHeaderView(getHeaderViewClickListener()));
        recyclerViewAskAnswerDetail.setLayoutManager(layoutManager);
        recyclerViewAskAnswerDetail.setAdapter(mAdapter);
    }

    private View.OnClickListener getHeaderViewClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(getActivity(), "Header view Click");
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }


}
