package com.tripint.intersight.adapter;

import android.content.Context;
import android.media.Image;
import android.support.design.widget.CheckableImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.bean.InterestedDataEntity;

import java.util.List;

/**
 * Created by Eric on 16/9/23.
 */

public class InterestedRecyclerViewAdapter extends BaseAdapter {

    int selectedIndex = -1;
    private Context context;
    private List<InterestedDataEntity> interestedDataEntities;

    public InterestedRecyclerViewAdapter(Context context, List<InterestedDataEntity> interestedDataEntities) {
        this.context = context;
        this.interestedDataEntities = interestedDataEntities;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_gridview_interested, null);
            holder.itemRecyclerviewInterestedIv = ((ImageView) convertView.findViewById(R.id.itemRecyclerviewInterestedIv));
            holder.itemRecyclerviewInterestedTv = ((TextView) convertView.findViewById(R.id.itemRecyclerviewInterestedTv));

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final int pos = position;
        holder.itemRecyclerviewInterestedIv.setImageDrawable(context.getResources().getDrawable(interestedDataEntities.get(position).getIcon()));
        holder.itemRecyclerviewInterestedTv.setText(interestedDataEntities.get(position).getTrade());
        if (selectedIndex == position) {
            holder.itemRecyclerviewInterestedIv.setSelected(true);
        } else {
            holder.itemRecyclerviewInterestedIv.setSelected(false);
        }
//        holder.itemRecyclerviewInterestedIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                view.setSelected(!view.isSelected());
//                interestedDataEntities.get(pos).setChecked(!view.isSelected());
//            }
//        });
        return convertView;
    }

    class ViewHolder{

        ImageView itemRecyclerviewInterestedIv;
        TextView itemRecyclerviewInterestedTv;
    }
}
