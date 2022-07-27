package com.xiaoshuo.business.api.association.util.calculator;

/**
 * 计算操作类型
 *
 * @author liyuanyuan
 */
public enum CalOpr {

    /**
     * 加
     */
    ADD("+", "加"),

    /**
     * 减
     */
    SUBTRACT("-", "减"),

    /**
     * 乘
     */
    MULTIPLY("*", "乘"),

    /**
     * 除
     */
    DIVIDE("/", "除"),

    ;

    CalOpr(String code, String desc) {
        this.code = code;
        this.name = desc;
    }

    private String code;

    private String name;

    public static CalOpr of(String code) {
        for (CalOpr opr : CalOpr.values()) {
            if (opr.sameCodeAs(code)) {
                return opr;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean sameCodeAs(String code) {
        return this.code.equals(code);
    }
}
