package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.model.QAModel;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class AskAnswerPageAdapter extends BaseQuickAdapter<QAModel> {
    public AskAnswerPageAdapter() {
        super(R.layout.item_ask_answer, getSampleData(100));
    }

    public AskAnswerPageAdapter(int dataSize) {
        super(R.layout.item_ask_answer, getSampleData(dataSize));
    }

    @Override
    protected void convert(BaseViewHolder helper, QAModel item) {
        helper.setText(R.id.textView_item_ask_title, item.getTitle())
                .setText(R.id.textView_item_ask_specialist, item.getCompany())
                .setText(R.id.textView_item_ask_voice, item.getLikeCount() + "")
                .addOnClickListener(R.id.textView_item_liked_number)
                .addOnClickListener(R.id.textView_item_ask_title)
                .linkify(R.id.textView_item_ask_title);

        Glide.with(mContext).load(item.getProfileImage())
                .crossFade()
                .placeholder(R.mipmap.iconfont_wechat)
                .transform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.image_ask_profile));
    }


    public static List<QAModel> getSampleData(int lenth) {
        List<QAModel> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            QAModel status = new QAModel();
            status.setCompany("Chad" + i);
            status.setId(i + "");
            status.setJobTitle("ChadChadChad");
            status.setLikeCount(100);
            status.setLisentetCount(999);
            status.setName("Name" + i);
            status.setProfileImage("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            status.setTitle("BaseRecyclerViewAdpaterHelper https://www.recyclerview.org");
            list.add(status);
        }
        return list;
    }
}
