package com.eyedeng.cauchy.domain;

import lombok.Data;

@Data
public class Line {
    private int x1,y1,x2,y2;
    private int stroke;
    // CSS属性,方便d3 select某条line
    private String id;
    public Line(int x1, int y1, int x2, int y2, int stroke, String id) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.stroke = stroke;
        this.id = id;
    }
}
