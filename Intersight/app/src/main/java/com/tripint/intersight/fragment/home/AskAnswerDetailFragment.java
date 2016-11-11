package com.tripint.intersight.fragment.home;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.tripint.intersight.R;
import com.tripint.intersight.adapter.AskAnswerPageDetailCommentAdapter;
import com.tripint.intersight.adapter.PaymentSelectAdapter;
import com.tripint.intersight.adapter.listener.RecyclerViewItemOnClick;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.KeyboardUtils;
import com.tripint.intersight.common.utils.NetworkUtils;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ListHolder;
import com.tripint.intersight.common.widget.filter.ItemModel;
import com.tripint.intersight.common.widget.filter.adapter.SimpleTextAdapter;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterItemClickListener;
import com.tripint.intersight.common.widget.filter.typeview.SingleListView;
import com.tripint.intersight.common.widget.filter.util.UIUtil;
import com.tripint.intersight.common.widget.filter.view.FilterCheckedTextView;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.listener.OnItemChildClickListener;
import com.tripint.intersight.entity.article.ArticleCommentEntity;
import com.tripint.intersight.entity.discuss.CommentResultEntity;
import com.tripint.intersight.entity.discuss.DiscussAskDetailEntity;
import com.tripint.intersight.entity.discuss.DiscussDetailResponseEntity;
import com.tripint.intersight.entity.discuss.DiscussEntity;
import com.tripint.intersight.entity.payment.AliPayResponseEntity;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.event.PayEvent;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.fragment.personal.PersonalMainPageFragment;
import com.tripint.intersight.helper.AliPayUtils;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.helper.PayUtils;
import com.tripint.intersight.helper.ProgressDialogUtils;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.service.CommonDataHttpRequest;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.service.PaymentDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;
import com.tripint.intersight.widget.tabbar.BottomTabBarItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 */
public class AskAnswerDetailFragment extends BaseBackFragment {

    public static final String ARG_DISCUSS_ID = "arg_discuss_id";
    private static final int MESSAGE_ID_RECONNECTING = 0x01;
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

    private boolean isPraises;
    private boolean isFollow;

    private AskAnswerPageDetailCommentAdapter mAdapter;

    private PageDataSubscriberOnNext<DiscussDetailResponseEntity> subscriber;

    private PageDataSubscriberOnNext<CommentResultEntity> putSubscriber;

    private PageDataSubscriberOnNext<WXPayResponseEntity> wxPaySubscriber;
    private PageDataSubscriberOnNext<AliPayResponseEntity> aliPaySubscriber;

    private DiscussDetailResponseEntity data;
    private DialogPlus dialogPlus;
    private int mDiscussId;
    private int toUid;
    private int pid;//评论的id
    private int askUserId;
    private int answerUserId;
    private String currentAction = "";
    private int isPayment;
    private String listenPayment;
    private String audioUrl;
    private PLMediaPlayer mMediaPlayer;
    private AVOptions mAVOptions;
    private boolean mIsActivityPaused = true;

