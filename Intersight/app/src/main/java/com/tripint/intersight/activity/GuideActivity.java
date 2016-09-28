package com.tripint.intersight.activity;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.tripint.intersight.R;
import com.tripint.intersight.adapter.GuideAdapter;
import com.tripint.intersight.fragment.GuideFragment;
import com.tripint.intersight.common.utils.PackageUtils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
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

        final RadioGroup guide_tabBar = ((RadioGroup) findViewById(R.id.guide_tabBar));

        guide_tabBar.check(R.id.guide_buttonOne);

        guide_tabBar.setClickable(false);

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
                int checkedId = R.id.guide_buttonOne;
                switch (i) {
                    case 0:
                        checkedId = R.id.guide_buttonOne;
                        break;
                    case 1:
                        checkedId = R.id.guide_buttonTwo;
                        break;
                    case 2:
                        checkedId = R.id.guide_buttonThree;
                        break;

                }
                guide_tabBar.check(checkedId);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
