package com.tripint.intersight.adapter;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseMultiItemQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.Ability;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.mine.AccountDetailEntity;
import com.tripint.intersight.entity.mine.AskAnswerEntity;
import com.tripint.intersight.entity.mine.FocusEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.entity.user.UserEntity;
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
        addItemType(MineMultipleItemModel.MY_DISCUSS, R.layout.item_recyclerview_myaskanswer_focused);

        //他的问答
        addItemType(MineMultipleItemModel.HIS_DISCUSS, R.layout.item_recyclerview_myaskanswer);
        //他关注的问答
        addItemType(MineMultipleItemModel.HIS_DISCUSS, R.layout.item_recyclerview_myaskanswer_focused);

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

        MineFollowPointEntity mineFollowPointEntity = null;
        AskAnswerEntity askAnswerEntity = null;
        FocusEntity focusEntity = null;
        UserEntity userEntity = null;
        Ability ability = null;
        AccountDetailEntity accountDetailEntity;

        switch (helper.getItemViewType()) {

            case MineMultipleItemModel.MY_OPTION://我的观点
                if (item.getMineFollowPointEntity() != null) {
                    mineFollowPointEntity = item.getMineFollowPointEntity();
                }
                helper
                        .setText(R.id.opinion_textView_mine_title_main, StringUtils.null2Length0(mineFollowPointEntity.getTitle()))
                        .setText(R.id.opinion_textView_mine_sub_title, StringUtils.null2Length0(mineFollowPointEntity.getContent()))
                        .setText(R.id.opinion_textView_item_focusnum, mineFollowPointEntity.getFollows() + "")
                        .setText(R.id.opinion_textView_item_talknum, mineFollowPointEntity.getComments() + "")
                        .setText(R.id.opinion_textView_item_agreenum, mineFollowPointEntity.getPraises() + "")
                        .setText(R.id.opinion_textView_item_trade, StringUtils.null2Length0(mineFollowPointEntity.getIndustryName()))
                ;
                break;

            case MineMultipleItemModel.MY_OPTION_FOLLOW://我关注的观点
                if (item.getMineFollowPointEntity() != null) {
                    mineFollowPointEntity = item.getMineFollowPointEntity();
                }
                helper
                        .setText(R.id.opinion_textView_owner_name, StringUtils.null2Length0(mineFollowPointEntity.getNickname()))
                        .setText(R.id.opinion_textView_owner_job_title, StringUtils.null2Length0(mineFollowPointEntity.getJobName()))
                        .setText(R.id.opinion_textView_owner_job_company, StringUtils.null2Length0(mineFollowPointEntity.getCompanyName()))
                        .setText(R.id.opinion_textView_mine_title_main, StringUtils.null2Length0(mineFollowPointEntity.getTitle()))
                        .setText(R.id.opinion_textView_mine_sub_title, StringUtils.null2Length0(mineFollowPointEntity.getContent()))
                        .setText(R.id.opinion_textView_item_focusnum, mineFollowPointEntity.getFollows() + "")
                        .setText(R.id.opinion_textView_item_talknum, mineFollowPointEntity.getComments() + "")
                        .setText(R.id.opinion_textView_item_agreenum, mineFollowPointEntity.getPraises() + "")
                        .setText(R.id.opinion_textView_item_trade, StringUtils.null2Length0(mineFollowPointEntity.getIndustryName()))
                ;

                Glide.with(mContext).load(mineFollowPointEntity.getAvatar())
                        .crossFade()
                        .placeholder(R.drawable.loading_normal_icon)
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.opinion_imageView_owner_profile));

                break;

            case MineMultipleItemModel.MY_DISCUSS://我的问答

                if (item.getAskAnswerEntity() != null) {
                    askAnswerEntity = item.getAskAnswerEntity();
                }

                helper
                        .setText(R.id.ask_textView_mine_title_main, StringUtils.null2Length0(askAnswerEntity.getAction()))//我的提问
                        .setText(R.id.ask_textView_mine_title_main_status, StringUtils.null2Length0(askAnswerEntity.getStatus()))//已回答
                        .setText(R.id.ask_textView_mine_sub_title, StringUtils.null2Length0(askAnswerEntity.getContent()))//标题
                        .setText(R.id.ask_text_view_my_item_time, StringUtils.null2Length0(askAnswerEntity.getCreateAt()))//时间
                        .setText(R.id.ask_text_view_my_item_industry, StringUtils.null2Length0(askAnswerEntity.getIndustry()))//行业
                ;
                break;
            case MineMultipleItemModel.MY_DISCUSS_FOLLOW://我关注的问答
                String avatar = "";
                if (item.getAskAnswerEntity() != null) {
                    askAnswerEntity = item.getAskAnswerEntity();
                    if (askAnswerEntity.getUserEntity() != null) {
                        userEntity = askAnswerEntity.getUserEntity();
                        if (userEntity.getAbility() != null){
                            ability = userEntity.getAbility();
                        }
                        avatar = userEntity.getAvatar();
                    }
                }
                helper
                        .setText(R.id.ask_focus_tv_name, StringUtils.null2Length0(userEntity.getNickname()))//名字
                        .setText(R.id.ask_focus_tv_title, StringUtils.null2Length0(ability.getName()))//职位
                        .setText(R.id.ask_focus_tv_company, StringUtils.null2Length0(userEntity.getCompany().getName()))//公司
                        .setText(R.id.ask_focus_tv_header, StringUtils.null2Length0(askAnswerEntity.getContent()))//标题
                        .setText(R.id.ask_focus_text_view_my_item_time, StringUtils.null2Length0(askAnswerEntity.getCreateAt()))//时间
                        .setText(R.id.ask_focus_text_view_my_item_industry, StringUtils.null2Length0(askAnswerEntity.getIndustry()))//行业
                ;
                Glide.with(mContext).load(avatar)
                        .crossFade()
                        .placeholder(R.drawable.loading_normal_icon)
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.ask_focus_iv_avatar));//头像
                break;
            case MineMultipleItemModel.MY_FOCUSE://我的关注
                if (item.getFocusEntity() != null) {
                    focusEntity = item.getFocusEntity();
                }

                helper.setText(R.id.focus_textView_owner_name, StringUtils.null2Length0(focusEntity.getNickname()));//名字
                helper.setText(R.id.focus_textView_owner_worktime, StringUtils.null2Length0(focusEntity.getWorktime()));//工作年限
                helper.setText(R.id.focus_textView_item_company, StringUtils.null2Length0(focusEntity.getCompanyName()));//公司
                helper.setText(R.id.focus_textView_item_job_title, StringUtils.null2Length0(focusEntity.getAbilityName()));//职位
                helper.setText(R.id.focus_textView_trade, StringUtils.null2Length0(focusEntity.getIndustryName()));//行业

                Glide.with(mContext).load(focusEntity.getAvatar())//头像
                        .crossFade()
                        .placeholder(R.drawable.loading_normal_icon)
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.focus_imageView_owner_profile));
                break;
            case MineMultipleItemModel.MY_FOCUSE_FOLLOW://被关注
                if (item.getFocusEntity() != null) {
                    focusEntity = item.getFocusEntity();
                }

                helper.setText(R.id.focus_textView_owner_name, StringUtils.null2Length0(focusEntity.getNickname()));//名字
                helper.setText(R.id.focus_textView_owner_worktime, StringUtils.null2Length0(focusEntity.getWorktime()));//工作年限
                helper.setText(R.id.focus_textView_item_company, StringUtils.null2Length0(focusEntity.getCompanyName()));//公司
                helper.setText(R.id.focus_textView_item_job_title, StringUtils.null2Length0(focusEntity.getAbilityName()));//职位
                helper.setText(R.id.focus_textView_trade, StringUtils.null2Length0(focusEntity.getIndustryName()));//行业

                Glide.with(mContext).load(focusEntity.getAvatar())//头像
                        .crossFade()
                        .placeholder(R.drawable.loading_normal_icon)
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.focus_imageView_owner_profile));
                break;

            case MineMultipleItemModel.MY_INTERVIEW://我的访谈
                InterviewEntity interviewEntity = null;
                if (item.getInterviewEntity() != null) {
                    interviewEntity = item.getInterviewEntity();
                }

                int type = interviewEntity.getType();//0约访,1被约访
                int status = interviewEntity.getStatus();//0联系中,1访谈成功
                if (type == 0) {
                    helper.setText(R.id.textView_mine_title_header, "我的约访");
                } else if (type == 1) {
                    helper.setText(R.id.textView_mine_title_header, "我被约访");
                }

                if (status == 0) {
                    helper.setText(R.id.textView_mine_title_status, "（联系中）");
                    helper.setTextColor(R.id.textView_mine_title_status, Color.RED);
                } else if (status == 1) {
                    helper.setText(R.id.textView_mine_title_status, "（访谈成功）");
                }

                helper
                        .setText(R.id.textView_mine_title_main, StringUtils.null2Length0(interviewEntity.getSubject()))//标题
                        .setText(R.id.textView_mine_sub_title, StringUtils.null2Length0(interviewEntity.getDescription()))//内容
                        .setText(R.id.text_view_my_item_trade, StringUtils.null2Length0(interviewEntity.getName()))//行业
                        .setText(R.id.text_view_my_item_data_time, interviewEntity.getCreateAt() + "");//时间
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

                break;
        }
    }


}