    private ArticleCommentEntity currentSubCommentEntity; //创建子摩评论
    private PLMediaPlayer.OnPreparedListener mOnPreparedListener = new PLMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(PLMediaPlayer mp) {
            Log.i(TAG, "On Prepared !");
//            btnQaRecordVoicePlay.setEnabled(true);
        }
    };
    private PLMediaPlayer.OnInfoListener mOnInfoListener = new PLMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(PLMediaPlayer mp, int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    ProgressDialogUtils.getInstants(mActivity).show();
                    break;
                case PLMediaPlayer.MEDIA_INFO_BUFFERING_END:
                case PLMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    ProgressDialogUtils.getInstants(mActivity).dismiss();
                    break;
                default:
                    break;
            }
            return true;
        }
    };
    private PLMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new PLMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(PLMediaPlayer mp, int percent) {
            Log.d(TAG, "onBufferingUpdate: " + percent + "%");
        }
    };
    /**
     * Listen the event of playing complete
     * For playing local file, it's called when reading the file EOF
     * For playing network stream, it's called when the buffered bytes played over
     * <p>
     * If setLooping(true) is called, the player will restart automatically
     * And ｀onCompletion｀ will not be called
     */
    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer mp) {
            Log.d(TAG, "Play Completed !");
        }
    };
    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            boolean isNeedReconnect = false;
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    showToastTips("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    showToastTips("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    showToastTips("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    showToastTips("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    showToastTips("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    showToastTips("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    showToastTips("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    showToastTips("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    showToastTips("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    showToastTips("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    showToastTips("unknown error !");
                    break;
            }
            // Todo pls handle the error status here, reconnect or call finish()
            releaseAudioPlayer();
            if (isNeedReconnect) {
                sendReconnectMessage();
            } else {

            }
            // Return true means the error has been handled
            // If return false, then `onCompletion` will be called
            return true;
        }
    };

    public static AskAnswerDetailFragment newInstance(DiscussEntity entiry) {

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

        EventBus.getDefault().register(this);

        toolbar.setTitle("问答详情");
        initToolbarNav(toolbar);
        httpRequestData();
        return view;
    }

    private void initView(View view) {

        inithttpPutRequestData();

        //问答标题与作者
        if (data.getDetail() != null) {
            DiscussAskDetailEntity entity = data.getDetail();

            askUserId = entity.getAuthorUserId();
            answerUserId = entity.getAnswerUserId();
            toUid = entity.getAuthorUserId();
            audioUrl = entity.getAudioUrl();
            isPayment = entity.getIsPayment();

            listenPayment = entity.getListenPayment();
            Log.e("audioUrl", audioUrl);

            containerChatReply.setVisibility(View.VISIBLE);
            Glide.with(mActivity)
                    .load(entity.getAuthorUserAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .placeholder(R.mipmap.ic_avatar)
                    .error(R.mipmap.ic_avatar)
                    .transform(new GlideCircleTransform(mActivity))
                    .into(imageAskProfile);
            String special = entity.getAuthorUserNickname() + "  ";
            special += entity.getAuthorUserCompany() + "  ";
            special += entity.getAuthorUserAbility();


            textViewItemAskSpecialist.setText(special);
            containerChatAuthor.setVisibility(View.VISIBLE);
            textViewItemAskTitle.setText(entity.getContent());
            textViewItemAskDateTime.setText(entity.getAuthorCreateAt());


            String specialAnswer = entity.getAnswerUserNickname() + "  ";
            specialAnswer += entity.getAnswerUserCompany() + "  ";
            specialAnswer += entity.getAnswerUserAbility();

            textViewItemAnswerSpecialist.setText(specialAnswer);

            textViewItemAnswerTitle.setText(entity.getAudioTime() + "s");
//            textViewItemAnswerPayment.setText(entity.getPayment() + "元即听");
            textViewItemAnswerPayment.setText("点击即听");

            textViewItemAnswerDateTime.setText(entity.getAnswerCreateAt());

            Glide.with(mActivity)
                    .load(entity.getAnswerUserAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .placeholder(R.mipmap.ic_avatar)
                    .error(R.mipmap.ic_avatar)
                    .transform(new GlideCircleTransform(mActivity))
                    .into(imageAskProfile);
            //点赞 点关注 点
            LinearLayout.LayoutParams mTabParams;

            mTabParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
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
                        CommonDataHttpRequest.getInstance(mActivity).deleteDiscussPraise(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId, toUid);

                    } else {
                        //点赞
                        currentAction = DiscussDataHttpRequest.TYPE_PRAISES;
                        CommonDataHttpRequest.getInstance(mActivity).createDiscussPraise(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId, toUid);
                    }

                }
            });
            userCommentBar.addView(item1);

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
                        CommonDataHttpRequest.getInstance(mActivity).deleteDiscussFocus(new ProgressSubscriber<CommentResultEntity>(putSubscriber, mActivity), mDiscussId, toUid);

                    } else {
                        currentAction = DiscussDataHttpRequest.TYPE_FOLLOW;
                        CommonDataHttpRequest.getInstance(mActivity).createDiscussFocus(new ProgressSubscriber<CommentResultEntity>(putSubscriber, mActivity), mDiscussId, toUid);
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
                    currentAction = DiscussDataHttpRequest.TYPE_REPORT;
                    BaseDataHttpRequest.getInstance(mActivity).reportDiscuss(new ProgressSubscriber<CommentResultEntity>(putSubscriber, mActivity), data.getDetail().getId());
                }
            });
            userCommentBar.addView(item3);
            userCommentBar.setVisibility(View.VISIBLE);
            layoutUserCommentContainer.setVisibility(View.VISIBLE);
        }

    }


    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<DiscussDetailResponseEntity>() {
            @Override
            public void onNext(DiscussDetailResponseEntity entity) {
                //接口请求成功后处理
                data = entity;
//                ToastUtil.showToast(mActivity, entity.getAbilityName().toString() +"");
                initView(null);
                initCommentAdapter();
            }
        };

        wxPaySubscriber = new PageDataSubscriberOnNext<WXPayResponseEntity>() {
            @Override
            public void onNext(WXPayResponseEntity entity) {
                //接口请求成功后处理,调起微信支付。
                PayUtils.getInstant().requestWXpay(entity);
                dialogPlus.dismiss();
                pop();
            }
        };

        aliPaySubscriber = new PageDataSubscriberOnNext<AliPayResponseEntity>() {
            @Override
            public void onNext(AliPayResponseEntity entity) {
                //接口请求成功后处理,调起微信支付。
                AliPayUtils.getInstant(mActivity).pay(entity);
                dialogPlus.dismiss();
                pop();
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
                        ToastUtil.showToast(mActivity, "提交成功");
                        editUserCommentReplay.setText("");
                        initCommentAdapter();
                        break;
                    case DiscussDataHttpRequest.TYPE_SUB_COMMENT: //行业领域
                        ToastUtil.showToast(mActivity, "提交成功");
                        editUserCommentReplay.setText("");
                        initCommentAdapter();
                        break;
                    case DiscussDataHttpRequest.TYPE_FOLLOW: //我的
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

    @OnClick({R.id.text_view_comment_submit, R.id.container_voice_message, R.id.image_ask_profile, R.id.image_answer_profile})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.text_view_comment_submit: //行业领域

                String content = editUserCommentReplay.getText().toString();
                if (StringUtils.isEmpty(content)) {
                    CommonUtils.showToast("点评内容不能为空");
                } else {
                    if (currentSubCommentEntity == null) {
                        currentAction = DiscussDataHttpRequest.TYPE_COMMENT;
                        CommonDataHttpRequest.getInstance(mActivity).createDiscussComment(new ProgressSubscriber(putSubscriber, mActivity), mDiscussId, toUid, content);
                    } else {
                        currentAction = DiscussDataHttpRequest.TYPE_SUB_COMMENT;
                        CommonDataHttpRequest.getInstance(mActivity).createDiscussSubComment(new ProgressSubscriber(putSubscriber, mActivity),
                                mDiscussId, toUid, content, pid);

                    }
                }

                break;
            case R.id.container_voice_message: //
                if (isPayment == 1000) {//未付钱
                    final List<PaymentEntity> paymentEntities = new ArrayList<>();

                    paymentEntities.add(new PaymentEntity(1, "支付宝", PaymentDataHttpRequest.TYPE_ALIPAY));
                    paymentEntities.add(new PaymentEntity(2, "微信支付", PaymentDataHttpRequest.TYPE_WXPAY));
                    PaymentSelectAdapter paymentDialogAdapter = new PaymentSelectAdapter(mActivity, paymentEntities);
                    dialogPlus = DialogPlusUtils.Builder(mActivity)
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
                        public void ItemOnClick(int position, Object itemdata) {
                            PaymentEntity select = (PaymentEntity) itemdata;

                            if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_WXPAY)) {
                                PaymentDataHttpRequest.getInstance(mActivity).requestWxPayForDiscuss(new ProgressSubscriber(wxPaySubscriber, mActivity),
                                        data.getDetail().getId(), data.getDetail().getAnswerUserId(), data.getDetail().getContent());
                            } else if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_ALIPAY)) {
                                PaymentDataHttpRequest.getInstance(mActivity).requestAliPayForDiscuss(new ProgressSubscriber(aliPaySubscriber, mActivity),
                                        data.getDetail().getId(), data.getDetail().getAnswerUserId(), data.getDetail().getContent());

                            }

                        }

                        @Override
                        public void ItemOnClick(int position, Object data, boolean isSelect) {

                        }
                    });
                } else if (isPayment == 1001) {
                    if (audioUrl != null) {
                        prepareAudioPlayer(audioUrl);
                        mMediaPlayer.start();
                    }
                }

