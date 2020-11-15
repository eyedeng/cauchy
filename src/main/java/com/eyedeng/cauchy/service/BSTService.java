package com.eyedeng.cauchy.service;

import com.eyedeng.cauchy.constant.Color;
import com.eyedeng.cauchy.constant.SVG;
import com.eyedeng.cauchy.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
@Service
public class BSTService {

    public static final int FullBTHei = 6;
    private Node root = null;

    // TODO %d 不能作id, line's stroke don't work
    String circleIdTemp = "C%d";
    String lineIdTemp = "%sC%d";
    String textIdTemp = "TC%d";
    int ID = 1;     // node's id number

    // private class Node extends Circle ?
    private static class Node{
        int data;
        Circle circle;
        Node left;
        Node right;

        Node(int d) {
            data = d;
            left = null;
            right = null;
        }
    }

    public static void main(String[] args) {
        Integer[] array = {84, 24, 57, 87, 13, 9, 56};
        BSTService tree = new BSTService();
        tree.create();

        tree.inorder();

//        List<Circle> circles = new ArrayList<>();
//        Circle c1 = new Circle(1,2,3,4,5);
//        circles.add(c1);
//        c1 = new Circle(3,4,3,4,5);
//        circles.add(c1);
//        for (Circle c :
//                circles) {
//            System.out.println(c.getCx());  // 1 \n 3
//        }

    }

    public TreeFrame create() {

        //array = new Integer[]{84, 24, 57, 87, 13, 9, 56};
//        BSTService bst = new BSTService();
//        for (Integer integer : array) {
//            bst.add(integer);
//        }

        TreeFrame treeFrame = new TreeFrame();
        List<Tree> frames = new ArrayList<>();
        Tree tree = new Tree();
        List<Line> edgeGroup = new ArrayList<>();
        List<Circle> vertexGroup = new ArrayList<>();
        List<Text> vertexTextGroup = new ArrayList<>();

        int r = 15;
        // 孩子节点与其父节点高度差
        int heiDif = 80;
        // 孩子节点与其父节点水平偏移量
        int offset = (int) (r * Math.pow(2, FullBTHei - 2));
        int cx = SVG.WIDTH / 2;
        int cy = heiDif;
        int fill = Color.WHITE;
        int stroke = Color.BLACK;
        
        // 层序遍历建初始帧

        Circle node = new Circle(cx, cy, r, fill, stroke, String.format(circleIdTemp, ID));
        Text data = new Text(cx, cy, stroke, String.valueOf(root.data), String.format(textIdTemp, ID));
        ID++;
        // 让node携带更多信息
        root.circle = node;

        vertexGroup.add(node);
        vertexTextGroup.add(data);

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        int count = 1;  // 每层入队总数
        while (!queue.isEmpty()) {
            int c = count;
            level++;
            count = 0;
            while (c-- > 0) {
                Node parent = queue.poll();
//                System.out.print(parent.data + " ");

                if (parent.left != null) {
                    cx = parent.circle.getCx() - offset;
                    setGraph(edgeGroup, vertexGroup, vertexTextGroup, heiDif, cx, cy + heiDif * level,
                            r, fill, stroke, level, parent, true);
                    queue.add(parent.left);
                    count++;
                }

                if (parent.right != null) {
                    cx = parent.circle.getCx() + offset;
                    setGraph(edgeGroup, vertexGroup, vertexTextGroup, heiDif, cx, cy + heiDif * level,
                            r, fill, stroke, level, parent, false);
                    queue.add(parent.right);
                    count++;
                }
            }
            offset /= 2;
        }

        tree.setEdgeGroup(edgeGroup);
        tree.setVertexGroup(vertexGroup);
        tree.setVertexTextGroup(vertexTextGroup);
//        System.out.println(tree);
        frames.add(tree);
        treeFrame.setFrames(frames);
        return treeFrame;
    }

    private void setGraph(List<Line> edgeGroup, List<Circle> vertexGroup, List<Text> vertexTextGroup,
                          int heiDif, int cx, int cy, int r, int fill, int stroke, int level, Node parent, boolean L) {
        Circle node;
        Text data;
        Line edge;
        node = new Circle(cx, cy, r, fill, stroke, String.format(circleIdTemp, ID));
        data = new Text(cx, cy, stroke, String.valueOf(L ? parent.left.data : parent.right.data),
                String.format(textIdTemp, ID));
        edge = new Line(parent.circle.getCx(), parent.circle.getCy(), cx, cy, stroke,
                String.format(lineIdTemp, parent.circle.getId(), ID));
        ID++;
        if (L)
            parent.left.circle = node;
        else
            parent.right.circle = node;
        vertexGroup.add(node);
        vertexTextGroup.add(data);
        edgeGroup.add(edge);
    }

