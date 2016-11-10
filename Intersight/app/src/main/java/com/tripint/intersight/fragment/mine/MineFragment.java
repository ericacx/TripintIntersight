package com.tripint.intersight.fragment.mine;


import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.tripint.intersight.common.Constants;
import com.tripint.intersight.common.imagepicker.AndroidImagePicker;
import com.tripint.intersight.common.utils.FileUtils;
import com.tripint.intersight.common.widget.cropiamge.CropImageActivity;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.filter.ItemModel;
import com.tripint.intersight.common.widget.filter.adapter.SimpleTextAdapter;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterItemClickListener;
import com.tripint.intersight.common.widget.filter.typeview.SingleListView;
import com.tripint.intersight.common.widget.filter.util.UIUtil;
import com.tripint.intersight.common.widget.filter.view.FilterCheckedTextView;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.mine.QiniuTokenEntity;
import com.tripint.intersight.entity.mine.UserHomeEntity;
import com.tripint.intersight.event.PersonalEvent;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.event.StartFragmentForResultEvent;
import com.tripint.intersight.fragment.PersonalInfoFragment;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;
import com.tripint.intersight.fragment.mine.message.NewMessageFragment;
import com.tripint.intersight.fragment.mine.photo.photo.PhotoWallActivity;
import com.tripint.intersight.fragment.mine.setting.SettingFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    @Bind(R.id.text_view_my_account_detail)
    TextView textViewMyAccountDetail;
    @Bind(R.id.textView_my_focus)
    TextView textViewMyFocus;
    //    @Bind(R.id.text_view_my_star)
//    TextView textViewMyStar;
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
        toolbarTitle.setText("我的");
        toolbarSearchButton.setImageResource(R.mipmap.iconfont_wodexx);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Subscribe
    private void initView(View view) {
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
            R.id.text_view_my_option, R.id.text_view_my_money, R.id.text_view_my_account_detail,
            R.id.textView_my_focus, R.id.text_view_help, R.id.text_view_setting
            , R.id.mineCIVPersonalInfo,R.id.toolbar_search_button})
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
                            Log.i(Constants.TAG, "=====onImageCropComplete (get bitmap=" + bmp.toString());
                            mineCIVPersonalInfo.setVisibility(View.VISIBLE);
                            mineCIVPersonalInfo.setImageBitmap(bmp);

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
            case R.id.text_view_my_account_detail://账户明细
                EventBus.getDefault().post(new StartFragmentEvent(AccountDetailFragment.newInstance()));
                break;
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
        }
    }

    private View createSingleListView(List<ItemModel> list) {
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
                        if (item.getKey() == 1) {
                            cameraMethod();
                        } else {
                            if (InterSightApp.getApp().getPermissionsChecker().lacksPermissions(InterSightApp.getApp().CONTACTS)) {
                                PermissionsActivity.startActivityForResult(mActivity, InterSightApp.getApp().REQUEST_CODE, InterSightApp.getApp().CONTACTS);
                            } else {
                                //跳转至最终的选择图片页面
                                Intent intent = new Intent(mActivity, PhotoWallActivity.class);
                                intent.putExtra("maximum", 1);
                                startActivityForResult(intent, 200);
                            }

                        }
                        dialog.dismiss();
                    }
                });

        singleListView.setList(list, -1);

        return singleListView;
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if(resultCode == Activity.RESULT_OK){
//            if (requestCode == REQ_IMAGE) {
//                mineCIVPersonalInfo.setVisibility(View.GONE);
//
//                List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
////                mAdapter.clear();
////                mAdapter.addAll(imageList);
//            }/*else if(requestCode == REQ_IMAGE_CROP){
//                Bitmap bmp = (Bitmap)data.getExtras().get("bitmap");
//                Log.i(TAG,"-----"+bmp.getRowBytes());
//            }*/
//        }
//
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 200 && resultCode == 400) {
//            ArrayList<String> paths = (ArrayList) data.getExtras().getSerializable("picData");
//            File temp = new File(paths.get(0).toString());
//            strImgPath = paths.get(0).toString();
////            startPhotoZoom(Uri.fromFile(temp));
//            mineCIVPersonalInfo.setImageBitmap(BitmapFactory.decodeFile(strImgPath));
//        } else if (requestCode == RESULT_CAPTURE_IMAGE && resultCode == mActivity.RESULT_OK) {
//            try {
//                if (null != data && null != data.getData()) {
//                    imgUri = data.getData();
//                }
//                strImgPath = FileUtils.getImageAbsolutePath(mActivity, imgUri);
//                mineCIVPersonalInfo.setImageBitmap(BitmapFactory.decodeFile(strImgPath));
//
////                startPhotoZoom(imgUri);
//            } catch (Exception e) {
//                Log.e("-----Exception--|=", e.getMessage());
//            }
//        } else if (requestCode == TAILORING && resultCode == mActivity.RESULT_OK) {
//            if (null != data) {
//                setPicToView(data);
//            }
//        }
////        mActivity.onActivityResult(requestCode, resultCode, data);
//    }

    /**
     * 照相功能
     */
    private void cameraMethod() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.TITLE, filename);

        imgUri = mActivity.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, RESULT_CAPTURE_IMAGE);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        try {
            strImgPath = FileUtils.getImageAbsolutePath(mActivity, uri);
            // create explicit intent
            Intent intent = new Intent(mActivity, CropImageActivity.class);

            // tell CropImageActivity activity to look for image to crop
            intent.putExtra(CropImageActivity.IMAGE_PATH, strImgPath);

            // allow CropImageActivity activity to rescale image
            intent.putExtra(CropImageActivity.SCALE, true);

            // if the aspect ratio is fixed to ratio 3/2
            intent.putExtra(CropImageActivity.ASPECT_X, 1);
            intent.putExtra(CropImageActivity.ASPECT_Y, 1);
            intent.putExtra(CropImageActivity.OUTPUT_X, 360);
            intent.putExtra(CropImageActivity.OUTPUT_Y, 360);

            // start activity CropImageActivity with certain request code and listen
            // for result
            startActivityForResult(intent, TAILORING);


        } catch (Exception e) {

        }
    }

