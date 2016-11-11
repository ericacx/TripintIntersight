package com.tripint.intersight.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Eric on 16/4/13.
 */
public class PackageUtils {
    public static String getPackageVersion(Context context){
        String version = "1";
        //获取PackageManager
        PackageManager packageManager = context.getPackageManager();

        try {
            //获取PackageInfo
            PackageInfo packageInfo =
                    packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            //获取版本号
            version = packageInfo.versionCode + "";

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return  version;
    }
}
