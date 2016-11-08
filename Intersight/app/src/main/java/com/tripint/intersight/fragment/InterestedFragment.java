package com.tripint.intersight.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.InterestedRecyclerViewAdapter;
import com.tripint.intersight.bean.InterestedDataEntity;
import com.tripint.intersight.entity.user.ChooseEntity;
import com.tripint.intersight.fragment.base.BaseFragment;
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
public class InterestedFragment extends BaseFragment {


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


    public static InterestedFragment newInstance() {

        Bundle args = new Bundle();

        InterestedFragment fragment = new InterestedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interested, container, false);
        ButterKnife.bind(this, view);

        initView();
        initData();

        interestedRecyclerViewAdapter = new InterestedRecyclerViewAdapter(mActivity, entityList);
        interestedGridView.setAdapter(interestedRecyclerViewAdapter);
        interestedGridView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        interestedGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                interestedRecyclerViewAdapter.setSelectedIndex(position);
                interestedRecyclerViewAdapter.notifyDataSetChanged();
            }
        });


        return view;
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
                    start(FocusTradeFragment.newInstance());
                }
            }
        };
    }

    private void initData() {
        InterestedDataEntity dataEntity1 = new InterestedDataEntity(R.drawable.btn_selector_choose_one, "青年学生");
        InterestedDataEntity dataEntity2 = new InterestedDataEntity(R.drawable.btn_selector_choose_two, "职场人士");
        entityList = new ArrayList<InterestedDataEntity>();
        entityList.add(dataEntity1);
        entityList.add(dataEntity2);
    }

    @OnClick(R.id.interestedNext)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.interestedNext:
                if (interestedRecyclerViewAdapter.getSelectedIndex() >= 0) {
                    int role = interestedRecyclerViewAdapter.getSelectedIndex() == 0 ? 2 : 1;
                    BaseDataHttpRequest.getInstance(mActivity).postChooseRole(
                            new ProgressSubscriber(subscriber, mActivity)
                            , role + "");
                } else {
                    CommonUtils.showToast("请选择您的身份类型");
                }
                break;
        }
    }
}
