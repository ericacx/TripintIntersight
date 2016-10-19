package com.tripint.intersight.fragment.home;


import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.qiniu.android.dns.DnsManager;
import com.qiniu.android.dns.IResolver;
import com.qiniu.android.dns.NetworkInfo;
import com.qiniu.android.dns.http.DnspodFree;
import com.qiniu.android.dns.local.AndroidDnsServer;
import com.qiniu.android.dns.local.Resolver;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.AudioSourceCallback;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.MicrophoneStreamingSetting;
import com.qiniu.pili.droid.streaming.StreamStatusCallback;
import com.qiniu.pili.droid.streaming.StreamingPreviewCallback;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingSessionListener;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.tripint.intersight.R;
import com.tripint.intersight.activity.PermissionsActivity;
import com.tripint.intersight.adapter.AskAnswerPageDetailCommentAdapter;
import com.tripint.intersight.adapter.PaymentSelectAdapter;
import com.tripint.intersight.adapter.listener.RecyclerViewItemOnClick;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.widget.countdown.CountDownView;
import com.tripint.intersight.common.widget.countdown.TimerListener;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ListHolder;
import com.tripint.intersight.entity.discuss.CommentEntity;
import com.tripint.intersight.entity.discuss.DiscussDetailEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;
import com.tripint.intersight.entity.stream.StreamResponseEntity;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.helper.AliPayUtils;
import com.tripint.intersight.helper.Config;
import com.tripint.intersight.helper.PayUtils;
import com.tripint.intersight.service.DiscussDataHttpRequest;
import com.tripint.intersight.service.PaymentDataHttpRequest;
import com.tripint.intersight.service.PiliStreamDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskReplayDetailFragment extends BaseBackFragment implements TimerListener, StreamStatusCallback,
        StreamingPreviewCallback,
        AudioSourceCallback,
        StreamingSessionListener,
        StreamingStateChangedListener {

    public static final String ARG_DISCUSS_ID = "arg_discuss_id";

    @Bind(R.id.text_qa_info)
    TextView textQaInfo;
    @Bind(R.id.btn_qa_record_voice_main)
    Button btnQaRecordVoice;
    @Bind(R.id.speaker_container)
    RelativeLayout layoutSpeakerContainer;

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
    @Bind(R.id.container_voice_message)
    RelativeLayout containerVoiceMessage;
    @Bind(R.id.textView_item_answer_date_time)
    TextView textViewItemAnswerDateTime;
    @Bind(R.id.image_answer_profile)
    CircleImageView imageAnswerProfile;
    @Bind(R.id.container_chat_reply)
    LinearLayout containerChatReply;
    @Bind(R.id.countdownview)
    CountDownView countdownview;
    @Bind(R.id.container_countdown)
    RelativeLayout containerCountdown;
    @Bind(R.id.toggle_agree_licence)
    ToggleButton toggleAgreeLicence;
    @Bind(R.id.btn_qa_record_voice_restart)
    Button btnQaRecordVoiceRestart;
    @Bind(R.id.btn_qa_record_voice_play)
    Button btnQaRecordVoicePlay;


    private AskAnswerPageDetailCommentAdapter mAdapter;

    private PageDataSubscriberOnNext<DiscussDetailEntity> subscriber;

    private PageDataSubscriberOnNext<StreamResponseEntity> streamSubscriber;

    private PageDataSubscriberOnNext<WXPayResponseEntity> paymentSubscriber;

    private DiscussDetailEntity data;

    private int mDiscussId;

    private String currentAction = "";

    private CommentEntity currentSubCommentEntity; //创建子摩评论

    protected MediaStreamingManager mMediaStreamingManager;
    protected CameraStreamingSetting mCameraStreamingSetting;
    protected MicrophoneStreamingSetting mMicrophoneStreamingSetting;
    protected StreamingProfile mProfile;
    protected static final int MSG_START_STREAMING = 0;
    protected static final int MSG_STOP_STREAMING = 1;

    protected boolean mShutterButtonPressed = false;

    public static AskReplayDetailFragment newInstance(DiscussEntiry entiry) {

        Bundle args = new Bundle();
        args.putInt(AskReplayDetailFragment.ARG_DISCUSS_ID, entiry.getId());
        AskReplayDetailFragment fragment = new AskReplayDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_ask_replay_detail, container, false);
        ButterKnife.bind(this, view);

        if (InterSightApp.getApp().getPermissionsChecker().lacksPermissions(InterSightApp.getApp().RECORD_AUDIO)) {
            PermissionsActivity.startActivityForResult(mActivity, InterSightApp.getApp().REQUEST_CODE, InterSightApp.getApp().RECORD_AUDIO);
        } else {
            httpRequestData();
        }
        return view;
    }

    private void initView(View view) {

        toolbar.setTitle("问答详情");
        initToolbarNav(toolbar);
        initToolbarMenu(toolbar);
        inithttpPutRequestData();


        //问答标题与作者
        if (data.getAuthor() != null) {
            containerChatReply.setVisibility(View.VISIBLE);
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
        //回答内容
        if (data.getDetail() != null) {
            containerChatAuthor.setVisibility(View.VISIBLE);
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

        } else {
            layoutSpeakerContainer.setVisibility(View.VISIBLE);
        }

        countdownview.setInitialTime(90000); // Initial time of 90 seconds.
        countdownview.setListener(this);

    }


    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_STREAMING:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // disable the shutter button before startStreaming

                            boolean res = mMediaStreamingManager.startStreaming();
                            mShutterButtonPressed = true;
                            Log.i(TAG, "res:" + res);
                            if (!res) {
                                mShutterButtonPressed = false;
                                setShutterButtonEnabled(true);
                            }
                            setShutterButtonPressed(mShutterButtonPressed);
                        }
                    }).start();
                    break;
                case MSG_STOP_STREAMING:
                    if (mShutterButtonPressed) {
                        // disable the shutter button before stopStreaming
                        setShutterButtonEnabled(false);
                        boolean res = mMediaStreamingManager.stopStreaming();
                        if (!res) {
                            mShutterButtonPressed = true;
                            setShutterButtonEnabled(true);
                        }
                        setShutterButtonPressed(mShutterButtonPressed);
                    }
                    break;

                default:
                    Log.e(TAG, "Invalid message");
                    break;
            }
        }
    };

    protected void setShutterButtonPressed(final boolean pressed) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShutterButtonPressed = pressed;
                btnQaRecordVoice.setPressed(pressed);
            }
        });
    }

    protected void setShutterButtonEnabled(final boolean enable) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnQaRecordVoice.setFocusable(enable);
                btnQaRecordVoice.setClickable(enable);
                btnQaRecordVoice.setEnabled(enable);
            }
        });
    }


    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<DiscussDetailEntity>() {
            @Override
            public void onNext(DiscussDetailEntity entity) {
                //接口请求成功后处理
                data = entity;
//                ToastUtil.showToast(mActivity, entity.getAbilityName().toString() +"");
                initView(null);

            }
        };

        paymentSubscriber = new PageDataSubscriberOnNext<WXPayResponseEntity>() {
            @Override
            public void onNext(WXPayResponseEntity entity) {
                //接口请求成功后处理,调起微信支付。
                PayUtils.getInstant().requestWXpay(entity);
//
            }
        };


        DiscussDataHttpRequest.getInstance(mActivity).getDiscussDetail(new ProgressSubscriber(subscriber, mActivity), mDiscussId);
    }

    private void inithttpPutRequestData() {
        streamSubscriber = new PageDataSubscriberOnNext<StreamResponseEntity>() {
            @Override
            public void onNext(StreamResponseEntity entity) {
                if (!StringUtils.isEmpty(entity.getPublishUrl())) {
                    Log.d(TAG, entity.getPublishUrl());
                    initStreamProfile(entity.getPublishUrl());
                    btnQaRecordVoice.setVisibility(View.VISIBLE);
                }
            }
        };

        PiliStreamDataHttpRequest.getInstance(mActivity).postPublishStreamUrl(new ProgressSubscriber<StreamResponseEntity>(streamSubscriber, mActivity));

    }

    @OnClick({R.id.container_voice_message, R.id.btn_qa_record_voice_main, R.id.btn_qa_record_voice_play, R.id.btn_qa_record_voice_restart})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.container_voice_message: //我的关注
                List<PaymentEntity> paymentEntities = new ArrayList<>();

                paymentEntities.add(new PaymentEntity(1, "支付宝", PaymentDataHttpRequest.TYPE_ALIPAY));
                paymentEntities.add(new PaymentEntity(2, "微信支付", PaymentDataHttpRequest.TYPE_WXPAY));
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

                        if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_WXPAY)) {

                        } else if (select.getChannelPartentId().equals(PaymentDataHttpRequest.TYPE_ALIPAY)) {
                            AliPayUtils.getInstant(mActivity).pay();
                        }

                    }

                    @Override
                    public void ItemOnClick(int position, Object data, boolean isSelect) {

                    }
                });
