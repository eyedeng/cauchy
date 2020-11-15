package com.eyedeng.cauchy.domain;

import lombok.Data;

/**
 * 图源改变的记录
 */
@Data
public class GraphChange {
    private Circle circle;
    private Text text;
    private Line line;

    public GraphChange(Circle circle, Text text, Line line) {
        this.circle = circle;
        this.text = text;
        this.line = line;
    }
}
