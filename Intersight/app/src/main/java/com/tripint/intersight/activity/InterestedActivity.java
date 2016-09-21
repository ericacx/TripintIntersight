package com.tripint.intersight.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tripint.intersight.R;
import com.tripint.intersight.common.utils.ToastUtil;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.service.BaseDataHttpRequest;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ProgressSubscriber;

import java.util.List;

/**
 * 订阅页面---感兴趣的
 */
public class InterestedActivity extends AppCompatActivity {

    private PageDataSubscriberOnNext<List<Industry>> subscriber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested);

        subscriber = new PageDataSubscriberOnNext<List<Industry>>() {
            @Override
            public void onNext(List<Industry> industries) {
                //接口请求成功后处理
                ToastUtil.showToast(InterestedActivity.this, industries.size() +"");
            }
        };

        BaseDataHttpRequest.getInstance().getIndustry(new ProgressSubscriber(subscriber, InterestedActivity.this), 0, 10);
    }
}
