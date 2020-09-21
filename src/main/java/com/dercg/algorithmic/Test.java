package com.dercg.algorithmic;

import java.util.LinkedList;
import java.util.Queue;

public class Test {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();

        queue.add(3);
        queue.add(5);
        queue.add(6);
        queue.add(2);
        queue.add(1);

        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
