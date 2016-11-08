package com.tripint.intersight.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageDetailCommentAdapter;
import com.tripint.intersight.common.utils.KeyboardUtils;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemChildClickListener;
import com.tripint.intersight.entity.article.ArticleCommentEntity;
import com.tripint.intersight.entity.article.ArticleDetailEntity;
import com.tripint.intersight.entity.article.OpinionDetailEntity;
import com.tripint.intersight.entity.discuss.CommentEntity;
import com.tripint.intersight.entity.discuss.CommentResultEntity;
import com.tripint.intersight.event.PersonalEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.service.CommonDataHttpRequest;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;
import com.tripint.intersight.widget.tabbar.BottomTabBarItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpinionDetailFragment extends BaseBackFragment {

    public static final String ARG_ARTICLE_ID = "arg_article_id";
    public static final String ARG_TYPE = "arg_type";
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.opinion_flipview_two_pic)
    ImageView opinionFlipviewTwoPic;
    @Bind(R.id.opinion_flipview_two_header)
    TextView opinionFlipviewTwoHeader;
    @Bind(R.id.opinion_flipview_two_name)
    TextView opinionFlipviewTwoName;
    @Bind(R.id.opinion_flipview_two_trade)
    TextView opinionFlipviewTwoTrade;
    @Bind(R.id.opinion_flipview_two_title)
    TextView opinionFlipviewTwoTitle;
    @Bind(R.id.opinion_detail_praise)
    LinearLayout opinionDetailPraise;
    @Bind(R.id.opinion_flipview_two_time)
    TextView opinionFlipviewTwoTime;
    @Bind(R.id.opinion_flipview_two_content)
    TextView opinionFlipviewTwoContent;
    @Bind(R.id.opinion_detail_ll_img_text)
    LinearLayout opinionDetailLlImgText;


    @Bind(R.id.opioion_flipview_three_content)
    TextView opioionFlipviewThreeContent;
    @Bind(R.id.opinion_flipview_three_name)
    TextView opinionFlipviewThreeName;
    @Bind(R.id.opinion_flipview_three_trade)
    TextView opinionFlipviewThreeTrade;
    @Bind(R.id.opinion_flipview_three_title)
    TextView opinionFlipviewThreeTitle;
    @Bind(R.id.opinion_detail_ll_praise)
    LinearLayout opinionDetailLlPraise;
    @Bind(R.id.opinion_flipview_three_time)
    TextView opinionFlipviewThreeTime;
    @Bind(R.id.opinion_flipview_ll_detail_text)
    LinearLayout opinionFlipviewLlDetailText;


    @Bind(R.id.recyclerView)
    RecyclerView recyclerViewAskAnswerComment;
    @Bind(R.id.text_view_comment_submit)
    TextView textViewCommentSubmit;
    @Bind(R.id.edit_user_comment_replay)
    EditText editUserCommentReplay;
    @Bind(R.id.user_replay_container)
    RelativeLayout userReplayContainer;
    BottomTabBarItem item1; // zan
    BottomTabBarItem item2; // 关注
    BottomTabBarItem item3; // 举报
    private int type;
    private int articleId;
    private PageDataSubscriberOnNext<CommentResultEntity> putSubscriber;
    private PageDataSubscriberOnNext<ArticleDetailEntity> subscriber;
    private ArticleDetailEntity data;
    private AskAnswerPageDetailCommentAdapter mAdapter;
    private int toUid;
    private int userId;
    private int pid;//评论的id
    private String currentAction = "";
    private ArticleCommentEntity currentSubCommentEntity; //创建子摩评论
    private boolean isPraises;
    private boolean isFollow;

    public static OpinionDetailFragment newInstance(int type, int articleId) {
        // Required empty public constructor
        OpinionDetailFragment fragment = new OpinionDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ARTICLE_ID, articleId);
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(ARG_TYPE);
            articleId = bundle.getInt(ARG_ARTICLE_ID);
            Log.e("articleId", String.valueOf(articleId));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opinion_detail, container, false);
        ButterKnife.bind(this, view);
        httpRequestData();
        return view;
    }

    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<ArticleDetailEntity>() {
            @Override
            public void onNext(ArticleDetailEntity entity) {
                //接口请求成功后处理
                data = entity;
//                ToastUtil.showToast(mActivity, entity.getAbilityName().toString() +"");
                initView(null);
                initCommentAdapter();
            }
        };

        BaseDataHttpRequest.getInstance(mActivity).getArticleDetail(new ProgressSubscriber(subscriber, mActivity), articleId);
    }

    private void initView(View view) {

        toolbar.setTitle("观点详情");
        initToolbarNav(toolbar);
        initToolbarMenu(toolbar);
        inithttpPutRequestData();

        if (data.getDetail() != null) {
            OpinionDetailEntity entity = data.getDetail();
            toUid = entity.getUserId();
            if (type == 1) {//图文

                opinionDetailLlImgText.setVisibility(View.VISIBLE);
                opinionFlipviewLlDetailText.setVisibility(View.GONE);

                Glide.with(mActivity).load(entity.getThumb())//图片
                        .crossFade()
                        .placeholder(R.drawable.loading_normal_icon)
                        .into(opinionFlipviewTwoPic);


                opinionFlipviewTwoHeader.setText(entity.getTitle());//标题
                opinionFlipviewTwoName.setText(entity.getUserNickname());
                opinionFlipviewTwoTrade.setText(entity.getUserCompany());
                opinionFlipviewTwoTitle.setText(entity.getUserAbility());
                opinionFlipviewTwoContent.setText(entity.getContent());//内容
                opinionFlipviewTwoTime.setText(entity.getCreateAt());//时间

                initPraiseAndFocus(type);
            } else if (type == 2) {//纯文字

                opinionFlipviewLlDetailText.setVisibility(View.VISIBLE);
                opinionDetailLlImgText.setVisibility(View.GONE);

                opioionFlipviewThreeContent.setText(entity.getContent());
                opinionFlipviewThreeName.setText(entity.getUserNickname());
                opinionFlipviewThreeTrade.setText(entity.getUserCompany());
                opinionFlipviewThreeTitle.setText(entity.getUserAbility());
                opinionFlipviewThreeTime.setText(entity.getCreateAt());

                initPraiseAndFocus(type);
            }


        }

    }


    private void initPraiseAndFocus(int type) {
        //点赞 点关注 点
        LinearLayout.LayoutParams mTabParams;

        mTabParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTabParams.weight = 1;
        isPraises = data.getDetail().getIsPraises() == 1001;
        isFollow = data.getDetail().getIsFavorites() == 1001;
        int praises = isPraises ? R.mipmap.iconfont_zan01 : R.mipmap.iconfont_zan02;
        int follow = isFollow ? R.mipmap.iconfont_heartbig01 : R.mipmap.iconfont_heartbig02;
        item1 = new BottomTabBarItem(mActivity, praises, "赞" + data.getDetail().getPraisesCount());
        item1.setSelected(isPraises);
        item1.setTabPosition(1);
        item1.setLayoutParams(mTabParams);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPraises) {
                    //取消赞
                    currentAction = DiscussDataHttpRequest.TYPE_UNPRAISES;
                    CommonDataHttpRequest.getInstance(mActivity).deleteArticlePraise(new ProgressSubscriber(putSubscriber, mActivity), articleId, toUid);
                } else {
                    //点赞
                    currentAction = DiscussDataHttpRequest.TYPE_PRAISES;
                    CommonDataHttpRequest.getInstance(mActivity).createArticlePraise(new ProgressSubscriber(putSubscriber, mActivity), articleId, toUid);
                }

            }
        });

        item2 = new BottomTabBarItem(mActivity, follow, "关注" + data.getDetail().getFavoritesCount());
        item2.setSelected(isFollow);
        item2.setTabPosition(2);
        item2.setLayoutParams(mTabParams);
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollow) {
                    //取消关注
                    currentAction = DiscussDataHttpRequest.TYPE_UNFOLLOW;
                    CommonDataHttpRequest.getInstance(mActivity).deleteArticleFocus(new ProgressSubscriber<CommentResultEntity>(putSubscriber, mActivity), articleId, toUid);
                } else {
                    currentAction = DiscussDataHttpRequest.TYPE_FOLLOW;
                    CommonDataHttpRequest.getInstance(mActivity).createArticleFocus(new ProgressSubscriber<CommentResultEntity>(putSubscriber, mActivity), articleId, toUid);
                }

            }
        });

        item3 = new BottomTabBarItem(mActivity, R.mipmap.iconfont_jubao, "举报");
        item3.setSelected(false);
        item3.setTabPosition(3);
        item3.setLayoutParams(mTabParams);
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAction = DiscussDataHttpRequest.TYPE_REPORT;
                BaseDataHttpRequest.getInstance(mActivity).reportArticle(new ProgressSubscriber<CommentResultEntity>(putSubscriber, mActivity), data.getDetail().getId());
            }
        });
        if (type == 1) {
            opinionDetailPraise.addView(item1);
            opinionDetailPraise.addView(item2);
            opinionDetailPraise.addView(item3);
            opinionDetailPraise.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            opinionDetailLlPraise.addView(item1);
            opinionDetailLlPraise.addView(item2);
            opinionDetailLlPraise.addView(item3);
            opinionDetailLlPraise.setVisibility(View.VISIBLE);
        }

    }

    private void inithttpPutRequestData() {
        putSubscriber = new PageDataSubscriberOnNext<CommentResultEntity>() {
            @Override
            public void onNext(CommentResultEntity entity) {
                switch (currentAction) {

                    case DiscussDataHttpRequest.TYPE_COMMENT: //行业领域
                        ToastUtil.showToast(mActivity, "提交成功");
//                        ArticleCommentEntity articleCommentEntity = new ArticleCommentEntity();
//                        articleCommentEntity.setId(data.getComments().get(0).getId()+1);
//                        articleCommentEntity.setContent(editUserCommentReplay.getText().toString());
//                        data.getComments().add(articleCommentEntity);
//                        mAdapter.notifyDataSetChanged();
                        editUserCommentReplay.setText("");
                        break;
                    case DiscussDataHttpRequest.TYPE_SUB_COMMENT: //行业领域
                        ToastUtil.showToast(mActivity, "提交成功");
                        editUserCommentReplay.setText("");
                        httpRequestData();
                        break;
                    case DiscussDataHttpRequest.TYPE_FOLLOW: //关注
                        item2.setSelected(true);
                        item2.setTitle(entity.getTotal() + "");
                        ToastUtil.showToast(mActivity, "关注成功");
                        isFollow = data.getDetail().getIsFavorites() == 1000;
                        break;
                    case DiscussDataHttpRequest.TYPE_UNFOLLOW: //我的
                        item2.setSelected(false);
                        item2.setTitle(entity.getTotal() + "");
                        ToastUtil.showToast(mActivity, "取消关注成功");
                        isFollow = data.getDetail().getIsFavorites() == 1001;
                        break;
                    case DiscussDataHttpRequest.TYPE_PRAISES: //我的
                        item1.setSelected(true);
                        item1.setTitle(entity.getTotal() + "");
                        ToastUtil.showToast(mActivity, "点赞成功");
                        isPraises = data.getDetail().getIsPraises() == 1000;
                        break;
                    case DiscussDataHttpRequest.TYPE_UNPRAISES:
                        item1.setSelected(false);
                        item1.setTitle(entity.getTotal() + "");
                        ToastUtil.showToast(mActivity, "取消赞成功");
                        isPraises = data.getDetail().getIsPraises() == 1001;
                        break;
                    case DiscussDataHttpRequest.TYPE_REPORT:
                        ToastUtil.showToast(mActivity, "举报信息提交成功");
                        break;
                }
            }
        };


    }

    private void initCommentAdapter() {
        mAdapter = new AskAnswerPageDetailCommentAdapter(data.getComments());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mAdapter.openLoadAnimation();
        recyclerViewAskAnswerComment.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                ArticleCommentEntity status = (ArticleCommentEntity) adapter.getItem(position);
                pid = ((ArticleCommentEntity) adapter.getItem(position)).getId();
                userId = ((ArticleCommentEntity) adapter.getItem(position)).getUserId();
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

    @OnClick(R.id.text_view_comment_submit)
    public void onClick() {

        String content = editUserCommentReplay.getText().toString();
        if (StringUtils.isEmpty(content)) {
            CommonUtils.showToast("点评内容不能为空");
        } else {
            if (currentSubCommentEntity == null) {
                currentAction = DiscussDataHttpRequest.TYPE_COMMENT;
                CommonDataHttpRequest.getInstance(mActivity).createArticleComment(new ProgressSubscriber(putSubscriber, mActivity), articleId, toUid, content);
            } else {
                currentAction = DiscussDataHttpRequest.TYPE_SUB_COMMENT;
                CommonDataHttpRequest.getInstance(mActivity).createArticleSubComment(new ProgressSubscriber(putSubscriber, mActivity),
                        articleId, userId, content, pid);
                Log.e("pid", String.valueOf(pid));

            }
        }

    }
}
