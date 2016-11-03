package com.tripint.intersight.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tripint.intersight.R;
import com.tripint.intersight.widget.image.RequestImageView;


/**
 * 作者：wangdakuan
 * 主要功能：广告栏
 * 创建时间：16/8/16 17:10
 */
public class BannerViewHolder implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_image, null);
        imageView = new RequestImageView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        Glide.with(imageView.getContext())
                .load(data)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .dontAnimate()
                .thumbnail(0.5f)
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .into(imageView);
    }
}
