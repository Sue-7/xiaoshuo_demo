package com.xiaoshuo.business.api.association.util.calculator;

import java.math.RoundingMode;

/**
 * 计算器接口
 *
 * @author liyuanyuan
 */
public interface ICalculate {

    /**
     * 计算
     *
     * @param request      计算请求
     * @param scale        结果精度
     * @param roundingMode 结果舍取方案
     * @param calNegate    结果正负
     * @return
     */
    CalResponse compute(CalRequest request, int scale, RoundingMode roundingMode, CalNegate calNegate);

}