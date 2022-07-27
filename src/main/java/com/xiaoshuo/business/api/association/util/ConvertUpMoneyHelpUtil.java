package com.xiaoshuo.business.api.association.util;

import com.google.common.collect.Maps;
import com.xiaoshuotech.minmetals.association.api.consts.Constant;

import java.util.Map;


/**
 * @author ：zhuchengle
 * @date ：Created in 2020/1/6 下午4:53
 * @description： 金额转汉字
 * @modified By：
 * @version: 1.0.0
 */
public class ConvertUpMoneyHelpUtil {
    /**
     * 金额key 最大到万亿
     */
    private static final String[] MONEY_KEY_ARRAY = new String[]{"cents", "tenCents", "yuan", "tenYuan", "hundred", "thousand", "tenThousand", "hundredThousand", "million", "tenMillion", "hundredMillion", "billion", "tenBillion", "hundredBillion", "trillion"};
    /**
     * 123元 装换成 321
     *
     * 将字符串转为int数组
     * @param number
     * @return
     */
    public static int[] toIntArrayFromMoney(String number) {
        int[] array = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            array[number.length() - i - 1] = Integer.parseInt(number.substring(i, i + 1));
        }
        return array;
    }

    /**
     * 金额转换成map
     *
     * 将字符串转为int数组
     * @param number
     * @param maxMoneyLength
     * @return
     */
    public static Map<String, String> formatMoneyMap(String number, int maxMoneyLength) {

        int[] moneyArray = toIntArrayFromMoney(number);
        Map<String, String> moneyMap = Maps.newLinkedHashMap();
        String moneyValue = "";
        for (int i = 0; i <= MONEY_KEY_ARRAY.length - 1; i++) {
            if(i <= moneyArray.length-1) {
                moneyValue = String.valueOf(moneyArray[i]);
            } else if(i == moneyArray.length){
                moneyValue = Constant.XIAOSHUOTECH_MONEY_RMB;
            } else {
                moneyValue = "";
            }
            moneyMap.put(MONEY_KEY_ARRAY[i], moneyValue);
        }
        return moneyMap;
    }

}
