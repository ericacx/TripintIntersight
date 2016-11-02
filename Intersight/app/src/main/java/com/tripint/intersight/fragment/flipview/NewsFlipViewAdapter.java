package com.tripint.intersight.fragment.flipview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripint.intersight.R;
import com.tripint.intersight.entity.article.NewsEntity;
import com.tripint.intersight.event.StartFragmentEvent;
import com.tripint.intersight.fragment.home.NewsDetailFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Eric on 16/9/14.
 */
public class NewsFlipViewAdapter extends BaseAdapter {

    private List<NewsEntity> resList;
    private Context mContext;
    private String url;
    public NewsFlipViewAdapter(Context context, List<NewsEntity> list) {
        this.mContext = context;
        this.resList = list;
    }

    @Override
    public int getCount() {
        return resList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
//        return items.get(position).getId();
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_flipview_one, parent, false);

            holder.opinionFlipIvPic = (ImageView) convertView.findViewById(R.id.opinionFlipIvPic);
            holder.newsFlipViewTvTitle = (TextView) convertView.findViewById(R.id.newsFlipViewTvTitle);
            holder.newsFlipViewTvName = (TextView) convertView.findViewById(R.id.newsFlipViewTvName);
            holder.opinionFlipViewTvContent = (TextView) convertView.findViewById(R.id.opinionFlipViewTvContent);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Glide.with(mContext).load(resList.get(position).getThumb())
                .crossFade()
                .placeholder(R.drawable.loading_normal_icon)
                .into(holder.opinionFlipIvPic);
        holder.newsFlipViewTvTitle.setText(resList.get(position).getTitle());
        holder.newsFlipViewTvName.setText(resList.get(position).getTag());
        holder.opinionFlipViewTvContent.setText(resList.get(position).getDesc());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    url = resList.get(0).getUrl();
                    EventBus.getDefault().post(new StartFragmentEvent(NewsDetailFragment.newInstance(url)));
                } else {
                    url = (resList.get(position)).getUrl();
                    EventBus.getDefault().post(new StartFragmentEvent(NewsDetailFragment.newInstance(url)));
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.opinionFlipIvPic)
        ImageView opinionFlipIvPic;
        @Bind(R.id.newsFlipViewTvTitle)
        TextView newsFlipViewTvTitle;
        @Bind(R.id.newsFlipViewTvName)
        TextView newsFlipViewTvName;
        @Bind(R.id.opinionFlipViewTvContent)
        TextView opinionFlipViewTvContent;
    }

}
