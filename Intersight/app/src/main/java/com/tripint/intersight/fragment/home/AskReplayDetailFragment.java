package com.tripint.intersight.fragment.home;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
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
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
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
import com.tripint.intersight.common.utils.NetworkUtils;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.widget.countdown.CountDownView;
import com.tripint.intersight.common.widget.countdown.TimerListener;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ListHolder;
import com.tripint.intersight.entity.discuss.DiscussAskDetailEntity;
import com.tripint.intersight.entity.discuss.DiscussDetailResponseEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.discuss.DiscussEntity;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;
import com.tripint.intersight.entity.stream.SaveStreamResponseEntity;
import com.tripint.intersight.entity.stream.StreamResponseEntity;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.fragment.base.BaseBackFragment;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.helper.Config;
import com.tripint.intersight.helper.PayUtils;
import com.tripint.intersight.helper.ProgressDialogUtils;
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

    //取得问答详情
    private PageDataSubscriberOnNext<DiscussDetailResponseEntity> subscriber;

    //取得推送流URL
    private PageDataSubscriberOnNext<StreamResponseEntity> streamSubscriber;

    //取得推送流URL
    private PageDataSubscriberOnNext<SaveStreamResponseEntity> streamSaveSubscriber;

    private PageDataSubscriberOnNext<WXPayResponseEntity> paymentSubscriber;

    private DiscussDetailResponseEntity data;

    private int mDiscussId;


    private StreamResponseEntity streamResponseEntity; //

    private SaveStreamResponseEntity saveStreamResponseEntity;

    ///Stream Publish

    protected MediaStreamingManager mMediaStreamingManager;
    protected CameraStreamingSetting mCameraStreamingSetting;
    protected MicrophoneStreamingSetting mMicrophoneStreamingSetting;
    protected StreamingProfile mProfile;
    protected static final int MSG_START_STREAMING = 0;
    protected static final int MSG_STOP_STREAMING = 1;

    // Stream Player
    private static final int MESSAGE_ID_RECONNECTING = 0x01;

    private PLMediaPlayer mMediaPlayer;
    private AVOptions mAVOptions;
    private boolean mIsStopped = false;
    private boolean mIsActivityPaused = true;

    // Stream End

    protected boolean mShutterButtonPressed = false;

    public static AskReplayDetailFragment newInstance(DiscussEntity entiry) {

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
        if (data.getDetail() != null) {
            DiscussAskDetailEntity entity = data.getDetail();

            containerChatReply.setVisibility(View.VISIBLE);
            Glide.with(mActivity).load(entity.getAuthorUserAvatar())
                    .crossFade()
//                    .placeholder(R.mipmap.ic_avatar)
                    .transform(new GlideCircleTransform(mActivity))
                    .into(imageAskProfile);
            String special = entity.getAuthorUserNickname();
            special += entity.getAuthorUserAbility();
            special += entity.getAuthorUserCompany();

            textViewItemAskSpecialist.setText(special);
            containerChatAuthor.setVisibility(View.VISIBLE);
            textViewItemAskTitle.setText(entity.getContent());
            textViewItemAskDateTime.setText(entity.getAuthorCreateAt());
            String specialAnswer = entity.getAnswerUserNickname();
            specialAnswer += entity.getAnswerUserAbility();
            specialAnswer += entity.getAnswerUserCompany();
                textViewItemAnswerSpecialist.setText(special);


            textViewItemAnswerTitle.setText(entity.getAudioTime() + "s");
            textViewItemAnswerPayment.setText(entity.getPayment() + "元即听");

            textViewItemAnswerDateTime.setText(entity.getAnswerCreateAt());
            Glide.with(mActivity).load(entity.getAuthorUserAvatar())
                    .crossFade()
//                    .placeholder(R.mipmap.ic_avatar)
                    .transform(new GlideCircleTransform(mActivity))
                    .into(imageAskProfile);

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
                            setShutterButtonEnabled(true);
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

    protected Handler mAudioHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != MESSAGE_ID_RECONNECTING) {
                return;
            }

            if (!NetworkUtils.isAvailable(mActivity)) {
                sendReconnectMessage();
                return;
            }
            // The PLMediaPlayer has moved to the Error state, if you want to retry, must reset first !
            prepareAudioPlayer(saveStreamResponseEntity.getAudioUrl());
        }
    };

    protected void setShutterButtonPressed(final boolean pressed) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShutterButtonPressed = pressed;

            }
        });
    }

    protected void setShutterButtonEnabled(final boolean enable) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (enable) {
                    Drawable drawable = getResources().getDrawable(R.drawable.iconfont_stop);
                    drawable.setBounds(0, 0, 64, 64);
                    btnQaRecordVoice.setCompoundDrawables(drawable, null, null, null);
                    btnQaRecordVoice.setText("结束录音");
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.iconfont_huatong);
                    drawable.setBounds(0, 0, 64, 64);
                    btnQaRecordVoice.setCompoundDrawables(drawable, null, null, null);
                    btnQaRecordVoiceRestart.setVisibility(View.VISIBLE);
                    btnQaRecordVoicePlay.setVisibility(View.VISIBLE);
                    btnQaRecordVoicePlay.setEnabled(false);
                    btnQaRecordVoice.setText("开始录音");
                }
            }
        });
    }


    private void httpRequestData() {
        subscriber = new PageDataSubscriberOnNext<DiscussDetailResponseEntity>() {
            @Override
            public void onNext(DiscussDetailResponseEntity entity) {
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

        streamSubscriber = new PageDataSubscriberOnNext<StreamResponseEntity>() {
            @Override
            public void onNext(StreamResponseEntity entity) {
                streamResponseEntity = entity;
                if (!StringUtils.isEmpty(entity.getPublishUrl())) {
                    Log.d(TAG, entity.getPublishUrl());
                    initStreamProfile(entity.getPublishUrl());
                    btnQaRecordVoice.setVisibility(View.VISIBLE);
                }
            }
        };
        streamSaveSubscriber = new PageDataSubscriberOnNext<SaveStreamResponseEntity>() {
            @Override
            public void onNext(SaveStreamResponseEntity entity) {
                if (!StringUtils.isEmpty(entity.getAudioUrl())) {
                    Log.d(TAG, entity.getAudioUrl());

                    saveStreamResponseEntity = entity;
                    //设置报放流参数，并开始播放
                    initPlayStreamProfile(entity.getAudioUrl());

                }
            }
        };

        DiscussDataHttpRequest.getInstance(mActivity).getDiscussDetail(new ProgressSubscriber<DiscussDetailResponseEntity>(subscriber, mActivity), mDiscussId);
    }

    private void inithttpPutRequestData() {


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
//                            AliPayUtils.getInstant(mActivity).pay();
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
                    stopAudioRecording();

                } else {
                    startAudioRecording();
                }
                break;
            case R.id.btn_qa_record_voice_play: //播放
                mMediaPlayer.start();
                mIsStopped = false;
                break;
            case R.id.btn_qa_record_voice_restart:
                mShutterButtonPressed = false;
                if (countdownview.isTimerRunning()) {
                    countdownview.reset();
                }

                btnQaRecordVoicePlay.setVisibility(View.GONE);
                btnQaRecordVoiceRestart.setVisibility(View.GONE);
                break;

        }

    }

    private void startAudioRecording() {
        countdownview.start();
        containerCountdown.setVisibility(View.VISIBLE);
        startStreaming();

    }

    private void stopAudioRecording() {
        countdownview.reset();
        stopStreaming();

        saveStreamHttpRequest();
    }

    private void saveStreamHttpRequest() {
        long timeLeft = 90 - countdownview.getCurrentMillis();
        PiliStreamDataHttpRequest.getInstance(mActivity).postSavePublishStream(
                new ProgressSubscriber<SaveStreamResponseEntity>(streamSaveSubscriber, mActivity),
                streamResponseEntity.getStreamId(),
                data.getDetail().getId(),
                (int) timeLeft);
    }


    @Override
    public void timerElapsed() {
        if (countdownview.isTimerRunning() && mShutterButtonPressed) {
            stopAudioRecording();

        }


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

    private void initPlayStreamProfile(String playStreamUrl) {

        mAVOptions = new AVOptions();

        int isLiveStreaming = 0;
        // the unit of timeout is ms
        mAVOptions.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        mAVOptions.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        // Some optimization with buffering mechanism when be set to 1
        mAVOptions.setInteger(AVOptions.KEY_LIVE_STREAMING, isLiveStreaming);
        if (isLiveStreaming == 1) {
            mAVOptions.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }

        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = 0;
        mAVOptions.setInteger(AVOptions.KEY_MEDIACODEC, codec);

        // whether start play automatically after prepared, default value is 1
        mAVOptions.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);

        AudioManager audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        prepareAudioPlayer(playStreamUrl);
    }

    public void releaseAudioPlayer() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    private void prepareAudioPlayer(String audioPath) {
        if (mMediaPlayer == null) {
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

    protected void startStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_START_STREAMING), 100);
    }

    protected void stopStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_STOP_STREAMING), 100);
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

    private PLMediaPlayer.OnPreparedListener mOnPreparedListener = new PLMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(PLMediaPlayer mp) {
            Log.i(TAG, "On Prepared !");
            btnQaRecordVoicePlay.setEnabled(true);
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

    private void sendReconnectMessage() {
        showToastTips("正在重连...");
        ProgressDialogUtils.getInstants(mActivity).show();
        mAudioHandler.removeCallbacksAndMessages(null);
        mAudioHandler.sendMessageDelayed(mAudioHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 500);
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
