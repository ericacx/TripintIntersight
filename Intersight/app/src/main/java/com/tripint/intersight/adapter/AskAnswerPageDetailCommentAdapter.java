package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.model.QADetailModel;
import com.tripint.intersight.model.QAModel;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class AskAnswerPageDetailCommentAdapter extends BaseQuickAdapter<QADetailModel> {
    public AskAnswerPageDetailCommentAdapter() {
        super(R.layout.item_ask_answer_chat_left, getSampleData(2));
    }

    public AskAnswerPageDetailCommentAdapter(int dataSize) {
        super(R.layout.item_ask_answer_chat_left, getSampleData(dataSize));
    }

    @Override
    protected void convert(BaseViewHolder helper, QADetailModel item) {
        helper.setText(R.id.textView_item_ask_specialist, item.getPersonName() + item.getCompany() + item.getJobTitle())
                .setText(R.id.textView_item_ask_title, item.getMessage())
                .setText(R.id.textView_item_ask_date_time, item.getDatetime())
                ;

        Glide.with(mContext).load(item.getProfileImgUrl())
                .crossFade()
                .placeholder(R.mipmap.iconfont_wechat)
                .transform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.image_ask_profile));
    }


    public static List<QADetailModel> getSampleData(int lenth) {
        List<QADetailModel> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            QADetailModel status = new QADetailModel();
            status.setCompany("腾讯信息科技" + i);
            status.setJobTitle("设计总经理" + "");
            status.setPersonName("Maggie ");
            status.setMessage("目前AR技术的整体发展情况及未来发展情况与未来的发展方向,最新动态最新内容说明" + i);
            status.setProfileImgUrl("https://avatars2.githubusercontent.com/u/6078720?v=3&s=200");
            status.setVoiceMessageUrl("https://avatars2.githubusercontent.com/u/6066620?v=3&s=400");
            list.add(status);
        }
        return list;
    }
}
