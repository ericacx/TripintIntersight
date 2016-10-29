package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.discuss.DiscussEntity;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.List;

/**
 */
public class AskAnswerPageAdapter extends BaseQuickAdapter<DiscussEntity> {
    public AskAnswerPageAdapter(List<DiscussEntity> discussEntiries) {
        super(R.layout.item_ask_answer, discussEntiries);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscussEntity item) {

        if (item != null) {

            helper.setText(R.id.textView_item_ask_title, item.getContent())
                    .setText(R.id.textView_item_ask_specialist, item.getUserNickname())
                    .setText(R.id.textView_item_liked_number, item.getFollowsCount() + "")
                    .setText(R.id.textView_item_listened_number, item.getListenCount() + "")
                    .setText(R.id.textView_item_ask_voice_payment, item.getPayment() + "元即听")
                    .setText(R.id.textView_item_ask_voice_time, item.getTimeLong() + "s")
                    .setImageResource(R.id.image_item_like, R.mipmap.iconfont_heartbig01)
                    .setImageResource(R.id.image_item_listened, R.mipmap.iconfont_ear)
                    .addOnClickListener(R.id.textView_item_liked_number)
                    .addOnClickListener(R.id.textView_item_ask_title)
                    .linkify(R.id.textView_item_ask_specialist);

            Glide.with(mContext).load(item.getUserAvatar())
                    .crossFade()
                    .placeholder(R.drawable.loading_normal_icon)
                    .transform(new GlideCircleTransform(mContext))
                    .into((ImageView) helper.getView(R.id.image_ask_profile));
        }

//        int isPraises = item.getIsPraises() == 1000 ? R.mipmap.iconfont_heartbig01 : R.mipmap.iconfont_heartbig02;
//        helper.setImageResource(R.id.image_item_like, isPraises);


    }


//    public static List<QAModel> getSampleData(int lenth) {
//        List<QAModel> list = new ArrayList<>();
//        for (int i = 0; i < lenth; i++) {
//            QAModel status = new QAModel();
//            status.setCompanyName("Chad" + i);
//            status.setId(i + "");
//            status.setJobTitle("ChadChadChad");
//            status.setLikeCount(100);
//            status.setLisentetCount(999);
//            status.setName("Name" + i);
//            status.setProfileImage("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
//            status.setTitle("BaseRecyclerViewAdpaterHelper https://www.recyclerview.org");
//            list.add(status);
//        }
//        return list;
//    }
}
