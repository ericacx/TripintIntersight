package com.tripint.intersight.common;

/**
 * Created by lirichen on 16/3/10.
 */
public class ApiException extends RuntimeException {

    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;
    public static final int WRONG_PASSWORD2 = 102;


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
            case USER_NOT_EXIST:
                message = "请选择角色";
                break;
            case WRONG_PASSWORD:
                message = "选择感兴趣的行业";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}

