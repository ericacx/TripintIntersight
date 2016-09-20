package com.tripint.intersight.fragment.flipview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripint.intersight.R;
import com.tripint.intersight.bean.NewsData;
import com.tripint.intersight.bean.OpinionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 16/9/14.
 */
public class NewsFlipViewAdapter extends BaseAdapter implements View.OnClickListener{


    public interface Callback{
        public void onPageRequested(int page);
    }


    private LayoutInflater inflater;
    private Callback callback;

    private List<NewsData> items = new ArrayList<NewsData>();

    public NewsFlipViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        for(int i = 0 ; i<10 ; i++){
            items.add(new NewsData("软银还债抛售80亿美元阿里股份 阿里:乐于回购", "好奇心日报","文/王尼马",R.mipmap.iconfont_mima,
                    485,R.mipmap.iconfont_mima,485,R.mipmap.iconfont_mima,"6月1日据外媒消息称,日本电信运营商软银表示,该公司计划减持至少79亿美元阿里股份\n" +
                    "        ,以此增加自身流动资金并偿还债务.减持后,软银所持阿里股份将由此前32.3降至28%,但其作为阿里最大股东的身份不变.\n" +
                    "        这也是自软银投资阿里以来,首次减持其浮漂.据悉,软银将采用分批抛售的方式,其中,约50亿美元股票软银将成立一个法定可转换信托基金\n" +
                    "    该公司计划减持至少79亿美元阿里股份\n" +
                    "        ,以此增加自身流动资金并偿还债务.减持后,软银所持阿里股份将由此前32.3降至28%,但其作为阿里最大股东的身份不变\n" +
                    "    该公司计划减持至少79亿美元阿里股份\n" +
                    "        ,以此增加自身流动资金并偿还债务.减持后,软银所持阿里股份将由此前32.3降至28%,但其作为阿里最大股东的身份不变\n" +
                    "    该公司计划减持至少79亿美元阿里股份\n" +
                    "        ,以此增加自身流动资金并偿还债务.减持后,软银所持阿里股份将由此前32.3降至28%,但其作为阿里最大股东的身份不变"));
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return items.size();
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
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.news_flipview_two, parent, false);

            holder.newsFlipViewTvTitleOne = (TextView) convertView.findViewById(R.id.newsFlipViewTvTitleOne);
            holder.newsFlipViewTvTradeOne = (TextView) convertView.findViewById(R.id.newsFlipViewTvTradeOne);
            holder.newsFlipViewTvNameOne = (TextView) convertView.findViewById(R.id.newsFlipViewTvNameOne);
            holder.newsFlipViewAgreeNumOne = (TextView) convertView.findViewById(R.id.newsFlipViewAgreeNumOne);
            holder.newsFlipViewTalkNumOne = (TextView) convertView.findViewById(R.id.newsFlipViewTalkNumOne);
            holder.newsFlipViewTvContentOne = (TextView) convertView.findViewById(R.id.newsFlipViewTvContentOne);

            holder.newsFlipViewAgreeOne = (ImageView) convertView.findViewById(R.id.newsFlipViewAgreeOne);
            holder.newsFlipViewTalkOne = (ImageView) convertView.findViewById(R.id.newsFlipViewTalkOne);
            holder.newsFlipViewReportOne = (ImageView) convertView.findViewById(R.id.newsFlipViewReportOne);


            holder.newsFlipViewTvTitleTwo = (TextView) convertView.findViewById(R.id.newsFlipViewTvTitleTwo);
            holder.newsFlipViewTvTradeTwo = (TextView) convertView.findViewById(R.id.newsFlipViewTvTradeTwo);
            holder.newsFlipViewTvNameTwo = (TextView) convertView.findViewById(R.id.newsFlipViewTvNameTwo);
            holder.newsFlipViewAgreeNumTwo = (TextView) convertView.findViewById(R.id.newsFlipViewAgreeNumTwo);
            holder.newsFlipViewTalkNumTwo = (TextView) convertView.findViewById(R.id.newsFlipViewTalkNumTwo);
            holder.newsFlipViewTvContentTwo = (TextView) convertView.findViewById(R.id.newsFlipViewTvContentTwo);

