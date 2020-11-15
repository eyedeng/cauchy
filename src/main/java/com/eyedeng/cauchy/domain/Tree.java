package com.eyedeng.cauchy.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Tree {
    private List<Line> edgeGroup;
    private List<Circle> vertexGroup;
    private List<Text> vertexTextGroup;

    public Tree(){}

    // 深拷贝!!!
    public Tree(Tree tree) {
        this.edgeGroup = new ArrayList<>();
        this.vertexGroup = new ArrayList<>();
        this.vertexTextGroup = new ArrayList<>();
        for (Line l :
                tree.getEdgeGroup()) {
            edgeGroup.add(new Line(l.getX1(), l.getY1(), l.getX2(), l.getY2(), l.getStroke(), l.getId()));
        }
        for (Circle c :
                tree.getVertexGroup()) {
            vertexGroup.add(new Circle(c.getCx(), c.getCy(), c.getR(), c.getFill(), c.getStroke(), c.getId()));
        }
        for (Text t :
                tree.getVertexTextGroup()) {
            vertexTextGroup.add(new Text(t.getX(), t.getY(), t.getStroke(), t.getText(), t.getId()));
        }
    }
}
