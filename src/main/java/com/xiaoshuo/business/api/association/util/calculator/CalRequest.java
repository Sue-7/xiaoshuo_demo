package com.xiaoshuo.business.api.association.util.calculator;

/**
 * 计算请求对象
 *
 * @author liyuanyuan
 */
public class CalRequest {

    /**
     * 计算关系图
     */
    private CalDag dag;

    public static CalRequest build(CalDag dag) {
        return new CalRequest(dag);
    }

    private CalRequest(CalDag dag) {
        this.dag = dag;
    }

    public CalDag getDag() {
        return dag;
    }

    public void setDag(CalDag dag) {
        this.dag = dag;
    }
}
