package com.eyedeng.cauchy.domain;

import lombok.Data;

@Data
public class Circle {
    private int cx;
    private int cy;
    private int r;
    private int fill;
    private int stroke;

    public Circle(int cx, int cy, int r, int fill, int stroke) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
        this.fill = fill;
        this.stroke = stroke;
    }
}
