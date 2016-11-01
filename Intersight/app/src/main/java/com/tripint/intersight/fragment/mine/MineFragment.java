package com.tripint.intersight.fragment.mine;


import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.activity.LoginActivity;
import com.tripint.intersight.activity.PermissionsActivity;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.utils.FileUtils;
import com.tripint.intersight.common.utils.PhotoUtil;
import com.tripint.intersight.common.utils.TakePhotoUtil;
import com.tripint.intersight.common.widget.cropiamge.CropImageActivity;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.filter.ItemModel;
import com.tripint.intersight.common.widget.filter.adapter.SimpleTextAdapter;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterItemClickListener;
import com.tripint.intersight.common.widget.filter.typeview.SingleListView;
import com.tripint.intersight.common.widget.filter.util.UIUtil;
import com.tripint.intersight.common.widget.filter.view.FilterCheckedTextView;
import com.tripint.intersight.entity.mine.UserHomeEntity;
import com.tripint.intersight.event.PersonalEvent;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.event.StartFragmentForResultEvent;
import com.tripint.intersight.fragment.PersonalInfoFragment;
import com.tripint.intersight.fragment.base.BaseLazyMainFragment;
import com.tripint.intersight.fragment.mine.setting.SettingFragment;
import com.tripint.intersight.service.MineDataHttpRequest;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseLazyMainFragment {

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
    @Bind(R.id.text_view_help)
    TextView textViewHelp;
    @Bind(R.id.text_view_setting)
    TextView textViewSetting;

    private PageDataSubscriberOnNext<UserHomeEntity> subscriber;

    private UserHomeEntity data;

    private Uri imgUri;

    private DialogPlus dialog;

    private static final int RESULT_CAPTURE_IMAGE = 1;// 照相的requestCode
    private static final int TAILORING = 2;// 裁剪
    private String strImgPath;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine1, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }


    @Subscribe
    private void initView(View view) {
        mineTextViewName.setText(data.getNickname());
        textViewMyMoney.setText(data.getBalance());
        Glide.with(mActivity).load(data.getAvatar())
                .crossFade()
                .fitCenter()
                .placeholder(R.mipmap.ic_avatar)
                .into(mineCIVPersonalInfo);
    }

    private void httpRequestData() {
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
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.mineIvRewriteInfo, R.id.text_view_mine_ask_answer, R.id.text_view_mine_interview,
            R.id.text_view_my_option, R.id.text_view_my_money, R.id.text_view_my_account_detail,
            R.id.textView_my_focus, R.id.text_view_help, R.id.text_view_setting
            , R.id.mineCIVPersonalInfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mineCIVPersonalInfo:
//                if (InterSightApp.getApp().getPermissionsChecker().lacksPermissions(InterSightApp.getApp().FILE_CAMERA)) {
//                    PermissionsActivity.startActivityForResult(mActivity, InterSightApp.getApp().REQUEST_CODE, InterSightApp.getApp().FILE_CAMERA);
//                } else {
//
//                    PhotoUtil.showDialog(mActivity);
//                    break;
//                }

                PhotoUtil.showDialog(this);
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
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = PhotoUtil.dealActivityResult(this, requestCode, resultCode, data, true);
        Log.e("bitmap", String.valueOf(bitmap));
        mineCIVPersonalInfo.setImageBitmap(bitmap);
        super.onActivityResult(requestCode, resultCode, data);
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
//                            cameraMethod();
                            //打开拍照
                        } else {
                            if (InterSightApp.getApp().getPermissionsChecker().lacksPermissions(InterSightApp.getApp().CONTACTS)) {
                                PermissionsActivity.startActivityForResult(mActivity, InterSightApp.getApp().REQUEST_CODE, InterSightApp.getApp().CONTACTS);
                            } else {
//                                //跳转至最终的选择图片页面
//                                Intent intent = new Intent(mActivity, PhotoWallActivity.class);
//                                intent.putExtra("maximum", 1);
//                                startActivityForResult(intent, 200);
                                // 打开相册
                            }

                        }
                        dialog.dismiss();
                    }
                });

        singleListView.setList(list, -1);

        return singleListView;
    }

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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 200 && resultCode == 400) {
//            ArrayList<String> paths = (ArrayList) data.getExtras().getSerializable("picData");
//            File temp = new File(paths.get(0).toString());
//            strImgPath = paths.get(0).toString();
//            startPhotoZoom(Uri.fromFile(temp));
//        } else if (requestCode == RESULT_CAPTURE_IMAGE && resultCode == mActivity.RESULT_OK) {
//            try {
//                if (null != data && null != data.getData()) {
//                    imgUri = data.getData();
//                }
//                startPhotoZoom(imgUri);
//            } catch (Exception e) {
//                Log.e("-----Exception--|=", e.getMessage());
//            }
//        } else if (requestCode == TAILORING && resultCode == mActivity.RESULT_OK) {
//            if (null != data) {
//                setPicToView(data);
//            }
//        }
////        onActivityResult(requestCode, resultCode, data);
////        mActivity.onActivityResult(requestCode, resultCode, data);
//    }


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
            intent.putExtra(CropImageActivity.OUTPUT_X, 120);
            intent.putExtra(CropImageActivity.OUTPUT_Y, 120);

            // start activity CropImageActivity with certain request code and listen
            // for result
            startActivityForResult(intent, TAILORING);


        } catch (Exception e) {

        }
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            String path = extras.getString(CropImageActivity.IMAGE_PATH);
            Bitmap bitmap = null;
            // if nothing received
            if (path != null) {
                bitmap = BitmapFactory.decodeFile(path);
            } else {
                bitmap = BitmapFactory.decodeFile(strImgPath);
            }

            mineCIVPersonalInfo.setImageBitmap(bitmap);
//            if (bitmap == null) {
//                return;
//            } else {
//                mineCIVPersonalInfo.setImageBitmap(bitmap);
//            }
//            /**
//             * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上
//             * 传到服务器
//             */
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
//            byte[] b = stream.toByteArray();
//            // 将图片流以字符串形式存储下来
//            String imageBase64Data = Base64.encodeToString(b, Base64.NO_WRAP);
//
////            userInfo.setIconUri(strImgPath);
//            mineCIVPersonalInfo.setImageBitmap(bitmap);
//            mineCIVPersonalInfo.setImageDrawable(bitmap);
////            userInfo.setImgIco(new PicComment(FileUtils.getFileName(strImgPath), imageBase64Data));
////            modify(userInfo);
        }
    }



}
