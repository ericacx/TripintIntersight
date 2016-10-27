package com.tripint.intersight.common;

/**
 * Created by lirichen on 16/3/10.
 */
public class ApiException extends RuntimeException {

    public static final int CHOOSE_ROLE = 100;
    public static final int CHOOSE_TRADE = 101;
    public static final int INTER_MAIN = 102;
    public static final int INPUT_REEOR = 406;
    public static final int USER_ROLE_EXIST = 4001;
    public static final int SEND_CODE_REEOR = 4008;
    public static final int INPUT_EMAIL_REEOR = 4006;
    public static final int REGISTER_USER_EXIST = 4009;



    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case CHOOSE_ROLE://100
                message = "请选择角色";
                break;
            case CHOOSE_TRADE://101
                message = "选择感兴趣的行业";
                break;
            case INTER_MAIN://102
                message = "进入主页";
                break;
            case REGISTER_USER_EXIST://4009
                message = "用户已经注册";
                break;
            case INPUT_REEOR://406
                message = "手机号或者密码输入有误";
                break;
            case USER_ROLE_EXIST:
                message = "角色设置失败";
                break;
            case SEND_CODE_REEOR://4008
                message = "验证码发送失败";
                break;
            case INPUT_EMAIL_REEOR://4006
                message = "验证码输入有误";
                break;
            default:
                message = "未知错误";
                break;
        }
        return message;
    }
}

