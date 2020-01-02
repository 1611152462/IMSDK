package com.ecareyun.cloudpos.framwork.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiezheng on 2018/7/10.
 */

public class QRCodeUtil {

    /**
     * 判断是否为会员二维码
     *
     * @param codeString
     * @return
     */
    public static boolean isVipQRCode(String codeString) {
        if (codeString.startsWith("{")) return true;
        if (codeString.startsWith("$MC:")) return true;
        return false;
    }

    /**
     * 从二维码种取出会员码
     *
     * @param codeString
     * @return
     */
    public static String getVipCode(String codeString) {
        if (codeString.startsWith("{")) {
            String[] split = codeString.split("\\|");
            return split[4];
        }
//        if (codeString.startsWith("$MC:")) {
//            return codeString;
//        }
        return codeString;
    }

    /**
     * 判断是否为微信支付码
     *
     * @param codeString 18位纯数字，以10、11、12、13、14、15开头
     * @return
     */
    public static boolean isWxPayCode(String codeString) {
        String pattern = "^1[0-5]\\d{16}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(codeString);
        return m.matches();
    }

    /**
     * 判断是否为支付宝支付码
     *
     * @param codeString 25、26、27、28、29、30开头,长度为16~24位的数字
     * @return
     */
    public static boolean isAliPayCode(String codeString) {
        String pattern = "^(2[5-9]|30)\\d{14,22}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(codeString);
        return m.matches();
    }

    /**
     * 判断是否为支付码
     *
     * @param codeString
     * @return
     */
    public static boolean isPayCode(String codeString) {
        return isWxPayCode(codeString) || isAliPayCode(codeString);
    }
}
