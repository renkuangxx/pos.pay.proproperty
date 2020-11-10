package com.jingpai.pos.customer.utils;

import java.math.BigDecimal;

/**
 * @author liuyang
 * @date 2020/1/18
 */
public class MoneyUtil {


    public static double fenToYuanDouble(int money) {
        return money / 100d;
    }

    public static int yuanToFen(double money) {
        BigDecimal bigDecimal = new BigDecimal(money);
        bigDecimal = bigDecimal.multiply(new BigDecimal(100));
        return bigDecimal.intValue();
    }

    /**
     * 元 转成  分
     *
     * @param yuan
     * @return
     */
    public static Integer yuanToFen(String yuan) {
        Integer fen = null;

        if (yuan != null && !yuan.isEmpty()) {
            BigDecimal bigDecimal = new BigDecimal(yuan);
            bigDecimal = bigDecimal.multiply(new BigDecimal(100));
            fen = bigDecimal.intValue();
        }

        return fen;
    }

    /**
     * 分  转成  元
     *
     * @param fen
     * @return
     */
    public static String fenToYuan(Integer fen) {
        return fenToYuan(fen, "0");
    }

    /**
     * 分  转成  元
     *
     * @param fen
     * @return
     */
    public static String fenToYuan(Integer fen, String defaultMoney) {
        String yuan = defaultMoney;
        if (fen != null && fen != 0) {
            BigDecimal bigDecimal = new BigDecimal(fen);
            bigDecimal = bigDecimal.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            yuan = bigDecimal.stripTrailingZeros().toPlainString();
        }
        return yuan;
    }

    public static String format(String yuan) {
        if (yuan != null) {
            yuan = fenToYuan(yuanToFen(yuan));
        }

        return yuan;
    }

    public static void main(String[] args) {
        System.out.println(fenToYuanDouble(256));
    }


}
