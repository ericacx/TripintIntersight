package com.tripint.intersight.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.helper.demo.AuthResult;
import com.tripint.intersight.helper.demo.PayResult;
import com.tripint.intersight.helper.demo.util.OrderInfoUtil2_0;

import java.util.Map;

/**
 * 重要说明:
 * <p>
 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class AliPayUtils {

    static AliPayUtils instant;
    private final Activity mActivity;

    private AliPayUtils(Activity activity) {
        this.mActivity = activity;
    }

    public static AliPayUtils getInstant(Activity activity) {

        if (instant == null) {
            instant = new AliPayUtils(activity);
        }
        return instant;

    }

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016092101937846";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /**
     * 商户私钥，pkcs8格式
     */
//	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKw6DM63f3Tb95Z0BufUSAJ8jQOf2yhuXLjaBDcRPYgq1eEAcGUaYG2hk+sMUNLFIj3a33oCrhWZKsdllbJ6p/+LqzT4DFtEzP1MH1KKvFHzLLtiOzqIziXI55/a7l8KGUTNDwPigrmZ9NXzjZNNzn0KDoCg7ldivATzQ53h+X1BAgMBAAECgYBlvDC+l4RxylI5jLZbkXksBtjhsDcsbezVwOtGgCeh3PPUYocCIg+eExkmenLv3kU41qa2Ewk0dvLfMHG6KVw8zqCohdSg9Wzf6S3Z36lmO+rSAooB/qm1k4kNqgrfcRJ/PESupbw8PYsKxdifXzCYIuLrR6D3fUeeW4CrZMPsQJBAN1XwCusLYEfOt4H3UPg5kG1ytKIedxkJg1dR//OmUo2gXSqyCC95S0z9F+qZYhnKupHOsBcdG25uwJ63zY+Q+0CQQDHMYuEPh4oazFzj9ztsgOL6bj/BXHeRsK/+/yblFrDFMpOl0ykO1jMwA2/zJRBsFpjWSEXtmOcpjW3EJJrwtwlAkBH9wvoJyb0YG8HWY87TpgOrUiwgub8HSOyHK4YIdf9JROaFxzSaGtm8wl1QTWZz9FIMriLaoQAWO7Qs5p3TG9RAkAkpi7/Q9aUpTRSCNQjP697XKNW+I980BQg8qIFlgQlBHw8fYXyaaDq+yMMeP2GIIZg5RM7o6ksN0CG0BvAi/epAkEAtpUcdsjv+IYNWViqh57W9lnvrnz0vpQfkxJncN7wrfIp2pvearIv6ahLcmqkvSDrEVZKKpUqXziKWmCP2n/zBQ==";
//	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANGIB6Py2bc+aQr/bvWtdVDuewrbqx81GPgaEcUaAdUHSB4JzELvxQ5TOGW124fAOefsd5uQr6qxCVNmpvTB4BpD1cqcxlClfNulSIIy4hf/yzAsIq/jKYEqRRbMPqurgdwLTDMYHTp7YY532uZNUov81ru3Zqp1T6mgPhtXamw3AgMBAAECgYEArQHV1TBzRhOZJkSM+PwVbxLKs8d3fxgXmtvjqkx0ENKnYG4Uo3xYyCA84/aJ4of9CBu/Essf0R/GpH+RAW2HvIlRwajk9TMgpmeJ2eUJfC5mRlGqkoAp3QDTfDy5UyARMH1L1USu9aNrulG2p6nSR4EwI1a1zK19Muj0ynWhfXECQQDxjKLcplNpjuqvyPOTOLpth4Ke5A16/4g8PP4nqArnL8Jy3X7NBcDI3PLM8vnKqB4eOwN12YtWze2qFuukTEh/AkEA3hEIjV/SbdX3sd6j5027miJjaj3fN+cmAblLWhDGOBZulmHoztXICcupbDyTZK/JpUqFlueJGlNGhbxKf3ZASQJBAOv681kRfUgRAaJ+dcgMkcVTr/6+2ZSidFw1Ui838n7tn9BMKAbwaelSQjqL3hFosRPjn3vQ7ln9kQSso8W9aM0CQBqTSUW5+PHiEGF99nmA6PmaMfGN8/+5rue36BegOEiQDq36i6TtGqJpeB4W5cC/9M7OK0yS6up2mtL4ZKX6XdkCQQC90A+1hDyhXWjiWBHrJHORsR+3PQWYtpwWGxnUdRNIOk4mulXrbDoCzosHZePJi5c1drQOyQW8Cw0BV9R6+dLo";


