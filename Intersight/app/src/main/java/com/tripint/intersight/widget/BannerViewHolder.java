package com.tripint.intersight.widget;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.tripint.intersight.widget.image.RequestImageView;


/**
 * 作者：wangdakuan
 * 主要功能：广告栏
 * 创建时间：16/8/16 17:10
 */
public class BannerViewHolder implements Holder<String> {
    private RequestImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new RequestImageView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        imageView.setImageUrl(data);
    }
}
