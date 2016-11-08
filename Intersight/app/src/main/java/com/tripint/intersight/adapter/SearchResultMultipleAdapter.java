package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseMultiItemQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.SearchArticleEntity;
import com.tripint.intersight.entity.discuss.InterviewEntity;
import com.tripint.intersight.model.MultipleSearchItemModel;
import com.tripint.intersight.widget.image.CircleImageView;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.List;

/**
 *
 */
public class SearchResultMultipleAdapter extends BaseMultiItemQuickAdapter<MultipleSearchItemModel> {


    public SearchResultMultipleAdapter(List<MultipleSearchItemModel> data) {
        super(data);
        addItemType(MultipleSearchItemModel.INTERVIEW, R.layout.item_recyclerview_search_person_result);
        addItemType(MultipleSearchItemModel.ARTICLE, R.layout.item_recyclerview_search_keyword_result);

    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleSearchItemModel item) {


        switch (helper.getItemViewType()) {
            case MultipleSearchItemModel.INTERVIEW:
                InterviewEntity entity = item.getInterview();

                String avatar = entity.getAvatar();
                String nickname = entity.getNickname();
                String experience = entity.getExperience();
                String position = entity.getPosition() == null ? "" : entity.getPosition().getName();
                String industry = entity.getIndustry() == null ? "" : entity.getIndustry().getName();
                String company = entity.getCompany() == null ? "" : entity.getCompany().getName();
                helper.setText(R.id.text_view_search_person_name, nickname)
                        .setText(R.id.text_view_search_person_industry, StringUtils.null2Length0(industry))
                        .setText(R.id.text_view_search_person_job_title, StringUtils.null2Length0(company))
                        .setText(R.id.text_view_search_person_job_pos, StringUtils.null2Length0(position))
                        .setText(R.id.text_view_search_person_year, StringUtils.null2Length0(experience))
                ;

                Glide.with(mContext).load(avatar)
                        .crossFade()
                        .placeholder(R.mipmap.ic_avatar)
                        .transform(new GlideCircleTransform(mContext))
                        .into((CircleImageView) helper.getView(R.id.image_search_person_profile));

                break;
            case MultipleSearchItemModel.ARTICLE:
                SearchArticleEntity articleEntity = item.getContent();
                helper.setText(R.id.search_text_view_title_main, articleEntity.getTitle())
                        .setText(R.id.search_text_view_title_sub, StringUtils.null2Length0(articleEntity.getContent()))
                        .setText(R.id.search_text_view_other, articleEntity.getPraises() + "个赞      "
                                + articleEntity.getComments() + "个评论        "
                                + articleEntity.getFollows() + "个关注")
                        .setText(R.id.search_text_view_industry, StringUtils.null2Length0(articleEntity.getIndustryName()))
                ;

                break;
        }
    }


}
