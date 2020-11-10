package com.jingpai.pos.customer.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Create by liujinheng
 * date 2020/6/4
 * function
 */
public class MathUtil {
    public static float add(double num1,double num2){
        //一定要使用BigDecimal类中参数类型为String的构造函数
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        BigDecimal interest = b1.add(b2); //触

        //向上四舍五入 number 位小数
        BigDecimal setScale = interest.setScale(2,BigDecimal.ROUND_HALF_UP);
        return setScale.floatValue();
    }

    public static String format00(double num1){
        DecimalFormat df = new DecimalFormat("0.00#");
        return df.format(num1);
    }

    /**
     * 数组中随机获取几个不同的值的index
     * @param n
     * @param strList
     * exceptIndex 排除某个
     * @return
     */
    public static List<Integer> getRandomArray(int n, List strList) {
        return getRandomArray(n,-1,strList);
    }

    public static List<Integer> getRandomArray(int n,int exceptIndex, List strList) {
        if (n > strList.size()||exceptIndex>strList.size()) { return null; }
        List<Integer> list = new ArrayList<Integer>();
        list.addAll(strList);

        Random random = new Random();
        // 当取出的元素个数大于数组的长度时，返回null
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // 去一个随机数，随机范围是list的长度
            int index = random.nextInt(list.size());
            while (result.contains(index)||index == exceptIndex){
                index = random.nextInt(list.size());
            }
            result.add(index);
        }
        return result;
    }

}
