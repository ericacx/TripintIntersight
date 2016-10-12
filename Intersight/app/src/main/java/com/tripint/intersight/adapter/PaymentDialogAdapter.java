package com.tripint.intersight.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseQuickAdapter;
import com.tripint.intersight.common.widget.recyclerviewadapter.BaseViewHolder;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.widget.image.transform.GlideCircleTransform;

import java.util.List;

/**
 */
public class PaymentDialogAdapter extends BaseQuickAdapter<PaymentEntity> {
    public PaymentDialogAdapter(List<PaymentEntity> discussEntiries) {
        super(R.layout.item_payment, discussEntiries);
    }

    @Override
    protected void convert(BaseViewHolder helper, PaymentEntity item) {


        helper.setText(R.id.text_view_pay_channel_name, item.getChannelName())
        ;


        Glide.with(mContext).load(item.getChannelLogo())
                .crossFade()
                .placeholder(R.drawable.loading_normal_icon)
                .transform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.image_pay_channel_logo));
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
