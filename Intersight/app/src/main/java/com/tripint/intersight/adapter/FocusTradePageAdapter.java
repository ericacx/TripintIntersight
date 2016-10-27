package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.List;

/**
 */
public class FocusTradePageAdapter extends BaseQuickAdapter<Industry> {
    public FocusTradePageAdapter(List<Industry> discussEntiries) {
        super(R.layout.item_focus_trade_industry, discussEntiries);
    }

    @Override
    protected void convert(BaseViewHolder helper, Industry item) {
        String avatar = item.getIcon();
        helper.setText(R.id.text_item_industry_name, item.getName())
                .linkify(R.id.text_item_industry_name);

        Glide.with(mContext).load(avatar)
                .crossFade()
                .placeholder(R.drawable.loading_normal_icon)
                .into((ImageView) helper.getView(R.id.image_item_industry));
    }

}
