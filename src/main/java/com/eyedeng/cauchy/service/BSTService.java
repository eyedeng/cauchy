package com.eyedeng.cauchy.service;

import com.eyedeng.cauchy.constant.Color;
import com.eyedeng.cauchy.constant.SVG;
import com.eyedeng.cauchy.domain.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BSTService {

    public static final int FullBTHei = 6;
    /** only data member is root of BST */
    private Node root = null;



    /** main function for tests */
    public static void main(String[] args) {
//        Integer[] array = {84, 24, 57, 87, 13, 9, 56};
//        BSTService tree = new BSTService();
//        for (int i = 0; i < array.length; i++) {
//            tree.add(array[i]);
//        }
//        tree.inorder();

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

    public TreeFrame create(Integer[] array) {

        array = new Integer[]{84, 24, 57, 87, 13, 9, 56};
        BSTService bst = new BSTService();
        for (int i = 0; i < array.length; i++) {
            bst.add(array[i]);
        }

        TreeFrame treeFrame = new TreeFrame();
        List<Tree> frames = new ArrayList<>();
        Tree tree = new Tree();
        List<Line> edgeGroup = new ArrayList<>(array.length-1);
        List<Circle> vertexGroup = new ArrayList<>(array.length);
        List<Text> vertexTextGroup = new ArrayList<>(array.length);

        int heiDif = 80;
        // 叶节点与其父节点水平偏移量
        int minOff = 25;
        int offset = (int) (minOff * Math.pow(2, FullBTHei - 2));
        int cx = SVG.WIDTH;
        int cy = heiDif;
        int r = 18;
        int fill = Color.WHITE;
        int stroke = Color.BLACK;
        
        // 层序遍历建初始帧

        Circle node = new Circle(cx, cy, r, fill, stroke);
        Text data = new Text(cx, cy, String.valueOf(bst.root.data));
        Line edge;
        // 让node携带更多信息
        bst.root.circleX = cx;
        bst.root.circleY = cy;
        vertexGroup.add(node);
        vertexTextGroup.add(data);

        Queue<Node> queue = new LinkedList<>();
        queue.add(bst.root);
        int level = 0;
        int count = 1;  // 每层入队总数
        while (!queue.isEmpty()) {
            int c = count;
            level++;
            count = 0;
            while (c-- > 0) {
                Node tempNode = queue.poll();
                System.out.print(tempNode.data + " ");

                if (tempNode.left != null) {
                    node = new Circle(cx - offset / level, cy + heiDif * level, r, fill, stroke);
                    data = new Text(cx - offset / level, cy + heiDif * level, String.valueOf(tempNode.left.data));

                    vertexGroup.add(node);
                    vertexTextGroup.add(data);
                    queue.add(tempNode.left);
                    count++;
                }

                if (tempNode.right != null) {
                    node = new Circle(cx + offset / level, cy + heiDif * level, r, fill, stroke);
                    data = new Text(cx + offset / level, cy + heiDif * level, String.valueOf(tempNode.right.data));
                    vertexGroup.add(node);
                    vertexTextGroup.add(data);
                    queue.add(tempNode.right);
                    count++;
                }
            }
        }


        treeFrame.setFrames(frames);
        return treeFrame;
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
    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            inOrder(node.left);
        }
        System.out.print(node.data + " ");
        if (node.right != null) {
            inOrder(node.right);
        }
    }

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

    /** To call inorder traversal on tree */
    public void inorder() {
        System.out.println("Inorder traversal of this tree is:");
        inOrder(this.root);
        System.out.println(); // for next line
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

    /** The Node class used for building binary search tree */
    private static class Node {
        int data;
        Node left;
        Node right;

        int circleX,circleY;

        /** Constructor with data as parameter */
        Node(int d) {
            data = d;
            left = null;
            right = null;
            circleX = 0;
            circleY = 0;
        }
    }
}