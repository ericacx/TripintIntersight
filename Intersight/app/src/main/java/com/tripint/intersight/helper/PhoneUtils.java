package com.tripint.intersight.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证手机号码
 * Created by Eric on 16/9/24.
 */

public class PhoneUtils {

    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        CharSequence inputStr = phoneNumber;
        //正则表达式

        String phone="^1[34578]\\d{9}$" ;


        Pattern pattern = Pattern.compile(phone);
        Matcher matcher = pattern.matcher(inputStr);


        if(matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public static boolean isPhone(String inputText) {
        Pattern p = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }
}
