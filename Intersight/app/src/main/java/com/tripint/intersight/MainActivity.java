package com.tripint.intersight;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tripint.intersight.fragment.AskFragment;
import com.tripint.intersight.fragment.IntersightPlusFragment;
import com.tripint.intersight.fragment.MineFragment;
import com.tripint.intersight.helper.ChangeFragmentHelper;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;//fragment控件
    private Toast toast;
    private TextView main_tvTitle;
    private boolean currentBackState;//返回键退出程序
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //初始界面关系管理
        Fragment fragment = new IntersightPlusFragment();

        ChangeFragmentHelper helper = new ChangeFragmentHelper();
        helper.setTargetFragment(fragment);
        helper.setIsClearStackBack(true);

        changeFragment(helper);

        initView();//初始化控件
    }

    /**
     * 初始化控件
     */
    private void initView() {
        toast = Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT);
        //盛放Fragment的容器
        FrameLayout main_container = ((FrameLayout) findViewById(R.id.main_container));

        RadioGroup main_tabBar = ((RadioGroup) findViewById(R.id.main_tabBar));
        main_tabBar.check(R.id.main_interplus);

        fragment = null;

        main_tabBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.main_interplus:
                        fragment = new IntersightPlusFragment();
                        break;
                    case R.id.main_ask:
                        fragment = new AskFragment();
                        break;
                    case R.id.main_mine:
                        fragment = new MineFragment();
                        break;
                }

                ChangeFragmentHelper helper = new ChangeFragmentHelper();
                helper.setTargetFragment(fragment);
                helper.setIsClearStackBack(true);
                changeFragment(helper);
            }
        });
    }


    public void changeFragment(ChangeFragmentHelper helper) {

        //获取需要跳转的Fragment
        Fragment targetFragment = helper.getTargetFragment();

        //获取是否清空回退栈
        boolean clearStackBack = helper.isClearStackBack();

        //获取是否加入回退栈
        String targetFragmentTag = helper.getTargetFragmentTag();

        //获取Bundle
        Bundle bundle = helper.getBundle();
        //是否给Fragment传值
        if (bundle != null) {
            targetFragment.setArguments(bundle);
        }

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        if (targetFragment != null) {
            fragmentTransaction.replace(R.id.main_container,targetFragment);
        }

        //是否添加到回退栈
        if (targetFragmentTag != null) {
            fragmentTransaction.addToBackStack(targetFragmentTag);
        }

        //是否清空回退栈
        if(clearStackBack){
            manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        fragmentTransaction.commit();
    }


    /**
     * 提示按回退键退出程序
     */
    @Override
    public void onBackPressed() {
        if (currentBackState){
            super.onBackPressed();
        }
        currentBackState = true;
        if (!isFinishing()){
            toast.show();
        }
        else{
            toast.cancel();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    currentBackState = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
