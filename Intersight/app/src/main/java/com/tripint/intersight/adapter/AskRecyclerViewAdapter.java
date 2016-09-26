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
        if (item.getUser() != null) {
            if (item.getUser().getCompany() != null) {
                sperialist = item.getUser().getCompany().getName();
            }
            if (item.getUser().getAbility() != null) {
                sperialist += item.getUser().getAbility().getName();
            }
            avatar = item.getUser().getAvatar();
        }

        helper.setText(R.id.textView_item_ask_title, item.getContent())
                .setText(R.id.textView_item_ask_time, sperialist)
                .setText(R.id.textView_item_ask_company, item.getFollowsCount() + "")
                .setText(R.id.textView_item_ask_job_title, item.getListenCount() + "")
                .setText(R.id.textView_item_ask_job_industry, item.getVoiceId() + "")
                .linkify(R.id.textView_item_ask_title);

        Glide.with(mContext).load(avatar)
                .crossFade()
                .placeholder(R.mipmap.loading_normal_icon)
                .transform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.image_ask_profile));
    }


}
