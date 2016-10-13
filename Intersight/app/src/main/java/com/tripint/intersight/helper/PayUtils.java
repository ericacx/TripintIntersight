package com.tripint.intersight.helper;

import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tripint.intersight.app.InterSightApp;
import com.tripint.intersight.common.Constants;
import com.tripint.intersight.entity.payment.WXPayResponseEntity;

/**
 * Created by lirichen on 2016/9/28.
 */

public class PayUtils {

    private IWXAPI api;

    private final String TAG = "INTERSIGHT";

    static PayUtils instant;

    private PayUtils() {
        initIWXAPI();
    }

    public static PayUtils getInstant() {

        if (instant == null) {
            instant = new PayUtils();
        }
        return instant;

    }

    public void initIWXAPI() {
        api = WXAPIFactory.createWXAPI(InterSightApp.getApp(), Constants.WX_PAY_ID, false);
        api.registerApp(Constants.WX_PAY_ID);
    }


    public void requestWXpay(WXPayResponseEntity requestModel) {
        try {
            PayReq req = new PayReq();
            req.appId = requestModel.getAppid();
            req.partnerId = requestModel.getPartnerid();
            req.prepayId = requestModel.getPrepayId();
            req.nonceStr = requestModel.getNoncestr();
            req.timeStamp = requestModel.getTimestamp();
            req.packageValue = requestModel.getPackageName();
            req.sign = requestModel.getSign();
            ProgressDialogUtils.getInstants(InterSightApp.getApp()).show();
            api.sendReq(req);
        } catch (Exception e) {
            Log.e(TAG, "Exception :" + e.getMessage());

        }
    }
//
//    private void requestAliPay() {
//        // 订单
//        String orderInfoStr = getOrderInfo(productName, productName, totalFee);
//        // 对订单做RSA 签名
//        String sign = sign(orderInfoStr);
//        try {
//            // 仅需对sign 做URL编码
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            Log.d(TAG, "payByAlipayClient UnsupportedEncodingException:" + e.getMessage());
//        }
//        // 完整的符合支付宝参数规范的订单信息
//        final String payInfo = orderInfoStr + "&sign=\"" + sign + "\"&" + AlipayUtils.getSignType();
//        Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                // 构造PayTask 对象
//                PayTask alipay = new PayTask(InterSightApp.getApp());
//                // 调用支付接口，获取支付结果
//                String result = alipay.pay(payInfo);
//                Message msg = new Message();
//                msg.what = ALIPAY_SDK_PAY_FLAG;
//                msg.obj = result;
//                aliHandler.sendMessage(msg);
//            }
//        };
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//    private Handler aliHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case ALIPAY_SDK_PAY_FLAG: {
//                    PayResult payResult = new PayResult((String) msg.obj);
//                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        RxBus.get().post(EnumKey.Registered.APP_PAY, true);
//                        CommonUtils.showToast("支付成功");
//                    } else {
//                        // 判断resultStatus 为非“9000”则代表可能支付失败
//                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                        if (TextUtils.equals(resultStatus, "8000")) {
//                            CommonUtils.showToast("支付结果确认中");
//
//                        } else {
//                            RxBus.get().post(EnumKey.Registered.APP_PAY, false);
//                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            CommonUtils.showToast("支付失败");
//                        }
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
//
//        ;
//    };

//    /** 支付宝支付业务：入参app_id */
//    public static final String APPID = "";
//
//    /** 支付宝账户登录授权业务：入参pid值 */
//    public static final String PID = "";
//    /** 支付宝账户登录授权业务：入参target_id值 */
//    public static final String TARGET_ID = "";
//
//    /** 商户私钥，pkcs8格式 */
//    public static final String RSA_PRIVATE = "";
//
//    private static final int SDK_PAY_FLAG = 1;
//    private static final int SDK_AUTH_FLAG = 2;
//
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    /**
//                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(AliPayUtils.this, "支付成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(AliPayUtils.this, "支付失败", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                }
//                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(AliPayUtils.this,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
//                    } else {
//                        // 其他状态值则为授权失败
//                        Toast.makeText(AliPayUtils.this,
//                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
//
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        };
//    };


//    /**
//     * create the order info. 创建订单信息
//     */
//    public String getOrderInfo(String subject, String body, String price) {
//        // 签约合作者身份ID
//        String orderInfo = "partner=" + "\"" + PARTNER + "\"";
//        // 签约卖家支付宝账号
//        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
//        // 商户网站唯一订单号
////        orderInfo += "&out_trade_no=" + "\"" + AlipayUtils.getOutTradeNo() + "\"";
//        orderInfo += "&out_trade_no=" + "\"" + OutTradeNo + "\"";
//        // 商品名称
//        orderInfo += "&subject=" + "\"" + subject + "\"";
//        // 商品详情
//        orderInfo += "&body=" + "\"" + body + "\"";
//        // 商品金额
//        orderInfo += "&total_fee=" + "\"" + price + "\"";
//        // 服务器异步通知页面路径
//        orderInfo += "&notify_url=" + "\"" + NOTIFY_URL + "\"";
//        // 服务接口名称， 固定值
//        orderInfo += "&service=\"mobile.securitypay.pay\"";
//        // 支付类型， 固定值
//        orderInfo += "&payment_type=\"1\"";
//        // 参数编码， 固定值
//        orderInfo += "&_input_charset=\"utf-8\"";
//        // 设置未付款交易的超时时间
//        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
//        // 取值范围：1m～15d。
//        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
//        // 该参数数值不接受小数点，如1.5h，可转换为90m。
//        orderInfo += "&it_b_pay=\"30m\"";
//        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
//        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
//        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//        orderInfo += "&return_url=\"m.alipay.com\"";
//        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
//        // orderInfo += "&paymethod=\"expressGateway\"";
//
//        return orderInfo;
//    }

}
