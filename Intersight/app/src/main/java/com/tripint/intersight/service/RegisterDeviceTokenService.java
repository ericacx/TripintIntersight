package com.tripint.intersight.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.Constants;
import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;
import com.tripint.intersight.common.utils.StringUtils;
import com.tripint.intersight.entity.user.UserDeviceTokenResponseEntity;
import com.tripint.intersight.widget.subscribers.PageDataSubscriberOnNext;
import com.tripint.intersight.widget.subscribers.ServiceProgressSubscriber;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * 作者：wangdakuan
 * 主要功能:后台服务（现在用于自动登录）
 * 创建时间：2015/10/15 18:20
 */
public class RegisterDeviceTokenService extends Service {

    protected InterSightApp app;

    private PageDataSubscriberOnNext<UserDeviceTokenResponseEntity> subscriber;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = InterSightApp.getApp();
        subscriber = new PageDataSubscriberOnNext<UserDeviceTokenResponseEntity>() {
            @Override
            public void onNext(UserDeviceTokenResponseEntity entity) {
                //接口请求成功后处理
//                data = entity;
                Log.d(Constants.TAG, String.valueOf(entity.getFlg()));
                stopSelf();
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerUserDeviceToken();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 用户登录
     */
    private void registerUserDeviceToken() {
        String deviceToken = ACache.get(this).getAsString(EnumKey.User.UMENG_DEVICE_TOKEN);
        if (StringUtils.isEmpty(deviceToken)) {
            stopSelf();
        } else {
            BaseDataHttpRequest.getInstance(this).registerUserDeviceToken(
                    new ServiceProgressSubscriber<UserDeviceTokenResponseEntity>(subscriber, this), deviceToken, 1);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
