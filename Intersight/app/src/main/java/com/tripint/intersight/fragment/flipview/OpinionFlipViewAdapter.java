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

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.entity.article.ArticlesEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.create.CreateOpinionFragment;
import com.tripint.intersight.fragment.home.OpinionDetailFragment;

import org.greenrobot.eventbus.EventBus;

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
    private RequestLoadMoreListener mRequestLoadMoreListener;
    private final int VIEWTYPE_ONE = 0;//首页,带有banner
    private final int VIEWTYPE_TWO = 1;//图文
    private final int VIEWTYPE_THREE = 2;//只有文字

    List<String> urlList = new ArrayList<>();//banner图片的数据源

    public OpinionFlipViewAdapter(Context context, List<ArticlesEntity> list) {
        this.context = context;
        this.resList = list;
    }

    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (resList.get(position).getType() == 0) {
            return VIEWTYPE_ONE;//第1个条目加载第1种布局
        } else if (resList.get(position).getType() == 1) {
            return VIEWTYPE_TWO;//第2个条目加载第2种布局
        } else
//        if (resList.get(position).getType() == 2)
        {
            return VIEWTYPE_THREE;//其他的条目加载第3种布局
        }
//        else {
//            return position;
//        }
    }


    public List<ArticlesEntity> getResList() {
        return resList;
    }

    public void addResList(List<ArticlesEntity> resList) {
        if (this.resList != null) {
            this.resList.addAll(resList);
            notifyDataSetChanged();
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {

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
                    viewHolderTwo.opinionFlipviewTwoTime = ((TextView) convertView.findViewById(R.id.opinion_flipview_two_time));
                    viewHolderTwo.opinionFlipviewTwoAgreeNum = ((TextView) convertView.findViewById(R.id.opinion_flipview_two_agreeNum));
                    viewHolderTwo.opinionFlipviewTwoTalkNum = ((TextView) convertView.findViewById(R.id.opinion_flipview_two_talkNum));

                    convertView.setTag(viewHolderTwo);
                    break;
                case VIEWTYPE_THREE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.opinion_flipview_three, parent, false);

                    viewHolderThree.opioionFlipviewThreeHeader = ((TextView) convertView.findViewById(R.id.opioion_flipview_three_header));
                    viewHolderThree.opioionFlipviewThreeContent = ((TextView) convertView.findViewById(R.id.opioion_flipview_three_content));
                    viewHolderThree.opinionFlipviewThreeTime = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_time));
                    viewHolderThree.opinionFlipviewThreeName = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_name));
                    viewHolderThree.opinionFlipviewThreeTrade = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_trade));
                    viewHolderThree.opinionFlipviewThreeTitle = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_title));
                    viewHolderThree.opinionFlipviewThreeAgreeNum = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_agreeNum));
                    viewHolderThree.opinionFlipviewThreeTalkNum = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_talkNum));

                    viewHolderThree.opinionFlipviewThreeLlOne = ((LinearLayout) convertView.findViewById(R.id.opinion_flipview_three_ll_one));
