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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.entity.article.ArticlesEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Eric on 16/9/20.
 */
public class OpinionFlipViewAdapter extends BaseAdapter {

    private Context context;
    private List<TextView> textViews;

    private List<ArticlesEntity> resList;//数据源

    private final int VIEWTYPE_ONE = 0;//首页,带有banner
    private final int VIEWTYPE_TWO = 1;//图文
    private final int VIEWTYPE_THREE = 2;//只有文字

    List<String> urlList = new ArrayList<>();//banner图片的数据源

    public OpinionFlipViewAdapter(Context context, List<ArticlesEntity> list) {
        this.context = context;
        this.resList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (resList.get(position).getType() == 0) {
            return VIEWTYPE_ONE;//第1个条目加载第1种布局
        } else if (resList.get(position).getType() == 1) {
            return VIEWTYPE_TWO;//第2个条目加载第2种布局
        }
        else
//        if (resList.get(position).getType() == 2)
        {
            return VIEWTYPE_THREE;//其他的条目加载第3种布局
        }
//        else {
//            return position;
//        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;//三种类型的布局
    }

    @Override
    public int getCount() {
        return resList.size();
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
                    viewHolderOne.opinionFlipviewOneViewpager = ((ViewPager) convertView.findViewById(R.id.opinion_flipview_one_viewpager));
                    viewHolderOne.opinionFlipviewOneTv1 = (TextView) convertView.findViewById(R.id.opinion_flipview_one_tv1);
                    viewHolderOne.opinionFlipviewOneTv2 = ((TextView) convertView.findViewById(R.id.opinion_flipview_one_tv2));
                    viewHolderOne.opinionFlipviewOneTv3 = ((TextView) convertView.findViewById(R.id.opinion_flipview_one_tv3));
                    viewHolderOne.opinionFlipviewOneHeader = ((TextView) convertView.findViewById(R.id.opinion_flipview_one_header));
                    viewHolderOne.opinionFlipviewOneContent = ((TextView) convertView.findViewById(R.id.opinion_flipview_one_content));
                    viewHolderOne.opinionFlipviewOneName = ((TextView) convertView.findViewById(R.id.opinion_flipview_one_name));
                    viewHolderOne.opinionFlipviewOneTrade = ((TextView) convertView.findViewById(R.id.opinion_flipview_one_trade));
                    viewHolderOne.opinionFlipviewOneTime = ((TextView) convertView.findViewById(R.id.opinion_flipview_one_time));
                    viewHolderOne.opinionFlipviewOneTitle = ((TextView) convertView.findViewById(R.id.opinion_flipview_one_title));
                    convertView.setTag(viewHolderOne);
                    break;
                case VIEWTYPE_TWO:
                    convertView = LayoutInflater.from(context).inflate(R.layout.opinion_flipview_two, parent, false);
                    viewHolderTwo.opinionFlipviewTwoPic = ((ImageView) convertView.findViewById(R.id.opinion_flipview_two_pic));
                    viewHolderTwo.opinionFlipviewTwoHeader = ((TextView) convertView.findViewById(R.id.opinion_flipview_two_header));
                    viewHolderTwo.opinionFlipviewTwoContent = ((TextView) convertView.findViewById(R.id.opinion_flipview_two_content));
                    viewHolderTwo.opinionFlipviewTwoName = ((TextView) convertView.findViewById(R.id.opinion_flipview_two_name));
                    viewHolderTwo.opinionFlipviewTwoTrade = ((TextView) convertView.findViewById(R.id.opinion_flipview_two_trade));
                    viewHolderTwo.opinionFlipviewTwoTitle = ((TextView) convertView.findViewById(R.id.opinion_flipview_two_title));
                    viewHolderTwo.opinionFlipviewTwoAgreeNum = ((TextView) convertView.findViewById(R.id.opinion_flipview_two_agreeNum));
                    viewHolderTwo.opinionFlipviewTwoTalkNum = ((TextView) convertView.findViewById(R.id.opinion_flipview_two_talkNum));

                    convertView.setTag(viewHolderTwo);
                    break;
                case VIEWTYPE_THREE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.opinion_flipview_three, parent, false);
                    viewHolderThree.opioionFlipviewThreeContent = ((TextView) convertView.findViewById(R.id.opioion_flipview_three_content));
                    viewHolderThree.opinionFlipviewThreeTime = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_time));
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


        //设置资源
        switch (type) {
            case VIEWTYPE_ONE:
                textViews = new ArrayList<TextView>();
                textViews.add(viewHolderOne.opinionFlipviewOneTv1);
                textViews.add(viewHolderOne.opinionFlipviewOneTv2);
                textViews.add(viewHolderOne.opinionFlipviewOneTv3);

                final ViewHolderOne finalViewHolderOne1 = viewHolderOne;
                viewHolderOne.opinionFlipviewOneTv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finalViewHolderOne1.opinionFlipviewOneViewpager.setCurrentItem(
                                finalViewHolderOne1.opinionFlipviewOneViewpager.getCurrentItem()
                                        - finalViewHolderOne1.opinionFlipviewOneViewpager.getCurrentItem() % 3);
                    }
                });

                final ViewHolderOne finalViewHolderOne2 = viewHolderOne;
                viewHolderOne.opinionFlipviewOneTv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finalViewHolderOne2.opinionFlipviewOneViewpager.setCurrentItem(
                                finalViewHolderOne2.opinionFlipviewOneViewpager.getCurrentItem()
                                        - finalViewHolderOne2.opinionFlipviewOneViewpager.getCurrentItem() % 3 + 1);
                    }
                });

                final ViewHolderOne finalViewHolderOne3 = viewHolderOne;
                viewHolderOne.opinionFlipviewOneTv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finalViewHolderOne3.opinionFlipviewOneViewpager.setCurrentItem(
                                finalViewHolderOne3.opinionFlipviewOneViewpager.getCurrentItem()
                                        - finalViewHolderOne3.opinionFlipviewOneViewpager.getCurrentItem() % 3 + 2);
                    }
                });


                for (int i = 0; i < resList.get(position).getBanner().size(); i++) {
                    urlList.add(resList.get(position).getBanner().get(i).getUrl());
                }
                viewHolderOne.opinionFlipviewOneViewpager.setAdapter(new ViewPagerBannerAdapter(context, urlList));
                viewHolderOne.opinionFlipviewOneViewpager.setCurrentItem(1200);
                Log.e("position", viewHolderOne.opinionFlipviewOneViewpager.getCurrentItem() + "");
                viewHolderOne.opinionFlipviewOneViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                if (resList.get(position).getArticle() != null) {
                    viewHolderOne.opinionFlipviewOneHeader.setText(resList.get(position).getArticle().get(position).getTitle());
                    viewHolderOne.opinionFlipviewOneContent.setText(resList.get(position).getArticle().get(position).getContent());

                    if (resList.get(position).getArticle().get(position).getIndustry() != null) {
                        viewHolderOne.opinionFlipviewOneTrade.setText(resList.get(position).getArticle().get(position).getIndustry().getName());
                    }
                    if (resList.get(position).getArticle().get(position).getUserInfo() != null) {
                        viewHolderOne.opinionFlipviewOneTitle.setText(resList.get(position).getArticle().get(position).getUserInfo().getPosition().getName());
                        viewHolderOne.opinionFlipviewOneTime.setText(resList.get(position).getArticle().get(position).getUserInfo().getCreateAt());
                        viewHolderOne.opinionFlipviewOneName.setText(resList.get(position).getArticle().get(position).getUserInfo().getNickname());
                    }

                }
                break;
            case VIEWTYPE_TWO:
                if (resList.get(position).getImgTextDetail() != null) {
                    Glide.with(context).load(resList.get(position).getImgTextDetail().get(0).getThumb())
                            .crossFade()
                            .placeholder(R.drawable.loading_normal_icon)
                            .into(viewHolderTwo.opinionFlipviewTwoPic);
                    viewHolderTwo.opinionFlipviewTwoHeader.setText(resList.get(position).getImgTextDetail().get(0).getTitle());
                    viewHolderTwo.opinionFlipviewTwoContent.setText(resList.get(position).getImgTextDetail().get(0).getContent());
//                    viewHolderTwo.opinionFlipviewTwoAgreeNum.setText(resList.get(position).getImgTextDetail().get(0).getPraises());
//                    viewHolderTwo.opinionFlipviewTwoTalkNum.setText(resList.get(position).getImgTextDetail().get(0).getComments());
                    if (resList.get(position).getImgTextDetail().get(0).getIndustry() != null) {//行业
                        viewHolderTwo.opinionFlipviewTwoTrade.setText(resList.get(position).getImgTextDetail().get(0).getIndustry().getName());
                    }
                    if (resList.get(position).getImgTextDetail().get(0).getUserInfo() != null) {
                        viewHolderTwo.opinionFlipviewTwoName.setText(resList.get(position).getImgTextDetail().get(0).getUserInfo().getNickname());
                        if (resList.get(position).getImgTextDetail().get(0).getUserInfo().getPosition() != null) {
                            viewHolderTwo.opinionFlipviewTwoTitle.setText(resList.get(position).getImgTextDetail().get(0).getUserInfo().getPosition().getName());
                        }
                    }
                }
                break;
            case VIEWTYPE_THREE:
