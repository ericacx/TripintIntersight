package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseMultiItemQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.model.MineMultipleItemModel;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

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
//                    int type = item.getInterviewEntity().getType();//0约访,1被约访
//                    int status = item.getInterviewEntity().getStatus();//0联系中,1访谈成功
//                    if (type == 0) {
//                        helper.setText(R.id.textView_mine_title_header, "我的约访");
//                    } else if (type == 1) {
//                        helper.setText(R.id.textView_mine_title_header, "我被约访");
//                    }
//
//                    if (status == 0) {
//                        helper.setText(R.id.textView_mine_title_status, "（联系中）");
//                        helper.setTextColor(R.id.textView_mine_title_status, Color.RED);
//                    } else if (status == 1) {
//                        helper.setText(R.id.textView_mine_title_status, "（访谈成功）");
//                    }

                    helper
                            .setText(R.id.textView_mine_title_main, StringUtils.null2Length0(item.getInterviewEntity().getSubject()))//标题
                            .setText(R.id.textView_mine_sub_title, StringUtils.null2Length0(item.getInterviewEntity().getDescription()))//内容
                            .setText(R.id.text_view_my_item_trade, StringUtils.null2Length0(item.getInterviewEntity().getName()))//行业
                            .setText(R.id.text_view_my_item_data_time, item.getInterviewEntity().getCreateAt() + "");//时间

                }

                break;

            case MineMultipleItemModel.MY_MESSAGE_NEW://新消息

                break;

            case MineMultipleItemModel.MY_MESSAGE_INTERVIEW://访谈消息

                break;

            case MineMultipleItemModel.MY_MESSAGE_ASK_ANSWER://问答消息

                break;

            case MineMultipleItemModel.MY_MESSAGE_COMMENT_PRAISE://评论/赞消息

                break;

            case MineMultipleItemModel.HIS_OPTION://他的观点

                break;

            case MineMultipleItemModel.HIS_OPTION_FOLLOW://他关注的观点

                break;

            case MineMultipleItemModel.HIS_DISCUSS://他的问答

                break;

            case MineMultipleItemModel.HIS_DISCUSS_FOLLOW://他关注的问答

                break;

            case MineMultipleItemModel.HIS_INTERVIEW://他的访谈

                break;

            case MineMultipleItemModel.MY_ACCOUNT_DETAIL://账户明细
                if (item.getAccountDetailEntity() != null){

                    int type = item.getAccountDetailEntity().getType();//类型:0回答,1提问 2约访 3充值
                    switch (type){
                        case 0:
                            helper.setText(R.id.accountDetailItemTvQuestionStatus,"回答:");
                            helper.setImageResource(R.id.accountDetailItemIvIcon,R.mipmap.iconfont_wenda);
                            break;
                        case 1:
                            helper.setText(R.id.accountDetailItemTvQuestionStatus,"提问:");
                            helper.setImageResource(R.id.accountDetailItemIvIcon,R.mipmap.iconfont_wenda);
                            break;
                        case 2:
                            helper.setText(R.id.accountDetailItemTvQuestionStatus,"约访:");
                            helper.setImageResource(R.id.accountDetailItemIvIcon,R.mipmap.iconfont_fangtan);
                            break;
                        case 3:
                            helper.setText(R.id.accountDetailItemTvQuestionStatus,"充值:");
                            helper.setImageResource(R.id.accountDetailItemIvIcon,R.mipmap.iconfont_yu_e);
                            break;
                    }

                    int status = item.getAccountDetailEntity().getStatus();//状态:0等待对方回答 1:等待访谈完成 2 没有东西
                    switch (status){
                        case 0:
                            helper.setText(R.id.accountDetailItemTvStatus,"等待对方回答");
                            break;
                        case 1:
                            helper.setText(R.id.accountDetailItemTvStatus,"等待访谈完成");
                            break;
                        case 2:
                            helper.setText(R.id.accountDetailItemTvStatus,"");
                            break;
                    }
                    helper
                            .setText(R.id.accountDetailItemTvQuestion,StringUtils.null2Length0(item.getAccountDetailEntity().getTitle()))
                            .setText(R.id.accountDetailItemTvMoney,item.getAccountDetailEntity().getAmountTotal()+"")
                            .setText(R.id.accountDetailItemTvDate,item.getAccountDetailEntity().getPayLastTime()+"");
                }
                break;
        }
    }


}