//                dialogPlus.show();
                break;
            case R.id.btn_qa_record_voice_main: //录音
                if (countdownview.isTimerRunning() && mShutterButtonPressed) {
                    countdownview.stop();
                    stopStreaming();
                    Drawable drawable = getResources().getDrawable(R.drawable.iconfont_huatong);
                    drawable.setBounds(0, 0, 64, 64);
                    btnQaRecordVoice.setCompoundDrawables(drawable, null, null, null);
                    btnQaRecordVoiceRestart.setVisibility(View.VISIBLE);
                    btnQaRecordVoicePlay.setVisibility(View.VISIBLE);
                    btnQaRecordVoice.setText("开始录音");

                } else {
                    countdownview.start();
                    startStreaming();
                    mShutterButtonPressed = true;
                    Drawable drawable = getResources().getDrawable(R.drawable.iconfont_stop);
                    drawable.setBounds(0, 0, 64, 64);
                    btnQaRecordVoice.setCompoundDrawables(drawable, null, null, null);
                    btnQaRecordVoice.setText("结束录音");
                }
                break;
            case R.id.btn_qa_record_voice_play: //播放

                break;
            case R.id.btn_qa_record_voice_restart:
                mShutterButtonPressed = false;
                countdownview.reset();
                btnQaRecordVoicePlay.setVisibility(View.GONE);
                btnQaRecordVoiceRestart.setVisibility(View.GONE);
                break;

        }

    }


    @Override
    public void timerElapsed() {
        //Do something here


    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.pause();
        }
    }

    @Override
    public void onDestroyView() {

        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.destroy();
        }
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initStreamProfile(String publishUrlFromServer) {
        StreamingProfile.AudioProfile aProfile = new StreamingProfile.AudioProfile(44100, 96 * 1024);
        StreamingProfile.VideoProfile vProfile = new StreamingProfile.VideoProfile(30, 1000 * 1024, 48);
        StreamingProfile.AVProfile avProfile = new StreamingProfile.AVProfile(vProfile, aProfile);

        mProfile = new StreamingProfile();
        try {
            mProfile.setPublishUrl(publishUrlFromServer);
        } catch (URISyntaxException e) {
            Log.e(TAG, e.getMessage());
        }

        mProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_HIGH3)
                .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