//                    viewHolderThree.opinionFlipviewThreeLlTwo = ((LinearLayout) convertView.findViewById(R.id.opinion_flipview_three_ll_two));
//
//                    viewHolderThree.opioionFlipviewThreeContentTwo = ((TextView) convertView.findViewById(R.id.opioion_flipview_three_content_two));
//                    viewHolderThree.opinionFlipviewThreeTimeTwo = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_time_two));
//                    viewHolderThree.opinionFlipviewThreeNameTwo = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_name_two));
//                    viewHolderThree.opinionFlipviewThreeTradeTwo = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_trade_two));
//                    viewHolderThree.opinionFlipviewThreePositionTwo = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_position_two));
//                    viewHolderThree.opinionFlipviewThreeAgreeNumTwo = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_agreeNum_two));
//                    viewHolderThree.opinionFlipviewThreeTalkNumTwo = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_talkNum_two));
//                    viewHolderThree.opinionFlipviewThreeReportTwo = ((TextView) convertView.findViewById(R.id.opinion_flipview_three_report_two));
//
//                    viewHolderThree.opinionFlipviewThreeImageZanTwo = ((ImageView) convertView.findViewById(R.id.opinion_flipview_three_image_zan_two));
//                    viewHolderThree.opinionFlipviewThreeImageGuanzhuTwo = ((ImageView) convertView.findViewById(R.id.opinion_flipview_three_image_guanzhu_two));
//                    viewHolderThree.opinionFlipviewThreeImageJubaoTwo = ((ImageView) convertView.findViewById(R.id.opinion_flipview_three_image_jubao_two));
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
                    viewHolderOne.opinionFlipviewOneHeader.setText(resList.get(position).getArticle().get(0).getTitle());
                    viewHolderOne.opinionFlipviewOneContent.setText(resList.get(position).getArticle().get(0).getContent());

                    viewHolderOne.opinionFlipviewOneTrade.setText(resList.get(position).getArticle().get(0).getUserCompany());
                    viewHolderOne.opinionFlipviewOneTitle.setText(resList.get(position).getArticle().get(0).getUserAbility());
                    viewHolderOne.opinionFlipviewOneTime.setText(resList.get(position).getArticle().get(0).getCreateAt());
                    viewHolderOne.opinionFlipviewOneName.setText(resList.get(position).getArticle().get(0).getUserNickname());

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
                    viewHolderTwo.opinionFlipviewTwoAgreeNum.setText(resList.get(position).getImgTextDetail().get(0).getPraisesCount() + "");
                    viewHolderTwo.opinionFlipviewTwoTalkNum.setText(resList.get(position).getImgTextDetail().get(0).getCommentsCount() + "");
                    viewHolderTwo.opinionFlipviewTwoTrade.setText(resList.get(position).getImgTextDetail().get(0).getUserCompany());
                    viewHolderTwo.opinionFlipviewTwoName.setText(resList.get(position).getImgTextDetail().get(0).getUserNickname());
                    viewHolderTwo.opinionFlipviewTwoTitle.setText(resList.get(position).getImgTextDetail().get(0).getUserAbility());
                    viewHolderTwo.opinionFlipviewTwoTime.setText(resList.get(position).getImgTextDetail().get(0).getCreateAt());

                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (position == 1) {
                                int articleIdOne = resList.get(1).getImgTextDetail().get(0).getId();
                                int typeOne = resList.get(1).getImgTextDetail().get(0).getType();
                                EventBus.getDefault().post(new StartFragmentEvent(OpinionDetailFragment.newInstance(typeOne, articleIdOne)));
                            } else {
                                int articleIdOne = resList.get(position).getImgTextDetail().get(0).getId();
                                int typeOne = resList.get(position).getImgTextDetail().get(0).getType();
                                EventBus.getDefault().post(new StartFragmentEvent(OpinionDetailFragment.newInstance(typeOne, articleIdOne)));
                            }
                        }
                    });
                }
                break;
            case VIEWTYPE_THREE:
                if (position > 0 && resList.get(position).getTextDetail() != null) {
                    if (resList.get(position).getTextDetail().size() == 1) {

                        viewHolderThree.opioionFlipviewThreeHeader.setText(resList.get(position).getTextDetail().get(0).getTitle());
                        viewHolderThree.opioionFlipviewThreeContent.setText(resList.get(position).getTextDetail().get(0).getContent());
                        viewHolderThree.opinionFlipviewThreeTime.setText(resList.get(position).getTextDetail().get(0).getCreateAt());
                        viewHolderThree.opinionFlipviewThreeName.setText(resList.get(position).getTextDetail().get(0).getUserNickname());
                        viewHolderThree.opinionFlipviewThreeTrade.setText(resList.get(position).getTextDetail().get(0).getUserCompany());
                        viewHolderThree.opinionFlipviewThreeTitle.setText(resList.get(position).getTextDetail().get(0).getUserAbility());
                        viewHolderThree.opinionFlipviewThreeAgreeNum.setText(resList.get(position).getTextDetail().get(0).getPraisesCount() + "");
                        viewHolderThree.opinionFlipviewThreeTalkNum.setText(resList.get(position).getTextDetail().get(0).getCommentsCount() + "");

                        viewHolderThree.opinionFlipviewThreeLlOne.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (position == 1) {
                                    int typeTwo = resList.get(1).getTextDetail().get(0).getType();
                                    int articleIdTwo = resList.get(1).getTextDetail().get(0).getId();
                                    EventBus.getDefault().post(new StartFragmentEvent(OpinionDetailFragment.newInstance(typeTwo, articleIdTwo)));
                                } else {
                                    int typeTwo = resList.get(position).getTextDetail().get(0).getType();
                                    int articleIdTwo = resList.get(position).getTextDetail().get(0).getId();
                                    EventBus.getDefault().post(new StartFragmentEvent(OpinionDetailFragment.newInstance(typeTwo, articleIdTwo)));
                                }

                            }
                        });


                    }


                }

