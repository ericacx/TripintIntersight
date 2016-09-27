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
import com.tripint.intersight.bean.OpinionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 16/9/14.
 */

//public class OpinionFlipViewAdapter extends BaseAdapter implements View.OnClickListener {
//
//    public interface Callback {
//        public void onPageRequested(int page);
//    }
//
//
//    private LayoutInflater inflater;
//    private Callback callback;
//
//    private List<OpinionData> items = new ArrayList<OpinionData>();
//
//    public OpinionFlipViewAdapter(Context context) {
//        inflater = LayoutInflater.from(context);
//        for (int i = 0; i < 10; i++) {
//            items.add(new OpinionData("AR技术可以利用计算机生成一种逼真的视,听,力,触和动等感觉的虚拟环境,通过各种传感设备使用户沉浸到该环境中\n" +
//                    "        ,实现用户和环境直接进行自然交互.此前曾有一种技术名为Virtual Reality（虚拟实境）技术,这项技术可以创造出一套全新的虚拟环境,\n" +
//                    "        听起来与AR技术很像.不过略为不同的是,AR技术强调虚实结合,例如谷歌眼镜就应用了AR技术,通过虚拟菜单实现了拍照,录像等功能.\n" +
//                    "        正是这种特性,让AR技术有了广阔的应用前景.AR技术可以模拟出试衣间,用户在试衣间可以挑选......", R.mipmap.p1, "目前AR技术的整体发展情况以及未来演讲方向?", "Susan", "互联网 | 游戏 |软件", "销售总监", R.mipmap.iconfont_mima, "485", R.mipmap.iconfont_mima, "485", R.mipmap.iconfont_mima));
//        }
//    }
//
//    public void setCallback(Callback callback) {
//        this.callback = callback;
//    }
//
//    @Override
//    public int getCount() {
//        return items.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
////        return items.get(position).getId();
//        return 0;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return true;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = inflater.inflate(R.layout.opinion_flipview_two, parent, false);
//
//            holder.opinionFlipViewTvTitle = (TextView) convertView.findViewById(R.id.opinionFlipViewTvTitle);
//            holder.opinionFlipViewTvName = (TextView) convertView.findViewById(R.id.opinionFlipViewTvName);
//            holder.opinionFlipViewTvTrade = (TextView) convertView.findViewById(R.id.opinionFlipViewTvTrade);
//            holder.opinionFlipViewTvPos = (TextView) convertView.findViewById(R.id.opinionFlipViewTvPos);
//            holder.opinionFlipViewAgreeNum = (TextView) convertView.findViewById(R.id.opinionFlipViewAgreeNum);
//            holder.opinionFlipViewTalkNum = (TextView) convertView.findViewById(R.id.opinionFlipViewTalkNum);
//            holder.opinionFlipViewTvContent = (TextView) convertView.findViewById(R.id.opinionFlipViewTvContent);
//
//            holder.opinionFlipIvPic = (ImageView) convertView.findViewById(R.id.opinionFlipIvPic);
//            holder.opinionFlipViewAgree = (ImageView) convertView.findViewById(R.id.opinionFlipViewAgree);
//            holder.opinionFlipViewTalk = (ImageView) convertView.findViewById(R.id.opinionFlipViewTalk);
//            holder.opinionFlipViewReport = (ImageView) convertView.findViewById(R.id.opinionFlipViewReport);
//
//            holder.opioionFlipBtnSubmit = (Button) convertView.findViewById(R.id.opioionFlipBtnSubmit);
//
//            holder.opioionFlipBtnSubmit.setOnClickListener(this);
//
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        //TODO set a text with the id as well
//        holder.opinionFlipViewTvTitle.setText(items.get(position).getTitle() + ":" + position);
//        holder.opinionFlipViewTvName.setText(items.get(position).getName() + ":" + position);
//        holder.opinionFlipViewTvTrade.setText(items.get(position).getTrade() + ":" + position);
//        holder.opinionFlipViewTvPos.setText(items.get(position).getPosition() + ":" + position);
//        holder.opinionFlipViewAgreeNum.setText(items.get(position).getAgreeNum() + ":" + position);
//        holder.opinionFlipViewTalkNum.setText(items.get(position).getTalkNum() + ":" + position);
//        holder.opinionFlipViewTvContent.setText(items.get(position).getContent() + ":" + position);
//
//
//        holder.opinionFlipIvPic.setImageResource(items.get(position).getPhoto());
//        holder.opinionFlipViewAgree.setImageResource(items.get(position).getAgree());
//        holder.opinionFlipViewTalk.setImageResource(items.get(position).getTalk());
//        holder.opinionFlipViewReport.setImageResource(items.get(position).getReport());
//
//        return convertView;
//    }
//
//    static class ViewHolder {
//
//        ImageView opinionFlipIvPic;
//        ImageView opinionFlipViewAgree;
//        ImageView opinionFlipViewTalk;
//        ImageView opinionFlipViewReport;
//
//        TextView opinionFlipViewTvTitle;
//        TextView opinionFlipViewTvName;
//        TextView opinionFlipViewTvTrade;
//        TextView opinionFlipViewTvPos;
//        TextView opinionFlipViewAgreeNum;
//        TextView opinionFlipViewTalkNum;
//        TextView opinionFlipViewTvContent;
//
//        Button opioionFlipBtnSubmit;
//    }
//
//    @Override
//    public void onClick(View v) {
////        switch(v.getId()){
////            case R.id.first_page:
////                if(callback != null){
////                    callback.onPageRequested(0);
////                }
////                break;
////            case R.id.last_page:
////                if(callback != null){
////                    callback.onPageRequested(getCount()-1);
////                }
////                break;
////        }
//    }
//
//    public void addItems(int amount) {
//        for (int i = 0; i < amount; i++) {
////            items.add(new Item());
//        }
//        notifyDataSetChanged();
//    }
//
//    public void addItemsBefore(int amount) {
//        for (int i = 0; i < amount; i++) {
////            items.add(0, new Item());
//        }
//        notifyDataSetChanged();
//    }
//}
