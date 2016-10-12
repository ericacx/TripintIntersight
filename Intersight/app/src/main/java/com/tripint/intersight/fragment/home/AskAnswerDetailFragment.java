package com.tripint.intersight.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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
import com.tripint.intersight.adapter.PaymentDialogAdapter;
import com.tripint.intersight.adapter.PaymentSelectAdapter;
import com.tripint.intersight.adapter.listener.RecyclerViewItemOnClick;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.KeyboardUtils;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ListHolder;
import com.tripint.intersight.common.widget.dialogplus.OnItemClickListener;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemChildClickListener;
import com.tripint.intersight.entity.discuss.CommentEntity;
import com.tripint.intersight.entity.discuss.CommentResultEntity;
import com.tripint.intersight.entity.discuss.DiscussDetailEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;
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

    @Bind(R.id.container_voice_message)
    RelativeLayout containerVoiceMessage;

    BottomTabBarItem item1; // zan
    BottomTabBarItem item2; // 关注
    BottomTabBarItem item3; // 举报


    private AskAnswerPageDetailCommentAdapter mAdapter;

    private PageDataSubscriberOnNext<DiscussDetailEntity> subscriber;

    private PageDataSubscriberOnNext<CommentResultEntity> putSubscriber;

    private DiscussDetailEntity data;

    private int mDiscussId;

    private String currentAction = "";

    private CommentEntity currentSubCommentEntity; //创建子摩评论

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
//                    .placeholder(R.mipmap.ic_avatar)
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
            item1 = new BottomTabBarItem(mActivity, praises, "赞" + data.getDetail().getPraises());
            item1.setSelected(isPraises);
            item1.setTabPosition(1);
            item1.setLayoutParams(mTabParams);
            item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isPraises) {
                        currentAction = DiscussDataHttpRequest.TYPE_UNPRAISES;
                        DiscussDataHttpRequest.getInstance(mActivity).deleteParises(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId);

                    } else {
                        currentAction = DiscussDataHttpRequest.TYPE_PRAISES;
                        DiscussDataHttpRequest.getInstance(mActivity).createParises(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId);
                    }

                }
            });
            userCommentBar.addView(item1);

            item2 = new BottomTabBarItem(mActivity, follow, "关注" + data.getDetail().getFollows());
            item2.setSelected(isFollow);
            item2.setTabPosition(2);
            item2.setLayoutParams(mTabParams);
            item2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFollow) {
                        currentAction = DiscussDataHttpRequest.TYPE_UNFOLLOW;
                        DiscussDataHttpRequest.getInstance(mActivity).deleteFollow(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId);

                    } else {
                        currentAction = DiscussDataHttpRequest.TYPE_FOLLOW;
                        DiscussDataHttpRequest.getInstance(mActivity).createFollow(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId);
                    }
                }
            });
            userCommentBar.addView(item2);

            item3 = new BottomTabBarItem(mActivity, R.mipmap.iconfont_jubao, "举报");
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
                switch (currentAction) {

                    case DiscussDataHttpRequest.TYPE_COMMENT: //行业领域
                        CommonUtils.showToast("提交成功");
                        editUserCommentReplay.setText("");
                        break;
                    case DiscussDataHttpRequest.TYPE_SUB_COMMENT: //行业领域
                        CommonUtils.showToast("提交成功");
                        editUserCommentReplay.setText("");

                        break;
                    case DiscussDataHttpRequest.TYPE_FOLLOW: //我的
                        item2.setSelected(true);
                        item2.setTitle(entity.getTotal() + "");
                        break;
                    case DiscussDataHttpRequest.TYPE_UNFOLLOW: //我的
                        item2.setSelected(false);
                        item2.setTitle(entity.getTotal() + "");
                        break;
                    case DiscussDataHttpRequest.TYPE_PRAISES: //我的
                        item1.setSelected(true);
                        item1.setTitle(entity.getTotal() + "");
                        break;
                    case DiscussDataHttpRequest.TYPE_UNPRAISES:
                        item1.setSelected(false);
                        item1.setTitle(entity.getTotal() + "");
                        break;
                }
            }
        };


    }

    @OnClick({R.id.text_view_comment_submit, R.id.container_voice_message, R.id.btn_qa_record_voice})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.text_view_comment_submit: //行业领域
                if (currentSubCommentEntity == null) {
                    currentAction = DiscussDataHttpRequest.TYPE_SUB_COMMENT;
                } else {
                    currentAction = DiscussDataHttpRequest.TYPE_COMMENT;
                }
                String content = editUserCommentReplay.getText().toString();
                if (StringUtils.isEmpty(content)) {
                    CommonUtils.showToast("点评内容不能为空");
                } else {
                    if (currentSubCommentEntity == null) {
                        DiscussDataHttpRequest.getInstance(mActivity).createComment(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId, content);
                    } else {
                        DiscussDataHttpRequest.getInstance(mActivity).createSubComment(new ProgressSubscriber(putSubscriber, mActivity),
                                mDiscussId, content, currentSubCommentEntity.getId(), currentSubCommentEntity.getToNickname());

                    }
                }

                break;
            case R.id.container_voice_message: //我的关注
                List<PaymentEntity> paymentEntities = new ArrayList<>();

                paymentEntities.add(new PaymentEntity(1, "支付宝", "ALIPAY"));
                paymentEntities.add(new PaymentEntity(2, "微信支付", "WEIPAY"));
                PaymentSelectAdapter paymentDialogAdapter = new PaymentSelectAdapter(mActivity, paymentEntities);
                final DialogPlus dialogPlus = DialogPlusUtils.Builder(mActivity)
                        .setHolder(DialogPlusUtils.LIST, new ListHolder())
                        .setAdapter(paymentDialogAdapter)
                        .setTitleName("请选择支付方式")
                        .setIsHeader(true)
                        .setIsFooter(false)
                        .setIsExpanded(false)
                        .setGravity(Gravity.CENTER)
                        .showCompleteDialog();
                paymentDialogAdapter.setOnRecyclerViewItemOnClick(new RecyclerViewItemOnClick() {
                    @Override
                    public void ItemOnClick(int position, Object data) {
                        PaymentEntity select = (PaymentEntity) data;


//                        dialogPlus.dismiss();
                    }

                    @Override
                    public void ItemOnClick(int position, Object data, boolean isSelect) {

                    }
                });
//                dialogPlus.show();
                break;
            case R.id.btn_qa_record_voice: //我的关注

                break;

        }

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
                    case R.id.textView_item_ask_action:
                        currentSubCommentEntity = status;
                        content = "name:" + status.getCreateAt();
                        KeyboardUtils.showSoftInput(mActivity, editUserCommentReplay);
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
