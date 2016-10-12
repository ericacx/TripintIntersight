package com.tripint.intersight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.listener.RecyclerViewItemOnClick;
import com.tripint.intersight.entity.user.PaymentEntity;
import com.tripint.intersight.widget.image.TouchImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 作者：wangdakuan
 * 主要功能: 选择适配器，单选
 * 创建时间：2015/12/23 10:57
 */
public class PaymentSelectAdapter extends RecyclerView.Adapter<PaymentSelectAdapter.ViewHolder> {

    RecyclerViewItemOnClick itemOnClick;
    private Context mContext;

    private List<PaymentEntity> paymentEntities; //出行人

    public void ItemOnClick(int position, Object data) {
        itemOnClick.ItemOnClick(position, data);
    }

    public void setOnRecyclerViewItemOnClick(RecyclerViewItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    public PaymentSelectAdapter(Context context, List<PaymentEntity> paymentEntities) {
        this.mContext = context;
        this.paymentEntities = paymentEntities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_payment,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final PaymentEntity paymentEntity = paymentEntities.get(i);

        viewHolder.titleName.setText(paymentEntity.getChannelName());

//        viewHolder.isSelect.setChecked(_baseSelects.isSelect());
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(null != itemOnClick){
//                    itemOnClick.ItemOnClick(i,_baseSelects);
//                }
//            }
//        });
        if ("ALIPAY".equals(paymentEntity.getChannelPartentId())) {
            viewHolder.imageLogo.setImageResource(R.mipmap.icon_alipay_logo);
        } else if ("WEIPAY".equals(paymentEntity.getChannelPartentId())) {
            viewHolder.imageLogo.setImageResource(R.mipmap.icon_appwx_logo);
        }
//        Glide.with(mContext).load(paymentEntity.getChannelLogo())
//                .crossFade()
//                .placeholder(R.drawable.loading_normal_icon)
//                .transform(new GlideCircleTransform(mContext))
//                .into(viewHolder.imageLogo);
    }

    @Override
    public int getItemCount() {
        int coun = 0;
        if (null != paymentEntities && paymentEntities.size() > 0) {
            coun = paymentEntities.size();
        }
        return coun;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Bind(R.id.text_view_pay_channel_name)
        TextView titleName;
        @Bind(R.id.image_pay_channel_logo)
        TouchImageView imageLogo;
//        @InjectView(R.id.is_select_btn)
//        ToggleButton isSelect;
    }

    /**
     * @param paymentEntities
     */
    public void setRefreshData(List<PaymentEntity> paymentEntities) {
        this.paymentEntities = paymentEntities;
        notifyDataSetChanged();
    }
}