//                convertView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        EventBus.getDefault().post(new StartFragmentEvent(OpinionDetailFragment.newInstance(typeOne,articleIdOne)));
//                    }
//                });

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
        @Bind(R.id.opinion_flipview_two_time)
        TextView opinionFlipviewTwoTime;//内容

    }

    /**
     * 第3种布局的控件
     */
    class ViewHolderThree {

        @Bind(R.id.opioion_flipview_three_header)
        TextView opioionFlipviewThreeHeader;
        @Bind(R.id.opioion_flipview_three_content)
        TextView opioionFlipviewThreeContent;//内容
        @Bind(R.id.opinion_flipview_three_time)
        TextView opinionFlipviewThreeTime;//时间
        @Bind(R.id.opinion_flipview_three_name)
        TextView opinionFlipviewThreeName;//名字
        @Bind(R.id.opinion_flipview_three_trade)
        TextView opinionFlipviewThreeTrade;//公司行业
        @Bind(R.id.opinion_flipview_three_title)
        TextView opinionFlipviewThreeTitle;//职位
        @Bind(R.id.opinion_flipview_three_agreeNum)
        TextView opinionFlipviewThreeAgreeNum;//点赞
        @Bind(R.id.opinion_flipview_three_talkNum)
        TextView opinionFlipviewThreeTalkNum;//评论数
        @Bind(R.id.opinion_flipview_three_report)
        TextView opinionFlipviewThreeReport;

        @Bind(R.id.opinion_flipview_three_ll_one)
        LinearLayout opinionFlipviewThreeLlOne;
//        @Bind(R.id.opinion_flipview_three_ll_two)
//        LinearLayout opinionFlipviewThreeLlTwo;
//
//        @Bind(R.id.opioion_flipview_three_content_two)
//        TextView opioionFlipviewThreeContentTwo;
//        @Bind(R.id.opinion_flipview_three_time_two)
//        TextView opinionFlipviewThreeTimeTwo;
//        @Bind(R.id.opinion_flipview_three_name_two)
//        TextView opinionFlipviewThreeNameTwo;
//        @Bind(R.id.opinion_flipview_three_trade_two)
//        TextView opinionFlipviewThreeTradeTwo;
//        @Bind(R.id.opinion_flipview_three_position_two)
//        TextView opinionFlipviewThreePositionTwo;
//        @Bind(R.id.opinion_flipview_three_agreeNum_two)
//        TextView opinionFlipviewThreeAgreeNumTwo;
//        @Bind(R.id.opinion_flipview_three_talkNum_two)
//        TextView opinionFlipviewThreeTalkNumTwo;
//        @Bind(R.id.opinion_flipview_three_report_two)
//        TextView opinionFlipviewThreeReportTwo;
//        @Bind(R.id.opinion_flipview_three_image_zan_two)
//        ImageView opinionFlipviewThreeImageZanTwo;
//        @Bind(R.id.opinion_flipview_three_image_guanzhu_two)
//        ImageView opinionFlipviewThreeImageGuanzhuTwo;
//        @Bind(R.id.opinion_flipview_three_image_jubao_two)
//        ImageView opinionFlipviewThreeImageJubaoTwo;
    }


    public interface RequestLoadMoreListener {

        void onLoadMoreRequested();

    }
}
