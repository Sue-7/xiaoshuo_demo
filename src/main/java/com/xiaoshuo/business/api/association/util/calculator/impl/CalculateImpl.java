package com.xiaoshuo.business.api.association.util.calculator.impl;


import com.xiaoshuotech.minmetals.association.api.util.calculator.*;

import java.math.RoundingMode;

/**
 * 计算器接口
 *
 * @author liyuanyuan
 */
public class CalculateImpl implements ICalculate {

    @Override
    public CalResponse compute(CalRequest request, int scale, RoundingMode roundingMode, CalNegate calNegate) {
        CalDag calDag = request.getDag();
        if (calDag == null) {
            throw new RuntimeException("没有构建计算图,无法计算");
        }
        if (scale < 0) {
            throw new RuntimeException("数据结果精度设置异常,精度:" + scale);
        }
        CalBigDecimal result = calDag.executeDag().getResult();
        return CalResponse.build(result).stScale(scale).stRoundingMode(roundingMode).stCalNegate(calNegate);
    }
}