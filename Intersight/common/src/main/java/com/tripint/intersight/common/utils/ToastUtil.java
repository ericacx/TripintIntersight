package com.tripint.intersight.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Eric on 16/9/5.
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showToast(Context context,String content){
        if (mToast == null){
            mToast = Toast.makeText(context, content,Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }
}