//                dialogPlus.show();
                break;
            case R.id.image_ask_profile:
                EventBus.getDefault().post(new StartFragmentEvent(PersonalMainPageFragment.newInstance(askUserId)));
                break;
            case R.id.image_answer_profile:
                EventBus.getDefault().post(new StartFragmentEvent(PersonalMainPageFragment.newInstance(answerUserId)));
                break;

        }

    }

    private View createSingleListView(List<ItemModel> list, final int type) {
        SimpleTextAdapter adapter = new SimpleTextAdapter<ItemModel>(null, mActivity) {
            @Override
            public String provideText(ItemModel item) {
                return item.getName();
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(UIUtil.dp(mActivity, 12), UIUtil.dp(mActivity, 12), 0, UIUtil.dp(mActivity, 12));
            }
        };
        SingleListView<ItemModel> singleListView = new SingleListView<>(mActivity)
                .adapter(adapter)
                .onItemClick(new OnFilterItemClickListener<ItemModel>() {
                    @Override
                    public void onItemClick(ItemModel item) {

//                        onFilterDone(type, item.getKey(), item.getName());
                    }
                });

        singleListView.setList(list, -1);

        return singleListView;
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

    @Subscribe
    public void onEvent(PayEvent event) {
        /* Do something */
        if (event.isResult()) {
            CommonUtils.showToast("开始听吧");

            httpRequestData();

            prepareAudioPlayer(audioUrl);
            mMediaPlayer.start();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        releaseAudioPlayer();
    }

    private void prepareAudioPlayer(String audioPath) {
        if (mMediaPlayer == null) {
            mAVOptions = new AVOptions();
            mMediaPlayer = new PLMediaPlayer(InterSightApp.getApp(), mAVOptions);
            mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            mMediaPlayer.setOnErrorListener(mOnErrorListener);
            mMediaPlayer.setOnInfoListener(mOnInfoListener);
            mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
            mMediaPlayer.setWakeMode(InterSightApp.getApp(), PowerManager.PARTIAL_WAKE_LOCK);
        }
        try {
            mMediaPlayer.setDataSource(audioPath);
            mMediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void releaseAudioPlayer() {
        prepareAudioPlayer(audioUrl);
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }


    private void sendReconnectMessage() {
        showToastTips("正在重连...");
        ProgressDialogUtils.getInstants(mActivity).show();
    }


    private void showToastTips(final String tips) {
        if (mIsActivityPaused) {
            return;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonUtils.showToast(tips);
            }
        });
    }
}
