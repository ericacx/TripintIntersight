package com.tripint.intersight.common.enumkey;

/**
 * 作者：
 * 主要功能:用于注册全局监听的key，传值的对应的KEY
 * 创建时间：2016/1/4 16:46
 */
public class EnumKey {


    /**
     * 用于应用ACache缓存KEY
     */
    public class User {
        public static final String USER_TOKEN = "user_token"; //
        public static final String UMENG_DEVICE_TOKEN = "umeng_device_token";

    }

    /**
     * 用于应用ACache缓存KEY
     */
    public class UmengAliasType {

        public static final String OFFICIAL = "TRIPINT"; //

    }

    /**
     * 用于应用ACache缓存KEY
     */
    public class ACacheKey {
        public static final String BUREAU_CITY = "bureauCity"; //当前城市
        public static final String LOGIN_INFO = "LoginInfo"; //保存登录账号信息KEY
        public static final String NO_MEMBER_LOGIN_INFO = "noMemberLoginInfo"; //非会员登录
        public static final String SCREENSHOTS_UNDER = "screenshots_under"; //截屏 下部分
        public static final String SCREENSHOTS_NO = "screenshots_no"; //截屏 上部分
        public static final String HISTORY_CACHE_CITY = "history_cache_city"; //搜索条件缓存
        public static final String KEYWORD_CACHE = "keyword_cache"; //搜索关键词缓存
        public static final String KEYWORD_APP_LAUNCHED = "keyword_app_launched"; //APP打开过

        public static final String USER_NAME = "user_name";
        public static final String USER_COMPANY = "user_company";
        public static final String USER_PHONE = "user_phone";
        public static final String USER_EMAIL = "user_email";

    }
}
