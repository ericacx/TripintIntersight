package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseMultiItemQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.model.MultipleSearchItemModel;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.List;

/**
 *
 */
public class SearchResultMultipleAdapter extends BaseMultiItemQuickAdapter<MultipleSearchItemModel> {


    public SearchResultMultipleAdapter(List<MultipleSearchItemModel> data) {
        super(data);
        addItemType(MultipleSearchItemModel.INTERVIEW, R.layout.item_ask_answer_chat_left);
        addItemType(MultipleSearchItemModel.ARTICLE, R.layout.item_ask_answer_chat_right);

    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleSearchItemModel item) {
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
        switch (helper.getItemViewType()){
            case MultipleSearchItemModel.INTERVIEW:
                helper.setText(R.id.textView_item_ask_specialist, sperialist)
                        .setText(R.id.textView_item_ask_title, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.textView_item_ask_date_time, StringUtils.null2Length0(item.getContent().getContent()))
                ;

                Glide.with(mContext).load(avatar)
                        .crossFade()
                        .placeholder(R.mipmap.iconfont_wechat)
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.image_ask_profile));

                break;
            case MultipleSearchItemModel.ARTICLE:
                helper.setText(R.id.textView_item_ask_specialist, sperialist)
                        .setText(R.id.textView_item_ask_title, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.textView_item_ask_date_time, StringUtils.null2Length0(item.getContent().getContent()))
                ;

                Glide.with(mContext).load(avatar)
                        .crossFade()
                        .placeholder(R.mipmap.iconfont_wechat)
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.image_ask_profile));
                break;
        }
        }


}
