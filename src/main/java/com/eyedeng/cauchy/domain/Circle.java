package com.eyedeng.cauchy.domain;

import lombok.Data;

@Data
public class Circle {
    private int cx;
    private int cy;
    private int r;
    private int fill;
    private int stroke;
    // CSS属性,方便d3 select某个circle
    private String id;

    public Circle(int cx, int cy, int r, int fill, int stroke, String id) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
        this.fill = fill;
        this.stroke = stroke;
        this.id = id;
    }
}
