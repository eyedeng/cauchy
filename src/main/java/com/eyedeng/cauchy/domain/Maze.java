package com.eyedeng.cauchy.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Maze {
    private Circle circle;
    private List<Rect> rectGroup;
    private Text description;

    public Maze(){}

    // 深拷贝!!!
    public Maze(Maze maze) {
        this.circle = new Circle(maze.getCircle().getCx(), maze.getCircle().getCy(),
                maze.getCircle().getR(), maze.getCircle().getFill(), maze.getCircle().getStroke(), maze.getCircle().getId());
        this.rectGroup = new ArrayList<>();
        this.description = new Text(0,0,0,maze.getDescription().getText(), "");
        for (Rect r :
                maze.getRectGroup()) {
            rectGroup.add(new Rect(r.getX(), r.getY(), r.getWidth(), r.getHeight(), r.getFill()));
        }
    }
}
