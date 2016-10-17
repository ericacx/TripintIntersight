package com.tripint.intersight.fragment.mine.photo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.ScreenUtils;
import com.tripint.intersight.fragment.mine.photo.utils.SDCardImageLoader;
import com.tripint.intersight.model.Img;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 作者：wangdakuan
 * 主要功能:
 * 创建时间：2015/7/20 11:04
 */
public class PicGVAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Img> imagePathList = null;

    private SDCardImageLoader loader;

    public PicGVAdapter(Context context) {
        this.context = context;
        loader = new SDCardImageLoader(ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenHeight(context));
    }

    @Override
    public int getCount() {
        return imagePathList == null ? 0 : imagePathList.size();
    }

    @Override
    public Img getItem(int position) {
        return imagePathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String filePath = getItem(position).getUrl();

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pic_grid, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setTag(filePath);
        loader.loadImage(3, filePath, holder.imageView);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.main_gridView_item_photo)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 添加刷新
     *
     * @param imagePathList
     */
    public void addRefreshData(ArrayList<Img> imagePathList) {
        if (null != this.imagePathList) {
            this.imagePathList.addAll(imagePathList);
        } else {
            this.imagePathList = imagePathList;
        }
        notifyDataSetChanged();
    }

    /**
     * 刷新原有数据
     *
     * @param imagePathList
     */
    public void refreshData(ArrayList<Img> imagePathList) {
        this.imagePathList = imagePathList;
        notifyDataSetChanged();
    }

    public ArrayList<Img> getImagePathList() {
        return imagePathList;
    }
}
