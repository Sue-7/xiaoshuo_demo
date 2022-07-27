package com.xiaoshuo.business.api.association.util.calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * 计算器数
 *
 * @author liyuanyuan
 */
public class CalBigDecimal {

    public static final CalBigDecimalZero ZERO = new CalBigDecimalZero();

    public static final CalBigDecimalOne ONE = new CalBigDecimalOne();

    public static final CalBigDecimalTen TEN = new CalBigDecimalTen();

    /**
     * 计算数
     */
    private BigDecimal value;

    /**
     * 默认数据精度
     */
    protected static final int DEFAULT_NEW_SCALE = 0;

    /**
     * 数据精度
     */
    private int newScale;

    protected static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.UNNECESSARY;

    /**
     * 数据舍取方案
     */
    private RoundingMode roundingMode;

    /**
     * 默认正数
     */
    protected static final CalNegate DEFAULT_CAL_NEGATE = CalNegate.ORIGINAL;

    /**
     * 数据正负
     */
    private CalNegate calNegate;

    public static CalBigDecimal of(BigDecimal value) {
        return of(value, DEFAULT_NEW_SCALE);
    }

    public static CalBigDecimal of(BigDecimal value, int newScale) {
        return of(value, newScale, DEFAULT_ROUNDING_MODE, DEFAULT_CAL_NEGATE);
    }

    public CalBigDecimal of(BigDecimal value, int newScale, RoundingMode roundingMode) {
        return of(value, newScale, roundingMode, DEFAULT_CAL_NEGATE);
    }

    public static CalBigDecimal of(BigDecimal value, int newScale, RoundingMode roundingMode, CalNegate calNegate) {
        return new CalBigDecimal(value, newScale, roundingMode, calNegate);
    }

    public CalBigDecimal(BigDecimal value, int newScale, RoundingMode roundingMode, CalNegate calNegate) {
        if (value != null) {
            this.value = BigDecimal.valueOf(value.doubleValue());
        }
        this.newScale = newScale;
        this.roundingMode = roundingMode;
        this.calNegate = calNegate;
    }

    public int getNewScale() {
        return newScale;
    }

    public CalBigDecimal setNewScale(int newScale) {
        this.newScale = newScale;
        return this;
    }

    public <T extends Number> T getValue(Class<T> clazz) {
        T data = null;
        BigDecimal temp = this.value;
        if (temp != null) {
            temp = temp.setScale(this.newScale, this.roundingMode);
        }
        boolean positive = false;
        if (temp != null) {
            if (temp.compareTo(BigDecimal.ZERO) > 0) {
                positive = true;
            }

            if (this.calNegate != CalNegate.ORIGINAL) {
                boolean validBln = (positive && this.calNegate == CalNegate.NEGATIVE) || (!positive && this.calNegate == CalNegate.POSITIVE);
                if (validBln) {
                    temp = temp.negate();
                }
            }

            if (clazz != BigDecimal.class) {
                if (clazz == Integer.class) {
                    data = (T) Integer.valueOf(temp.intValue());
                } else if (clazz == Float.class) {
                    data = (T) Float.valueOf(temp.floatValue());
                } else if (clazz == Double.class) {
                    data = (T) Double.valueOf(temp.doubleValue());
                } else if (clazz == Long.class) {
                    data = (T) Long.valueOf(temp.longValue());
                } else if (clazz == Short.class) {
                    data = (T) Short.valueOf(temp.shortValue());
                } else if (clazz == BigInteger.class) {
                    data = (T) BigInteger.valueOf(temp.longValue());
                } else if (clazz == Byte.class) {
                    data = (T) Byte.valueOf(temp.byteValue());
                } else {
                    data = (T) temp;
                }
            } else {
                data = (T) temp;
            }
        }
        return data;
    }

    /**
     * 获得原始数据
     */
    public BigDecimal getOrgValue() {
        return this.value;
    }

    public CalBigDecimal setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public CalNegate getCalNegate() {
        return calNegate;
    }

    public CalBigDecimal setCalNegate(CalNegate calNegate) {
        this.calNegate = calNegate;
        return this;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    public CalBigDecimal setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        return this;
    }

    private static final class CalBigDecimalZero extends CalBigDecimal {
        private CalBigDecimalZero() {
            super(BigDecimal.ZERO, DEFAULT_NEW_SCALE, DEFAULT_ROUNDING_MODE, DEFAULT_CAL_NEGATE);
        }
    }

    private static final class CalBigDecimalOne extends CalBigDecimal {
        private CalBigDecimalOne() {
            super(BigDecimal.ONE, DEFAULT_NEW_SCALE, DEFAULT_ROUNDING_MODE, DEFAULT_CAL_NEGATE);
        }
    }

    private static final class CalBigDecimalTen extends CalBigDecimal {
        private CalBigDecimalTen() {
            super(BigDecimal.TEN, DEFAULT_NEW_SCALE, DEFAULT_ROUNDING_MODE, DEFAULT_CAL_NEGATE);
        }
    }
}