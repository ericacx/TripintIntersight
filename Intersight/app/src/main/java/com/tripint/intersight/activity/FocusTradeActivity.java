package com.tripint.intersight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.tripint.intersight.R;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.user.ChooseEntity;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订阅---关注的行业
 */
public class FocusTradeActivity extends AppCompatActivity {

    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.interestedNext)
    Button interestedNext;
    @Bind(R.id.interestedGridView)
    GridView interestedGridView;

    private ChooseEntity chooseEntity;
    private PageDataSubscriberOnNext<ChooseEntity> subscriber;

    List<Integer> list = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_trade);
        ButterKnife.bind(this);

        initView();

        interestedGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initView() {
        subscriber = new PageDataSubscriberOnNext<ChooseEntity>() {
            @Override
            public void onNext(ChooseEntity entity) {
                chooseEntity = entity;
                Log.e("focus", String.valueOf(entity.getStatus()));
                int status = entity.getStatus();
                if (status ==102){
                    Intent intent = new Intent();
                    intent.setClass(FocusTradeActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    @OnClick({R.id.interestedNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.interestedNext:

                list.add(1);
                list.add(2);
                list.add(3);
                String s = new Gson().toJson(list);
                Log.e("s", s);
                BaseDataHttpRequest.getInstance(FocusTradeActivity.this)
                        .postInsterestIndustry(
                                new ProgressSubscriber<ChooseEntity>(subscriber,FocusTradeActivity.this)
                                ,s);

                Intent intent = new Intent();
                intent.setClass(FocusTradeActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
