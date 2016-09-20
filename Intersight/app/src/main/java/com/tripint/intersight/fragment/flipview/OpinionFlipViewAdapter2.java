package com.tripint.intersight.fragment.flipview;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripint.intersight.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eric on 16/9/20.
 */
public class OpinionFlipViewAdapter2 extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<TextView> textViews;
    private int[] images = {R.mipmap.p1,R.mipmap.p2,R.mipmap.p3};


    public OpinionFlipViewAdapter2(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.opinion_flipview_one, parent, false);
            holder = new ViewHolder();
            holder.opinionFlipViewPager = ((ViewPager) convertView.findViewById(R.id.opinionFlipViewPager));
            holder.opinionFlipViewTv1 = ((TextView) convertView.findViewById(R.id.opinionFlipViewTv1));
            holder.opinionFlipViewTv2 = ((TextView) convertView.findViewById(R.id.opinionFlipViewTv2));
            holder.opinionFlipViewTv3 = ((TextView) convertView.findViewById(R.id.opinionFlipViewTv3));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        textViews = new ArrayList<TextView>();

        textViews.add(holder.opinionFlipViewTv1);
        textViews.add(holder.opinionFlipViewTv2);
        textViews.add(holder.opinionFlipViewTv3);

        holder.opinionFlipViewTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.opinionFlipViewPager.setCurrentItem(holder.opinionFlipViewPager.getCurrentItem() - holder.opinionFlipViewPager.getCurrentItem() % 3);
            }
        });
        holder.opinionFlipViewTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.opinionFlipViewPager.setCurrentItem(holder.opinionFlipViewPager.getCurrentItem() - holder.opinionFlipViewPager.getCurrentItem() % 3 + 1);
            }
        });
        holder.opinionFlipViewTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.opinionFlipViewPager.setCurrentItem(holder.opinionFlipViewPager.getCurrentItem() - holder.opinionFlipViewPager.getCurrentItem() % 3 + 2);
            }
        });


        holder.opinionFlipViewPager.setAdapter(new ViewPagerBannerAdapter(context,images));
        holder.opinionFlipViewPager.setCurrentItem(1200);
        Log.e("position",holder.opinionFlipViewPager.getCurrentItem()+"" );
        holder.opinionFlipViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("po", position+"");
                for (int i = 0; i < textViews.size(); i++) {
                    textViews.get(i).setTextColor(ContextCompat.getColor(context, R.color.gray));
                }
                textViews.get(position % 3).setTextColor(ContextCompat.getColor(context, R.color.white));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return convertView;
    }

    @Override
    public void onClick(View view) {

    }


    static class ViewHolder {
        ViewPager opinionFlipViewPager;
        TextView opinionFlipViewTv1;
        TextView opinionFlipViewTv2;
        TextView opinionFlipViewTv3;
//        TextView opinionFlipViewTvTitle;
//        TextView opinionFlipViewTvContent;
//        TextView opinionTvName;
//        TextView opinionTvTrade;
//        TextView opinionTvTitle;
//        TextView opinionTvTime;
//        ImageView opinionIvDown;
    }

}
