package com.tripint.intersight.app;

import android.Manifest;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.tencent.bugly.crashreport.CrashReport;
import com.tripint.intersight.R;
import com.tripint.intersight.common.Constants;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.helper.CommonUtils;
import com.tripint.intersight.helper.PermissionsChecker;
import com.umeng.analytics.MobclickAgent;
import com.umeng.common.UmLog;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
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
        MultiDex.install(this);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信
//        PlatformConfig.setWeixin(Constants.WX_APP_ID, Constants.WX_APP_SECRET);
        PlatformConfig.setWeixin("wxc179ce907846f2ab", "dc92c186ac3285cafbb0647f8cbc6b66");

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
        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                UmLog.i("Intersight", "device token: " + deviceToken);
//                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                UmLog.i("Intersight", "error: " + s);

            }
        });
        app = this;
        init();
    }

    /**
     * Intialize the request manager and the image cache
     */
    private void init() {


        initUmengAgent();
//        initUmengMessage();
        initBugly();

        //Glide 图片加载
        Glide.get(this)
                .register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new OkHttpClient()));

    }


    private void initUmengMessage() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // sdk关闭通知声音
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
//		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

//		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);


        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知
        //参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);


        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                UmLog.i("Intersight", "device token: " + deviceToken);
//                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                UmLog.i("Intersight", "error: " + s);

            }
        });
    }

    private void initUmengAgent() {

        //友盟统计
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setCatchUncaughtExceptions(false);

    }

    private void initBugly() {

        //Bugly 异常统计
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppChannel(CommonUtils.getUmengChannel());
        strategy.setAppReportDelay(60);
        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, false);

    }


    /**
     * @return true 已登录  false 未登录
     */
    public boolean isUserLogin() {
        String token = ACache.get(this).getAsString(EnumKey.User.USER_TOKEN);
        return !StringUtils.isEmpty(token);
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
