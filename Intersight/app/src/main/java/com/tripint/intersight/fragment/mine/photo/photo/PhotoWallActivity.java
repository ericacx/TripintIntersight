package com.tripint.intersight.fragment.mine.photo.photo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.activity.base.BaseActivity;
import com.tripint.intersight.common.utils.DialogPlusUtils;
import com.tripint.intersight.common.widget.dialogplus.DialogPlus;
import com.tripint.intersight.common.widget.dialogplus.ViewHolder;
import com.tripint.intersight.common.widget.filter.adapter.SimpleTextAdapter;
import com.tripint.intersight.common.widget.filter.interfaces.OnFilterItemClickListener;
import com.tripint.intersight.common.widget.filter.typeview.SingleListView;
import com.tripint.intersight.common.widget.filter.util.UIUtil;
import com.tripint.intersight.common.widget.filter.view.FilterCheckedTextView;
import com.tripint.intersight.fragment.mine.photo.adapter.PhotoWallAdapter;
import com.tripint.intersight.fragment.mine.photo.utils.Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择照片页面
 * Created by hanj on 14-10-15.
 */
public class PhotoWallActivity extends BaseActivity {

    @Bind(R.id.photo_wall_grid)
    GridView mPhotoWall; //图片展示

    @Bind(R.id.btn_all)
    TextView btnAll; //全部图片
    @Bind(R.id.btn_confirm)
    TextView btnConfirm; //确认
    @Bind(R.id.mask_view)
    View maskView;

    private ArrayList<String> list; //图片数据
    private PhotoWallAdapter adapter;
    private DialogPlus popWindow; //弹出菜单管理

    private int imagePathList = 0;
    private int maximum = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_photo_wall);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        imagePathList = getIntent().getIntExtra("imagePathList", 0);
        maximum = getIntent().getIntExtra("maximum", 5);
        btnConfirm.setText(String.format("确认(%s/%s)", imagePathList, maximum));
//        ScreenUtils.initScreen(this);
        initData();
    }

    private void initData() {
        list = getLatestImagePaths(100);
        adapter = new PhotoWallAdapter(this, list) {
            @Override
            public void selectionNumber(int number) {
                number = imagePathList + number;
                btnConfirm.setText(String.format("确认(%s/%s)", number, maximum));
            }
        };
        adapter.setMaxNumber(maximum - imagePathList);
        mPhotoWall.setAdapter(adapter);
    }

    @OnClick({R.id.btn_all, R.id.btn_confirm})
    public void setOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.btn_all: //全部图片
                maskView.setVisibility(View.VISIBLE);
                initWindow();
                break;
            case R.id.btn_confirm: //确认
                //选择图片完成,回到起始页面
                ArrayList<String> paths = getSelectImagePaths();
                if (paths != null) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("picData", paths);
                    intent.putExtras(bundle);
                    setResult(400, intent);
                }
                finish();
                break;
        }
    }

    /**
     * 根据图片所属文件夹路径，刷新页面
     */
    private void updateView(int code, String folderPath) {
        list.clear();
        adapter.clearSelectionMap();
        adapter.notifyDataSetChanged();

        if (code == 100) {   //某个相册
            int lastSeparator = folderPath.lastIndexOf(File.separator);
            String folderName = folderPath.substring(lastSeparator + 1);
//            titleTV.setText(folderName);
            list.addAll(getAllImagePathsByFolder(folderPath));
        } else if (code == 200) {  //最近照片
//            titleTV.setText("最近照片");
            list.addAll(getLatestImagePaths(100));
        }

        adapter.notifyDataSetChanged();
        if (list.size() > 0) {
            //滚动至顶部
            mPhotoWall.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 获取指定路径下的所有图片文件。
     */
    private ArrayList<String> getAllImagePathsByFolder(String folderPath) {
        File folder = new File(folderPath);
        String[] allFileNames = folder.list();
        if (allFileNames == null || allFileNames.length == 0) {
            return null;
        }

        ArrayList<String> imageFilePaths = new ArrayList<String>();
        for (int i = allFileNames.length - 1; i >= 0; i--) {
            if (Utility.isImage(allFileNames[i])) {
                imageFilePaths.add(folderPath + File.separator + allFileNames[i]);
            }
        }

        return imageFilePaths;
    }

    /**
     * 使用ContentProvider读取SD卡最近图片。
     */
    private ArrayList<String> getLatestImagePaths(int maxCount) {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;

        ContentResolver mContentResolver = getContentResolver();

        // 只查询jpg和png的图片,按最新修改排序
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);

        ArrayList<String> latestImagePaths = null;
        if (cursor != null) {
            //从最新的图片开始读取.
            //当cursor中没有数据时，cursor.moveToLast()将返回false
            if (cursor.moveToLast()) {
                latestImagePaths = new ArrayList<String>();

                while (true) {
                    // 获取图片的路径
                    String path = cursor.getString(0);
                    latestImagePaths.add(path);

                    if (latestImagePaths.size() >= maxCount || !cursor.moveToPrevious()) {
                        break;
                    }
                }
            }
            cursor.close();
        }

        return latestImagePaths;
    }

    /**
     * 获取已选择的图片路径
     */
    private ArrayList<String> getSelectImagePaths() {
        SparseBooleanArray map = adapter.getSelectionMap();
        if (map.size() == 0) {
            return null;
        }
        ArrayList<String> selectedImageList = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {
            if (map.get(i)) {
                selectedImageList.add(list.get(i));
            }
        }
        return selectedImageList;
    }

    /**
     * 初始化菜单
     */
    private void initWindow() {
        List<String> list = new ArrayList<String>();
        list.add("最近相册");
        //相册
        list.addAll(getImagePathsByContentProvider());
        DialogPlusUtils.Builder(this)
                .setHolder(DialogPlusUtils.VIEW, new ViewHolder(createSingleListView(list)))
                .setTitleName("请选择支付方式")
                .setIsHeader(true)
                .setIsFooter(false)
                .setIsExpanded(false)
                .setGravity(Gravity.BOTTOM)
                .showCompleteDialog();
    }

    private View createSingleListView(List<String> list) {
        SimpleTextAdapter adapter = new SimpleTextAdapter<String>(null, this) {
            @Override
            public String provideText(String item) {
                return item;
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(UIUtil.dp(PhotoWallActivity.this, 12), UIUtil.dp(PhotoWallActivity.this, 12), 0, UIUtil.dp(PhotoWallActivity.this, 12));
            }
        };
        SingleListView<String> singleListView = new SingleListView<>(PhotoWallActivity.this)
                .adapter(adapter)
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {

                    }
                });

        singleListView.setList(list, -1);

        return singleListView;
    }

    /**
     * 使用ContentProvider读取SD卡所有图片。
     */
    private ArrayList<String> getImagePathsByContentProvider() {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;

        ContentResolver mContentResolver = getContentResolver();

        // 只查询jpg和png的图片
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);
        ArrayList<String> list = null;
        if (cursor != null) {
            if (cursor.moveToLast()) {
                //路径缓存，防止多次扫描同一目录
                HashSet<String> cachePath = new HashSet<String>();
                list = new ArrayList<String>();

                while (true) {
                    // 获取图片的路径
                    String imagePath = cursor.getString(0);

                    File parentFile = new File(imagePath).getParentFile();
                    String parentPath = parentFile.getAbsolutePath();

                    //不扫描重复路径
                    if (!cachePath.contains(parentPath)) {
                        list.add(parentPath);
                        cachePath.add(parentPath);
                    }

                    if (!cursor.moveToPrevious()) {
                        break;
                    }
                }
            }
            cursor.close();
        }
        return list;
    }
}
