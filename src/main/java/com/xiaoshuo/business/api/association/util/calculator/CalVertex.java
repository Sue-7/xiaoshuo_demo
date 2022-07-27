package com.xiaoshuo.business.api.association.util.calculator;

/**
 * 计算的顶点(数)
 *
 * @author liyuanyuan
 */
public class CalVertex {

    private CalBigDecimal vertexValue;

    public CalBigDecimal getVertexValue() {
        return vertexValue;
    }

    private CalVertex() {
    }

    public static CalVertex build() {
        return new CalVertex();
    }

    public CalVertex stVertexValue(CalBigDecimal vertexValue) {
        this.vertexValue = vertexValue;
        return this;
    }
}