package com.eyedeng.cauchy.service;

import com.eyedeng.cauchy.constant.Color;
import com.eyedeng.cauchy.domain.Circle;
import com.eyedeng.cauchy.domain.Maze;
import com.eyedeng.cauchy.domain.MazeFrame;
import com.eyedeng.cauchy.domain.Rect;
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

//    public GraphService(){
//        maze=new int[][]{
//                {0,0,0,0,0},
//                {0,1,0,1,0},
//                {0,1,1,0,0},
//                {0,1,1,0,1},
//                {0,0,1,0,0}
//        };
//        N = 5;
//        visitedSign=new int [N][N];
//    }

    class Node {        // 节点位置信息

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
        visited = new int[row][col];

        MazeFrame mazeFrame = new MazeFrame();
        List<Maze> frames = new ArrayList<>();
        initMaze = new Maze();
        List<Rect> rectGroup = new ArrayList<>();

        Circle circle = new Circle(staX, staY + edge/2, r, Color.BLUE, Color.WHITE, "");
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
            frames.add(ma);

            for (int i = 0; i < 4; i++) {                //四个方向依次判断
                int x = head.x + direction[i][0];
                int y = head.y + direction[i][1];

                ma = new Maze(frames.get(frames.size() - 1));
                if (i == 0) {      // 左
                    ma.getCircle().setCx(visRect.getX());
                    ma.getCircle().setCy(visRect.getY() + edge / 2);
                } else if (i == 1) {  // 上
                    ma.getCircle().setCx(visRect.getX() + edge / 2);
                    ma.getCircle().setCy(visRect.getY());
                } else if (i == 2) {  // 右
                    ma.getCircle().setCx(visRect.getX() + edge);
                    ma.getCircle().setCy(visRect.getY() + edge / 2);
                } else {               // 下
                    ma.getCircle().setCx(visRect.getX() + edge / 2);
                    ma.getCircle().setCy(visRect.getY() + edge);
                }
                frames.add(ma);

                if (x == row - 1 && y == col - 1 && maze[x][y] == 0
                        && visited[x][y] == 0) {    // 定义终点的操作

                    ma = new Maze(frames.get(frames.size() - 1));
                    ma.getRectGroup().get(idx).setFill(Color.BLUE);  // 已访问
                    frames.add(ma);

                    Node top = stack.pop();
                    System.out.println("总步数：" + (top.step + 1));
                    System.out.println("逆向路线：");
                    System.out.println("(" + (row - 1) + "," + (col - 1) + ")");
                    System.out.println("(" + top.x + "," + top.y + ")");

                    ma = new Maze(frames.get(frames.size() - 1));
                    ma.getRectGroup().get(Idx(row - 1, col - 1)).setFill(Color.RED);
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
                    frames.add(ma);

                    return true;
                }
                if (x >= 0 && x < row && y >= 0 && y < col && maze[x][y] == 0
                        && visited[x][y] == 0) {    //非边界，未撞墙，未曾到达过
                    Node newNode = new Node(x, y, head.x, head.y, head.step + 1);
                    visited[x][y] = 1;
                    queue.offer(newNode);

                    ma = new Maze(frames.get(frames.size() - 1));
                    ma.getRectGroup().get(Idx(x, y)).setFill(Color.GREEN);    // 入队，将访问
                    frames.add(ma);
                }
            }

            ma = new Maze(frames.get(frames.size() - 1));
            ma.getRectGroup().get(idx).setFill(Color.BLUE);  // 已访问
            frames.add(ma);

        }
        return false;
    }


    public MazeFrame bfs() {
        MazeFrame mazeFrame = new MazeFrame();
        List<Maze> frames = new ArrayList<>();
        frames.add(initMaze);
        if (!bfsProcess(frames)) {
            System.out.println("找不到");
        }
        mazeFrame.setFrames(frames);
        return mazeFrame;
    }

    public MazeFrame dfs() {
        MazeFrame mazeFrame = new MazeFrame();

        return mazeFrame;
    }

    public static void main(String[] args) {

        GraphService service = new GraphService();
        if (!service.bfsProcess(null)) {
            System.out.println("找不到");
        }
    }

}