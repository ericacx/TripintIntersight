package com.tripint.intersight.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.entity.payment.AliPayResponseEntity;
import com.tripint.intersight.helper.demo.AuthResult;
import com.tripint.intersight.helper.demo.PayResult;
import com.tripint.intersight.helper.demo.SignUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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

    // 商户PID
    public static final String PARTNER = "2088421845801566";
    // 商户收款账号
    public static final String SELLER = "info6@pushr.biz";

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
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mActivity, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT).show();

                        }
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
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void pay(AliPayResponseEntity entity) {
        if (entity == null || TextUtils.isEmpty(entity.getOrderString())) {
            new AlertDialog.Builder(mActivity).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //

                        }
                    }).show();
            return;
        }

        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
//        String sign = sign(orderInfo);
//        String sign = entity.getOrderString();
//        try {
//            /**
//             * 仅需对sign 做URL编码
//             */
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
//        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        final String payInfo = entity.getOrderString();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(mActivity);
        String version = payTask.getVersion();
        Toast.makeText(mActivity, version, Toast.LENGTH_SHORT).show();
    }

//    /**
//     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
//     *
//     * @param v
//     */
//    public void h5Pay(View v) {
//        Intent intent = new Intent(this, H5PayDemoActivity.class);
//        Bundle extras = new Bundle();
//        /**
//         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
//         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
//         * 商户可以根据自己的需求来实现
//         */
//        String url = "http://m.taobao.com";
//        // url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
//        extras.putString("url", url);
//        intent.putExtras(extras);
//        startActivity(intent);
//    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
