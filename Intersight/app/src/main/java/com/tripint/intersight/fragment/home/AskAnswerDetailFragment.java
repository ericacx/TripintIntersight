package com.tripint.intersight.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageDetailCommentAdapter;
import com.tripint.intersight.adapter.AskAnswerPageDetailMultipleAdapter;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.filter.util.CommonUtil;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemChildClickListener;
import com.tripint.intersight.entity.discuss.CommentEntity;
import com.tripint.intersight.entity.discuss.CommentResultEntity;
import com.tripint.intersight.entity.discuss.DiscussDetailEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.model.MultipleChatItemModel;
import com.tripint.intersight.model.QAModel;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;
import com.tripint.intersight.widget.tabbar.BottomTabBar;
import com.tripint.intersight.widget.tabbar.BottomTabBarItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskAnswerDetailFragment extends BaseBackFragment {

    public static final String ARG_DISCUSS_ID = "arg_discuss_id";

    @Bind(R.id.text_qa_info)
    TextView textQaInfo;
    @Bind(R.id.btn_qa_record_voice)
    Button btnQaRecordVoice;
    @Bind(R.id.speaker_container)
    LinearLayout layoutSpeakerContainer;
    @Bind(R.id.user_comment_bar)
    LinearLayout userCommentBar;
    @Bind(R.id.recycler_view_ask_answer_comment)
    RecyclerView recyclerViewAskAnswerComment;
    @Bind(R.id.user_comment_container)
    LinearLayout layoutUserCommentContainer;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.image_ask_profile)
    CircleImageView imageAskProfile;
    @Bind(R.id.textView_item_ask_specialist)
    TextView textViewItemAskSpecialist;
    @Bind(R.id.textView_item_ask_title)
    TextView textViewItemAskTitle;
    @Bind(R.id.textView_item_ask_date_time)
    TextView textViewItemAskDateTime;
    @Bind(R.id.container_chat_author)
    LinearLayout containerChatAuthor;
    @Bind(R.id.textView_item_answer_specialist)
    TextView textViewItemAnswerSpecialist;
    @Bind(R.id.textView_item_answer_title)
    TextView textViewItemAnswerTitle;
    @Bind(R.id.textView_item_answer_payment)
    TextView textViewItemAnswerPayment;
    @Bind(R.id.textView_item_answer_date_time)
    TextView textViewItemAnswerDateTime;
    @Bind(R.id.image_answer_profile)
    CircleImageView imageAnswerProfile;
    @Bind(R.id.container_chat_reply)
    LinearLayout containerChatReply;
    @Bind(R.id.text_view_comment_submit)
    TextView textViewCommentSubmit;
    @Bind(R.id.edit_user_comment_replay)
    EditText editUserCommentReplay;
    @Bind(R.id.user_replay_container)
    RelativeLayout userReplayContainer;


    private AskAnswerPageDetailCommentAdapter mAdapter;

    private PageDataSubscriberOnNext<DiscussDetailEntity> subscriber;

    private PageDataSubscriberOnNext<CommentResultEntity> putSubscriber;

    private DiscussDetailEntity data;

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
        if (bundle != null) {
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
        inithttpPutRequestData();

        if (data.getDetail() != null) {
            textViewItemAskTitle.setText(data.getDetail().getContent());
            textViewItemAskDateTime.setText(data.getDetail().getCreateAt());
            String special = data.getAuthor().getNickname();
            if (data.getDetail().getUserInfo() != null) {
                if (data.getDetail().getUserInfo().getAbility() != null) {
                    special += data.getAuthor().getAbility().getName();
                }
                if (data.getDetail().getUserInfo().getIndustry() != null) {
                    special += data.getAuthor().getIndustry().getName();
                }
                textViewItemAnswerSpecialist.setText(special);

            }
            if (data.getDetail().getVoices() != null) {
                textViewItemAnswerTitle.setText(data.getDetail().getVoices().getTimeLong() + "s");
                textViewItemAnswerPayment.setText(data.getDetail().getVoices().getPayment() + "元即听");
            }
            textViewItemAnswerDateTime.setText(data.getDetail().getCreateAt());
        }

        if (data.getAuthor() != null) {

            Glide.with(mActivity).load(data.getAuthor().getAvatar())
                    .crossFade()
                    .placeholder(R.mipmap.ic_avatar)
                    .transform(new GlideCircleTransform(mActivity))
                    .into(imageAskProfile);
            String special = data.getAuthor().getNickname();
            if (data.getAuthor().getAbility() != null) {
                special += data.getAuthor().getAbility().getName();
            }
            if (data.getAuthor().getIndustry() != null) {
                special += data.getAuthor().getIndustry().getName();
            }
            textViewItemAskSpecialist.setText(special);
        }


        if (data.getDetail() != null) {
            //
            LinearLayout.LayoutParams mTabParams;

            mTabParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            mTabParams.weight = 1;
            final boolean isPraises = data.getDetail().getIsPraises() == 1001;
            final boolean isFollow = data.getDetail().getIsFollows() == 1001;
            int praises = isPraises ? R.mipmap.iconfont_zan01 : R.mipmap.iconfont_zan02;
            int follow = isFollow ? R.mipmap.iconfont_heartbig01 : R.mipmap.iconfont_heartbig02;
            BottomTabBarItem item1 = new BottomTabBarItem(mActivity, praises, "赞" + data.getDetail().getPraises());
            item1.setSelected(isPraises);
            item1.setTabPosition(1);
            item1.setLayoutParams(mTabParams);
            item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isPraises){
                        DiscussDataHttpRequest.getInstance(mActivity).deleteParises(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId);

                    }else {
                        DiscussDataHttpRequest.getInstance(mActivity).createParises(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId);
                    }

                }
            });
            userCommentBar.addView(item1);

            BottomTabBarItem item2 = new BottomTabBarItem(mActivity, follow, "关注" + data.getDetail().getFollows());
            item2.setSelected(isFollow);
            item2.setTabPosition(2);
            item2.setLayoutParams(mTabParams);
            item2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFollow){
                        DiscussDataHttpRequest.getInstance(mActivity).deleteFollow(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId);

                    }else {
                        DiscussDataHttpRequest.getInstance(mActivity).createFollow(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId);
                    }
                }
            });
            userCommentBar.addView(item2);

            BottomTabBarItem item3 = new  BottomTabBarItem(mActivity, R.mipmap.iconfont_jubao, "举报");
            item3.setSelected(false);
            item3.setTabPosition(3);
            item3.setLayoutParams(mTabParams);
            item3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.showToast("jubao");
                }
            });
            userCommentBar.addView(item3);
