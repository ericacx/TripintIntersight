package com.tripint.intersight.fragment.flipview;

import android.content.Context;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.widget.image.TouchImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

/**
 * Created by Eric on 16/9/20.
 */
public class ViewPagerBannerAdapter extends PagerAdapter{


    private Context context;
    private List<String> resList;

    public ViewPagerBannerAdapter(Context context, List<String> resList){
        this.context = context;
        this.resList = resList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        Glide.with(context).load(resList.get(position % resList.size()))
                .into(imageView);
        ((ViewPager)container).addView(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView ;
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
