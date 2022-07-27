package com.xiaoshuotech.minmetals.association.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Description: 算法测试
 * @Package xiaoshuotech.minmetals.deal.api
 * @Author Sue
 * @Datetime 2022/5/9 21:31
 * @Version 1.0.0
 */
@Slf4j
public class LeeCodeTest {


    @Test
    public void main() {
//        //回文数字
//        int param = 1221;
//        boolean result = this.isPalindrome(param);

        //罗马数字转数字
        String param = "IV";
        int result = romanToInt(param);

        log.info("输入：{}，输出：{}", param, result);
    }

    /**
     * 回文数字
     * 给定一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        int surplus = x;
        int result = 0;
        while (surplus >= 1) {
            result = result * 10 + surplus % 10;
            surplus = surplus / 10;
        }
        return result == x;
//        String reverseX = new StringBuffer(String.valueOf(x)).reverse().toString();//方法1
//        String reverseX = this.reverse(String.valueOf(x));//方法2
//        return reverseX.equals(String.valueOf(x));
    }

    /**
     * 罗马数字转整数
     *
     * @param s
     * @return
     */
    public int romanToInt(String s) {

        int result = 0;
        char temp = ' ';
        for (char item : s.toCharArray()) {
            if (item == 'I') {
                result = result + 1;
                temp = item;
            } else if (item == 'V') {
                if (temp == 'I') {
                    result = result + 3;
                    temp = item;
                } else {
                    result = result + 5;
                    temp = item;
                }
            } else if (item == 'X') {
                if (temp == 'I') {
                    result = result + 8;
                    temp = item;
                } else {
                    result = result + 10;
                    temp = item;
                }
            } else if (item == 'L') {
                if (temp == 'X') {
                    result = result + 30;
                    temp = item;
                } else {
                    result = result + 50;
                    temp = item;
                }
            } else if (item == 'C') {
                if (temp == 'X') {
                    result = result + 80;
                    temp = item;
                } else {
                    result = result + 100;
                    temp = item;
                }
            } else if (item == 'D') {
                if (temp == 'C') {
                    result = result + 300;
                    temp = item;
                } else {
                    result = result + 500;
                    temp = item;
                }
            } else if (item == 'M') {
                if (temp == 'C') {
                    result = result + 800;
                    temp = item;
                } else {
                    result = result + 100;
                    temp = item;
                }
            } else {
                log.warn("errorFunc");
                break;
            }
        }

        return result;
    }

    /**
     * 字符串取反
     *
     * @param s
     * @return
     */
    private String reverse(String s) {
        int len = s.length();
        if (len <= 1) {
            return s;
        }
        String left = s.substring(0, len / 2);
        String right = s.substring(len / 2, len);
        return reverse(right) + reverse(left);
    }
}
