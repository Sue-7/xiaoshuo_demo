package com.xiaoshuo.business.api.association.util.calculator;

/**
 * 数据正负
 *
 * @author liyuanyuan
 */
public enum CalNegate {

    /**
     * 正
     */
    POSITIVE(1, "正"),

    /**
     * 负
     */
    NEGATIVE(-1, "负"),

    /**
     * 原始
     */
    ORIGINAL(0, "原始");

    CalNegate(int factor, String desc) {
        this.factor = factor;
        this.name = desc;
    }

    private int factor;

    private String name;

    public static CalNegate of(int factor) {
        for (CalNegate negate : CalNegate.values()) {
            if (negate.sameCodeAs(factor)) {
                return negate;
            }
        }
        return CalNegate.POSITIVE;
    }

    public Integer getCode() {
        return factor;
    }

    public String getName() {
        return name;
    }

    public boolean sameCodeAs(int code) {
        return this.factor == code;
    }
}