    /**
     * d3: select(id) 属性变换过渡
     * @param node
     * @param changes
     */
    private void inOrder(Node node, List<GraphChange> changes) {
        if (node == null) {
            return;
        }
        // 当前遍历到visitC节点
        Circle visitC = node.circle;
        visitC.setStroke(Color.YELLOW);
        visitC.setFill(Color.WHITE);

        // node与circle绑定,易找原circle,text与circle在group里的index(记录在id)相同,从而找原text
//        int idx = Integer.parseInt(visitC.getId()) - 1;  // id从1始
//        Text visitT = vertexTextGroup.get(idx);
//        visitT.setStroke(Color.YELLOW);

        String textId = "T" + node.circle.getId();
        Text visitT = new Text(0, 0, Color.YELLOW, "", textId);
        changes.add(new GraphChange(visitC, visitT, null));
//        System.out.println(changes);
        if (node.left != null) {
            // 当前遍历到visitL边
            // 通过相同id直接新建line,为需改变的属性(stroke)赋值
            String lindId = node.circle.getId() +  node.left.circle.getId();
            Line visitL = new Line(0, 0, 0, 0, Color.YELLOW, lindId);
            changes.add(new GraphChange(null, null, visitL));

            inOrder(node.left, changes);
        }

        // 访问此节点
        // 必须new新circle, visitedC = node.circle 后 visitedC.setXX 会覆盖之前加入changes的visitC(两者都指向node.circle)
        Circle visitedC = new Circle(0, 0, 0, Color.YELLOW, 0, node.circle.getId());
        textId = "T" + node.circle.getId();
        Text visitedT = new Text(0, 0, Color.RED, "", textId);
        changes.add(new GraphChange(visitedC, visitedT, null));

        if (node.right != null) {
            String lindId = node.circle.getId() +  node.right.circle.getId();
            Line line = new Line(0, 0, 0, 0, Color.YELLOW, lindId);
            changes.add(new GraphChange(null, null, line));

            inOrder(node.right, changes);
        }
    }

    public GraphChanges inorder() {
        GraphChanges graphChanges = new GraphChanges();
        List<GraphChange> changes = new ArrayList<>();
        inOrder(this.root, changes);
        graphChanges.setChanges(changes);
//        System.out.println(changes);
        return graphChanges;
    }


    /**
     * Recursive method to delete a data if present in BST.
     *
     * @param node the current node to search for data
     * @param data the value to be deleted
     * @return Node the updated value of root parameter after delete operation
     */
    private Node delete(Node node, int data) {
        if (node == null) {
            System.out.println("No such data present in BST.");
        } else if (node.data > data) {
            node.left = delete(node.left, data);
        } else if (node.data < data) {
            node.right = delete(node.right, data);
        } else {
            if (node.right == null && node.left == null) { // If it is leaf node
                node = null;
            } else if (node.left == null) { // If only right node is present
                Node temp = node.right;
                node.right = null;
                node = temp;
            } else if (node.right == null) { // Only left node is present
                Node temp = node.left;
                node.left = null;
                node = temp;
            } else { // both child are present
                Node temp = node.right;
                // Find leftmost child of right subtree
                while (temp.left != null) {
                    temp = temp.left;
                }
                node.data = temp.data;
                node.right = delete(node.right, temp.data);
            }
        }
        return node;
    }

    /**
     * Recursive insertion of value in BST.
     *
     * @param node to check if the data can be inserted in current node or its subtree
     * @param data the value to be inserted
     * @return the modified value of the root parameter after insertion
     */
    private Node insert(Node node, int data) {
        if (node == null) {
            node = new Node(data);
        } else if (node.data > data) {
            node.left = insert(node.left, data);
        } else if (node.data < data) {
            node.right = insert(node.right, data);
        }
        return node;
    }

    /**
     * Recursively print Preorder traversal of the BST
     *
     * @param node the root node
     */
    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        if (node.left != null) {
            preOrder(node.left);
        }
        if (node.right != null) {
            preOrder(node.right);
        }
    }

    /**
     * Recursively print Postorder travesal of BST.
     *
     * @param node the root node
     */
    private void postOrder(Node node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            postOrder(node.left);
        }
        if (node.right != null) {
            postOrder(node.right);
        }
        System.out.print(node.data + " ");
    }

    /**
     * Recursively print Inorder traversal of BST.
     *
     * @param node the root node
     */


    /**
     * Serach recursively if the given value is present in BST or not.
     *
     * @param node the current node to check
     * @param data the value to be checked
     * @return boolean if data is present or not
     */
    private boolean search(Node node, int data) {
        if (node == null) {
            return false;
        } else if (node.data == data) {
            return true;
        } else if (node.data > data) {
            return search(node.left, data);
        } else {
            return search(node.right, data);
        }
    }

    /**
     * add in BST. if the value is not already present it is inserted or else no change takes place.
     *
     * @param data the value to be inserted
     */
    public void add(int data) {
        this.root = insert(this.root, data);
    }

    /**
     * If data is present in BST delete it else do nothing.
     *
     * @param data the value to be removed
     */
    public void remove(int data) {
        this.root = delete(this.root, data);
    }



    /** To call postorder traversal on tree */
    public void postorder() {
        System.out.println("Postorder traversal of this tree is:");
        postOrder(this.root);
        System.out.println(); // for next li
    }

    /** To call preorder traversal on tree. */
    public void preorder() {
        System.out.println("Preorder traversal of this tree is:");
        preOrder(this.root);
        System.out.println(); // for next li
    }

    /**
     * To check if given value is present in tree or not.
     *
     * @param data the data to be found for
     */
    public boolean find(int data) {
        if (search(this.root, data)) {
            System.out.println(data + " is present in given BST.");
            return true;
        }
        System.out.println(data + " not found.");
        Node nn;
        return false;
    }


}