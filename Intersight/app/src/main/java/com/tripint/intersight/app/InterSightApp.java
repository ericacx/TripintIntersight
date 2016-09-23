package com.tripint.intersight.app;

import android.Manifest;
import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.tencent.bugly.crashreport.CrashReport;
import com.tripint.intersight.common.Constants;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.helper.PermissionsChecker;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;

import java.io.InputStream;

import okhttp3.OkHttpClient;


/**
 * app
 */
public class InterSightApp extends Application {

    private boolean isUserLogin = false; //登陆为 true 未登陆为 false
    private boolean isBinding = false; //是否绑定第三方账号（第一次）


    public PermissionsChecker mPermissionsChecker; // 权限检测器
    public final int REQUEST_CODE = 911; // 请求码
    // 文件权限
    public final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,  //允许程序写入外部存储
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, //挂载、反挂载外部文件系统
            Manifest.permission.READ_LOGS, //读取系统底层日志
            Manifest.permission.WRITE_SETTINGS, //允许读写系统设置项
    };
    //获取位置权限
    public final String[] LOCATIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    //获取联系人权限
    public final String[] CONTACTS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS //允许应用访问联系人通讯录信息
    };

    //获取摄像头权限
    public final String[] CAMERA = new String[]{
            Manifest.permission.CAMERA //允许访问摄像头进行拍照
    };

    //获取拨打电话权限
    public final String[] PHONE = new String[]{
            Manifest.permission.CALL_PHONE, //允许程序从非系统拨号器里输入电话号码
            Manifest.permission.READ_PHONE_STATE //访问电话状态
    };

    private static InterSightApp app;

    public static InterSightApp getApp() {
        return app;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信
        PlatformConfig.setWeixin(Constants.WX_APP_ID, Constants.WX_APP_SECRET);
        //新浪微博
//        PlatformConfig.setSinaWeibo("1374619301", "9a98812343eae184440afb67e45477e7");
        PlatformConfig.setSinaWeibo("2917336160", "16d6ccf2d1d5c97266408741e07434dc"); //新key
        //QQ
        PlatformConfig.setQQZone("100998019", "9a77e1afb11032783b02767ed4583874");
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        refWatcher = LeakCanary.install(this);
        app = this;
        init();
    }

    /**
     * Intialize the request manager and the image cache
     */
    private void init() {

        //友盟统计
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setCatchUncaughtExceptions(false);

        //Bugly 异常统计
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppChannel(CommonUtils.getUmengChannel());
        strategy.setAppReportDelay(60);
        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, false);

        //Glide 图片加载
        Glide.get(this)
                .register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new OkHttpClient()));

    }


    public boolean isUserLogin() {
        return isUserLogin;
    }

    public void setUserLogin(boolean userLogin) {
        isUserLogin = userLogin;
    }

    public boolean isBinding() {
        return isBinding;
    }

    public void setBinding(boolean binding) {
        isBinding = binding;
    }

    /**
     * 权限检测器
     *
     * @return
     */
    public PermissionsChecker getPermissionsChecker() {
        if (null == mPermissionsChecker) {
            mPermissionsChecker = new PermissionsChecker(getApplicationContext());
        }
        return mPermissionsChecker;
    }
}
