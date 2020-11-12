package com.eyedeng.cauchy.service;

import com.eyedeng.cauchy.domain.Tree;
import com.eyedeng.cauchy.domain.TreeFrame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeService<T extends Comparable<T>> {

    private Node root = null;
    private int nodeCount = 0;

    private class Node {
        T data;
        Node left, right;

        public Node(Node left, Node right, T elem) {
            this.data = elem;
            this.left = left;
            this.right = right;
        }
    }

    public TreeFrame create(Integer[] array) {
        TreeFrame treeFrame = new TreeFrame();
        List<Tree> frames = new ArrayList<>();
        Tree tree = new Tree();

        treeFrame.setFrames(frames);
        return treeFrame;
    }
    public boolean add(T elem) {
        if (contains(elem)) {
            return false;
        } else {
            root = add(root, elem);
            nodeCount++;
            return true;
        }
    }

    private Node add(Node node, T elem) {

        if (node == null) {
            node = new Node(null, null, elem);

        } else {
            if (elem.compareTo(node.data) < 0) {
                node.left = add(node.left, elem);
            } else {
                node.right = add(node.right, elem);
            }
        }

        return node;
    }

    // returns true is the element exists in the tree
    public boolean contains(T elem) {
        return contains(root, elem);
    }

    // private recursive method to find an element in the tree
    private boolean contains(Node node, T elem) {

        // Base case: reached bottom, value not found
        if (node == null) return false;

        int cmp = elem.compareTo(node.data);

        // Dig into the left subtree because the value we're
        // looking for is smaller than the current value
        if (cmp < 0) return contains(node.left, elem);

            // Dig into the right subtree because the value we're
            // looking for is greater than the current value
        else if (cmp > 0) return contains(node.right, elem);

            // We found the value we were looking for
        else return true;
    }

    public static void main(String[] args) {
//        List<Integer> array = new ArrayList<>(Arrays.asList(84, 24, 57, 87, 13, 9, 56));
        Integer[] array = {84, 24, 57, 87, 13, 9, 56};
        TreeService<Integer> treeService = new TreeService<>();
        for (int i = 0; i < array.length; i++) {
            treeService.add(array[i]);
        }

    }
}
