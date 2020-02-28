package com.dercg.algorithmic;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class BinarySearch {
    public static int search(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        int count=0;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = mid + 1;
            } else {
                count++;
                System.out.println("count:"+count);
                return mid;
            }
            count++;
        }
        System.out.println("count:"+count);
        return -1;
    }

    public static void main(String[] args) {
        int[] intlist = new int[1000];
        for (int i = intlist.length - 1; i >= 0; i--) {
            intlist[i] = StdRandom.uniform(1000);
        }
        System.out.println(Arrays.toString(intlist));
        Arrays.sort(intlist);
        System.out.println(Arrays.toString(intlist));
        System.out.println(search(30,intlist));
    }
}
