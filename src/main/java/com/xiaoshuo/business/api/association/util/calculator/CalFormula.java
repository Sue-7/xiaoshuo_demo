package com.xiaoshuo.business.api.association.util.calculator;

import com.xiaoshuo.business.api.association.util.calculator.impl.CalculateImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 计算公式
 *
 * @author liyuanyuan
 */
public class CalFormula {

    private final static Logger logger = LoggerFactory.getLogger(CalFormula.class);

    private final static String LOG_PRE = "【计算公式】";

    private static void infoLoger(String msg) {
        logger.info(LOG_PRE + msg);
    }

    /**
     * 计算器
     */
    private static ICalculate calculate = new CalculateImpl();

    /**
     * 计算利息 (基数*年利率/年天数*占用天数)
     *
     * @param baseAmount 基数   （剩余本金 /还款金额 ）
     * @param monthRate   年利率
     * @param monthDay    年天数
     * @param takeDay    占用天数
     * @return
     */
    public static BigDecimal calcInterest(BigDecimal baseAmount, BigDecimal monthRate, BigDecimal monthDay, BigDecimal takeDay) {
        infoLoger("计算,基数:" + baseAmount + ",月利率:" + monthRate + ",月天数:" + monthDay + ",占用天数:" + takeDay);
        BigDecimal result = null;
        if (baseAmount != null && monthRate != null && monthDay != null && takeDay != null) {
            try {
                //绘制计算公式
                CalDag calDag = CalDag
                        .build()
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(baseAmount)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(monthRate)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.DIVIDE))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(monthDay)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(takeDay)));
                if (monthRate.longValue() > 1) {
                    calDag.addEdge(CalEdge.build().stEdgeOpr(CalOpr.DIVIDE))
                            .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(new BigDecimal(100))));
                }
                CalRequest request = CalRequest.build(calDag);
                CalResponse response = calculate.compute(request, 8, RoundingMode.HALF_UP, CalNegate.ORIGINAL);
                result = response.getResultNumber(BigDecimal.class);
            } catch (Exception e) {
                logger.error("计息过程计算异常:", e);
            }
        }
        infoLoger("利息计算结果:" + result);
        return result;
    }

    /**
     * 逾利(单利) (基数*利率*倍率)
     *
     * @param baseAmount 基数   （剩余本金）
     * @param monthRate   利率
     * @param powerRate  倍率 (暂时无倍率)
     * @param monthDay    年天数
     * @param takeDay    占用天数
     * @return 逾利金额
     */
    public static BigDecimal calcSingleOverdue(BigDecimal baseAmount, BigDecimal monthRate, BigDecimal monthDay,
                                               BigDecimal takeDay, BigDecimal powerRate) {
        infoLoger("逾利(单)计算,基数:" + baseAmount + ",月利率:" + monthRate + ",月天数:" + monthDay
                + ",占用天数:" + takeDay + ",倍率:" + powerRate);
        BigDecimal result = null;
        if (baseAmount != null && monthRate != null) {
            try {
                // 绘制计算公式
                CalDag calDag = CalDag.build()
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(baseAmount)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(monthRate)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.DIVIDE))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(monthDay)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(takeDay)));
                if (monthRate.longValue() > 1) {
                    calDag.addEdge(CalEdge.build().stEdgeOpr(CalOpr.DIVIDE))
                            .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(new BigDecimal(100))));
                }
                CalRequest request = CalRequest.build(calDag);
                CalResponse response = calculate.compute(request, 8, RoundingMode.HALF_UP, CalNegate.ORIGINAL);
                result = response.getResultNumber(BigDecimal.class);
            } catch (Exception e) {
                logger.error("逾利(单)计算异常:", e);
            }
        }
        infoLoger("逾利(单)计算结果:" + result);
        return result;
    }

    /**
     * 逾利(复利) ((基数+剩余利息)*利率*倍率)
     *
     * @param baseAmount   基数   （剩余本金）
     * @param debtInterest 剩余利息
     * @param yearRate     利率
     * @param powerRate    倍率
     * @param takeDay      占用天数
     * @return
     */
    public static BigDecimal calcCompoundOverdue(BigDecimal baseAmount, BigDecimal debtInterest, BigDecimal yearRate, BigDecimal powerRate, BigDecimal takeDay) {
        infoLoger("逾利(复)计算,基数:" + baseAmount + ",剩余利息:" + debtInterest + ",利率:" + yearRate + ",倍率:" + powerRate + ",占用天数:" + takeDay);
        BigDecimal result = null;
        if (baseAmount != null && debtInterest != null && yearRate != null && powerRate != null) {
            try {
                //绘制计算公式
                CalDag calDag = CalDag
                        .build()
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(baseAmount)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.ADD))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(debtInterest)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(yearRate)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(powerRate)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(takeDay)));
                ;
                if (yearRate.longValue() > 1) {
                    calDag.addEdge(CalEdge.build().stEdgeOpr(CalOpr.DIVIDE))
                            .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(new BigDecimal(100))));
                }
                CalRequest request = CalRequest.build(calDag);
                CalResponse response = calculate.compute(request, 4, RoundingMode.HALF_UP, CalNegate.ORIGINAL);
                result = response.getResultNumber(BigDecimal.class);
            } catch (Exception e) {
                logger.error("逾利(复)计算异常:", e);
            }
        }
        infoLoger("逾利(复)计算结果:" + result);
        return result;
    }


    /**
     * 逾利(复利) 罚金  ((基数+剩余利息)*倍率)
     *
     * @param baseAmount   基数   （剩余本金）
     * @param debtInterest 剩余利息
     * @param powerRate    倍率
     * @return
     */
    public static BigDecimal calcCompoundOverdueForfeit(BigDecimal baseAmount, BigDecimal debtInterest, BigDecimal powerRate) {
        infoLoger("逾利(复)罚金计算,基数:" + baseAmount + ",剩余利息:" + debtInterest + ",倍率:" + powerRate);
        BigDecimal result = null;
        if (baseAmount != null && debtInterest != null && powerRate != null) {
            try {
                //绘制计算公式
                CalDag calDag = CalDag
                        .build()
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(baseAmount)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.ADD))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(debtInterest)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(powerRate)));
                CalRequest request = CalRequest.build(calDag);
                CalResponse response = calculate.compute(request, 4, RoundingMode.HALF_UP, CalNegate.ORIGINAL);
                result = response.getResultNumber(BigDecimal.class);
            } catch (Exception e) {
                logger.error("逾利(复)罚金计算异常:", e);
            }
        }
        infoLoger("逾利(复)罚金计算结果:" + result);
        return result;
    }


    /**
     * 计算保理利息 ((基数-保理费)*利率*占用天数)
     *
     * @param baseAmount      基数   （剩余本金）
     * @param factoringAmount 保理费
     * @param yearRate        利率
     * @param takeDay         占用天数
     * @return
     */
    public static BigDecimal calcFactoringInterest(BigDecimal baseAmount, BigDecimal factoringAmount, BigDecimal yearRate, BigDecimal takeDay) {
        infoLoger("保理利息计算,基数:" + baseAmount + ",保理费:" + factoringAmount + ",利率:" + yearRate + ",占用天数:" + takeDay);
        BigDecimal result = null;
        if (baseAmount != null && factoringAmount != null && yearRate != null && takeDay != null) {
            try {
                //绘制计算公式
                CalDag calDag = CalDag
                        .build()
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(baseAmount)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.SUBTRACT))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(factoringAmount)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(yearRate)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(takeDay)));
                if (yearRate.longValue() > 1) {
                    calDag.addEdge(CalEdge.build().stEdgeOpr(CalOpr.DIVIDE))
                            .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(new BigDecimal(100))));
                }
                CalRequest request = CalRequest.build(calDag);
                CalResponse response = calculate.compute(request, 4, RoundingMode.HALF_UP, CalNegate.ORIGINAL);
                result = response.getResultNumber(BigDecimal.class);
            } catch (Exception e) {
                logger.error("保理利息计算异常:", e);
            }
        }
        infoLoger("保理利息计算结果:" + result);
        return result;
    }

    /**
     * 计算保理罚息 ((基数-保理费)*利率*占用天数*倍率)
     *
     * @param baseAmount      基数   （剩余本金）
     * @param factoringAmount 保理费
     * @param yearRate        利率
     * @param powerRate       倍率
     * @param takeDay         占用天数
     * @return
     */
    public static BigDecimal calcFactoringOverdueInterest(BigDecimal baseAmount, BigDecimal factoringAmount, BigDecimal yearRate, BigDecimal powerRate, BigDecimal takeDay) {
        infoLoger("保理罚息计算,基数:" + baseAmount + ",保理费:" + factoringAmount + ",利率:" + yearRate + ",倍率:" + powerRate + ",占用天数:" + takeDay);
        BigDecimal result = null;
        if (baseAmount != null && factoringAmount != null && yearRate != null && powerRate != null && takeDay != null) {
            try {
                //绘制计算公式
                CalDag calDag = CalDag
                        .build()
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(baseAmount)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.SUBTRACT))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(factoringAmount)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(yearRate)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(powerRate)))
                        .addEdge(CalEdge.build().stEdgeOpr(CalOpr.MULTIPLY))
                        .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(takeDay)));
                if (yearRate.longValue() > 1) {
                    calDag.addEdge(CalEdge.build().stEdgeOpr(CalOpr.DIVIDE))
                            .addVertex(CalVertex.build().stVertexValue(CalBigDecimal.of(new BigDecimal(100))));
                }
                CalRequest request = CalRequest.build(calDag);
                CalResponse response = calculate.compute(request, 4, RoundingMode.HALF_UP, CalNegate.ORIGINAL);
                result = response.getResultNumber(BigDecimal.class);
            } catch (Exception e) {
                logger.error("保理罚息计算异常:", e);
            }
        }
        infoLoger("保理罚息计算结果:" + result);
        return result;
    }

    public static void main(String[] args) {
        CalFormula.calcInterest(new BigDecimal("33333"), new BigDecimal("0.0850"), new BigDecimal("360"), new BigDecimal("1"));
    }
}