//    /**
//     * 修改
//     *
//     * @param _userInfo
//     */
//    private void modify(final MyUserInfo _userInfo) {
//        HashMap<String, String> requestParams = new HashMap<String, String>();
//        requestParams.put("data", JSON.toJSONString(_userInfo));
//        modifyUserInfo = toModifyUserInfo(_userInfo);
//        userServer.modifyMyBaseInfo(new UserDataRepository(), AndroidSchedulers.mainThread(), modifyUserInfo, requestParams);
//    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            String path = extras.getString(CropImageActivity.IMAGE_PATH);
            Bitmap photo = null;
            // if nothing received
            if (path != null) {
                photo = BitmapFactory.decodeFile(path);
            } else {
                photo = BitmapFactory.decodeFile(strImgPath);
            }

            if (photo == null) {
                return;
            } else {
                mineCIVPersonalInfo.setImageBitmap(photo);
            }
            /**
             * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上
             * 传到服务器
             */
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] b = stream.toByteArray();
            // 将图片流以字符串形式存储下来
            String imageBase64Data = Base64.encodeToString(b, Base64.NO_WRAP);
//            userInfo.setIconUri(strImgPath);
//            mineCIVPersonalInfo.setImageBitmap(photo);
//            userInfo.setImgIco(new PicComment(FileUtils.getFileName(strImgPath), imageBase64Data));
//            modify(userInfo);
        }
    }

    @Override
    public void onRefresh() {
        MineDataHttpRequest.getInstance(mActivity).getUserHome(new ProgressSubscriber(subscriber, mActivity));
        swipeRefreshLayout.setRefreshing(false);
    }
}