            holder.newsFlipViewAgreeTwo = (ImageView) convertView.findViewById(R.id.newsFlipViewAgreeTwo);
            holder.newsFlipViewTalkTwo = (ImageView) convertView.findViewById(R.id.newsFlipViewTalkTwo);
            holder.newsFlipViewReportTwo = (ImageView) convertView.findViewById(R.id.newsFlipViewReportTwo);


            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //TODO set a text with the id as well
        holder.newsFlipViewTvTitleOne.setText(items.get(position).getTitle()+":"+position);
        holder.newsFlipViewTvNameOne.setText(items.get(position).getName()+":"+position);
        holder.newsFlipViewTvTradeOne.setText(items.get(position).getPublicName()+":"+position);
        holder.newsFlipViewAgreeNumOne.setText(items.get(position).getAgreeNum()+":"+position);
        holder.newsFlipViewTalkNumOne.setText(items.get(position).getCommentNum()+":"+position);
        holder.newsFlipViewTvContentOne.setText(items.get(position).getContent()+":"+position);


        holder.newsFlipViewAgreeOne.setImageResource(items.get(position).getAgree());
        holder.newsFlipViewTalkOne.setImageResource(items.get(position).getComment());
        holder.newsFlipViewReportOne.setImageResource(items.get(position).getReport());


        holder.newsFlipViewTvTitleTwo.setText(items.get(position).getTitle()+":"+position);
        holder.newsFlipViewTvNameTwo.setText(items.get(position).getName()+":"+position);
        holder.newsFlipViewTvTradeTwo.setText(items.get(position).getPublicName()+":"+position);
        holder.newsFlipViewAgreeNumTwo.setText(items.get(position).getAgreeNum()+":"+position);
        holder.newsFlipViewTalkNumTwo.setText(items.get(position).getCommentNum()+":"+position);
        holder.newsFlipViewTvContentTwo.setText(items.get(position).getContent()+":"+position);


        holder.newsFlipViewAgreeTwo.setImageResource(items.get(position).getAgree());
        holder.newsFlipViewTalkTwo.setImageResource(items.get(position).getComment());
        holder.newsFlipViewReportTwo.setImageResource(items.get(position).getReport());



        return convertView;
    }

    static class ViewHolder{

        ImageView newsFlipViewAgreeOne;
        ImageView newsFlipViewTalkOne;
        ImageView newsFlipViewReportOne;

        TextView newsFlipViewTvTitleOne;
        TextView newsFlipViewTvTradeOne;
        TextView newsFlipViewTvNameOne;
        TextView newsFlipViewAgreeNumOne;
        TextView newsFlipViewTalkNumOne;
        TextView newsFlipViewTvContentOne;


        ImageView newsFlipViewAgreeTwo;
        ImageView newsFlipViewTalkTwo;
        ImageView newsFlipViewReportTwo;

        TextView newsFlipViewTvTitleTwo;
        TextView newsFlipViewTvTradeTwo;
        TextView newsFlipViewTvNameTwo;
        TextView newsFlipViewAgreeNumTwo;
        TextView newsFlipViewTalkNumTwo;
        TextView newsFlipViewTvContentTwo;


    }

    @Override
    public void onClick(View v) {
//        switch(v.getId()){
//            case R.id.first_page:
//                if(callback != null){
//                    callback.onPageRequested(0);
//                }
//                break;
//            case R.id.last_page:
//                if(callback != null){
//                    callback.onPageRequested(getCount()-1);
//                }
//                break;
//        }
    }

    public void addItems(int amount) {
        for(int i = 0 ; i<amount ; i++){
//            items.add(new Item());
        }
        notifyDataSetChanged();
    }

    public void addItemsBefore(int amount) {
        for(int i = 0 ; i<amount ; i++){
//            items.add(0, new Item());
        }
        notifyDataSetChanged();
    }
}