//                if (resList.get(position).getTextDetail() != null) {
//                    viewHolderThree.opioionFlipviewThreeContent.setText(resList.get(position).getTextDetail().get(0).getContent());
//                    viewHolderThree.opinionFlipviewThreeTime.setText(resList.get(position).getTextDetail().get(0).getCreateAt());

//                    viewHolderThree.opioionFlipviewThreeContent.setText(resList.get(position).getTextDetail().get(1).getContent());
//                    viewHolderThree.opinionFlipviewThreeTime.setText(resList.get(position).getTextDetail().get(1).getCreateAt());

//                }
                break;
        }
        return convertView;
    }

    /**
     * 第1种布局的控件
     */
    class ViewHolderOne {
        ViewPager opinionFlipviewOneViewpager;
        @Bind(R.id.opinion_flipview_one_tv1)
        TextView opinionFlipviewOneTv1;
        @Bind(R.id.opinion_flipview_one_tv2)
        TextView opinionFlipviewOneTv2;
        @Bind(R.id.opinion_flipview_one_tv3)
        TextView opinionFlipviewOneTv3;
        @Bind(R.id.opinion_flipview_one_header)
        TextView opinionFlipviewOneHeader;//标题
        @Bind(R.id.opinion_flipview_one_content)
        TextView opinionFlipviewOneContent;//内容
        @Bind(R.id.opinion_flipview_one_name)
        TextView opinionFlipviewOneName;//名字
        @Bind(R.id.opinion_flipview_one_trade)
        TextView opinionFlipviewOneTrade;//行业
        @Bind(R.id.opinion_flipview_one_title)
        TextView opinionFlipviewOneTitle;//职位
        @Bind(R.id.opinion_flipview_one_time)
        TextView opinionFlipviewOneTime;//时间
        @Bind(R.id.opinion_flipview_one_down)
        ImageView opinionFlipviewOneDown;//下滑
    }

    /**
     * 第2种布局的控件
     */
    class ViewHolderTwo {
        @Bind(R.id.opinion_flipview_two_pic)
        ImageView opinionFlipviewTwoPic;//图片
        @Bind(R.id.opioion_flipview_two_submit)
        Button opioionFlipviewTwoSubmit;//发表观点按钮
        @Bind(R.id.opinion_flipview_two_header)
        TextView opinionFlipviewTwoHeader;//标题
        @Bind(R.id.opinion_flipview_two_name)
        TextView opinionFlipviewTwoName;//名字
        @Bind(R.id.opinion_flipview_two_trade)
        TextView opinionFlipviewTwoTrade;//行业
        @Bind(R.id.opinion_flipview_two_title)
        TextView opinionFlipviewTwoTitle;//职位
        @Bind(R.id.opinion_flipview_two_agreeNum)
        TextView opinionFlipviewTwoAgreeNum;//赞数
        @Bind(R.id.opinion_flipview_two_talkNum)
        TextView opinionFlipviewTwoTalkNum;//评论数
        @Bind(R.id.opinion_flipview_two_report)
        TextView opinionFlipviewTwoReport;//举报
        @Bind(R.id.opinion_flipview_two_content)
        TextView opinionFlipviewTwoContent;//内容
    }

    /**
     * 第3种布局的控件
     */
    class ViewHolderThree {
        @Bind(R.id.opioion_flipview_three_content)
        TextView opioionFlipviewThreeContent;
        @Bind(R.id.opinion_flipview_three_time)
        TextView opinionFlipviewThreeTime;
        @Bind(R.id.opinion_flipview_two_name)
        TextView opinionFlipviewTwoName;
        @Bind(R.id.opinion_flipview_two_trade)
        TextView opinionFlipviewTwoTrade;
        @Bind(R.id.opinion_flipview_two_title)
        TextView opinionFlipviewTwoTitle;
        @Bind(R.id.opinion_flipview_three_agreeNum)
        TextView opinionFlipviewThreeAgreeNum;
        @Bind(R.id.opinion_flipview_three_talkNum)
        TextView opinionFlipviewThreeTalkNum;
        @Bind(R.id.opinion_flipview_three_report)
        TextView opinionFlipviewThreeReport;

    }

}