//            userCommentBar
//                    .addItem(new BottomTabBarItem(mActivity, praises, "赞" + data.getDetail().getPraises()))
//                    .addItem(new BottomTabBarItem(mActivity, follow, "关注" + data.getDetail().getFollows()))
//                    .addItem(new BottomTabBarItem(mActivity, R.mipmap.iconfont_jubao, "举报"));
//            userCommentBar.setBackgroundColor(getResources().getColor(R.color.colorBottomBarPrimary));
//            userCommentBar.setOnTabSelectedListener(new BottomTabBar.OnTabSelectedListener() {
//                @Override
//                public void onTabSelected(int position, int prePosition) {
//                    //showHideFragment(mFragments[position], mFragments[prePosition]);
//                }
//
//                @Override
//                public void onTabUnselected(int position) {
//
//                }
//
//                @Override
//                public void onTabReselected(int position) {
//
//                }
//            });
        }

    }


    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<DiscussDetailEntity>() {
            @Override
            public void onNext(DiscussDetailEntity entity) {
                //接口请求成功后处理
                data = entity;
//                ToastUtil.showToast(mActivity, entity.getAbility().toString() +"");
                initView(null);
                initCommentAdapter();
            }
        };


        DiscussDataHttpRequest.getInstance(mActivity).getDiscussDetail(new ProgressSubscriber(subscriber, mActivity), mDiscussId);
    }

    private void inithttpPutRequestData() {
        putSubscriber = new PageDataSubscriberOnNext<CommentResultEntity>() {
            @Override
            public void onNext(CommentResultEntity entity) {
                CommonUtils.showToast(entity.getFlg());
            }
        };


    }

    @OnClick({R.id.text_view_comment_submit})
    public void onClick(View view){
       String content = editUserCommentReplay.getText().toString();
            DiscussDataHttpRequest.getInstance(mActivity).createComment(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId, content);

    }


    private void initCommentAdapter() {



        mAdapter = new AskAnswerPageDetailCommentAdapter(data.getComments());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mAdapter.openLoadAnimation();
        recyclerViewAskAnswerComment.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                CommentEntity status = (CommentEntity) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.image_ask_profile:
                        content = "img:" + status.getContent();
                        break;
                    case R.id.textView_item_ask_title:
                        content = "name:" + status.getCreateAt();
                        break;
                }
                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
            }
        });

        recyclerViewAskAnswerComment.setLayoutManager(layoutManager);
        recyclerViewAskAnswerComment.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }


}
