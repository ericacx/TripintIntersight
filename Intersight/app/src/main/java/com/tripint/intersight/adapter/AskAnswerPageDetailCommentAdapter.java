package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.discuss.CommentEntity;
import com.tripint.intersight.model.QADetailModel;
import com.tripint.intersight.model.QAModel;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class AskAnswerPageDetailCommentAdapter extends BaseQuickAdapter<CommentEntity> {
    public AskAnswerPageDetailCommentAdapter(List<CommentEntity> entities) {
        super(R.layout.item_recyclerview_askanswer_comment, entities);
    }


    @Override
    protected void convert(BaseViewHolder helper, CommentEntity item) {
        if (item.getUser() != null) {
            Glide.with(mContext).load(item.getUser().getAvatar())
                    .crossFade()
                    .placeholder(R.mipmap.iconfont_wechat)
                    .transform(new GlideCircleTransform(mContext))
                    .into((ImageView) helper.getView(R.id.image_ask_profile));
            String special = item.getUser().getNickname();
            if (item.getUser().getAbility() != null) {

                special += item.getUser().getAbility().getName();
            }


            if (item.getUser().getIndustry() != null) {

                special += item.getUser().getIndustry().getName();
            }

            helper.setText(R.id.textView_item_ask_specialist, special)
                    .setText(R.id.textView_item_ask_title, item.getContent())
                    .setText(R.id.textView_item_ask_date_time, item.getCreateAt());


        }
    }

}
