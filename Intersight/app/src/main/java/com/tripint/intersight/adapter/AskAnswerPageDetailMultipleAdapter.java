package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseMultiItemQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.model.MultipleChatItemModel;
import com.tripint.intersight.model.QADetailModel;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class AskAnswerPageDetailMultipleAdapter extends BaseMultiItemQuickAdapter<MultipleChatItemModel> {


    public AskAnswerPageDetailMultipleAdapter(List<MultipleChatItemModel> data) {
        super(data);
        addItemType(MultipleChatItemModel.CHAT_LEFT, R.layout.item_ask_answer_chat_left);
        addItemType(MultipleChatItemModel.CHAT_RIGHT, R.layout.item_ask_answer_chat_right);

    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleChatItemModel item) {
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
            case MultipleChatItemModel.CHAT_LEFT:
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
            case MultipleChatItemModel.CHAT_RIGHT:
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