//	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKw6DM63f3Tb95Z0BufUSAJ8jQOf2yhuXLjaBDcRPYgq1eEAcGUaYG2hk+sMUNLFIj3a33oCrhWZKsdllbJ6p/+LqzT4DFtEzP1MH1KKvFHzLLtiOzqIziXI55/a7l8KGUTNDwPigrmZ9NXzjZNNzn0KDoCg7ldivATzQ53h+X1BAgMBAAECgYBlvDC+l4RxylI5jLZbkXksBtjhsDcsbezVwOtGgCeh3PPUYocCIg+eExkmenLv3kU41qa2Ewk0dvLfMHG6KVw8zqCohdSg9Wzf6S3Z36lm/O+rSAooB/qm1k4kNqgrfcRJ/PESupbw8PYsKxdifXzCYIuLrR6D3fUeeW4CrZMPsQJBAN1XwCusLYEfOt4H3UPg5kG1ytKIedxkJg1dR//OmUo2gXSqyCC95S0z9F+qZYhnKupHOsBcdG25uwJ63zY+Q+0CQQDHMYuEPh4oazFzj9ztsgOL6bj/BXHeRsK/+/yblFrDFMpOl0ykO1jMwA2/zJRBsFpjWSEXtmOcpjW3EJJrwtwlAkBH9wvoJyb0YG8HWY87TpgOrUiwgub8HSOyHK4YIdf9JROaFxzSaGtm8wl1QTWZz9FIMriLaoQAWO7Qs5p3TG9RAkAkpi7/Q9aUpTRSCNQjP697XKNW+I980BQg8qIFlgQlBHw8fYXyaaDq+yMMeP2GIIZg5RM7o6ksN0CG0BvAi/epAkEAtpUcdsjv+IYNWViqh57W9lnvrnz0vpQfkxJncN7wrfIp2pvearIv6ahLcmqkvSDrEVZKKpUqXziKWmCP2n/zBQ==";

    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKw6DM63f3Tb95Z0" +
            "BufUSAJ8jQOf2yhuXLjaBDcRPYgq1eEAcGUaYG2hk+sMUNLFIj3a33oCrhWZKsdl" +
            "lbJ6p/+LqzT4DFtEzP1MH1KKvFHzLLtiOzqIziXI55/a7l8KGUTNDwPigrmZ9NXz" +
            "jZNNzn0KDoCg7ldivATzQ53h+X1BAgMBAAECgYBlvDC+l4RxylI5jLZbkXksBtjh" +
            "sDcsbezVwOtGgCeh3PPUYocCIg+eExkmenLv3kU41qa2Ewk0dvLfMHG6KVw8zqCo" +
            "hdSg9Wzf6S3Z36lm/O+rSAooB/qm1k4kNqgrfcRJ/PESupbw8PYsKxdifXzCYIuL" +
            "rR6D3fUeeW4CrZMPsQJBAN1XwCusLYEfOt4H3UPg5kG1ytKIedxkJg1dR//OmUo2" +
            "gXSqyCC95S0z9F+qZYhnKupHOsBcdG25uwJ63zY+Q+0CQQDHMYuEPh4oazFzj9zt" +
            "sgOL6bj/BXHeRsK/+/yblFrDFMpOl0ykO1jMwA2/zJRBsFpjWSEXtmOcpjW3EJJr" +
            "wtwlAkBH9wvoJyb0YG8HWY87TpgOrUiwgub8HSOyHK4YIdf9JROaFxzSaGtm8wl1" +
            "QTWZz9FIMriLaoQAWO7Qs5p3TG9RAkAkpi7/Q9aUpTRSCNQjP697XKNW+I980BQg" +
            "8qIFlgQlBHw8fYXyaaDq+yMMeP2GIIZg5RM7o6ksN0CG0BvAi/epAkEAtpUcdsjv" +
            "+IYNWViqh57W9lnvrnz0vpQfkxJncN7wrfIp2pvearIv6ahLcmqkvSDrEVZKKpUq" +
            "XziKWmCP2n/zBQ==";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(InterSightApp.getApp(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(InterSightApp.getApp(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(InterSightApp.getApp(),
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(InterSightApp.getApp(),
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    /**
     * 支付宝支付业务
     */
    public void payV2() {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(mActivity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //

                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        try {
            String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
            final String orderInfo = orderParam + "&" + sign;

            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    PayTask alipay = new PayTask(mActivity);
                    Map<String, String> result = alipay.payV2(orderInfo, true);
                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);

                }
            };


            Thread payThread = new Thread(payRunnable);
            payThread.start();
        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
    }

    /**
     * 支付宝账户授权业务
     *
     * @param v
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(InterSightApp.getApp()).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, RSA_PRIVATE);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(mActivity);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(mActivity);
        String version = payTask.getVersion();
        Toast.makeText(InterSightApp.getApp(), version, Toast.LENGTH_SHORT).show();
    }

//	/**
//	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
//	 * 
//	 * @param v
//	 */
//	public void h5Pay(View v) {
//		Intent intent = new Intent(InterSightApp.getApp(), H5PayDemoActivity.class);
//		Bundle extras = new Bundle();
//		/**
//		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
//		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
//		 * 商户可以根据自己的需求来实现
//		 */
//		String url = "http://m.taobao.com";
//		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
//		extras.putString("url", url);
//		intent.putExtras(extras);
//		startActivity(intent);
//	}

}
