package com.eyedeng.cauchy.domain;

import lombok.Data;

import java.util.List;
@Data
public class Tree {
    private List<Line> edgeGroup;
    private List<Circle> vertexGroup;
    private List<Text> vertexTextGroup;

}
