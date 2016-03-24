package com.cifpay.cifpaylib.utils;

import java.math.BigDecimal;

/**
 * Created by ZBQ on 2015/12/1.
 * 人民币转化
 */
public class RMBUtil {
    /**
     * @param amount
     * @return
     */
    public static String toYuan(long amount) {
        BigDecimal b = new BigDecimal(amount);
        return b.movePointLeft(2).toString();
    }

    /**
     * @param amount
     * @return
     */
    public static long toFen(String amount) {
        BigDecimal b = new BigDecimal(amount);
        return b.movePointRight(2).longValue();
    }
}
