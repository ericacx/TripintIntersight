package com.tripint.intersight.fragment.flipview;

import android.content.Context;
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

/**
 * Created by Eric on 16/9/20.
 */
public class OpinionFlipViewAdapter2 extends BaseAdapter implements View.OnClickListener {

    ;
    private Context context;
    private List<TextView> textViews;
    private List<String> resList;
    private final int VIEWTYPE_ONE = 1;
    private final int VIEWTYPE_TWO = 2;
    private final int VIEWTYPE_THREE = 3;

    public OpinionFlipViewAdapter2(Context context, List<String> list) {
        this.context = context;
        this.resList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEWTYPE_ONE;
        } else if (position == 1) {
            return VIEWTYPE_TWO;
        } else {
            return VIEWTYPE_THREE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getCount() {
        return 5;
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

        int type = getItemViewType(position);

        ViewHolderOne viewHolderOne = null;
        ViewHolderTwo viewHolderTwo = null;
        ViewHolderThree viewHolderThree = null;

        //设置视图
        if (convertView == null) {

            viewHolderOne = new ViewHolderOne();
            viewHolderTwo = new ViewHolderTwo();
            viewHolderThree = new ViewHolderThree();

            //确定布局
            switch (type) {
                case VIEWTYPE_ONE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.opinion_flipview_one, parent, false);
                    viewHolderOne.opinionFlipViewPager = ((ViewPager) convertView.findViewById(R.id.opinionFlipViewPager));
                    viewHolderOne.opinionFlipViewTv1 = ((TextView) convertView.findViewById(R.id.opinionFlipViewTv1));
                    viewHolderOne.opinionFlipViewTv2 = ((TextView) convertView.findViewById(R.id.opinionFlipViewTv2));
                    viewHolderOne.opinionFlipViewTv3 = ((TextView) convertView.findViewById(R.id.opinionFlipViewTv3));
                    convertView.setTag(viewHolderOne);
                    break;
                case VIEWTYPE_TWO:
                    convertView = LayoutInflater.from(context).inflate(R.layout.opinion_flipview_two, parent, false);
                    convertView.setTag(viewHolderTwo);
                    break;
                case VIEWTYPE_THREE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.opinion_flipview_three, parent, false);
                    convertView.setTag(viewHolderThree);
                    break;
                default:
                    break;
            }

        } else {
            switch (type) {
                case VIEWTYPE_ONE:
                    viewHolderOne = (ViewHolderOne) convertView.getTag();
                    break;
                case VIEWTYPE_TWO:
                    viewHolderTwo = (ViewHolderTwo) convertView.getTag();
                    break;
                case VIEWTYPE_THREE:
                    viewHolderThree = (ViewHolderThree) convertView.getTag();
                    break;
            }

        }

        switch (type) {
            case VIEWTYPE_ONE:
                textViews = new ArrayList<TextView>();

                textViews.add(viewHolderOne.opinionFlipViewTv1);
                textViews.add(viewHolderOne.opinionFlipViewTv2);
                textViews.add(viewHolderOne.opinionFlipViewTv3);

                final ViewHolderOne finalViewHolderOne = viewHolderOne;
                viewHolderOne.opinionFlipViewTv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finalViewHolderOne.opinionFlipViewPager.setCurrentItem(finalViewHolderOne.opinionFlipViewPager.getCurrentItem() - finalViewHolderOne.opinionFlipViewPager.getCurrentItem() % 3);
                    }
                });
                final ViewHolderOne finalViewHolderOne1 = viewHolderOne;
                viewHolderOne.opinionFlipViewTv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finalViewHolderOne1.opinionFlipViewPager.setCurrentItem(finalViewHolderOne1.opinionFlipViewPager.getCurrentItem() - finalViewHolderOne1.opinionFlipViewPager.getCurrentItem() % 3 + 1);
                    }
                });
                final ViewHolderOne finalViewHolderOne2 = viewHolderOne;
                viewHolderOne.opinionFlipViewTv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finalViewHolderOne2.opinionFlipViewPager.setCurrentItem(finalViewHolderOne2.opinionFlipViewPager.getCurrentItem() - finalViewHolderOne2.opinionFlipViewPager.getCurrentItem() % 3 + 2);
                    }
                });


                viewHolderOne.opinionFlipViewPager.setAdapter(new ViewPagerBannerAdapter(context, resList));
                viewHolderOne.opinionFlipViewPager.setCurrentItem(1200);
                Log.e("position", viewHolderOne.opinionFlipViewPager.getCurrentItem() + "");
                viewHolderOne.opinionFlipViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        Log.e("po", position + "");
                        for (int i = 0; i < textViews.size(); i++) {
                            textViews.get(i).setTextColor(ContextCompat.getColor(context, R.color.gray));
                        }
                        textViews.get(position % 3).setTextColor(ContextCompat.getColor(context, R.color.white));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                break;
            case VIEWTYPE_TWO:

                break;
            case VIEWTYPE_THREE:

                break;
        }


        return convertView;
    }

    @Override
    public void onClick(View view) {

    }


    class ViewHolderOne {
        ViewPager opinionFlipViewPager;
        TextView opinionFlipViewTv1;
        TextView opinionFlipViewTv2;
        TextView opinionFlipViewTv3;
        TextView opinionFlipViewTvTitle;
        TextView opinionFlipViewTvContent;
        TextView opinionTvName;
        TextView opinionTvTrade;
        TextView opinionTvTitle;
        TextView opinionTvTime;
        ImageView opinionIvDown;
    }

    class ViewHolderTwo {
        @Bind(R.id.opinionFlipIvPic)
        ImageView opinionFlipIvPic;
        @Bind(R.id.opinionFlipViewTvTitle)
        TextView opinionFlipViewTvTitle;
        @Bind(R.id.opinionFlipViewTvName)
        TextView opinionFlipViewTvName;
        @Bind(R.id.opinionFlipViewTvTrade)
        TextView opinionFlipViewTvTrade;
        @Bind(R.id.opinionFlipViewTvPos)
        TextView opinionFlipViewTvPos;
        @Bind(R.id.opinionFlipViewAgree)
        ImageView opinionFlipViewAgree;
        @Bind(R.id.opinionFlipViewAgreeNum)
        TextView opinionFlipViewAgreeNum;
        @Bind(R.id.opinionFlipViewTalk)
        ImageView opinionFlipViewTalk;
        @Bind(R.id.opinionFlipViewTalkNum)
        TextView opinionFlipViewTalkNum;
        @Bind(R.id.opinionFlipViewReport)
        ImageView opinionFlipViewReport;
        @Bind(R.id.opinionFlipViewTvContent)
        TextView opinionFlipViewTvContent;
        @Bind(R.id.opioionFlipBtnSubmit)
        Button opioionFlipBtnSubmit;
        @Bind(R.id.linearlayout)
        LinearLayout linearlayout;
    }

    class ViewHolderThree {

    }

}
