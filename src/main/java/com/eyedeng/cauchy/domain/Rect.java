package com.eyedeng.cauchy.domain;

import lombok.Data;

@Data
public class Rect {
    private int x;
    private int y;
    private int width;
    private int height;
    private int fill;

    public Rect(int x, int y, int width, int height, int fill) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fill = fill;
    }
}
