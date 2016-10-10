package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.List;

/**
 */
public class AskRecyclerViewAdapter extends BaseQuickAdapter<DiscussEntiry> {
    public AskRecyclerViewAdapter(List<DiscussEntiry> discussEntiries) {
        super(R.layout.item_recyclerview_ask_main, discussEntiries);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscussEntiry item) {
        String sperialist = "";
        String avatar = "";
        if (item.getUserInfo() != null) {
            if (item.getUserInfo().getCompany() != null) {
                sperialist = item.getUserInfo().getCompany().getName();
            }
            if (item.getUserInfo().getAbility() != null) {
                sperialist += item.getUserInfo().getAbility().getName();
            }
            avatar = item.getUserInfo().getAvatar();
        }

        helper.setText(R.id.textView_item_ask_title, item.getContent())
                .setText(R.id.textView_item_ask_time, sperialist)
                .setText(R.id.textView_item_ask_company, item.getFollows() + "")
                .setText(R.id.textView_item_ask_job_title, item.getListens() + "")
                .setText(R.id.textView_item_ask_job_industry, item.getIndustryId() + "")
                .linkify(R.id.textView_item_ask_title);

        Glide.with(mContext).load(avatar)
                .crossFade()
                .placeholder(R.drawable.loading_normal_icon)
                .transform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.image_ask_profile));
    }


}
