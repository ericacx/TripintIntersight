package com.tripint.intersight.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageAdapter;
import com.tripint.intersight.adapter.AskAnswerPageDetailMultipleAdapter;
import com.tripint.intersight.common.fragmentation.SupportFragment;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemChildClickListener;
import com.tripint.intersight.event.TabSelectedEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.model.MultipleChatItemModel;
import com.tripint.intersight.model.QADetailModel;
import com.tripint.intersight.model.QAModel;
import com.tripint.intersight.widget.BannerViewHolder;
import com.tripint.intersight.widget.tabbar.BottomTabBar;
import com.tripint.intersight.widget.tabbar.BottomTabBarItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskAnswerDetailFragment extends BaseBackFragment {

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


    public static AskAnswerDetailFragment newInstance() {

        Bundle args = new Bundle();

        AskAnswerDetailFragment fragment = new AskAnswerDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_answer_detail, container, false);
        ButterKnife.bind(this, view);

        initView(view);
        initAdapter();
        return view;
    }

    private void initView(View view) {

        toolbar.setTitle("问答");
        initToolbarNav(toolbar);
        initToolbarMenu(toolbar);
        //网络加载
        userCommentBar
                .addItem(new BottomTabBarItem(mActivity, R.mipmap.ic_launcher, "赞157"))
                .addItem(new BottomTabBarItem(mActivity, R.drawable.ic_expandable, "关注"))
                .addItem(new BottomTabBarItem(mActivity, R.drawable.ic_expandable, "举报"));
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


    private void initAdapter() {
        mAdapter = new AskAnswerPageDetailMultipleAdapter(getSampleData(1));
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


    public static List<MultipleChatItemModel> getSampleData(int lenth) {
        List<MultipleChatItemModel> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {


            QADetailModel status = new QADetailModel();
            status.setCompany("腾讯信息科技" + i);
            status.setJobTitle("设计总经理" + "");
            status.setPersonName("Maggie ");
            status.setMessage("目前AR技术的整体发展情况及未来发展情况与未来的发展方向,最新动态最新内容说明" + i);
            status.setProfileImgUrl("https://avatars2.githubusercontent.com/u/6078720?v=3&s=200");
            status.setVoiceMessageUrl("https://avatars2.githubusercontent.com/u/6066620?v=3&s=400");
//            model.setContent(status);
            MultipleChatItemModel model = new MultipleChatItemModel(MultipleChatItemModel.CHAT_LEFT, status);
            list.add(model);
            MultipleChatItemModel model1 = new MultipleChatItemModel(MultipleChatItemModel.CHAT_RIGHT, status);
            list.add(model1);
        }
        return list;
    }
}
