package com.tripint.intersight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.bean.DataEntity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Eric on 16/9/13.
 */
public class AskRecyclerViewAdapter extends RecyclerView.Adapter<AskRecyclerViewAdapter.AskRecyclerViewHolder> {


    private Context context;
    private List<DataEntity> dataEntityList;

    public AskRecyclerViewAdapter(Context context, List<DataEntity> dataEntityList) {
        this.context = context;
        this.dataEntityList = dataEntityList;
    }

    @Override
    public AskRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ask_recyclerview_item, null);
        return new AskRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AskRecyclerViewHolder holder, int position) {


        holder.askIvIcon.setImageResource(dataEntityList.get(position).getIcon());
        holder.askTvName.setText(dataEntityList.get(position).getName());
        holder.askTvWorkTime.setText(dataEntityList.get(position).getWorkTime());
        holder.askTvCompany.setText(dataEntityList.get(position).getCompany());
        holder.askTvTitle.setText(dataEntityList.get(position).getTitle());
        holder.askTvTrade.setText(dataEntityList.get(position).getTrade());
    }

    @Override
    public int getItemCount() {
        if (dataEntityList != null) {
            return dataEntityList.size();
        }
        return 0;
    }


    public class AskRecyclerViewHolder extends RecyclerView.ViewHolder {


        ImageView askIvIcon;
        TextView askTvName;
        TextView askTvWorkTime;
        TextView askTvCompany;
        TextView askTvTitle;
        TextView askTvTrade;

        public AskRecyclerViewHolder(View itemView) {
            super(itemView);
            askIvIcon = ((ImageView) itemView.findViewById(R.id.askIvIcon));
            askTvName = ((TextView) itemView.findViewById(R.id.askTvName));
            askTvWorkTime = ((TextView) itemView.findViewById(R.id.askTvWorkTime));
            askTvCompany = ((TextView) itemView.findViewById(R.id.askTvCompany));
            askTvTitle = ((TextView) itemView.findViewById(R.id.askTvTitle));
            askTvTrade = ((TextView) itemView.findViewById(R.id.askTvTrade));
        }

    }
}
