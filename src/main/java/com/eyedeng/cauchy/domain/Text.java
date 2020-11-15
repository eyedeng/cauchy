package com.eyedeng.cauchy.domain;

import lombok.Data;

@Data
public class Text {
    private int x,y;
    private int stroke;
    private String text;
    // CSS属性,方便d3 select某给text
    private String id;
    public Text(int x, int y, int stroke, String text, String id) {
        this.x = x;
        this.y = y;
        this.stroke = stroke;
        this.text = text;
        this.id = id;
    }
}
