package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.discuss.InterviewEntity;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.List;

/**
 */
public class AskRecyclerViewAdapter extends BaseQuickAdapter<InterviewEntity> {
    public AskRecyclerViewAdapter(List<InterviewEntity> discussEntiries) {
        super(R.layout.item_recyclerview_ask_main, discussEntiries);
    }

    @Override
    protected void convert(BaseViewHolder helper, InterviewEntity item) {
        String sperialist = "";
        String avatar = "";

        String position = item.getPosition() == null ? "" : item.getPosition().getName();
        String industry = item.getIndustry() == null ? "" : item.getIndustry().getName();
        String company = item.getCompany() == null ? "" : item.getCompany().getName();
        helper.setText(R.id.textView_item_ask_title, item.getNickname())
                .setText(R.id.textView_item_ask_time, item.getCreateAt())
                .setText(R.id.textView_item_ask_company, company + "")
                .setText(R.id.textView_item_ask_job_title, position + "")
                .setText(R.id.textView_item_ask_job_industry, industry + "")
                .linkify(R.id.textView_item_ask_title);

        Glide.with(mContext).load(item.getAvatar())
                .crossFade()
                .placeholder(R.mipmap.ic_avatar)
                .transform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.image_ask_profile));
    }


}
