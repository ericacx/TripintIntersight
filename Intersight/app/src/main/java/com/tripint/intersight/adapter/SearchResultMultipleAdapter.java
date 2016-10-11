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
        addItemType(MultipleSearchItemModel.INTERVIEW, R.layout.item_recyclerview_search_person_result);
        addItemType(MultipleSearchItemModel.ARTICLE, R.layout.item_recyclerview_search_keyword_result);

    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleSearchItemModel item) {
        String sperialist = "";
        String avatar = "";
        if (item.getContent().getUserInfo() != null) {
            if (item.getContent().getUserInfo().getCompany() != null) {
                sperialist = item.getContent().getUserInfo().getCompany().getName();
            }
            if (item.getContent().getUserInfo().getAbility() != null) {
                sperialist += item.getContent().getUserInfo().getAbility().getName();
            }
            avatar = item.getContent().getUserInfo().getAvatar();
        }
        switch (helper.getItemViewType()) {
            case MultipleSearchItemModel.INTERVIEW:
                helper.setText(R.id.text_view_search_person_name, sperialist)
                        .setText(R.id.text_view_search_person_industry, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.text_view_search_person_job_title, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.text_view_search_person_job_pos, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.text_view_search_person_year, StringUtils.null2Length0(item.getContent().getContent()))
                ;

                Glide.with(mContext).load(avatar)
                        .crossFade()
                        .placeholder(R.mipmap.ic_avatar)
                        .transform(new GlideCircleTransform(mContext))
                        .into((ImageView) helper.getView(R.id.image_search_person_profile));

                break;
            case MultipleSearchItemModel.ARTICLE:
                helper.setText(R.id.search_text_view_title_main, sperialist)
                        .setText(R.id.search_text_view_title_sub, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.search_text_view_other, StringUtils.null2Length0(item.getContent().getContent()))
                        .setText(R.id.search_text_view_industry, StringUtils.null2Length0(item.getContent().getContent()))
                ;

                break;
        }
    }


}
