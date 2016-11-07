package com.tripint.intersight.activity;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.GuideAdapter;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;
import com.tripint.intersight.common.widget.CirclePageIndicator;
import com.tripint.intersight.fragment.GuideFragment;
import com.tripint.intersight.common.utils.PackageUtils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //记录是否使用过
        SharedPreferences sharedPreferences = getSharedPreferences("version",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //专门获取版本的工具类
        String packageVersion = PackageUtils.getPackageVersion(this);
        editor.putString("version",packageVersion);

        //commit
        editor.commit();

        initView();
    }

    private void initView() {
        /**
         * 获取RadioGroup
         */

        mIndicator = (CirclePageIndicator) findViewById(R.id.circle_indicator);


        viewPager = ((ViewPager) findViewById(R.id.guide_viewPager));

        ArrayList<Fragment> guideFragment = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            GuideFragment fragment = new GuideFragment();

            //Bundle传值，在Fragment中接受，来确定最后一页导航页是否显示“进去看看”
            Bundle bundle = new Bundle();

            bundle.putInt("id",i);

            fragment.setArguments(bundle);

            guideFragment.add(fragment);
        }


        viewPager.setAdapter(new GuideAdapter(getSupportFragmentManager(), guideFragment));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            //设置ViewPager的滑动监听
            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mIndicator.setViewPager(viewPager);

        ACache.get(this).put(EnumKey.ACacheKey.KEYWORD_APP_LAUNCHED, true);
    }
}
