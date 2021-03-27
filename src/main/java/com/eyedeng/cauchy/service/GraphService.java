package com.eyedeng.cauchy.service;

import com.eyedeng.cauchy.constant.Color;
import com.eyedeng.cauchy.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {

    private Maze initMaze;
    // row * col
    private int row;
    private int col;

    private int [][] maze;      // 定义迷宫地图
    private int [][] visited;   // 定义访问过的位置
    private final int[][] direction = {            // {x,y}
            {0, -1}, {-1, 0}, {0, 1}, {1, 0}       // 左，上，右，下
    };

    private final int r = 10;
    private final int edge = 40;
    private final int staX = 100, staY = 20;
    private Node end;

//                {0,0,0,0,0},
//                {0,1,0,1,0},
//                {0,1,1,0,0},
//                {0,1,1,0,1},
//                {0,0,1,0,0}


    static class Node {        // 节点位置信息

        int x, y;
        int step;
        int preX, preY;

        Node(int x, int y, int preX, int preY, int step) {
            this.x = x;
            this.y = y;
            this.preX = preX;
            this.preY = preY;
            this.step = step;
        }
    }

    public MazeFrame create(List<List<Integer>> arrays) {
        row = arrays.size();
        col = arrays.get(0).size();
        maze = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = arrays.get(i).get(j);
            }
        }
