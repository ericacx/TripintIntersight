package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.discuss.Specialist;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.List;

/**
 */
public class AskRecyclerViewAdapter extends BaseQuickAdapter<Specialist> {
    public AskRecyclerViewAdapter(List<Specialist> discussEntiries) {
        super(R.layout.item_recyclerview_ask_main, discussEntiries);
    }

    @Override
    protected void convert(BaseViewHolder helper, Specialist item) {
        String sperialist = "";
        String avatar = "";


        helper.setText(R.id.textView_item_ask_title, item.getNickname())
                .setText(R.id.textView_item_ask_time, item.getCreateAt())
                .setText(R.id.textView_item_ask_company, item.getCompanyName() + "")
                .setText(R.id.textView_item_ask_job_title, item.getAbilityName() + "")
                .setText(R.id.textView_item_ask_job_industry, item.getIndustryName() + "")
                .linkify(R.id.textView_item_ask_title);

        Glide.with(mContext).load(item.getAvatar())
                .crossFade()
                .placeholder(R.drawable.loading_normal_icon)
                .transform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.image_ask_profile));
    }


}
