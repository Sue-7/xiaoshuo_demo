package com.xiaoshuo.business.api.association.util.calculator;

import java.math.RoundingMode;

/**
 * 计算结果返回
 *
 * @author liyuanyuan
 */
public class CalResponse {

    /**
     * 结果
     */
    private CalBigDecimal result;

    private CalResponse(CalBigDecimal result) {
        this.result = result;
    }

    public static CalResponse build(CalBigDecimal result) {
        if (result == null) {
            result = CalBigDecimal.ZERO;
        }
        return new CalResponse(result);
    }

    public CalResponse stScale(int newScale) {
        this.result.setNewScale(newScale);
        return this;
    }

    public CalResponse stCalNegate(CalNegate calNegate) {
        this.result.setCalNegate(calNegate);
        return this;
    }

    public CalResponse stRoundingMode(RoundingMode roundingMode) {
        this.result.setRoundingMode(roundingMode);
        return this;
    }

    public <T extends Number> T getResultNumber(Class<T> clazz) {
        CalBigDecimal tempValue = result;
        if (tempValue == null) {
            tempValue = CalBigDecimal.ZERO;
        }
        return tempValue.getValue(clazz);
    }
}