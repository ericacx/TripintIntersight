package com.tripint.intersight.fragment.mine.photo.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.ScreenUtils;
import com.tripint.intersight.fragment.mine.photo.utils.SDCardImageLoader;
import com.tripint.intersight.helper.CommonUtils;

import java.util.ArrayList;

/**
 * PhotoWall中GridView的适配器
 */
public abstract class PhotoWallAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imagePathList = null;
    private int maxNumber = 5; //可选择最大值
    private SDCardImageLoader loader;
    boolean ignoreChange = false;
    //记录是否被选择
    private SparseBooleanArray selectionMap;

    public PhotoWallAdapter(Context context, ArrayList<String> imagePathList) {
        this.context = context;
        this.imagePathList = imagePathList;

        loader = new SDCardImageLoader(ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenHeight(context));
        selectionMap = new SparseBooleanArray();
    }

    @Override
    public int getCount() {
        return imagePathList == null ? 0 : imagePathList.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String filePath = (String) getItem(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_photo_wall, null);
            holder = new ViewHolder();

            holder.imageView = (ImageView) convertView.findViewById(R.id.photo_wall_item_photo);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.photo_wall_item_cb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //tag的key必须使用id的方式定义以保证唯一，否则会出现IllegalArgumentException.
        holder.checkBox.setTag(R.id.tag_first, position);
        holder.checkBox.setTag(R.id.tag_second, holder.imageView);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!ignoreChange) {
                    Integer position = (Integer) buttonView.getTag(R.id.tag_first);
                    ImageView image = (ImageView) buttonView.getTag(R.id.tag_second);
                    if (isChecked) {
                        if (selectionMap.size() < maxNumber) {
                            selectionMap.put(position, isChecked);
                            image.setColorFilter(context.getResources().getColor(R.color.black_translucent));
                        } else {
                            holder.checkBox.setChecked(false);
                            CommonUtils.showToast("图片选择满了");
                        }
                    } else {
                        selectionMap.delete(position);
                        image.setColorFilter(null);
                    }
                    selectionNumber(selectionMap.size());
                }
            }
        });
        ignoreChange = true;
        holder.checkBox.setChecked(selectionMap.get(position));
        ignoreChange = false;
        holder.imageView.setTag(filePath);
        loader.loadImage(4, filePath, holder.imageView);
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        CheckBox checkBox;
    }

    public SparseBooleanArray getSelectionMap() {
        return selectionMap;
    }

    public void clearSelectionMap() {
        selectionMap.clear();
    }

    public abstract void selectionNumber(int number);

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }
}