//                .setPreferredVideoEncodingSize(960, 544)
                .setEncodingSizeLevel(Config.ENCODING_LEVEL)
                .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY)
                .setAVProfile(avProfile)
                .setDnsManager(getMyDnsManager())
                .setStreamStatusConfig(new StreamingProfile.StreamStatusConfig(3))
//                .setEncodingOrientation(StreamingProfile.ENCODING_ORIENTATION.PORT)
                .setSendingBufferProfile(new StreamingProfile.SendingBufferProfile(0.2f, 0.8f, 3.0f, 20 * 1000));
        mProfile.setAudioQuality(StreamingProfile.AUDIO_QUALITY_LOW1);

        mCameraStreamingSetting = new CameraStreamingSetting();

        mMicrophoneStreamingSetting = new MicrophoneStreamingSetting();
        mMicrophoneStreamingSetting.setBluetoothSCOEnabled(false);

        mProfile.setAudioQuality(StreamingProfile.AUDIO_QUALITY_LOW1);

        mMediaStreamingManager = new MediaStreamingManager(mActivity, AVCodecType.SW_AUDIO_CODEC);
        mMediaStreamingManager.prepare(mProfile);
        mMediaStreamingManager.setStreamingStateListener(this);
        mMediaStreamingManager.resume();

    }

    protected void startStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_START_STREAMING), 50);
    }

    protected void stopStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_STOP_STREAMING), 50);
    }

    private static DnsManager getMyDnsManager() {
        IResolver r0 = new DnspodFree();
        IResolver r1 = AndroidDnsServer.defaultResolver();
        IResolver r2 = null;
        try {
            r2 = new Resolver(InetAddress.getByName("119.29.29.29"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new DnsManager(NetworkInfo.normal, new IResolver[]{r0, r1, r2});
    }

    @Override
    protected void initToolbarNav(Toolbar toolbar) {
        super.initToolbarNav(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAudioSourceAvailable(ByteBuffer byteBuffer, int i, long l, boolean b) {

    }

    @Override
    public void notifyStreamStatusChanged(StreamingProfile.StreamStatus streamStatus) {

    }

    @Override
    public boolean onPreviewFrame(byte[] bytes, int i, int i1) {
        return false;
    }

    @Override
    public boolean onRecordAudioFailedHandled(int i) {
        return false;
    }

    @Override
    public boolean onRestartStreamingHandled(int i) {
        return false;
    }

    @Override
    public Camera.Size onPreviewSizeSelected(List<Camera.Size> list) {
        return null;
    }

    @Override
    public void onStateChanged(StreamingState streamingState, Object o) {

    }
}