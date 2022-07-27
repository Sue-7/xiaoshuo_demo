package com.xiaoshuo.business.api.association.util.calculator;

/**
 * 计算边(关系) 操作符
 *
 * @author liyuanyuan
 */
public class CalEdge {

    private CalOpr edgeOpr;

    private CalEdge() {
    }

    public static CalEdge build() {
        return new CalEdge();
    }

    public CalOpr getEdgeOpr() {
        return edgeOpr;
    }

    public CalEdge stEdgeOpr(CalOpr edgeOpr) {
        this.edgeOpr = edgeOpr;
        return this;
    }
}
