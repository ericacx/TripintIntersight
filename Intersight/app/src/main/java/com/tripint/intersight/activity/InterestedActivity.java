package com.tripint.intersight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.InterestedRecyclerViewAdapter;
import com.tripint.intersight.bean.InterestedDataEntity;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.user.ChooseEntity;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订阅页面---感兴趣的
 */
public class InterestedActivity extends AppCompatActivity {


    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.interestedNext)
    Button interestedNext;
    @Bind(R.id.interestedGridView)
    ListView interestedGridView;

    private InterestedRecyclerViewAdapter interestedRecyclerViewAdapter;
    private List<InterestedDataEntity> entityList;

    private ChooseEntity chooseEntity;
    private PageDataSubscriberOnNext<ChooseEntity> subscriber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested);
        ButterKnife.bind(this);

        initView();
        initData();

        interestedRecyclerViewAdapter = new InterestedRecyclerViewAdapter(InterestedActivity.this,entityList);
        interestedGridView.setAdapter(interestedRecyclerViewAdapter);
        interestedGridView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        interestedGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                interestedRecyclerViewAdapter.setSelectedIndex(position);
                interestedRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

    }

    private void initView() {
        //验证码
        subscriber = new PageDataSubscriberOnNext<ChooseEntity>() {
            @Override
            public void onNext(ChooseEntity entity) {
                //接口请求成功后处理
                chooseEntity = entity;
                Log.e("interested", String.valueOf(entity.getStatus()));
                int status = entity.getStatus();
                if (status == 101){
                    Intent intent = new Intent();
                    intent.setClass(InterestedActivity.this,FocusTradeActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    private void initData() {
        InterestedDataEntity dataEntity1 = new InterestedDataEntity(R.drawable.btn_selector_choose_one, "学生");
        InterestedDataEntity dataEntity2 = new InterestedDataEntity(R.drawable.btn_selector_choose_two, "职场人员");
        entityList = new ArrayList<InterestedDataEntity>();
        entityList.add(dataEntity1);
        entityList.add(dataEntity2);
    }

    @OnClick(R.id.interestedNext)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.interestedNext:
                if (interestedRecyclerViewAdapter.getSelectedIndex() > 0) {
                    BaseDataHttpRequest.getInstance(InterestedActivity.this).postChooseRole(
                            new ProgressSubscriber(subscriber, InterestedActivity.this)
                            , interestedRecyclerViewAdapter.getSelectedIndex() + "");
                } else {
                    CommonUtils.showToast("请选择");
                }
                break;
        }
    }
}
