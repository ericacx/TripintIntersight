package com.tripint.intersight.adapter;

import android.graphics.Color;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseMultiItemQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 个人中心 我的关注 我的观点 我的问答 我的访谈 Adapter
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MineCommonMultipleAdapter extends BaseMultiItemQuickAdapter<MineMultipleItemModel> {


    public MineCommonMultipleAdapter(List<MineMultipleItemModel> data) {
        super(data);

        //我的观点
        addItemType(MineMultipleItemModel.MY_OPTION, R.layout.item_recyclerview_myopinion);
        //我关注的观点
        addItemType(MineMultipleItemModel.MY_OPTION_FOLLOW, R.layout.item_recyclerview_myopinion);

        //他的观点
        addItemType(MineMultipleItemModel.HIS_OPTION, R.layout.item_recyclerview_myopinion);
        //他关注的观点
        addItemType(MineMultipleItemModel.HIS_OPTION_FOLLOW, R.layout.item_recyclerview_myopinion);

        //我的问答
        addItemType(MineMultipleItemModel.MY_DISCUSS, R.layout.item_recyclerview_myaskanswer);
        //我关注的问答
        addItemType(MineMultipleItemModel.MY_DISCUSS_FOLLOW, R.layout.item_recyclerview_myaskanswer_focused);

        //他的问答
        addItemType(MineMultipleItemModel.HIS_DISCUSS, R.layout.item_recyclerview_myaskanswer);
        //他关注的问答
        addItemType(MineMultipleItemModel.HIS_DISCUSS_FOLLOW, R.layout.item_recyclerview_myaskanswer_focused);

        //我的访谈
        addItemType(MineMultipleItemModel.MY_INTERVIEW, R.layout.item_recyclerview_myinterview);

        //他的访谈
        addItemType(MineMultipleItemModel.HIS_INTERVIEW, R.layout.item_recyclerview_myinterview);

        //我的关注
        addItemType(MineMultipleItemModel.MY_FOCUSE, R.layout.item_recyclerview_myfocused);
        //被关注
        addItemType(MineMultipleItemModel.MY_FOCUSE_FOLLOW, R.layout.item_recyclerview_myfocused);


        //新消息
        addItemType(MineMultipleItemModel.MY_MESSAGE_NEW, R.layout.item_recyclerview_message);

        //访谈消息
        addItemType(MineMultipleItemModel.MY_MESSAGE_INTERVIEW, R.layout.item_recyclerview_message);

        //问答消息
        addItemType(MineMultipleItemModel.MY_MESSAGE_ASK_ANSWER, R.layout.item_recyclerview_message);

        //评论/赞消息
        addItemType(MineMultipleItemModel.MY_MESSAGE_COMMENT_PRAISE, R.layout.item_recyclerview_comment);

        //账户明细
        addItemType(MineMultipleItemModel.MY_ACCOUNT_DETAIL, R.layout.item_recyclerview_accountdetail);
    }


    @Override
    protected void convert(BaseViewHolder helper, MineMultipleItemModel item) {

        switch (helper.getItemViewType()) {

            case MineMultipleItemModel.MY_OPTION://我的观点
                if (item.getMineFollowPointEntity() != null) {

                    helper
                            .setText(R.id.opinion_textView_mine_title_main, StringUtils.null2Length0(item.getMineFollowPointEntity().getTitle()))
                            .setText(R.id.opinion_textView_mine_sub_title, StringUtils.null2Length0(item.getMineFollowPointEntity().getContent()))
                            .setText(R.id.opinion_textView_item_focusnum, item.getMineFollowPointEntity().getFollows() + "")
                            .setText(R.id.opinion_textView_item_talknum, item.getMineFollowPointEntity().getComments() + "")
                            .setText(R.id.opinion_textView_item_agreenum, item.getMineFollowPointEntity().getPraises() + "")
                            .setText(R.id.opinion_textView_item_trade, StringUtils.null2Length0(item.getMineFollowPointEntity().getIndustryName()))
                    ;
                }

                break;

            case MineMultipleItemModel.MY_OPTION_FOLLOW://我关注的观点
                if (item.getMineFollowPointEntity() != null) {
                    helper
                            .setText(R.id.opinion_textView_owner_name, StringUtils.null2Length0(item.getMineFollowPointEntity().getNickname()))
                            .setText(R.id.opinion_textView_owner_job_title, StringUtils.null2Length0(item.getMineFollowPointEntity().getJobName()))
                            .setText(R.id.opinion_textView_owner_job_company, StringUtils.null2Length0(item.getMineFollowPointEntity().getCompanyName()))
                            .setText(R.id.opinion_textView_mine_title_main, StringUtils.null2Length0(item.getMineFollowPointEntity().getTitle()))
                            .setText(R.id.opinion_textView_mine_sub_title, StringUtils.null2Length0(item.getMineFollowPointEntity().getContent()))
                            .setText(R.id.opinion_textView_item_focusnum, item.getMineFollowPointEntity().getFollows() + "")
                            .setText(R.id.opinion_textView_item_talknum, item.getMineFollowPointEntity().getComments() + "")
                            .setText(R.id.opinion_textView_item_agreenum, item.getMineFollowPointEntity().getPraises() + "")
                            .setText(R.id.opinion_textView_item_trade, StringUtils.null2Length0(item.getMineFollowPointEntity().getIndustryName()))
                    ;

                    Glide.with(mContext).load(item.getMineFollowPointEntity().getAvatar())
                            .crossFade()
                            .placeholder(R.mipmap.ic_avatar)
                            .transform(new GlideCircleTransform(mContext))
                            .into((ImageView) helper.getView(R.id.opinion_imageView_owner_profile));
                }

                break;

            case MineMultipleItemModel.MY_DISCUSS://我的问答

                if (item.getAskAnswerEntity() != null) {
                    helper
                            .setText(R.id.ask_textView_mine_title_main, StringUtils.null2Length0(item.getAskAnswerEntity().getAction()))//我的提问
                            .setText(R.id.ask_textView_mine_title_main_status, StringUtils.null2Length0(item.getAskAnswerEntity().getStatus()))//已回答
                            .setText(R.id.ask_textView_mine_sub_title, StringUtils.null2Length0(item.getAskAnswerEntity().getContent()))//标题
                            .setText(R.id.ask_text_view_my_item_time, StringUtils.null2Length0(item.getAskAnswerEntity().getCreateAt()))//时间
                            .setText(R.id.ask_text_view_my_item_industry, StringUtils.null2Length0(item.getAskAnswerEntity().getIndustry()))//行业
                    ;
                }
                break;

            case MineMultipleItemModel.MY_DISCUSS_FOLLOW://我关注的问答

                if (item.getAskAnswerEntity() != null) {
                    helper
                            .setText(R.id.ask_focus_tv_name, StringUtils.null2Length0(item.getAskAnswerEntity().getUserNickname()))//名字
                            .setText(R.id.ask_focus_tv_title, StringUtils.null2Length0(item.getAskAnswerEntity().getUserAbility()))//职位
                            .setText(R.id.ask_focus_tv_company, StringUtils.null2Length0(item.getAskAnswerEntity().getUserCompany()))//公司
                            .setText(R.id.ask_focus_tv_header, StringUtils.null2Length0(item.getAskAnswerEntity().getContent()))//标题
                            .setText(R.id.ask_focus_text_view_my_item_time, StringUtils.null2Length0(item.getAskAnswerEntity().getCreateAt()))//时间
                            .setText(R.id.ask_focus_text_view_my_item_industry, StringUtils.null2Length0(item.getAskAnswerEntity().getIndustryName()))//行业
                    ;
                    Glide.with(mContext).load(item.getAskAnswerEntity().getUserAvatar())
                            .crossFade()
                            .placeholder(R.mipmap.ic_avatar)
                            .transform(new GlideCircleTransform(mContext))
                            .into((ImageView) helper.getView(R.id.ask_focus_iv_avatar));//头像
                }

                break;
            case MineMultipleItemModel.MY_FOCUSE://我的关注
                if (item.getFocusEntity() != null) {
                    helper.setText(R.id.focus_textView_owner_name, StringUtils.null2Length0(item.getFocusEntity().getNickname()));//名字
                    helper.setText(R.id.focus_textView_owner_worktime, StringUtils.null2Length0(item.getFocusEntity().getWorktime()));//工作年限
                    helper.setText(R.id.focus_textView_item_company, StringUtils.null2Length0(item.getFocusEntity().getCompanyName()));//公司
                    helper.setText(R.id.focus_textView_item_job_title, StringUtils.null2Length0(item.getFocusEntity().getAbilityName()));//职位
                    helper.setText(R.id.focus_textView_trade, StringUtils.null2Length0(item.getFocusEntity().getIndustryName()));//行业

                    Glide.with(mContext).load(item.getFocusEntity().getAvatar())//头像
                            .crossFade()
                            .placeholder(R.mipmap.ic_avatar)
                            .transform(new GlideCircleTransform(mContext))
                            .into((ImageView) helper.getView(R.id.focus_imageView_owner_profile));
                }


                break;
            case MineMultipleItemModel.MY_FOCUSE_FOLLOW://被关注
                if (item.getFocusEntity() != null) {
                    helper.setText(R.id.focus_textView_owner_name, StringUtils.null2Length0(item.getFocusEntity().getNickname()));//名字
                    helper.setText(R.id.focus_textView_owner_worktime, StringUtils.null2Length0(item.getFocusEntity().getWorktime()));//工作年限
                    helper.setText(R.id.focus_textView_item_company, StringUtils.null2Length0(item.getFocusEntity().getCompanyName()));//公司
                    helper.setText(R.id.focus_textView_item_job_title, StringUtils.null2Length0(item.getFocusEntity().getAbilityName()));//职位
                    helper.setText(R.id.focus_textView_trade, StringUtils.null2Length0(item.getFocusEntity().getIndustryName()));//行业

                    Glide.with(mContext).load(item.getFocusEntity().getAvatar())//头像
                            .crossFade()
                            .placeholder(R.mipmap.ic_avatar)
                            .transform(new GlideCircleTransform(mContext))
                            .into((ImageView) helper.getView(R.id.focus_imageView_owner_profile));
                }

                break;

            case MineMultipleItemModel.MY_INTERVIEW://我的访谈
                if (item.getInterviewEntity() != null) {
                    Date date = new Date(item.getInterviewEntity().getCreateAt());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time = simpleDateFormat.format(date);
//                    type:0约访,1被约访
//                    status;0联系中,1访谈成功
//                    if (item.getInterviewEntity().getStatus() == 0) {
//                        helper.setText(R.id.textView_mine_title_header, "我的约访");
//                    } else if (item.getInterviewEntity().getStatus() == 1) {
//                        helper.setText(R.id.textView_mine_title_header, "我被约访");
//                    }

                    if (item.getInterviewEntity().getStatus() == 0) {
                        helper.setText(R.id.textView_mine_title_status, "（联系中）");
                        helper.setTextColor(R.id.textView_mine_title_status, Color.RED);
                    } else if (item.getInterviewEntity().getStatus() == 1) {
                        helper.setText(R.id.textView_mine_title_status, "（访谈成功）");
                    }

                    helper
                            .setText(R.id.textView_mine_title_header, StringUtils.null2Length0(item.getInterviewEntity().getType()))//我的约访
                            .setText(R.id.textView_mine_title_main, StringUtils.null2Length0(item.getInterviewEntity().getSubject()))//标题
                            .setText(R.id.textView_mine_sub_title, StringUtils.null2Length0(item.getInterviewEntity().getDescription()))//内容
                            .setText(R.id.text_view_my_item_trade, StringUtils.null2Length0(item.getInterviewEntity().getName()))//行业
                            .setText(R.id.text_view_my_item_data_time, StringUtils.null2Length0(time));//时间

                }

                break;

            case MineMultipleItemModel.MY_MESSAGE_NEW://新消息

                if (item.getMessageContentEntity() != null) {
                    Date date = new Date(item.getMessageContentEntity().getCreateAt());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time = simpleDateFormat.format(date);
                    helper.setText(R.id.message_time, time);
                    if (item.getMessageContentEntity().getState() == 3) {
                        String h3 = "<font color='#BABABA' size='14'>" + "你发起了 " + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + " 关于 " + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + " 的访谈需求已确定,安排在 " + "</font>"
                                + "<font color='#ED5564' size='14'>" + item.getMessageContentEntity().getTime() + "</font>"
                                + "<font color='#BABABA' size='14'>" + " ,会议邀请码为 " + "</font>"
                                + "<font color='#ED5564' size='14'>" + item.getMessageContentEntity().getCode() + "</font>"
                                + "<font color='#BABABA' size='14'>" + " ,请准时参加 " + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h3));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 4) {
                        String h4 = "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "邀请你参与关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的访谈需求已确定,安排在" + "</font>"
                                + "<font color='#ED5564' size='14'>" + item.getMessageContentEntity().getTime() + "</font>"
                                + "<font color='#BABABA' size='14'>" + ",请准时参加" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h4));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 9) {
                        String h9 = "<font color='#BABABA' size='14'>" + "你发起了关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的提问," + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "回答了你" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h9));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 10) {
                        String h10 = "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "邀请了你回答" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的提问。" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h10));

                        helper.setText(R.id.message_status, "前往回答");
                    }
                }
                break;

            case MineMultipleItemModel.MY_MESSAGE_INTERVIEW://访谈消息
                /**
                 * status:0-8
                 */
                if (item.getMessageContentEntity() != null) {

                    Date date = new Date(item.getMessageContentEntity().getCreateAt());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time = simpleDateFormat.format(date);

                    helper.setText(R.id.message_time, time);
                    Log.e("aaaa", item.getMessageContentEntity().getSubject() + "");
                    Log.e("aaaa", item.getMessageContentEntity().getCode() + "");
                    Log.e("aaaa", item.getMessageContentEntity().getTime() + "");
                    Log.e("aaaa", item.getMessageContentEntity().getId() + "");
                    Log.e("aaaa", item.getMessageContentEntity().getState() + "");
                    Log.e("aaaa", item.getMessageContentEntity().getCreateAt() + "");
                    Log.e("BBB", item.getMessageContentEntity().getNickname() + "");
                    if (item.getMessageContentEntity().getState() == 0) {
                        String h0 = "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "邀请你参加关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的访谈。" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h0));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 1) {
                        String h1 = "<font color='#BABABA' size='14'>" + "我发起了" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的访谈。" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h1));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 2) {
                        String h2 = "<font color='#BABABA' size='14'>" + "你发起了" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的访谈需求已收到,会有线下顾问开始联系。" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h2));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 3) {
                        String h3 = "<font color='#BABABA' size='14'>" + "你发起了" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的访谈需求已确定,安排在" + "</font>"
                                + "<font color='#ED5564' size='14'>" + item.getMessageContentEntity().getTime() + "</font>"
                                + "<font color='#BABABA' size='14'>" + ",会议邀请码为" + "</font>"
                                + "<font color='#ED5564' size='14'>" + item.getMessageContentEntity().getCode() + "</font>"
                                + "<font color='#ED5564' size='14'>" + ",请准时参加" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h3));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 4) {
                        String h4 = "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "邀请你参与关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的访谈需求已确定,安排在" + "</font>"
                                + "<font color='#ED5564' size='14'>" + item.getMessageContentEntity().getTime() + "</font>"
                                + "<font color='#BABABA' size='14'>" + ",请准时参加" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h4));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 5) {
                        String h5 = "<font color='#BABABA' size='14'>" + "你发起的" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的访谈已完成,感谢使用洞察+平台" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h5));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 6) {
                        String h6 = "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "邀请你参与关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的访谈已完成,感谢你的参与" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h6));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 7) {
                        String h7 = "<font color='#BABABA' size='14'>" + "你发起的" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的访谈已取消,欢迎继续使用洞察+选择你的洞察家" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h7));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 8) {
                        String h8 = "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "邀请你参与关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的访谈已取消,感谢你的参与" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h8));
                        helper.setText(R.id.message_status, "前往查看");
                    }
                }

                break;

            case MineMultipleItemModel.MY_MESSAGE_ASK_ANSWER://问答消息
                if (item.getMessageContentEntity() != null) {
                    Date date = new Date(item.getMessageContentEntity().getCreateAt());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time = simpleDateFormat.format(date);
                    Log.e("aaaa", item.getMessageContentEntity().getSubject() + "");
                    Log.e("aaaa", item.getMessageContentEntity().getCode() + "");
                    Log.e("aaaa", item.getMessageContentEntity().getTime() + "");
                    Log.e("aaaa", item.getMessageContentEntity().getId() + "");
                    Log.e("aaaa", item.getMessageContentEntity().getState() + "");
                    Log.e("aaaa", item.getMessageContentEntity().getCreateAt() + "");
                    Log.e("BBB", item.getMessageContentEntity().getNickname() + "");
                    helper.setText(R.id.message_time, time);
                    if (item.getMessageContentEntity().getState() == 9) {
                        String h9 = "<font color='#BABABA' size='14'>" + "你发起了关于" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的提问," + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "回答了你" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h9));
                        helper.setText(R.id.message_status, "前往查看");
                    } else if (item.getMessageContentEntity().getState() == 10) {
                        String h10 = "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getNickname() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "邀请了你回答" + "</font>"
                                + "<font color='#FFFFFF' size='14'>" + item.getMessageContentEntity().getSubject() + "</font>"
                                + "<font color='#BABABA' size='14'>" + "的提问。" + "</font>";
                        helper.setText(R.id.message_content, Html.fromHtml(h10));

                        helper.setText(R.id.message_status, "前往回答");
                    }
                }
                break;

            case MineMultipleItemModel.MY_MESSAGE_COMMENT_PRAISE://评论/赞消息
                if (item.getCommentPraiseEntity() != null) {

                    Date date = new Date(item.getCommentPraiseEntity().getCreateAt());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time = simpleDateFormat.format(date);
                    Glide.with(mContext).load(item.getCommentPraiseEntity().getAvatar())
                            .crossFade()
                            .placeholder(R.mipmap.ic_avatar)
                            .transform(new GlideCircleTransform(mContext))
                            .into((ImageView) helper.getView(R.id.comment_praise_avatar));//头像
                    helper
                            .setText(R.id.comment_praise_name,StringUtils.null2Length0(item.getCommentPraiseEntity().getNickname()))//姓名
                            .setText(R.id.comment_praise_company,StringUtils.null2Length0(item.getCommentPraiseEntity().getCompanyName()))//公司
                            .setText(R.id.comment_praise__title,StringUtils.null2Length0(item.getCommentPraiseEntity().getAbilityName()))//职位
                            .setText(R.id.comment_praise_time, StringUtils.null2Length0(time));//时间
                    Log.e("title", item.getCommentPraiseEntity().getTitle() + "");
                    Log.e("title", item.getCommentPraiseEntity().getNickname() + "");
                    Log.e("title", item.getCommentPraiseEntity().getAbilityName() + "");
                    Log.e("name", item.getCommentPraiseEntity().getCompanyName() + "");
                    Log.e("time", item.getCommentPraiseEntity().getCreateAt() + "");
                    Log.e("state", item.getCommentPraiseEntity().getState() + "");


                    if (item.getCommentPraiseEntity().getState() == 11){//评论文章
                        helper
                                .setVisible(R.id.comment_praise_ll_comment,true)
                                .setText(R.id.comment_praise_status,"评论了我的文章:")//状态
                                .setText(R.id.comment_praise_content,StringUtils.null2Length0(item.getCommentPraiseEntity().getTitle()))//标题
                                .setTextColor(R.id.comment_praise_content,R.color.blue)//标题的颜色
                                .setText(R.id.comment_praise_comment,StringUtils.null2Length0(item.getCommentPraiseEntity().getContent()))//回复
                        ;
                    } else if (item.getCommentPraiseEntity().getState() == 12){//回复了回复
                        helper
                                .setVisible(R.id.comment_praise_ll_comment,true)
                                .setText(R.id.comment_praise_status,"回复了我的回复:")//状态
                                .setText(R.id.comment_praise_content,StringUtils.null2Length0(item.getCommentPraiseEntity().getPidComment()))//标题
                                .setTextColor(R.id.comment_praise_content,R.color.line)//标题颜色
                                .setText(R.id.comment_praise_comment,StringUtils.null2Length0(item.getCommentPraiseEntity().getContent()))//回复
                        ;
                    }else if (item.getCommentPraiseEntity().getState() == 13){//赞赏了文章
                        helper.setVisible(R.id.comment_praise_ll_comment,false)
                                .setText(R.id.comment_praise_status,"赞赏了我的文章:")//状态
                                .setText(R.id.comment_praise_content,StringUtils.null2Length0(item.getCommentPraiseEntity().getTitle()))//标题
                                .setTextColor(R.id.comment_praise_content,R.color.line)//标题颜色
                        ;
                    }else if (item.getCommentPraiseEntity().getState() == 14){//赞赏了评论
                        helper.setVisible(R.id.comment_praise_ll_comment,false)
                                .setText(R.id.comment_praise_status,"赞赏了我的评论:")//状态
                                .setText(R.id.comment_praise_content,StringUtils.null2Length0(item.getCommentPraiseEntity().getPidComment()))//标题
                                .setTextColor(R.id.comment_praise_content,R.color.line)//标题颜色
                        ;
                    }else if (item.getCommentPraiseEntity().getState() == 15){//评论了访谈
                        helper
                                .setVisible(R.id.comment_praise_ll_comment,true)
                                .setText(R.id.comment_praise_status,"评论了我的访谈:")//状态
                                .setText(R.id.comment_praise_content,StringUtils.null2Length0(item.getCommentPraiseEntity().getTitle()))//标题
                                .setTextColor(R.id.comment_praise_content,R.color.line)//标题的颜色
                                .setText(R.id.comment_praise_comment,StringUtils.null2Length0(item.getCommentPraiseEntity().getContent()))//回复
                        ;
                    }else if (item.getCommentPraiseEntity().getState() == 16){//评论了问答
                        helper
                                .setVisible(R.id.comment_praise_ll_comment,true)
                                .setText(R.id.comment_praise_status,"评论了我的问答:")//状态
                                .setText(R.id.comment_praise_content,StringUtils.null2Length0(item.getCommentPraiseEntity().getTitle()))//标题
                                .setTextColor(R.id.comment_praise_content,R.color.blue)//标题的颜色
                                .setText(R.id.comment_praise_comment,StringUtils.null2Length0(item.getCommentPraiseEntity().getContent()))//回复
                        ;
                    }else if (item.getCommentPraiseEntity().getState() == 17){//评论了回复
                        helper
                                .setVisible(R.id.comment_praise_ll_comment,true)
                                .setText(R.id.comment_praise_status,"评论了我的回复:")//状态
                                .setText(R.id.comment_praise_content,StringUtils.null2Length0(item.getCommentPraiseEntity().getPidComment()))//标题
                                .setTextColor(R.id.comment_praise_content,R.color.line)//标题的颜色
                                .setText(R.id.comment_praise_comment,StringUtils.null2Length0(item.getCommentPraiseEntity().getContent()))//回复
                        ;
                    }else if (item.getCommentPraiseEntity().getState() == 18){//赞赏了问答
                        helper.setVisible(R.id.comment_praise_ll_comment,false)
                                .setText(R.id.comment_praise_status,"赞赏了我的问答:")//状态
                                .setText(R.id.comment_praise_content,StringUtils.null2Length0(item.getCommentPraiseEntity().getTitle()))//标题
                                .setTextColor(R.id.comment_praise_content,R.color.line)//标题颜色
                        ;
                    }else if (item.getCommentPraiseEntity().getState() == 19){//赞赏了评论
                        helper.setVisible(R.id.comment_praise_ll_comment,false)
                                .setText(R.id.comment_praise_status,"赞赏了我的评论:")//状态
                                .setText(R.id.comment_praise_content,StringUtils.null2Length0(item.getCommentPraiseEntity().getPidComment()))//标题
                                .setTextColor(R.id.comment_praise_content,R.color.line)//标题颜色
                        ;
                    }
                }
                break;

            case MineMultipleItemModel.HIS_OPTION://他的观点
                if (item.getMineFollowPointEntity() != null) {

                    helper
                            .setText(R.id.opinion_textView_mine_title_main, StringUtils.null2Length0(item.getMineFollowPointEntity().getTitle()))
                            .setText(R.id.opinion_textView_mine_sub_title, StringUtils.null2Length0(item.getMineFollowPointEntity().getContent()))
                            .setText(R.id.opinion_textView_item_focusnum, item.getMineFollowPointEntity().getFollows() + "")
                            .setText(R.id.opinion_textView_item_talknum, item.getMineFollowPointEntity().getComments() + "")
                            .setText(R.id.opinion_textView_item_agreenum, item.getMineFollowPointEntity().getPraises() + "")
                            .setText(R.id.opinion_textView_item_trade, StringUtils.null2Length0(item.getMineFollowPointEntity().getIndustryName()))
                    ;
                }
                break;

            case MineMultipleItemModel.HIS_OPTION_FOLLOW://他关注的观点
                if (item.getMineFollowPointEntity() != null) {
                    helper
                            .setText(R.id.opinion_textView_owner_name, StringUtils.null2Length0(item.getMineFollowPointEntity().getNickname()))
                            .setText(R.id.opinion_textView_owner_job_title, StringUtils.null2Length0(item.getMineFollowPointEntity().getJobName()))
                            .setText(R.id.opinion_textView_owner_job_company, StringUtils.null2Length0(item.getMineFollowPointEntity().getCompanyName()))
                            .setText(R.id.opinion_textView_mine_title_main, StringUtils.null2Length0(item.getMineFollowPointEntity().getTitle()))
                            .setText(R.id.opinion_textView_mine_sub_title, StringUtils.null2Length0(item.getMineFollowPointEntity().getContent()))
                            .setText(R.id.opinion_textView_item_focusnum, item.getMineFollowPointEntity().getFollows() + "")
                            .setText(R.id.opinion_textView_item_talknum, item.getMineFollowPointEntity().getComments() + "")
                            .setText(R.id.opinion_textView_item_agreenum, item.getMineFollowPointEntity().getPraises() + "")
                            .setText(R.id.opinion_textView_item_trade, StringUtils.null2Length0(item.getMineFollowPointEntity().getIndustryName()))
                    ;

                    Glide.with(mContext).load(item.getMineFollowPointEntity().getAvatar())
                            .crossFade()
                            .placeholder(R.mipmap.ic_avatar)
                            .transform(new GlideCircleTransform(mContext))
                            .into((ImageView) helper.getView(R.id.opinion_imageView_owner_profile));
                }
                break;

            case MineMultipleItemModel.HIS_DISCUSS://他的问答
                if (item.getAskAnswerEntity() != null) {
                    helper
                            .setText(R.id.ask_textView_mine_title_main, StringUtils.null2Length0(item.getAskAnswerEntity().getAction()))//我的提问
                            .setText(R.id.ask_textView_mine_title_main_status, StringUtils.null2Length0(item.getAskAnswerEntity().getStatus()))//已回答
                            .setText(R.id.ask_textView_mine_sub_title, StringUtils.null2Length0(item.getAskAnswerEntity().getContent()))//标题
                            .setText(R.id.ask_text_view_my_item_time, StringUtils.null2Length0(item.getAskAnswerEntity().getCreateAt()))//时间
                            .setText(R.id.ask_text_view_my_item_industry, StringUtils.null2Length0(item.getAskAnswerEntity().getIndustry()))//行业
                    ;
                }
                break;

            case MineMultipleItemModel.HIS_DISCUSS_FOLLOW://他关注的问答
                if (item.getAskAnswerEntity() != null) {
                    helper
                            .setText(R.id.ask_focus_tv_name, StringUtils.null2Length0(item.getAskAnswerEntity().getUserNickname()))//名字
                            .setText(R.id.ask_focus_tv_title, StringUtils.null2Length0(item.getAskAnswerEntity().getUserAbility()))//职位
                            .setText(R.id.ask_focus_tv_company, StringUtils.null2Length0(item.getAskAnswerEntity().getUserCompany()))//公司
                            .setText(R.id.ask_focus_tv_header, StringUtils.null2Length0(item.getAskAnswerEntity().getContent()))//标题
                            .setText(R.id.ask_focus_text_view_my_item_time, StringUtils.null2Length0(item.getAskAnswerEntity().getCreateAt()))//时间
                            .setText(R.id.ask_focus_text_view_my_item_industry, StringUtils.null2Length0(item.getAskAnswerEntity().getIndustryName()))//行业
                    ;
                    Glide.with(mContext).load(item.getAskAnswerEntity().getUserAvatar())
                            .crossFade()
                            .placeholder(R.mipmap.ic_avatar)
                            .transform(new GlideCircleTransform(mContext))
                            .into((ImageView) helper.getView(R.id.ask_focus_iv_avatar));//头像
                }
                break;

            case MineMultipleItemModel.HIS_INTERVIEW://他的访谈
                if (item.getInterviewEntity() != null) {
                    Date date = new Date(item.getInterviewEntity().getCreateAt());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time = simpleDateFormat.format(date);
//                    type:0约访,1被约访
//                    status;0联系中,1访谈成功
//                    if (item.getInterviewEntity().getStatus() == 0) {
//                        helper.setText(R.id.textView_mine_title_header, "我的约访");
//                    } else if (item.getInterviewEntity().getStatus() == 1) {
//                        helper.setText(R.id.textView_mine_title_header, "我被约访");
//                    }

                    if (item.getInterviewEntity().getStatus() == 0) {
                        helper.setText(R.id.textView_mine_title_status, "（联系中）");
                        helper.setTextColor(R.id.textView_mine_title_status, Color.RED);
                    } else if (item.getInterviewEntity().getStatus() == 1) {
                        helper.setText(R.id.textView_mine_title_status, "（访谈成功）");
                    }

                    helper
                            .setText(R.id.textView_mine_title_header, StringUtils.null2Length0(item.getInterviewEntity().getType()))//我的约访
                            .setText(R.id.textView_mine_title_main, StringUtils.null2Length0(item.getInterviewEntity().getSubject()))//标题
                            .setText(R.id.textView_mine_sub_title, StringUtils.null2Length0(item.getInterviewEntity().getDescription()))//内容
                            .setText(R.id.text_view_my_item_trade, StringUtils.null2Length0(item.getInterviewEntity().getName()))//行业
                            .setText(R.id.text_view_my_item_data_time, StringUtils.null2Length0(time));//时间

                }

                break;

            case MineMultipleItemModel.MY_ACCOUNT_DETAIL://账户明细
                if (item.getAccountDetailEntity() != null) {
                    Date date = new Date(item.getAccountDetailEntity().getPayLastTime());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time = simpleDateFormat.format(date);

//                    int type = item.getAccountDetailEntity().getType();//类型:0回答,1提问 2约访 3充值
                    switch (item.getAccountDetailEntity().getType()) {
                        case 0:
                            helper.setText(R.id.accountDetailItemTvQuestionStatus, "回答:");
                            helper.setImageResource(R.id.accountDetailItemIvIcon, R.mipmap.iconfont_wenda);
                            break;
                        case 1:
                            helper.setText(R.id.accountDetailItemTvQuestionStatus, "提问:");
                            helper.setImageResource(R.id.accountDetailItemIvIcon, R.mipmap.iconfont_wenda);
                            break;
                        case 2:
                            helper.setText(R.id.accountDetailItemTvQuestionStatus, "约访:");
                            helper.setImageResource(R.id.accountDetailItemIvIcon, R.mipmap.iconfont_fangtan);
                            break;
                        case 3:
                            helper.setText(R.id.accountDetailItemTvQuestionStatus, "充值:");
                            helper.setImageResource(R.id.accountDetailItemIvIcon, R.mipmap.iconfont_yu_e);
                            break;
                    }

//                    int status = item.getAccountDetailEntity().getStatus();//状态:0等待对方回答 1:等待访谈完成 2 没有东西
                    switch (item.getAccountDetailEntity().getStatus()) {
                        case 0:
                            helper.setText(R.id.accountDetailItemTvStatus, "等待对方回答");
                            break;
                        case 1:
                            helper.setText(R.id.accountDetailItemTvStatus, "等待访谈完成");
                            break;
                        case 2:
                            helper.setText(R.id.accountDetailItemTvStatus, "");
                            break;
                    }
                    helper
                            .setText(R.id.accountDetailItemTvQuestion, StringUtils.null2Length0(item.getAccountDetailEntity().getTitle()))
                            .setText(R.id.accountDetailItemTvMoney, StringUtils.null2Length0(item.getAccountDetailEntity().getAmountTotal()))
                            .setText(R.id.accountDetailItemTvDate, StringUtils.null2Length0(time));
                }
                break;
        }
    }


}