//        int[][] arrays = new int[][]{
////                {0,0,0,0,0},
////                {0,1,0,1,0},
////                {0,1,1,0,0},
////                {0,1,1,0,1},
////                {0,0,1,0,0}
//                {0,0,0,0,0,1,0,1,0,1},
//                {0,1,0,1,0,1,0,0,1,0},
//                {0,1,1,0,0,0,0,1,1,0},
//                {0,1,1,0,1,1,0,1,1,1},
//                {0,0,1,0,0,0,0,0,0,0}
//        };


        MazeFrame mazeFrame = new MazeFrame();
        List<Maze> frames = new ArrayList<>();
        initMaze = new Maze();
        List<Rect> rectGroup = new ArrayList<>();

        Circle circle = new Circle(staX, staY + edge/2, r, Color.BLUE, Color.WHITE, "");
        Text text = new Text(staX, staY + edge * (maze.length + 1), Color.BLACK,"创建迷宫矩阵。黑：不可走；白：可走", "");
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                Rect rect = new Rect(staX + edge * j, staY + edge * i, edge, edge, Color.WHITE);
                if (maze[i][j] == 1) {
                    rect.setFill(Color.BLACK);
                }
                rectGroup.add(rect);
            }
        }
        initMaze.setCircle(circle);
        initMaze.setRectGroup(rectGroup);
        initMaze.setDescription(text);
        frames.add(initMaze);
        mazeFrame.setFrames(frames);
        System.out.println("---create---");
        System.out.println(mazeFrame);
        return mazeFrame;
    }

    // 根据坐标获取矩形在group中下标
    private int Idx(int x, int y) {
        return x * col + y;
    }


    private boolean bfsProcess(List<Maze> frames){

        Node node = new Node(0,0,-1,-1,0);
        Queue<Node> queue = new LinkedList<>();
        Deque<Node> stack = new LinkedList<>();
        visited[node.x][node.y] = 1;
        queue.offer(node);
        String pathStr = "";


        while(!queue.isEmpty()){
            Node head = queue.poll();
            stack.push(head);               //  将其压入堆栈，用于回溯路径

//            int idx = Idx(head.x, head.y);
//            ma.getRectGroup().get(idx).setFill(Color.YELLOW);  // 已访问
//            frames.add(ma);
            int idx = Idx(head.x, head.y);
            Maze ma = new Maze(frames.get(frames.size() - 1));
            Rect visRect = ma.getRectGroup().get(idx);
            visRect.setFill(Color.YELLOW);  // 正在访问

            pathStr = "队头<- " + printPath(queue) + " <-队尾 ";
            ma.getDescription().setText(pathStr + "\n 出队(" + (head.x+1) + "," + (head.y+1) + ")");
            frames.add(ma);

            for (int i = 0; i < 4; i++) {                //四个方向依次判断
                int x = head.x + direction[i][0];
                int y = head.y + direction[i][1];

                ma = new Maze(frames.get(frames.size() - 1));
                if (i == 0) {      // 左
                    ma.getCircle().setCx(visRect.getX());
                    ma.getCircle().setCy(visRect.getY() + edge / 2);
                    ma.getDescription().setText(pathStr + "\n 向左试探");
                } else if (i == 1) {  // 上
                    ma.getCircle().setCx(visRect.getX() + edge / 2);
                    ma.getCircle().setCy(visRect.getY());
                    ma.getDescription().setText(pathStr + "\n 向上试探");
                } else if (i == 2) {  // 右
                    ma.getCircle().setCx(visRect.getX() + edge);
                    ma.getCircle().setCy(visRect.getY() + edge / 2);
                    ma.getDescription().setText(pathStr + "\n 向右试探");
                } else {               // 下
                    ma.getCircle().setCx(visRect.getX() + edge / 2);
                    ma.getCircle().setCy(visRect.getY() + edge);
                    ma.getDescription().setText(pathStr + "\n 向下试探");
                }
                frames.add(ma);

                if (x == end.x && y == end.y && maze[x][y] == 0) {    // 定义终点的操作

                    ma = new Maze(frames.get(frames.size() - 1));
                    ma.getRectGroup().get(idx).setFill(Color.BLUE);  // 已访问

                    queue.offer(new Node(x, y, 0,0,0));
                    pathStr = "队头<- " + printPath(queue) + " <-队尾 ";
                    ma.getDescription().setText(pathStr + "\n 入队(" + (x+1) + "," + (y+1) + ")");
                    frames.add(ma);

                    Node top = stack.pop();
                    System.out.println("总步数：" + (top.step + 1));
                    System.out.println("逆向路线：");
                    System.out.println("(" + end.x + "," + end.y + ")");
                    System.out.println("(" + top.x + "," + top.y + ")");

                    ma = new Maze(frames.get(frames.size() - 1));
                    ma.getRectGroup().get(Idx(end.x, end.y)).setFill(Color.RED);
                    ma.getRectGroup().get(Idx(top.x, top.y)).setFill(Color.RED);

                    int preX = top.preX;
                    int preY = top.preY;
                    while (!stack.isEmpty()) {
                        top = stack.pop();
                        if (preX == top.x && preY == top.y) {
                            System.out.println("(" + preX + "," + preY + ")");

                            ma.getRectGroup().get(Idx(preX, preY)).setFill(Color.RED);

                            preX = top.preX;
                            preY = top.preY;
                        }
                    }
                    ma.getCircle().setFill(Color.RED);
                    ma.getDescription().setText(pathStr + "\n 找到了！");
                    frames.add(ma);

                    return true;
                }
                if (x >= 0 && x < row && y >= 0 && y < col && maze[x][y] == 0
                        && visited[x][y] == 0) {    //非边界，未撞墙，未曾到达过
                    Node newNode = new Node(x, y, head.x, head.y, head.step + 1);
                    visited[x][y] = 1;
                    queue.offer(newNode);
                    pathStr = "队头<- " + printPath(queue) + " <-队尾 ";

                    ma = new Maze(frames.get(frames.size() - 1));
                    ma.getRectGroup().get(Idx(x, y)).setFill(Color.GREEN);    // 入队，将访问
                    ma.getDescription().setText(pathStr + "\n 入队(" + (x+1) + "," + (y+1) + ")");
                    frames.add(ma);
                }
            }

            ma = new Maze(frames.get(frames.size() - 1));
            ma.getRectGroup().get(idx).setFill(Color.BLUE);  // 已访问
            frames.add(ma);

        }
        return false;
    }

    private String printPath(Queue<Node> queue) {
        StringBuffer sb = new StringBuffer();
        Queue<Node> q = new ArrayDeque<>(queue);
        if (q.isEmpty()) {
            return "空";
        }
        while (!q.isEmpty()) {
            Node node = q.poll();
            sb.append("(" + (node.x+1) + "," + (node.y+1) + ") ");
        }
        return sb.toString();
    }

    private String printPath(Deque<Node> path) {
        StringBuffer sb = new StringBuffer();
        Deque<Node> p = new ArrayDeque<>(path);
        if (p.isEmpty()) {
            return "空";
        }
        while (!p.isEmpty()) {
            Node node = p.pop();
            sb.append("(" + (node.x+1) + "," + (node.y+1) + ") ");
        }
        return sb.toString();
    }

    boolean dfsProcess(List<Maze> frames, Node node, Deque<Node> path) {
        System.out.println(node.x + "," + node.y);
        path.push(node);
        String pathStr = "栈顶<- " + printPath(path) + " _]栈底 ";
        int idx = Idx(node.x, node.y);
        Maze ma = new Maze(frames.get(frames.size() - 1));

        if (node.x == row - 1 && node.y == col - 1) {
            ma.getRectGroup().get(idx).setFill(Color.RED);
            ma.getDescription().setText(pathStr + "\n 找到了！");
            frames.add(ma);

            return true;
        }
        visited[node.x][node.y] = 1;

        Rect visRect = ma.getRectGroup().get(idx);
        visRect.setFill(Color.BLUE);  // 正在访问
        ma.getDescription().setText(pathStr + "\n 入栈(" + node.x + "," + node.y + ")");
        frames.add(ma);

        for (int i = 0; i < 4; i++) {
            int x = node.x + direction[i][0];
            int y = node.y + direction[i][1];

            ma = new Maze(frames.get(frames.size() - 1));
            if (i == 0) {      // 左
                ma.getCircle().setCx(visRect.getX());
                ma.getCircle().setCy(visRect.getY() + edge / 2);
                ma.getDescription().setText(pathStr + "\n 向左试探");
            } else if (i == 1) {  // 上
                ma.getCircle().setCx(visRect.getX() + edge / 2);
                ma.getCircle().setCy(visRect.getY());
                ma.getDescription().setText(pathStr + "\n 向上试探");
            } else if (i == 2) {  // 右
                ma.getCircle().setCx(visRect.getX() + edge);
                ma.getCircle().setCy(visRect.getY() + edge / 2);
                ma.getDescription().setText(pathStr + "\n 向右试探");
            } else {               // 下
                ma.getCircle().setCx(visRect.getX() + edge / 2);
                ma.getCircle().setCy(visRect.getY() + edge);
                ma.getDescription().setText(pathStr + "\n 向下试探");
            }
            frames.add(ma);

            if (x >= 0 && x < row && y >= 0 && y < col && maze[x][y] == 0
                    && visited[x][y] == 0) {

                if (dfsProcess(frames, new Node(x, y, node.x, node.y, node.step + 1), path)) {
                    return true;
                }

                ma = new Maze(frames.get(frames.size() - 1));
                ma.getRectGroup().get(Idx(x, y)).setFill(Color.GREEN);  // 回溯
                path.pop();
                pathStr = "栈顶<- " + printPath(path) + " _]栈底 ";
                ma.getDescription().setText(pathStr + "\n 退栈：(" + x + "," + y + ")");
                frames.add(ma);

            }

        }
        return false;
    }

    public MazeFrame bfs() {
        MazeFrame mazeFrame = new MazeFrame();
        List<Maze> frames = new ArrayList<>();
        frames.add(initMaze);

        visited = new int[row][col];
        end = new Node(row - 1, col - 1, 0, 0, 0);
        if (!bfsProcess(frames)) {
            System.out.println("Not found.");
        }
        mazeFrame.setFrames(frames);
        return mazeFrame;
    }

    public MazeFrame dfs() {
        MazeFrame mazeFrame = new MazeFrame();
        List<Maze> frames = new ArrayList<>();
        frames.add(initMaze);

        visited = new int[row][col];
        end = new Node(row - 1, col - 1, 0, 0, 0);
        Deque<Node> path = new ArrayDeque<>();
        if (!dfsProcess(frames, new Node(0,0, 0, 0, 0), path))
            System.out.println("Not found.");
        mazeFrame.setFrames(frames);
        return mazeFrame;
    }

    public MazeFrame search(int x, int y) {
        System.out.println(x + "--" + y);
        MazeFrame mazeFrame = new MazeFrame();
        List<Maze> frames = new ArrayList<>();
        frames.add(initMaze);

        visited = new int[row][col];
        end = new Node(x, y, 0, 0, 0);
        if (!bfsProcess(frames)) {
            System.out.println("Not found.");
        }
        mazeFrame.setFrames(frames);
        return mazeFrame;
    }

    public static void main(String[] args) {


    }

}