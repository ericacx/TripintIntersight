package com.tripint.intersight.fragment.mine;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tripint.intersight.R;
import com.tripint.intersight.activity.LoginActivity;
import com.tripint.intersight.activity.PermissionsActivity;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;
import com.tripint.intersight.common.imagepicker.AndroidImagePicker;
import com.tripint.intersight.common.utils.ImageUtils;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.mine.QiniuTokenEntity;
import com.tripint.intersight.entity.mine.UserHomeEntity;
import com.tripint.intersight.event.PersonalEvent;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.event.StartFragmentForResultEvent;
import com.tripint.intersight.fragment.PersonalInfoFragment;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;
import com.tripint.intersight.fragment.mine.message.NewMessageFragment;
import com.tripint.intersight.fragment.mine.setting.SettingFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseLazyMainFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int RESULT_CAPTURE_IMAGE = 1;// 照相的requestCode
    private static final int TAILORING = 2;// 裁剪
    private final int REQ_IMAGE = 9000;
    @Bind(R.id.mineCIVPersonalInfo)
    CircleImageView mineCIVPersonalInfo;
    @Bind(R.id.mine_text_view_name)
    TextView mineTextViewName;
    @Bind(R.id.mineIvRewriteInfo)
    ImageView mineIvRewriteInfo;//编辑个人资料
    @Bind(R.id.text_view_mine_ask_answer)
    TextView textViewMineAskAnswer;
    @Bind(R.id.text_view_mine_interview)
    TextView textViewMineInterview;
    @Bind(R.id.text_view_my_option)
    TextView textViewMyOption;
    @Bind(R.id.text_view_my_money)
    TextView textViewMyMoney;
    @Bind(R.id.textView_my_focus)
    TextView textViewMyFocus;
    @Bind(R.id.text_view_setting)
    TextView textViewSetting;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar_search_button)
    ImageView toolbarSearchButton;
    @Bind(R.id.text_view_help)
    TextView textViewHelp;
    @Bind(R.id.mine_rl_money)
    RelativeLayout mineRlMoney;
    @Bind(R.id.toolbar_back)
    ImageView toolbarBack;


    private PageDataSubscriberOnNext<UserHomeEntity> subscriber;
    private PageDataSubscriberOnNext<QiniuTokenEntity> tokenSubscriber;
    private PageDataSubscriberOnNext<CodeDataEntity> updateSubscriber;
    private CodeDataEntity codeDataEntity;
    private QiniuTokenEntity tokenEntity;
    private UserHomeEntity data;
    private String qiniuToken;
    private Uri imgUri;
    private byte[] b;
    private DialogPlus dialog;
    private String strImgPath;

    private String key;//随机字符串

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //生成随机字符串
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine1, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initToolbar();
        return view;
    }

    private void initToolbar() {
        toolbarTitle.setText("个人");
        toolbarSearchButton.setImageResource(R.mipmap.iconfont_wodexx);
        toolbarBack.setImageResource(R.mipmap.iconfont_wodexx);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Subscribe
    private void initView(View view) {
        if (data.getNickname() != null) {
            ACache.get(mActivity).put(EnumKey.ACacheKey.USER_NAME, data.getNickname());
        }
        if (data.getMobile() != null) {
            ACache.get(mActivity).put(EnumKey.ACacheKey.USER_PHONE, data.getMobile());
        }
        if (data.getEmail() != null) {
            ACache.get(mActivity).put(EnumKey.ACacheKey.USER_EMAIL, data.getEmail());
        }

        mineTextViewName.setText(data.getNickname());
        textViewMyMoney.setText(data.getBalance());
        Glide.with(mActivity)
                .load(data.getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .dontAnimate()
                .thumbnail(0.5f)
                .placeholder(R.mipmap.ic_avatar)
                .error(R.mipmap.ic_avatar)
                .into(mineCIVPersonalInfo);
    }

    private void httpRequestData() {

        updateSubscriber = new PageDataSubscriberOnNext<CodeDataEntity>() {
            @Override
            public void onNext(CodeDataEntity entity) {
                codeDataEntity = entity;
            }
        };

        tokenSubscriber = new PageDataSubscriberOnNext<QiniuTokenEntity>() {
            @Override
            public void onNext(QiniuTokenEntity qiniuTokenEntity) {
                tokenEntity = qiniuTokenEntity;
                qiniuToken = tokenEntity.getQiniuToken();
            }
        };
        MineDataHttpRequest.getInstance(mActivity).getQiniuToken(new ProgressSubscriber<QiniuTokenEntity>(tokenSubscriber, mActivity));

        subscriber = new PageDataSubscriberOnNext<UserHomeEntity>() {
            @Override
            public void onNext(UserHomeEntity entity) {
                //接口请求成功后处理
                data = entity;
                initView(null);

            }
        };

        MineDataHttpRequest.getInstance(mActivity).getUserHome(new ProgressSubscriber(subscriber, mActivity));
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (!InterSightApp.getApp().isUserLogin()) {
            Intent intent = new Intent();
            intent.setClass(mActivity, LoginActivity.class);
            startActivityForResult(intent, StartFragmentForResultEvent.REQ_LOGIN_FRAGMENT);
        } else {
            httpRequestData();
        }

    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

        refeashContent();
    }

    private void refeashContent() {

    }

    @Subscribe
    public void onEvent(PersonalEvent event) {
        httpRequestData();
    }

    @Override
    public void onDestroyView() {
        AndroidImagePicker.getInstance().onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }

    @OnClick({R.id.mineIvRewriteInfo, R.id.text_view_mine_ask_answer, R.id.text_view_mine_interview,
            R.id.text_view_my_option, R.id.text_view_my_money,R.id.toolbar_back,
            R.id.textView_my_focus, R.id.text_view_help, R.id.text_view_setting
            , R.id.mineCIVPersonalInfo, R.id.toolbar_search_button, R.id.mine_rl_money})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mineCIVPersonalInfo:

                if (InterSightApp.getApp().getPermissionsChecker().lacksPermissions(InterSightApp.getApp().FILE_CAMERA)) {
                    PermissionsActivity.startActivityForResult(mActivity, InterSightApp.getApp().REQUEST_CODE, InterSightApp.getApp().FILE_CAMERA);
                } else {
//                    List<ItemModel> list = new ArrayList<>();
//                    list.add(new ItemModel(1, "拍照"));
//                    list.add(new ItemModel(2, "从相册选择"));
//
//                    dialog = DialogPlusUtils.Builder(mActivity)
//                            .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createSingleListView(list)))
//                            .setTitleName("请选择")
//                            .setIsHeader(true)
//                            .setIsFooter(false)
//                            .setIsExpanded(false)
//                            .setGravity(Gravity.CENTER)
//                            .showCompleteDialog();
                    AndroidImagePicker.getInstance().pickAndCrop(mActivity, true, 120, new AndroidImagePicker.OnImageCropCompleteListener() {
                        @Override
                        public void onImageCropComplete(Bitmap bmp, float ratio) {
//                            Log.i(Constants.TAG, "=====onImageCropComplete (get bitmap=" + bmp.toString());
//                            mineCIVPersonalInfo.setVisibility(View.VISIBLE);
//                            mineCIVPersonalInfo.setImageBitmap(bmp);
                            Glide.with(mActivity)
                                    .load(ImageUtils.bitmap2Bytes(bmp, Bitmap.CompressFormat.JPEG))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .centerCrop()
                                    .dontAnimate()
                                    .thumbnail(0.5f)
                                    .placeholder(R.mipmap.ic_avatar)
                                    .error(R.mipmap.ic_avatar)
                                    .into(mineCIVPersonalInfo);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                            b = stream.toByteArray();

                        }
                    });

                    key = getRandomString(20);
                    final UploadManager uploadManager = new UploadManager();
                    uploadManager.put(b, key, qiniuToken, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            MineDataHttpRequest.getInstance(mActivity).postUpdateAvatar(new ProgressSubscriber<CodeDataEntity>(updateSubscriber, mActivity), key);
                        }
                    }, null);
                    return;
//                    Intent intent = new Intent();
//
//                    int requestCode = REQ_IMAGE;
//
//                    intent.setClass(mActivity, ImagesGridActivity.class);
//                    startActivityForResult(intent, requestCode);
                }


                break;
            case R.id.mineIvRewriteInfo://编辑个人资料
                EventBus.getDefault().post(new StartFragmentEvent(PersonalInfoFragment.newInstance(data)));
                break;
            case R.id.text_view_mine_ask_answer://我的问答
                EventBus.getDefault().post(new StartFragmentEvent(MyAskAnswerFragment.newInstance()));
                break;
            case R.id.text_view_mine_interview://我的访谈
                EventBus.getDefault().post(new StartFragmentEvent(MyInterviewFragment.newInstance()));
                break;
            case R.id.text_view_my_option://我的观点
                EventBus.getDefault().post(new StartFragmentEvent(MyOpinionFragment.newInstance()));
                break;
            case R.id.text_view_my_money://账户余额

                break;
