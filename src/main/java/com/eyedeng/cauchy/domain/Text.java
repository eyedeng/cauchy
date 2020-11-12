package com.eyedeng.cauchy.domain;

import lombok.Data;

@Data
public class Text {
    private int x,y;
    private String text;

    public Text(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }
}
