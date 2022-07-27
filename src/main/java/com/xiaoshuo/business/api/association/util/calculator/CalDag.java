package com.xiaoshuo.business.api.association.util.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计算关系图 (计算公式)
 *
 * @author liyuanyuan
 */
public class CalDag {

    private List<CalNode> nodes;

    private CalBigDecimal result;

    private AtomicInteger pointer = new AtomicInteger(0);

    /**
     * 添加关系开关
     */
    private volatile boolean addFlag = true;

    private LinkedHashMap<Integer, CalVertex> vertexLinkedHashMap = new LinkedHashMap<>();

    private LinkedHashMap<Integer, CalEdge> edgeLinkedHashMap = new LinkedHashMap<>();

    private CalDag() {
    }

    private CalDag(List<CalNode> nodes) {
        this.nodes = nodes;
    }

    public static CalDag build() {
        return new CalDag(new ArrayList<CalNode>());
    }

    public static CalDag build(List<CalNode> nodes) {
        return new CalDag(nodes);
    }

    private void off() {
        if (addFlag) {
            addFlag = false;
        }
    }

    private void on() {
        if (!addFlag) {
            addFlag = true;
        }
    }

    /**
     * 添加计算的顶点 （计算数）
     *
     * @param vertex
     * @return
     */
    public CalDag addVertex(CalVertex vertex) {
        if (pointer.get() == -1) {
            throw new RuntimeException("计算关系图已绘制结束,无法再添加顶点,更改计算图结构");
        }
        if (!addFlag) {
            throw new RuntimeException("添加计算数之前,先添加计算关系");
        }
        vertexLinkedHashMap.put(pointer.incrementAndGet(), vertex);
        off();
        return this;
    }

    /**
     * 添加计算的边 (计算关系)
     *
     * @param edge
     * @return
     */
    public CalDag addEdge(CalEdge edge) {
        if (pointer.get() == -1) {
            throw new RuntimeException("计算关系图已绘制结束,无法再添加边,更改计算图结构");
        }
        if (addFlag) {
            throw new RuntimeException("添加计算关系之前,先添加计算数");
        }
        edgeLinkedHashMap.put(pointer.get(), edge);
        on();
        return this;
    }

    /**
     * 添加计算的边 (计算关系)
     *
     * @param vertex
     * @param edge
     * @return
     */
    public CalDag addVertexAndEdge(CalVertex vertex, CalEdge edge) {
        this.addVertex(vertex);
        this.addEdge(edge);
        return this;
    }

    /**
     * 添加计算的节点
     *
     * @param node
     * @return
     */
    public CalDag addVertexAndEdge(CalNode node) {
        this.addVertexAndEdge(node.getVertex(), node.getEdge());
        return this;
    }

    /**
     * 绘制计算关系图 生成计算
     *
     * @return
     */
    public synchronized CalDag executeDag() {
        if (result == null) {
            //绘图
            nodes.clear();
            for (Map.Entry<Integer, CalVertex> entry : vertexLinkedHashMap.entrySet()) {
                CalVertex vertex = entry.getValue();
                CalEdge edge = edgeLinkedHashMap.get(entry.getKey());
                CalNode node = new CalNode();
                node.setVertex(vertex);
                node.setEdge(edge);
                nodes.add(node);
            }
            pointer.set(-1);
            //执行
            CalOpr preEdgeOpr;
            BigDecimal currVertexValue = null;
            CalOpr currEdgeOpr = null;
            BigDecimal tempResult = null;
            for (int i = 0; i < nodes.size(); i++) {
                CalNode currNode = nodes.get(i);
                CalVertex currVertex = currNode.getVertex();
                CalEdge currEdge = currNode.getEdge();
                preEdgeOpr = currEdgeOpr;
                if (currVertex != null) {
                    currVertexValue = currVertex.getVertexValue().getOrgValue();
                }
                if (currEdge != null) {
                    currEdgeOpr = currEdge.getEdgeOpr();
                }
                /* 设置初始值 */
                if (i == 0) {
                    tempResult = currVertexValue;
                } else {
                    /* 计算 */
                    if (CalOpr.ADD == preEdgeOpr) {
                        tempResult = tempResult.add(currVertexValue);
                    } else if (CalOpr.SUBTRACT == preEdgeOpr) {
                        tempResult = tempResult.subtract(currVertexValue);
                    } else if (CalOpr.MULTIPLY == preEdgeOpr) {
                        tempResult = tempResult.multiply(currVertexValue);
                    } else if (CalOpr.DIVIDE == preEdgeOpr) {
                        tempResult = tempResult.divide(currVertexValue, 12, RoundingMode.HALF_UP);
                    }
                }
            }
            this.result = CalBigDecimal.of(tempResult);
        }
        return this;
    }

    public CalBigDecimal getResult() {
        return result;
    }
}