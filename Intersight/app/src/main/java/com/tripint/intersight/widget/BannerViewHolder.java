package com.tripint.intersight.widget;

import android.content.Context;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.SizeUtils;
import com.tripint.intersight.widget.image.RequestImageView;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;


/**
 * 作者：wangdakuan
 * 主要功能：广告栏
 * 创建时间：16/8/16 17:10
 */
public class BannerViewHolder implements Holder<String> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_image, null);
        imageView = (ImageView) view.findViewById(R.id.banner_image_view);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        Glide.with(context).load(data)
                .crossFade()
                .fitCenter()
                .dontAnimate()
                .placeholder(R.drawable.loading_normal_icon)
                .into(imageView);
    }
}
