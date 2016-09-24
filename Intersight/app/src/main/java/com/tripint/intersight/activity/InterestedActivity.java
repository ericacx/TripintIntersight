package com.tripint.intersight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.InterestedRecyclerViewAdapter;
import com.tripint.intersight.bean.InterestedDataEntity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested);
        ButterKnife.bind(this);

        initData();

        interestedRecyclerViewAdapter = new InterestedRecyclerViewAdapter(InterestedActivity.this,entityList);
        interestedGridView.setAdapter(interestedRecyclerViewAdapter);
    }

    private void initData() {
        InterestedDataEntity dataEntity1 = new InterestedDataEntity(R.mipmap.img_choseone_0101,"知识技能");
        InterestedDataEntity dataEntity2 = new InterestedDataEntity(R.mipmap.img_choseone_0101,"行业资讯");
        entityList = new ArrayList<InterestedDataEntity>();
        entityList.add(dataEntity1);
        entityList.add(dataEntity2);
    }

    @OnClick(R.id.interestedNext)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.interestedNext:
                Intent intent = new Intent();
                intent.setClass(InterestedActivity.this,FocusTradeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
