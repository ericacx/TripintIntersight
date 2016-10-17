package com.tripint.intersight.fragment.mine.photo.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by hanj on 14-10-14.
 */
public class Utility {

    /**
     * 判断SD卡是否可用
     */
    public static boolean isSDcardOK() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡跟路径。SD卡不可用时，返回null
     */
    public static String getSDcardRoot() {
        if (isSDcardOK()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        return null;
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int msgId) {
        Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取字符串中某个字符串出现的次数。
     */
    public static int countMatches(String res, String findString) {
        if (res == null) {
            return 0;
        }

        if (findString == null || findString.length() == 0) {
            throw new IllegalArgumentException("The param findString cannot be null or 0 length.");
        }

        return (res.length() - res.replace(findString, "").length()) / findString.length();
    }

    /**
     * 判断该文件是否是一个图片。
     */
    public static boolean isImage(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }

    /**
     * 获取目录中图片的个数。
     */
    private int getImageCount(File folder) {
        int count = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            if (Utility.isImage(file.getName())) {
                count++;
            }
        }

        return count;
    }

    /**
     * 获取目录中最新的一张图片的绝对路径。
     */
    private String getFirstImagePath(File folder) {
        File[] files = folder.listFiles();
        for (int i = files.length - 1; i >= 0; i--) {
            File file = files[i];
            if (Utility.isImage(file.getName())) {
                return file.getAbsolutePath();
            }
        }

        return null;
    }
}