//            case R.id.text_view_my_account_detail://账户明细
//                EventBus.getDefault().post(new StartFragmentEvent(AccountDetailFragment.newInstance()));
//                break;
            case R.id.textView_my_focus://我的关注
                EventBus.getDefault().post(new StartFragmentEvent(MyFocusedFragment.newInstance()));
                break;
//            case R.id.text_view_my_star://明星洞察家
//                EventBus.getDefault().post(new StartFragmentEvent(StarIntersighterFragment.newInstance()));
//                break;
            case R.id.text_view_help://使用帮助
                EventBus.getDefault().post(new StartFragmentEvent(UseHelpFragment.newInstance()));
//                EventBus.getDefault().post(new StartFragmentEvent(CreateOpinionFragment.newInstance()));
                break;
            case R.id.text_view_setting://设置
                EventBus.getDefault().post(new StartFragmentEvent(SettingFragment.newInstance()));
                break;
            case R.id.toolbar_search_button://消息
                EventBus.getDefault().post(new StartFragmentEvent(NewMessageFragment.newInstance()));
                break;
            case R.id.mine_rl_money://账户余额
                EventBus.getDefault().post(new StartFragmentEvent(AccountBalanceFragment.newInstance()));
                break;
            case R.id.toolbar_back://消息
                EventBus.getDefault().post(new StartFragmentEvent(NewMessageFragment.newInstance()));
                break;
        }
    }


    @Override
    public void onRefresh() {
        MineDataHttpRequest.getInstance(mActivity).getUserHome(new ProgressSubscriber(subscriber, mActivity));
        swipeRefreshLayout.setRefreshing(false);
    }

}
