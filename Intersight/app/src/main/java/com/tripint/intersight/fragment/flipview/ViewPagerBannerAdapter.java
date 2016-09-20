package com.tripint.intersight.fragment.flipview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.tripint.intersight.R;
import com.tripint.intersight.widget.image.TouchImageView;

import java.util.ArrayList;

/**
 * Created by Eric on 16/9/20.
 */
public class ViewPagerBannerAdapter extends PagerAdapter{


    private Context context;
    private int[] resList;

    public ViewPagerBannerAdapter(Context context, int[] resList){
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
        imageView.setImageResource(resList[position % resList.length]);
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
