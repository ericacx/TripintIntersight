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

        addItemType(MineMultipleItemModel.MY_DISCUSS, R.layout.item_recyclerview_myaskanswer);
        addItemType(MineMultipleItemModel.MY_FOCUSE, R.layout.item_recyclerview_myfocused);
        addItemType(MineMultipleItemModel.MY_INTERVIEW, R.layout.item_recyclerview_myinterview);

    }

    @Override
    protected void convert(BaseViewHolder helper, MineMultipleItemModel item) {
        String sperialist = "";
        String avatar = "";
        if (item.getContent().getUser() != null) {
            if (item.getContent().getUser().getCompany() != null) {
                sperialist = item.getContent().getUser().getCompany().getName();
            }
            if (item.getContent().getUser().getAbility() != null) {
                sperialist += item.getContent().getUser().getAbility().getName();
            }
            avatar = item.getContent().getUser().getAvatar();
        }

        switch (helper.getItemViewType()) {
            case MineMultipleItemModel.MY_OPTION_FOLLOW://我关注的观点
                helper
                        .setText(R.id.opinion_textView_owner_name,StringUtils.null2Length0(item.getMineFollowPointEntity().getNickname()))
                        .setText(R.id.opinion_textView_owner_job_title, StringUtils.null2Length0(item.getMineFollowPointEntity().getJobName()))
                        .setText(R.id.opinion_textView_owner_job_company, StringUtils.null2Length0(item.getMineFollowPointEntity().getCompanyName()))
                        .setText(R.id.opinion_textView_mine_title_main, StringUtils.null2Length0(item.getMineFollowPointEntity().getTitle()))
                        .setText(R.id.opinion_textView_mine_sub_title, StringUtils.null2Length0(item.getMineFollowPointEntity().getContent()))
                        .setText(R.id.opinion_textView_item_focusnum, item.getMineFollowPointEntity().getFollows())
                        .setText(R.id.opinion_textView_item_talknum,item.getMineFollowPointEntity().getComments())
                        .setText(R.id.opinion_textView_item_agreenum, item.getMineFollowPointEntity().getPraises())
                        .setText(R.id.opinion_textView_item_trade, StringUtils.null2Length0(item.getMineFollowPointEntity().getIndustryName()))
                ;

                Glide.with(mContext).load(item.getMineFollowPointEntity().getAvatar())
                        .crossFade()
                        .placeholder(R.mipmap.loading_normal_icon)
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.opinion_imageView_owner_profile));

                break;
            case MineMultipleItemModel.MY_OPTION://我的观点
                helper
                        .setText(R.id.opinion_textView_mine_title_main, StringUtils.null2Length0(item.getMineFollowPointEntity().getTitle()))
                        .setText(R.id.opinion_textView_mine_sub_title, StringUtils.null2Length0(item.getMineFollowPointEntity().getContent()))
                        .setText(R.id.opinion_textView_item_focusnum, item.getMineFollowPointEntity().getFollows())
                        .setText(R.id.opinion_textView_item_talknum,item.getMineFollowPointEntity().getComments())
                        .setText(R.id.opinion_textView_item_agreenum, item.getMineFollowPointEntity().getPraises())
                        .setText(R.id.opinion_textView_item_trade, StringUtils.null2Length0(item.getMineFollowPointEntity().getIndustryName()))
                ;
                break;
            case MineMultipleItemModel.MY_DISCUSS:
                helper.setText(R.id.textView_mine_title_main, sperialist)
                        .setText(R.id.textView_mine_sub_title, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.text_view_my_item_data_time, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.text_view_my_item_industry, StringUtils.null2Length0(item.getContent().getContent()))

                ;

//                Glide.with(mContext).load(avatar)
//                        .crossFade()
//                        .placeholder(R.mipmap.loading_normal_icon)
//                        .transform(new GlideCircleTransform(mContext))
//                        .into((ImageView) helper.getView(R.id.image_view_has_replay));
                break;
            case MineMultipleItemModel.MY_INTERVIEW:
                helper.setText(R.id.textView_mine_title_main, sperialist)
                        .setText(R.id.textView_mine_sub_title, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.text_view_my_item_data_time, StringUtils.null2Length0(item.getContent().getContent()))
                ;

                Glide.with(mContext).load(avatar)
                        .crossFade()
                        .placeholder(R.mipmap.loading_normal_icon)
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.imageView_owner_profile));

                break;
            case MineMultipleItemModel.MY_FOCUSE:
                helper.setText(R.id.textView_item_ask_company, sperialist)
                        .setText(R.id.textView_item_ask_job_title, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.textView_item_industry_name, StringUtils.null2Length0(item.getContent().getContent()))
                ;

                Glide.with(mContext).load(avatar)
                        .crossFade()
                        .placeholder(R.mipmap.loading_normal_icon)
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.imageView_owner_profile));
                break;
        }
    }


}
