//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.xiaoshuo.business.api.association.util;

import com.xiaoshuotech.cloud.core.constant.StringPool;
import com.xiaoshuotech.cloud.core.util.Func;
import org.apache.commons.lang3.StringUtils;

/**
 * @author heng.yang
 * @date 2021/04/22
 */
public class ConvertUpMoneyUtil {
    private static final String[] NUMBERS = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] IUNIT = new String[]{"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"};
    private static final String[] DUNIT = new String[]{"角", "分", "厘"};
    private static final String STR_MATCHS = "(-)?[\\d]*(.)?[\\d]*";
    private static final String ZERO = "0";
    private static final String TWO_ZERO = "0.00";
    private static final String THREE_ZERO = "0.0";
    private static final Integer THREE = 3;
    private static final Integer FOUR = 4;
    private static final Integer EIGHT = 8;

    public ConvertUpMoneyUtil() {
    }

    public static String toChinese(String str) {
        if (!StringUtils.isBlank(str) && str.matches(STR_MATCHS)) {
            if (!ZERO.equals(str) && !TWO_ZERO.equals(str) && !THREE_ZERO.equals(str)) {
                boolean flag = false;
                if (str.startsWith(StringPool.DASH)) {
                    flag = true;
                    str = str.replaceAll(StringPool.DASH, "");
                }

                str = str.replaceAll(StringPool.COMMA, "");
                String integerStr;
                String decimalStr;
                if (str.indexOf(StringPool.DOT) > 0) {
                    integerStr = str.substring(0, str.indexOf("."));
                    decimalStr = str.substring(str.indexOf(".") + 1);
                } else if (str.indexOf(StringPool.DOT) == 0) {
                    integerStr = "";
                    decimalStr = str.substring(1);
                } else {
                    integerStr = str;
                    decimalStr = "";
                }

                if (integerStr.length() > IUNIT.length) {
                    System.out.println(str + "：超出计算能力");
                    return str;
                } else {
                    int[] integers = toIntArray(integerStr);
                    if (integers.length > 1 && integers[0] == 0) {
                        System.out.println("抱歉，请输入数字！");
                        if (flag) {
                            str = "-" + str;
                        }

                        return str;
                    } else {
                        boolean isWan = isWan5(integerStr);
                        int[] decimals = toIntArray(decimalStr);
                        String integersResult = getChineseInteger(integers, isWan);
                        String decimalsResult = getChineseDecimal(decimals);
                        if (Func.isEmpty(decimalsResult)) {
                            decimalsResult = "整";
                        }

                        String result = integersResult + decimalsResult;
                        return flag ? "负" + result : result;
                    }
                }
            } else {
                return "零元";
            }
        } else {
            System.out.println("抱歉，请输入数字！");
            return str;
        }
    }

    private static int[] toIntArray(String number) {
        int[] array = new int[number.length()];

        for (int i = 0; i < number.length(); ++i) {
            array[i] = Integer.parseInt(number.substring(i, i + 1));
        }

        return array;
    }

    public static String getChineseInteger(int[] integers, boolean isWan) {
        StringBuffer chineseInteger = new StringBuffer("");
        int length = integers.length;
        if (length == 1 && integers[0] == 0) {
            return "";
        } else {
            for (int i = 0; i < length; ++i) {
                String key = "";
                if (integers[i] == 0) {
                    if (length - i == 13) {
                        key = IUNIT[4];
                    } else if (length - i == 9) {
                        key = IUNIT[8];
                    } else if (length - i == 5 && isWan) {
                        key = IUNIT[4];
                    } else if (length - i == 1) {
                        key = IUNIT[0];
                    }

                    if (length - i > 1 && integers[i + 1] != 0) {
                        key = key + NUMBERS[0];
                    }
                }

                chineseInteger.append(integers[i] == 0 ? key : NUMBERS[integers[i]] + IUNIT[length - i - 1]);
            }

            return chineseInteger.toString();
        }
    }

    private static String getChineseDecimal(int[] decimals) {
        StringBuffer chineseDecimal = new StringBuffer("");

        for (int i = 0; i < decimals.length && i != THREE; ++i) {
            chineseDecimal.append(decimals[i] == 0 ? "" : NUMBERS[decimals[i]] + DUNIT[i]);
        }

        return chineseDecimal.toString();
    }

    private static boolean isWan5(String integerStr) {
        int length = integerStr.length();
        if (length > FOUR) {
            String subInteger = "";
            if (length > EIGHT) {
                subInteger = integerStr.substring(length - 8, length - 4);
            } else {
                subInteger = integerStr.substring(0, length - 4);
            }

            return Integer.parseInt(subInteger) > 0;
        } else {
            return false;
        }
    }
}

