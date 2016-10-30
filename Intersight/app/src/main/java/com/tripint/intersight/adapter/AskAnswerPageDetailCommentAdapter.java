package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.article.ArticleCommentEntity;
import com.tripint.intersight.entity.discuss.CommentEntity;
import com.tripint.intersight.model.QADetailModel;
import com.tripint.intersight.model.QAModel;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class AskAnswerPageDetailCommentAdapter extends BaseQuickAdapter<ArticleCommentEntity> {
    public AskAnswerPageDetailCommentAdapter(List<ArticleCommentEntity> entities) {
        super(R.layout.item_recyclerview_askanswer_comment, entities);
    }


    @Override
    protected void convert(BaseViewHolder helper, ArticleCommentEntity item) {
        if (item!= null) {
            Glide.with(mContext).load(item.getUserAvatar())
                    .crossFade()
                    .placeholder(R.mipmap.ic_avatar)
                    .transform(new GlideCircleTransform(mContext))
                    .into((ImageView) helper.getView(R.id.image_ask_profile));

            helper.setText(R.id.textView_item_ask_specialist, item.getUserNickname())
                    .setText(R.id.textView_item_ask_title, item.getContent())
                    .setText(R.id.textView_item_ask_date_time, item.getCreateAt())
            .addOnClickListener(R.id.textView_item_ask_action);

        }
    }

}
