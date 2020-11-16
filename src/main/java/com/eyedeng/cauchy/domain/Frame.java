package com.eyedeng.cauchy.domain;

import com.eyedeng.cauchy.constant.Color;

import java.util.Arrays;

/**
 * 尝试类，ugly
 */
public class Frame {

    public Node[] array;

    public class Node{
        public int value;
        public int color;

        public Node(int value, int color) {
            this.value = value;
            this.color = color;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", color=" + color +
                    '}';
        }
    }

    public Frame(Integer[] a) {
        int len = a.length;
        this.array = new Node[len];
        for (int i=0; i<len; i++){
            this.array[i] = new Node(a[i], Color.BLUE);
        }
    }

    public Frame(Frame old) {
        int len = old.array.length;
        this.array = new Node[len];
        for (int i=0; i<len; i++){
            this.array[i] = new Node(old.array[i].value, old.array[i].color);
        }
    }

    @Override
    public String toString() {
        return "Frame{" +
                "array=" + Arrays.toString(array) +
                '}';
    }
}
