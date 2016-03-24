package com.cifpay.cifpaylib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kun on 2015/6/16.
 * 判断各种字符串规则合法性的类
 */
public class RuleUtils {


    /**
     * 检测是否为手机号
     *
     * @param mobileNO
     * @return boolean true or false
     */
    public static boolean isMobileNO(String mobileNO) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobileNO);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }


        return flag;
    }

    /**
     * 检测是否为邮箱格式
     *
     * @param email 字符串
     * @return boolean  true or false;
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            //  String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+com$";
            //            String check = "^[\\w[-]]?[\\w[.-]]*[\\w[-]]@[\\w[-]][\\w]*[\\w]?\\.[\\w]+$";
            //            String check = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
            String check = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 检测是否为URL格式
     *
     * @param url 字符串
     * @return boolean  true or false;
     */
    public static boolean isURL(String url) {
        boolean flag;
        try {
            String check = "^((https?|ftp)?:\\/\\/)?([\\da-z%\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.%\\?&=#\\+:@|-]*)*\\/?$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(url);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }
}
