package com.tripint.intersight.widget.image;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tripint.intersight.R;
import com.tripint.intersight.utils.StringUtils;


/**
 * 作者：wangzhihao
 * 主要功能:图片请求控件
 * 创建时间：2016/7/20 09:28
 */
public class RequestImageView extends ImageView {

    public RequestImageView(Context context) {
        super(context);
    }

    public RequestImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RequestImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RequestImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 设置错误图片
     *
     * @param url 路径
     */
    public void setError(Integer url) {
        setImageResource(url);
    }

    /**
     * 加载数据
     *
     * @param url 路径
     */
    public void setImageUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            url = "There is no picture";
        }
        if (null == this || null == this.getContext()) {
            return;
        }
        try {
            Glide.with(this.getContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.loading_normal_icon)
                    .error(R.mipmap.loading_normal_icon)
                    .fitCenter()
                    .crossFade()
                    .dontAnimate()
                    .into(this);
        } catch (Exception e) {
            Log.e("春秋旅游", "图片加载=" + e.getMessage());
        }
    }

}
