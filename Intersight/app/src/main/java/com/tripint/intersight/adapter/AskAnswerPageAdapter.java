package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.List;

/**
 */
public class AskAnswerPageAdapter extends BaseQuickAdapter<DiscussEntiry> {
    public AskAnswerPageAdapter(List<DiscussEntiry> discussEntiries) {
        super(R.layout.item_ask_answer, discussEntiries);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscussEntiry item) {
        String sperialist = "";
        String avatar = "";
        if (item.getUser() != null) {
            if (item.getUser().getCompany() != null) {
                sperialist = item.getUser().getCompany().getName();
            }
            if (item.getUser().getAbility() != null) {
                sperialist += item.getUser().getAbility().getName();
            }
            avatar = item.getUser().getAvatar();
        }

        helper.setText(R.id.textView_item_ask_title, item.getContent())
                .setText(R.id.textView_item_ask_specialist, item.getUser().getCompany().getName())
                .setText(R.id.textView_item_liked_number, item.getFollowsCount() + "")
                .setText(R.id.textView_item_listened_number, item.getListenCount() + "")
                .setText(R.id.textView_item_ask_voice, item.getVoiceId() + "")
                .addOnClickListener(R.id.textView_item_liked_number)
                .addOnClickListener(R.id.textView_item_ask_title)
                .linkify(R.id.layout_container_item_ask_answer);

        Glide.with(mContext).load(avatar)
                .crossFade()
                .placeholder(R.mipmap.iconfont_wechat)
                .transform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.image_ask_profile));
    }


//    public static List<QAModel> getSampleData(int lenth) {
//        List<QAModel> list = new ArrayList<>();
//        for (int i = 0; i < lenth; i++) {
//            QAModel status = new QAModel();
//            status.setCompany("Chad" + i);
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
