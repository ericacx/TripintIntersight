package com.tripint.intersight.helper;

import android.content.Context;

import com.tripint.intersight.widget.progress.ProgressDialogHandler;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by liukun on 16/3/10.
 */
public class ProgressDialogUtils {

    private final String TAG = "Tripint_Intersight";
    private ProgressDialogHandler mProgressDialogHandler;

    private static ProgressDialogUtils instants;

    private Context context;


    public static ProgressDialogUtils getInstants(Context context) {
        if (instants == null) {
            instants = new ProgressDialogUtils(context);
        }
        return instants;
    }

    public ProgressDialogUtils(Context context) {
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    public void show() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    public void dismiss() {
        dismissProgressDialog();
    }


}