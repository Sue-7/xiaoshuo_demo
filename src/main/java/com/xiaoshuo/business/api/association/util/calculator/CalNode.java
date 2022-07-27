package com.xiaoshuo.business.api.association.util.calculator;

/**
 * 计算节点
 *
 * @author liyuanyuan
 */
public class CalNode {

    private CalVertex vertex;

    private CalEdge edge;

    public CalVertex getVertex() {
        return vertex;
    }

    public void setVertex(CalVertex vertex) {
        this.vertex = vertex;
    }

    public CalEdge getEdge() {
        return edge;
    }

    public void setEdge(CalEdge edge) {
        this.edge = edge;
    }
}
